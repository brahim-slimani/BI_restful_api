package com.slimani.rest_reporting.dao;


import com.slimani.rest_reporting.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :name")
    List<User> findUsersByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.username = :name")
    User findUserByName(@Param("name") String name);


}
