package org.scut.mychart.controller;

import java.util.Map;

import org.scut.mychart.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/charts/register", produces="application/json;charset=UTF-8")
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/countByGender")
    @ResponseBody
    public Map<String, Object> getCountByGender(){
		Map<String, Object> result = registerService.getCountByGender();
		return result;
    }  
	
	@RequestMapping("/countByGenderLine")
    @ResponseBody
    public Map<String, Object> getCountByGenderLine(){
		Map<String, Object> result = registerService.getCountByGenderLine();
		return result;
    } 
	
	@RequestMapping("/areaCoverage")
    @ResponseBody
    public Map<String, Object> getAreaCoverage(){
		Map<String, Object> result = registerService.getAreaCoverage();
		return result;
    } 
	
	@RequestMapping("/ageRange")
    @ResponseBody
    public Map<String, Object> getAgeRange(){
		Map<String, Object> result = registerService.getAgeRange();
		return result;
    } 

	@RequestMapping("/hospitalTotal")
	@ResponseBody
	public Map<String, Object> getHospitalTotal(){
		Map<String, Object> result = registerService.getHospitalTotal();
		return result;
	} 

	@RequestMapping("/hospitalPercent")
	@ResponseBody
	public Map<String, Object> getHospitalPercent(int startTime, int endTime){
		Map<String, Object> result = registerService.getHospitalPercent(startTime, endTime);
		return result;
	} 
	
	@RequestMapping("/departmentTotal")
	@ResponseBody
	public Map<String, Object> getDepartmentTotal(){
		Map<String, Object> result = registerService.getDepartmentTotal();
		return result;
	}
	
	@RequestMapping("/departmentPercent")
	@ResponseBody
	public Map<String, Object> getDepartmentPercent(int startTime, int endTime){
		Map<String, Object> result = registerService.getDepartmentPercent(startTime, endTime);
		return result;
	} 
}
