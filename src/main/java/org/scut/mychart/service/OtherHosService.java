package org.scut.mychart.service;

import java.util.Map;

/**
 * 获取异地就医的相关数据
 * @author spiden
 *
 */

public interface OtherHosService {
	
	/**
	 * 获取柱状图数据
	 * @return
	 */
	
	public Map<String,Object> getHistogramData();
	
	
	/**
	 * 获取折线图数据
	 * @return
	 */
	public Map<String, Object> getLineData();
	
	
	/**
	 * 获取仪表盘数据
	 * @return
	 */
	public Map<String, Object> getPanelData();
	
	/**
	 * 获取漏斗图数据
	 * @return
	 */
	public Map<String, Object> getFunnelData();
	
	/**
	 *获取柱状图数据 按医院
	 * @return
	 */
	public Map<String, Object> getHistogramData_hos(); 
	
	/**
	 * 获取柱状图数据 按部门
	 * @return
	 */
	public Map<String, Object> getHistogramData_dep();
	
	/**
	 * 获取柱状图数据 按医生
	 * @return
	 */
	public Map<String, Object> getHistogramData_doc();

}
