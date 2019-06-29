package com.slimani.rest_reporting;

import com.slimani.rest_reporting.dao.RoleRepository;
import com.slimani.rest_reporting.dao.UserRepository;
import com.slimani.rest_reporting.entities.Role;
import com.slimani.rest_reporting.entities.User;
import com.slimani.rest_reporting.param.aesEncryption.AESCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableJpaAuditing
@SpringBootApplication
public class RestReportingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestReportingApplication.class, args);
    }

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    AESCrypt aesCrypt = new AESCrypt();


    @Override
    public void run(String... args) throws Exception {


        try{

            if(roleRepository.findRolesByName("ROLE_ADMIN").size()<1){
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            if (roleRepository.findRolesByName("ROLE_MM").size()<1) {
                roleRepository.save(new Role("ROLE_MM"));
            }
            if (roleRepository.findRolesByName("ROLE_SD").size()<1) {
                roleRepository.save(new Role("ROLE_SD"));
            }
            if (roleRepository.findRolesByName("ROLE_DD").size()<1) {
                roleRepository.save(new Role("ROLE_DD"));
            }
            if (roleRepository.findRolesByName("ROLE_AG").size()<1) {
                roleRepository.save(new Role("ROLE_AG"));
            }

            if (userRepository.findUsersByName("admin").size()<1){
                //userRepository.save(new User("admin",bCryptPasswordEncoder.encode("elit2019"),
                  //      roleRepository.findRolesByName("ROLE_ADMIN")));
                userRepository.save(new User("admin",aesCrypt.encrypt("elit2019"),
                      roleRepository.findRolesByName("ROLE_ADMIN")));
            }

            System.out.println(aesCrypt.encrypt("elit2019"));



        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
