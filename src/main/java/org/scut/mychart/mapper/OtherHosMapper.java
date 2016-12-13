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
	 * 医院占比
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	public List<OtherHosModel> selectHosPercent(String sTime,String eTime);
	
	/**
	 * 获取柱状图数据 按部门
	 * @return
	 */
	public List<OtherHosModel> selectHosMax(String sTime,String eTime);
	
	public List<OtherHosModel> selectDepartment();
	
	/**部门占比
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	public List<OtherHosModel> selectDepPercent(String sTime,String eTime);
	
	public List<OtherHosModel> selectDepMax(String sTime,String eTime);
	

}
