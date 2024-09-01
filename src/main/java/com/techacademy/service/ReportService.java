package com.techacademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techacademy.constants.ErrorKinds;
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
    
    // 日報削除
    public ErrorKinds delete(Integer id) {
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
    
    // ある従業員の日報のリストを取得
    public List<Report> findByEmployee(Iterable<Integer> id) {
        return reportRepository.findAllById(id);
    }
}
