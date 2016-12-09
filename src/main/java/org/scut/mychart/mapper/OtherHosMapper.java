package org.scut.mychart.mapper;

import java.util.List;
import java.util.Map;

import org.scut.mychart.model.OtherHosModel;

/**
 * 异地就医mapper
 * @author spiden
 *
 */
public interface OtherHosMapper {
	
	/**获取总数量
	 * @return
	 */
	public List<OtherHosModel> selectTotalNum(); 
	
	/**获取仪表盘的数据
	 * @return
	 */
	public List<OtherHosModel> selectArea();
	public List<OtherHosModel> selectYearNum();
	/**
	 * 获取漏斗图数据
	 * @return
	 */
	public List<OtherHosModel> selectAge();
	
	/**
	 * 获取柱状图数据 按医院
	 * @return
	 */
	public List<OtherHosModel> selectHospital();
	
	/**
	 * 获取柱状图数据 按部门
	 * @return
	 */
	public List<OtherHosModel> selectDepartment();
	
	/**
	 * 获取柱状图数据 按医生
	 * @return
	 */
	public List<OtherHosModel> selectDoctor();

}
