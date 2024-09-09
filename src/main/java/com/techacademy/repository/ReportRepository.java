package com.techacademy.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    
    List<Report> findByEmployee(Employee employee);
    
    @Query(value = "select report_date from reports where employee_code = :code", nativeQuery=true)
    List<String> findReportDateByCode(String code);
    
}