package com.slimani.rest_reporting.dao;


import com.slimani.rest_reporting.entities.Dashboard;
import com.slimani.rest_reporting.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @Query("SELECT d FROM Dashboard d WHERE d.title = :title")
    Report findDashboardByTitle(@Param("title") String title);
}
