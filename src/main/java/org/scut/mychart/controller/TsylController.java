package org.scut.mychart.controller;
import java.util.Map;


import org.scut.mychart.service.TsylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Created by linqidi on 2016/12/9.
 */
@Controller
@RequestMapping(value="/charts/tsyl", produces="application/json;charset=UTF-8")
public class TsylController {
    @Autowired
    private TsylService tsylService;

    @RequestMapping("/countByGender")
    @ResponseBody
    public Map<String, Object> getCountByGender() {
        Map<String, Object> result = tsylService.getCountByGender();
        return result;
    }

    @RequestMapping("/countByGenderLine")
    @ResponseBody
    public Map<String, Object> getCountByGenderLine() {
        Map<String, Object> result = tsylService.getCountByGenderLine();
        return result;
    }

    @RequestMapping("/areaCoverage")
    @ResponseBody
    public Map<String, Object> getAreaCoverage() {
        Map<String, Object> result = tsylService.getAreaCoverage();
        return result;
    }

    @RequestMapping("/ageRange")
    @ResponseBody
    public Map<String, Object> getAgeRange() {
        Map<String, Object> result = tsylService.getAgeRange();
        return result;
    }

    @RequestMapping("/hospitalTotal")
    @ResponseBody
    public Map<String, Object> getHospitalTotal() {
        Map<String, Object> result = tsylService.getHospitalTotal();
        return result;

    }
    @RequestMapping("/departmentTotal")
    @ResponseBody
    public Map<String, Object> getDepartmentTotal(){
        Map<String, Object> result = tsylService.getDepartmentTotal();
        return result;
    }
}