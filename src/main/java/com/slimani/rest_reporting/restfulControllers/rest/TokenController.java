package com.slimani.rest_reporting.restfulControllers.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.slimani.rest_reporting.restfulControllers.UserController;
import com.slimani.rest_reporting.dao.UserRepository;
import com.slimani.rest_reporting.security.JwtGenerator;
import com.slimani.rest_reporting.security.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/token")
public class TokenController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bcr;

    private JwtGenerator jwtGenerator;


    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }



    @PostMapping
    public ObjectNode generate(@RequestBody final JwtUser jwtUser) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        UserController uc = new UserController();
        if(uc.login(jwtUser,userRepository,bcr)){
            node.put("response","Token "+jwtGenerator.generate(jwtUser));
            return node;
        }else{

            node.put("response","login error !");
            return node;
        }


    }



}
