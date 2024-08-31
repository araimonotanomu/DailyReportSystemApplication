package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "reports")
@SQLRestriction("delete_flg = false")
public class Report {
    
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotEmpty
    private String id;
    
    // 日付
    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;
    
    // タイトル
    @Column(columnDefinition="VARCHAR(100)", nullable = false)
    private String title;
    
    // 内容
    @Column(columnDefinition="LONGTEXT", nullable = false)
    private String content;
    
    // 社員番号
    @ManyToOne
    @JoinColumn(name = "employee_code", columnDefinition="VARCHAR(10)", nullable = false, referencedColumnName = "code")
    private String employeeCode;
    
 // 削除フラグ
    @Column(columnDefinition="TINYINT", nullable = false)
    private boolean deleteFlg; 
    
 // 登録日時
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 更新日時
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
}
