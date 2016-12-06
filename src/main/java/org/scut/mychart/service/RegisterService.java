package org.scut.mychart.service;

import java.util.Map;


public interface RegisterService {
	public Map<String, Object> getCountByGender();
	
	public Map<String, Object> getCountByGenderLine();

	public Map<String, Object> getAreaCoverage();

	public Map<String, Object> getAgeRange();

	public Map<String, Object> getHospitalTotal();

	public Map<String, Object> getHospitalPercent(int startTime, int endTime);

	public Map<String, Object> getDepartmentTotal();
	
	public Map<String, Object> getDepartmentPercent(int startTime, int endTime);
}
