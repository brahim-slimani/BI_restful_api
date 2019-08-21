package com.slimani.rest_reporting.webControllers;

import com.slimani.rest_reporting.dao.RoleRepository;
import com.slimani.rest_reporting.dao.UserRepository;
import com.slimani.rest_reporting.entities.Role;
import com.slimani.rest_reporting.entities.User;
import com.slimani.rest_reporting.param.SendingMail;
import com.slimani.rest_reporting.param.aesEncryption.AESCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    User userEdit = new User();

    boolean b = false;
    boolean path = false;

    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("user", new User());

        path = false;
        return "index";
    }

    @RequestMapping(value = "/errLogin")
    public String err(Model model){
        model.addAttribute("user", new User());

        path = false;
        return "errLogin";
    }


    @RequestMapping(value = "/admin")
    public String login(Model model, User user, String access){

        AESCrypt aesCrypt = new AESCrypt();
        model.addAttribute("users", userRepository.findUsers());


        try{
            if(user.getUsername().equals(userRepository.findUserByName(user.getUsername()).getUsername())
                    && user.getPassword().equals(aesCrypt.decrypt(userRepository.findUserByName(user.getUsername()).getPassword()))){

                model.addAttribute("loginLog","success");
                return "users";


            }else{

                model.addAttribute("loginLog","failed");
                return "redirect:errLogin";
            }

        }catch (Exception e){

            model.addAttribute("loginLog","failed");

            return "redirect:errLogin";
        }



    }




    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(Model model, User newUser, HttpServletRequest request) throws Exception {

        model.addAttribute("user", newUser);

        AESCrypt aesCrypt = new AESCrypt();
        List<Role> roles = roleRepository.findRolesByName(newUser.getRole());
        userRepository.save(new User(newUser.getUsername(), aesCrypt.encrypt(newUser.getPassword()), roles, newUser.getRole(), newUser.getMail(), newUser.getContact()));

        SendingMail sm = new SendingMail();

        sm.send(newUser.getMail(), "Acount Sonelgaz BI Tool", "Username : "+newUser.getUsername()+ "   Password : "+newUser.getPassword());

        model.addAttribute("users", userRepository.findUsers());


        return "users";


    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersList(Model model){
        model.addAttribute("users", userRepository.findUsers());
        model.addAttribute("user", new User());


        if(path == true){
            return "users";
        }else{
            return "errLogin";
        }


    }

    @RequestMapping(value = "/return", method = RequestMethod.GET)
    public String cancel(Model model){
        model.addAttribute("users", userRepository.findUsers());
        model.addAttribute("user", new User());


        if(path == true){
            return "users";
        }else{
            return "errLogin";
        }


    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId, Model model, HttpServletRequest request) {

        userRepository.delete(userRepository.getOne(userId));

        model.addAttribute("user", new User());

        model.addAttribute("users", userRepository.findUsers());

        path = true;
        return "redirect:../users";

    }

    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") Long userId, Model model) {

        userEdit = userRepository.getOne(userId);
        model.addAttribute("userupdate", userRepository.getOne(userId));


        System.out.println(userEdit.getMail());

        path = true;

        return "update";

    }





    @PostMapping(value = "/saveEdit")
    public String saveEdit(Model model, User user) throws Exception {


        AESCrypt aesCrypt = new AESCrypt();

        userRepository.mergeUserById(user.getUsername(), aesCrypt.encrypt(user.getPassword()),
                user.getMail(), user.getContact(), userEdit.getId());


        model.addAttribute("user", new User());
        model.addAttribute("users", userRepository.findUsers());

        path = true;

        return "users";


    }

}
