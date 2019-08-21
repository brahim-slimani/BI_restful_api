package com.slimani.rest_reporting.restfulControllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.slimani.rest_reporting.dao.*;
import com.slimani.rest_reporting.entities.Dashboard;
import com.slimani.rest_reporting.entities.Report;
import com.slimani.rest_reporting.entities.User;
import com.slimani.rest_reporting.restfulControllers.pojoRest.UserPojo;
import com.slimani.rest_reporting.security.pojo.JwtUser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserServiceController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);


    @Autowired
    UserRepository userRepository;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping(value = "/forgotPassword")
    public ObjectNode updateUserFirst(@RequestBody UserPojo user) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        User u = userRepository.findUserByEmail(user.getMail());

        if(userRepository.findUserByEmail(user.getMail()).getId()>0){
            u.setPassword(user.getPassword());

            userRepository.save(u);
            node.put("response", "Password updated successfully !");
            return node;

        }else{
            node.put("response", "Operation failed, this email doesn't match to any user !");
            return node;
        }


        
    }

    @GetMapping(value = "/checkEmail")
    public ObjectNode getEmail(@RequestParam String param) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        try{

            if (userRepository.existsById(userRepository.findUserByEmail(param).getId())) {
                node.put("response", param);
            } else {
                node.put("response", "email doesn't exist");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return node;

    }
}
