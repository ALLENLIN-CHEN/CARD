package org.scut.mychart.mapper;

import java.util.List;
import org.scut.mychart.model.ClinicModel;

public interface ClinicMapper {
	
	public List<ClinicModel> getAmountBySex(String business);
	
	public List<ClinicModel> getAmountByArea(String business);
	
	public List<ClinicModel> getAmountByAge(String business);
	
	public List<ClinicModel> getAmountByHospital(String business);
	
	public List<ClinicModel> getAmountByDepartment(String business);
	
	public List<ClinicModel> getAmountByDoctor(String business);
	
}






































































