package com.slimani.rest_reporting.dao;


import com.slimani.rest_reporting.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


    @Query("SELECT r FROM Report r WHERE r.title = :title")
    Report findReportByTitle(@Param("title") String title);

}
