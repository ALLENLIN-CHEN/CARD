package org.scut.mychart.controller;

import java.util.Map;
import org.scut.mychart.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/charts/clinic",produces="application/json;charset=UTF-8")
public class ClinicController {
	@Autowired
	private ClinicService clinicService;
	
	@RequestMapping("/countByGender")
    @ResponseBody
    public Map<String,Object> getAmountBySex(){
		Map<String,Object> result=clinicService.getAmountBySex();
		return result;
	}
	
	@RequestMapping("/countByGenderLine")
    @ResponseBody
    public Map<String, Object> getCountByGenderLine(){
		Map<String, Object> result = clinicService.getAmountBySexInLine();
		return result;
    } 
	
	@RequestMapping("/areaCoverage")
    @ResponseBody
    public Map<String, Object> getAreaCoverage(){
		Map<String, Object> result = clinicService.getAreaCoverage();
		return result;
    } 
	
	@RequestMapping("/ageRange")
    @ResponseBody
    public Map<String, Object> getAgeRange(){
		Map<String, Object> result = clinicService.getAgeRange();
		return result;
    } 
	
	@RequestMapping("/hospitalTotal")
	@ResponseBody
	public Map<String, Object> getHospitalTotal(){
		Map<String, Object> result = clinicService.getRankByHospital();
		return result;
	} 
	
	@RequestMapping("/departmentTotal")
	@ResponseBody
	public Map<String, Object> getDepartmentTotal(){
		Map<String, Object> result = clinicService.getRankByDepartment();
		return result;
	}
	
	@RequestMapping("doctorTotal")
	@ResponseBody
	public Map<String,Object> getDoctorTotal(){
		Map<String,Object> result=clinicService.getRankByDoctor();
		return result;
	}
}
