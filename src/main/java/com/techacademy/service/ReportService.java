package com.techacademy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    
    // 日報一覧表示処理
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

}
