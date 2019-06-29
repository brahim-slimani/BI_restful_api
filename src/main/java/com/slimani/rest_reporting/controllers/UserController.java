package com.slimani.rest_reporting.controllers;

import com.slimani.rest_reporting.dao.RoleRepository;
import com.slimani.rest_reporting.dao.UserRepository;
import com.slimani.rest_reporting.entities.Role;
import com.slimani.rest_reporting.entities.User;
import com.slimani.rest_reporting.param.aesEncryption.AESCrypt;
import com.slimani.rest_reporting.security.pojo.JwtUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserController {


    public Boolean login(JwtUser jwtuser, UserRepository userRepository, BCryptPasswordEncoder bcr) throws Exception {

        AESCrypt aesCrypt = new AESCrypt();
/*

        try{
            if(bcr.matches(
                    aesCrypt.decrypt(jwtuser.getPassword()),
                    userRepository.findUserByName(jwtuser.getUserName()).getPassword())
            ){
                return true;
            }else return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }*/

        if(aesCrypt.decrypt(jwtuser.getPassword()).equals(aesCrypt.decrypt(userRepository.findUserByName(jwtuser.getUserName()).getPassword()))){
            return true;
        }else{
            return false;
        }







    }

    public void register(JwtUser user, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bcr){
        List<Role> roles = roleRepository.findRolesByName(user.getRole());
        //userRepository.save(new User(user.getUserName(), bcr.encode(user.getPassword()),roles));

        userRepository.save(new User(user.getUserName(), user.getPassword(),roles));



    }


    public Boolean hasRoleAdmin(User user){
        Boolean b = false;
        for(Role r:user.getRoles()){
            if(r.getRole().equals("ROLE_ADMIN")) b = true;
        }

        return b;

    }


}
