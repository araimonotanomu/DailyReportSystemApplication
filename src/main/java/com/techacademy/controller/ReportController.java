package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.service.ReportService;

@Controller
@RequestMapping("reports")
public class ReportController {
    
    private final ReportService reportService;
    
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    // 日報一覧画面
    @GetMapping
    public String list(Model model) {
        
        model.addAttribute("reportList", reportService.findAll());
        model.addAttribute("ListSize", reportService.findAll().size());
        
        return "reports/list";
    }
    
    // 日報詳細画面
    @GetMapping(value = "/{id}/")
    public String detail(@PathVariable Integer id, Model model) {
        
        model.addAttribute("report", reportService.findById(id));
        return "reports/detail";
    }
    
    // 日報更新画面
    @GetMapping(value="{id}/update")
    public String edit(@PathVariable("id") Integer id, Model model) {
        
        if ( id != null ) {
            model.addAttribute("report", reportService.findById(id));
        }
        
        return "reports/update";
    }
    

}
