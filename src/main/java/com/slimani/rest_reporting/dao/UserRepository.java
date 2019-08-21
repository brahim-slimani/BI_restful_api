package com.slimani.rest_reporting.dao;


import com.slimani.rest_reporting.entities.Role;
import com.slimani.rest_reporting.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :name")
    List<User> findUsersByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.username = :name")
    User findUserByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.username <> 'admin'")
    List<User> findUsers();

    @Transactional
    @Modifying
    @Query("update User u set u.username = :name, u.password = :pass,  " +
            "u.mail = :mail, u.contact = :contact where u.id = :userId")
    void mergeUserById(@Param("name") String username,
                       @Param("pass") String password,
                       @Param("mail") String mail,
                       @Param("contact") String contact,
                       @Param("userId") Long userId);


    @Query("SELECT u.mail From User u WHERE u.username = :username")
    String getMail(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.mail = :mail")
    User findUserByEmail(@Param("mail") String mail);

    @Query("SELECT distinct(u.mail) FROM User u WHERE u.mail is not null ")
    List<String> findAllEmails();


}
