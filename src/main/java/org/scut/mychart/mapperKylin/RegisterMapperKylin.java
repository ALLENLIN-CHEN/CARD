package org.scut.mychart.mapperKylin;

import org.scut.mychart.model.RegisterModel;

import java.util.List;

public interface RegisterMapperKylin {
	
	public List<RegisterModel> getCountByGender(String business);
	
	public List<RegisterModel> getAreaCoverage(String business);

	public List<RegisterModel> getAgeRange(String business);

	public List<RegisterModel> getHospitalTotal(String business);

	public List<RegisterModel> getHospitalByTime(String business, String startTime, String endTime);

	public List<RegisterModel> getHospitalMaxByDay(String business, String startTime, String endTime);

	public List<RegisterModel> getDepartmentTotal(String business);
	
	public List<RegisterModel> getDepartmentByTime(String business, String startTime, String endTime);

	public List<RegisterModel> getDepartmentMaxByDay(String business, String startTime, String endTime);
	
	public List<RegisterModel> getDoctorTotal(String business);
	
	public List<RegisterModel> getDoctorByTime(String business, String startTime, String endTime);

	public List<RegisterModel> getDoctorMaxByDay(String business, String startTime, String endTime);
}
