package org.scut.mychart.controller;

import org.scut.mychart.service.ChartExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller  
@RequestMapping(value = "/charts/expense",produces = "application/json;charset=UTF-8")
public class ChartExpenseController {
    @Autowired
    private ChartExpenseService chartExpenseService;
      
    @RequestMapping("/chartExpenseBar")
    @ResponseBody
    public Map<String, Object> getChartExpenseBar(){
        Map<String, Object> result = chartExpenseService.getChartExpenseBarOption();
        return result;
    }

    @RequestMapping("/chartExpenseLine")
    @ResponseBody
    public Map<String, Object> getChartExpenseLine(){
        Map<String, Object> result = chartExpenseService.getChartExpenseLineOption();
        return result;
    }

    @RequestMapping("/chartExpenseGauge")
    @ResponseBody
    public Map<String, Object> getChartExpenseGauge(){
        Map<String, Object> result = chartExpenseService.getChartExpenseGaugeOption();
        return result;
    }

    @RequestMapping("/ExpenseageRange")
    @ResponseBody
    public Map<String, Object> getChartExpenseFunnel(){
        Map<String, Object> result = chartExpenseService.getChartExpenseFunnelOption();
        return result;
    }

    @RequestMapping("/hospitalTotal")
    @ResponseBody
    public Map<String, Object> getHospitalTotal(){
        Map<String, Object> result = chartExpenseService.getHospitalTotal();
        return result;
    }

    @RequestMapping("/departmentTotal")
    @ResponseBody
    public Map<String, Object> getDepartmentTotal(){
        Map<String, Object> result = chartExpenseService.getDepartmentTotal();
        return result;
    }
}   

