package com.slimani.rest_reporting.dao;


import com.slimani.rest_reporting.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.role = :name")
    List<Role> findRolesByName(@Param("name") String name);

    @Query("SELECT r FROM Role r WHERE r.role = :name")
    Role findRoleByName(@Param("name") String name);

}
