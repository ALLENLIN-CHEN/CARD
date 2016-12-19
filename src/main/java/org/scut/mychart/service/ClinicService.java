package org.scut.mychart.service;

import java.util.Map;

public interface ClinicService {
	public Map<String,Object> getAmountBySex(); 
	
	public Map<String,Object> getAmountBySexInLine();
	
	public Map<String,Object> getAreaCoverage();
	
	public Map<String,Object> getAgeRange();
	
	public Map<String,Object> getRankByHospital();
	
	public Map<String,Object> getRankByDepartment();
	
	public Map<String,Object> getRankByDoctor();
}


































