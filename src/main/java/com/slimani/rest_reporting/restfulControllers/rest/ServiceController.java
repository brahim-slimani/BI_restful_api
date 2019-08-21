package com.slimani.rest_reporting.restfulControllers.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slimani.rest_reporting.entities.Dashboard;
import com.slimani.rest_reporting.restfulControllers.UserController;
import com.slimani.rest_reporting.restfulControllers.pojoRest.DashboardPojo;
import com.slimani.rest_reporting.restfulControllers.pojoRest.ReportPojo;
import com.slimani.rest_reporting.dao.*;
import com.slimani.rest_reporting.entities.Report;
import com.slimani.rest_reporting.entities.User;
import com.slimani.rest_reporting.param.aesEncryption.AESCrypt;
import com.slimani.rest_reporting.restfulControllers.pojoRest.UserPojo;
import com.slimani.rest_reporting.security.pojo.JwtUser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/rest")
public class ServiceController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    DashboardRepository dashboardRepository;

    AdhocImpl adhocRepository = new AdhocImpl();


    //register new user
    @PostMapping(value = "olap/register")
    public ObjectNode register(@RequestBody JwtUser user){
        UserController uc = new UserController();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails ud = (UserDetails) auth.getPrincipal();

        if(uc.hasRoleAdmin(userRepository.findUserByName(ud.getUserName()))){*/
            try{

                uc.register(user, userRepository, roleRepository, bCryptPasswordEncoder);


                node.put("response","User registred successfully !");

                return node;
            }catch (Exception e){
                node.put("response","Registration failed, this username already exists !");
                return node;
            }
        //}else node.put("response","Access forbiden !"); return node;



    }

    //save new report
    @PostMapping(value = "olap/saveReport")
    public ObjectNode saveReport(@RequestBody ReportPojo reportPojo){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        Report report = new Report(reportPojo.getTitle(),
                reportPojo.getContext(), reportPojo.getType(), reportPojo.getColumns(), reportPojo.getRows(),
                userRepository.findUserByName(reportPojo.getUsername()));


        try{

            reportRepository.save(report);

            node.put("response","Report created successfully !");

            return node;
        }catch (Exception e){
            node.put("response","Operation failed, REST issue !");
            return node;
        }


    }

    //save dash
    @PostMapping(value = "olap/saveDashboard")
    public ObjectNode saveDashboard(@RequestBody DashboardPojo dashboardPojo) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        JSONObject reportJSONObject = new JSONObject("{ reports: " + dashboardPojo.getReportsString() + "}");
        JSONArray reportJSONArray = reportJSONObject.getJSONArray("reports");

        List<Report> reports = new ArrayList<>();

        JSONObject reportObject;
        for (int i = 0; i < reportJSONArray.length(); i++) {
            reportObject = reportJSONArray.getJSONObject(i);
            Report resultReport = reportRepository.findReportByTitleAndContext(reportObject.getString("title"), reportObject.getString("context"));
            reports.add(resultReport);
        }

        Dashboard dashboard = new Dashboard();
        dashboard.setTitle(dashboardPojo.getTitle());
        dashboard.setUserD(userRepository.findUserByName(dashboardPojo.getUsername()));
        dashboard.setReports(reports);
        dashboardRepository.save(dashboard);
        try {
            List<Dashboard> dashboards = new ArrayList<>();
            dashboards.add(dashboard);
            Iterator<Report> iterator = reports.iterator();
            while (iterator.hasNext()){
                iterator.next().setDashboards(dashboards);
                reportRepository.save(iterator.next());
            }


            node.put("response", "Dashboard created successfully !");
            return node;
        } catch (Exception e) {
            node.put("response", "Operation failed, REST issue !");
            System.out.println("catch" + e);
            return node;
        }

    }


    // delete dashboard
    @DeleteMapping(value = "olap/deleteDashboard")
    public ObjectNode deleteDashboard(@RequestParam String title) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        try {
            dashboardRepository.delete(dashboardRepository.findDashboardByTitle(title));

            node.put("response", "Dashboard deleted successfully !");
            return node;
        } catch (Exception e) {

            node.put("response", "Operation failed, REST issue!");
            return node;
        }
    }

    //get all dashboards
    @GetMapping(value = "olap/allDashboards")
    public List<DashboardPojo> getAllDashboards() {
        List<Dashboard> dashboards = dashboardRepository.findAll();
        List<DashboardPojo> dashboardPojos = new ArrayList<>();
        for (Dashboard dashboard : dashboards) {
            dashboardPojos.add(new DashboardPojo(dashboard.getId(), dashboard.getTitle(), dashboard.getUserD().getUsername()));
        }
        return dashboardPojos;
    }


    //get dashboards of user
    @GetMapping(value = "olap/dashboards")
    public List<DashboardPojo> getDashboards(@RequestParam String username) {

        List<Dashboard> dashboards = new ArrayList<>(userRepository.findUserByName(username).getDashboards());
        System.out.println("dashboards size: " + dashboards.size());

        List<DashboardPojo> customDashboards = new ArrayList<>();

        for (Dashboard dashboard : dashboards) {
            customDashboards.add(new DashboardPojo(dashboard.getId(), dashboard.getTitle()));
        }


        return customDashboards;
    }


    //get reports of dashboards
    @GetMapping(value = "olap/dashboardReports/{id}")
    public List<ReportPojo> getReportsByDashboard(@PathVariable("id") Long id) {

        Dashboard dashboard = dashboardRepository.findDashboardById(id);
        List<ReportPojo> reportsList = new ArrayList<>();
        List<Report> reports;
        reports = dashboard.getReports();
        for (Report report : reports) {
            reportsList.add(new ReportPojo(report.getTitle(), report.getContext(), report.getType(), report.getColumns(), report.getRows(), report.getUserR().getUsername()));
        }
        return reportsList;
    }

    //save report in MV
    @PostMapping(value = "olap/createMV")
    public ObjectNode createMV(@RequestParam String queryMV){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        try{

            adhocRepository.adhocQuery(queryMV);
            node.put("response","Operation successful !");

            return node;
        }catch (Exception e){
            node.put("response","Operation failed, REST issue !");
            return node;
        }

    }

    //refresh MV
    @PostMapping(value = "olap/syncMV")
    public ObjectNode syncView(@RequestParam String view){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        try{

            adhocRepository.adhocQuery("refresh materialized view "+view);
            node.put("response","Operation successful !");

            return node;
        }catch (Exception e){
            node.put("response","Operation failed, REST issue !");
            return node;
        }

    }



    //delete user
    @DeleteMapping(value = "olap/deleteUser")
    public ObjectNode deleteUser(@RequestParam String username){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();


        try{

            userRepository.delete(userRepository.findUserByName(username));

            node.put("response","User deleted successfully !");

            return node;
        }catch (Exception e){
            node.put("response","Operation failed, REST issue !");
            return node;
        }


    }


    //delete report
    @DeleteMapping(value = "olap/deleteReport")
    public ObjectNode deleteReport(@RequestParam String title){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();


        try{

            reportRepository.delete(reportRepository.findReportByTitle(title));

            node.put("response","Report deleted successfully !");

            return node;
        }catch (Exception e){
            node.put("response","Operation failed, REST issue !");
            return node;
        }


    }

    //get all reports
    @GetMapping(value = "olap/allReports")
    public List<Report> getAllReports() {

        return reportRepository.findAll();
    }


    //get user's reports
    @GetMapping(value = "olap/reports")
    public List<Report> getReprts(@RequestParam String username) {

        List<Report> reports = new ArrayList<>(userRepository.findUserByName(username).getReports());

        List<Report> customReports = new ArrayList<>();

        int i = 0;
        while (i<reports.size()){

            customReports.add(new Report(reports.get(i).getTitle(),reports.get(i).getContext(), reports.get(i).getType(), reports.get(i).getColumns(), reports.get(i).getRows()));
            i++;
        }


        return customReports;
    }


    //get users and their roles
    @GetMapping(value = "olap/usersRoles")
    public List<String> getUsersRoles() {

        int i = 0;
        List<User> listUsers = userRepository.findAll();
        List<String> adhocList = new ArrayList<>();



        while(i<listUsers.size()){
            //adhocList.add(new String(listUsers.get(i).getUsername()+"  ("+listUsers.get(i).getRoles().get(0).getRole())+ ")");
            adhocList.add(new String(listUsers.get(i).getUsername()));
            i++;
        }

        return adhocList;
    }

    //get users
    @GetMapping(value = "olap/users")
    public List<String> getUsers() {

        int i = 0;
        List<User> listUsers = userRepository.findAll();
        List<String> list = new ArrayList<>();



        while(i<listUsers.size()){
            if(!listUsers.get(i).getUsername().equals("admin")){
                list.add(new String(listUsers.get(i).getUsername()));
            }

            i++;
        }

        return list;
    }

    //get role of user
    @GetMapping(value = "olap/role")
    public ObjectNode getRole(@RequestParam String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put("response",userRepository.findUserByName(username).getRoles().get(0).getRole());

        return node;
    }

    //get detail of user
    @GetMapping(value = "olap/detailUser")
    public ObjectNode getDetail(@RequestParam String username) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        AESCrypt aesCrypt = new AESCrypt();

        node.put("username",  username);
        node.put("role", userRepository.findUserByName(username).getRoles().get(0).getRole());
        node.put("password", aesCrypt.decrypt(userRepository.findUserByName(username).getPassword()));

        return node;
    }


    //adhoc olap qurey
    @GetMapping(value = "/olap")
    public ArrayNode getData(@RequestParam String query){

        return adhocRepository.adhocQuery(query);
    }

    //get facts tables (olap cubes)
    @GetMapping(value = "olap/fact")
    public ArrayNode getCubes(){

        String query = "select table_name as fact from information_schema.tables where table_name like 'fais%';";
        return adhocRepository.adhocQuery(query);
    }

    //get measures from fact table
    @GetMapping(value = "olap/measures")
    public ArrayNode getMeasures(@RequestParam String factTable){

        String query = "SELECT COLUMN_NAME AS measure FROM information_schema.COLUMNS " +
                "WHERE TABLE_NAME = '"+factTable+"' and COLUMN_NAME not like 'id%' ;";
        return adhocRepository.adhocQuery(query);
    }

    //get dimensions tables
    @GetMapping(value = "olap/dimensions")
    public ArrayNode getDimensions(@RequestParam String factTable){

        String query = "SELECT distinct ccu.table_name AS dimension " +
                "FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu " +
                "ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu " +
                "ON ccu.constraint_name = tc.constraint_name where tc.table_name = '"+factTable+"';";
        return adhocRepository.adhocQuery(query);
    }

    //get dimension's columns
    @GetMapping(value = "olap/dimensionColumns")
    public ArrayNode getDimensionColumns(@RequestParam String dimension){

        String query = "SELECT COLUMN_NAME AS column FROM information_schema.COLUMNS " +
                "WHERE TABLE_NAME = '"+dimension+"' and COLUMN_NAME not like 'id%' ;";
        return adhocRepository.adhocQuery(query);
    }


    //get filter rows
    @GetMapping(value = "olap/columnRow")
    public ArrayNode getFilterColumns(@RequestParam String table, @RequestParam String column ){

        String query = "SELECT DISTINCT "+column+" as column FROM "+table+" ;" ;

        return adhocRepository.adhocQuery(query);
    }


    // get email by username
    @GetMapping(value = "olap/checkEmail")
    public ObjectNode getEmail(@RequestParam String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        String mail = userRepository.getMail(username);
        if(mail != null){
            node.put("email", mail);
        }
        else{
            node.put("email", "email doesn't exist");
        }

        return node;

    }


    // update user password after the first login
    @PostMapping(value = "olap/updateUser")
    public ObjectNode updateUserPassword(@RequestBody UserPojo user) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        User u = userRepository.findUserByName(user.getUsername());
        u.setPassword(user.getPassword());
        u.setMail(user.getMail());

        try {
            userRepository.save(u);
            node.put("response", "Password updated successfully !");
            return node;
        } catch (Exception e) {
            node.put("response", "Operation failed, REST issue !");
            System.out.println("catch" + e);
            return node;
        }

    }




    // find all emails
    @GetMapping(value = "olap/usersEmails")
    public ArrayNode findAllEmails() {

        ObjectMapper mapper = new ObjectMapper();
        List<String> emailsList = userRepository.findAllEmails();
        ArrayNode arrayNode = mapper.valueToTree(emailsList);
        return arrayNode;
    }



}
