package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    
    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public ReportService(ReportRepository reportRepository, PasswordEncoder passwordEncoder) {
        this.reportRepository = reportRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // 日報保存
    @Transactional
    public Report save(Report report,  UserDetail userDetail) {
        
        reportRepository.save(report);
        return report;  
    }
    
    // 日報更新
    @Transactional
    public Report update(Report report, Integer id, UserDetail userDetail) {
        
        LocalDateTime now = LocalDateTime.now();
        Report beforeReport = findById(id);
        report.setCreatedAt(beforeReport.getCreatedAt());
        report.setUpdatedAt(now);
        report.setEmployee(userDetail.getEmployee());
        
        reportRepository.save(report);
        
        return report;
    }
    
    // 日報削除
    public String delete(Integer id) {
        
        reportRepository.deleteById(id);
        return null;
    }
    
    // 日報一覧表示処理
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    // 1件を表示
    public Report findById(Integer id) {
        // findByIdで検索
        Optional<Report> option = reportRepository.findById(id);
        Report report = option.orElse(null);
        return report;
    }
    
    // ログイン中の従業員の日報のリストを取得
    public List<Report> findAllByEmployee(Employee employee, UserDetail userDetail) {
        Employee loggedInEmployee = userDetail.getEmployee();
        List<Report> ReportList = reportRepository.findByEmployee(loggedInEmployee);
        return ReportList;
    }
    
    // 他の従業員の日報のリストを取得
    public List<Report> findAllByCode(Employee employee){
        List<Report> reportList = reportRepository.findByEmployee(employee);
        return reportList;
    }
    
    // ログイン中の従業員の日報の日付のリストを取得
    public List<String> findReportDateByEmployee(String code, Employee employee, UserDetail userDetail) {
        String code1 = userDetail.getEmployee().getCode();
        return reportRepository.findReportDateByCode(code1);
        
    }
    
}
