package com.techacademy.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {
    
    private final ReportService reportService;
    
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    // 日報一覧画面
    @Transactional
    @GetMapping
    public String list(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        
        Employee employee = userDetail.getEmployee();
        String role = employee.getRole().toString();
        
        if ( role.equals("ADMIN")) {
            model.addAttribute("reportList", reportService.findAll());
            model.addAttribute("ListSize", reportService.findAll().size());
            
        } else {
            model.addAttribute("reportList", reportService.findAllByEmployee(employee, userDetail));
            model.addAttribute("ListSize", reportService.findAllByEmployee(employee, userDetail).size());
        }
        
        return "reports/list";
    }
    
    // 日報詳細画面
    @GetMapping(value = "/{id}/")
    public String detail(@PathVariable Integer id, Model model) {
        
        model.addAttribute("report", reportService.findById(id));
        return "reports/detail";
    }
    
    // 日報新規登録画面
    @GetMapping(value = "/add")
    public String create(@ModelAttribute Report report, @AuthenticationPrincipal UserDetail userDetail, Model model) {
        
        model.addAttribute("name", userDetail.getEmployee().getName());
        return "reports/new";
    }
    
    // 日報新規登録処理
    @PostMapping(value = "/add")
    public String add(@Validated Report report, BindingResult res, Integer id, String code, @AuthenticationPrincipal UserDetail userDetail, Model model) {
        
        Employee employee = userDetail.getEmployee();
        report.setEmployee(employee);
                
        report.setDeleteFlg(false);
        
        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);
        
        if (res.hasErrors()) {
            return create(report, userDetail, model);
            
         // 登録済の日報の日付との重複チェック    
        } else {
            List<String> AllReportDate = reportService.findReportDateByEmployee(code, employee, userDetail);
            
            if (AllReportDate.contains(report.getReportDate().toString())) {
                model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DATECHECK_ERROR), ErrorMessage.getErrorValue(ErrorKinds.DATECHECK_ERROR));
                return create(report, userDetail, model);
            }
            
            reportService.save(report, userDetail);
        }
        
        return "redirect:/reports";
    }
    
    // 日報更新画面
    @GetMapping(value = "{id}/update")
    public String edit(@PathVariable("id") Integer id, Model model) {
        
        if ( id != null ) {
            model.addAttribute("report", reportService.findById(id));
        }
        
        return "reports/update";
    }
    
    // 日報更新処理
    @PostMapping("{id}/update")
    public String update(@Validated Report report, BindingResult res, @PathVariable Integer id, String code, Employee employee, @AuthenticationPrincipal UserDetail userDetail, Model model) {
        
    // 入力チェック
        if (res.hasErrors()) {
            return edit(null, model);
        } 
        
    // 日付重複チェック
        // 変更前と同じなら可
        Report beforeReport = reportService.findById(id);
        if (report.getReportDate().equals(beforeReport.getReportDate())) {     
            reportService.update(report, id, userDetail);
            
        } else {
        
            List<String> AllReportDate = reportService.findReportDateByEmployee(code, employee, userDetail);
            if (AllReportDate.contains(report.getReportDate().toString())) {
                model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DATECHECK_ERROR), ErrorMessage.getErrorValue(ErrorKinds.DATECHECK_ERROR));
                return create(report, userDetail, model);
        } else {
            reportService.update(report, id, userDetail);
        }
        }
        
        return "redirect:/reports";
    }
    
    // 日報削除処理
    @PostMapping(value = "/{id}/delete")
    public String delete(@PathVariable Integer id, Model model) {
        
        reportService.delete(id);
        
        return "redirect:/reports";
    }

}
