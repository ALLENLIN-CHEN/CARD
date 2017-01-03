package org.scut.mychart.service;

import java.util.Map;


public interface RegisterService {
	public Map<String, Object> getCountByGender();
	
	public Map<String, Object> getCountByGenderLine();

	public Map<String, Object> getAreaCoverage();

	public Map<String, Object> getAgeRange();

	public Map<String, Object> getHospitalTotal();

	public Map<String, Object> getHospitalPercent(String startTime, String endTime);

	public Map<String, Object> getDepartmentTotal();
	
	public Map<String, Object> getDepartmentPercent(String startTime, String endTime);

	public Map<String, Object> getDoctorTotal();

	public Map<String, Object> getDoctorPercent(String startTime, String endTime);
	
	/**
	 * 用于获取关于大屏的展示数据
	 */
	public Map<String, Object> getScreenData();
}
