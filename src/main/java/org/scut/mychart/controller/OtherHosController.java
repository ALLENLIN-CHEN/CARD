package org.scut.mychart.controller;

import java.util.Map;

import org.scut.mychart.model.OtherHosModel;
import org.scut.mychart.service.OtherHosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *获取数据
 * @author spiden
 *
 */
@Controller
@RequestMapping(value="/charts", produces="application/json;charset=UTF-8")
public class OtherHosController {
	@Autowired
	private OtherHosService otherHosService;
	
	
	/**
	 * 获得柱状图人数数量
	 * @return
	 */
	@RequestMapping("/otherHisGetTotal")
    @ResponseBody
    public Map<String, Object> getHisTotal(){
		Map<String, Object> map =  otherHosService.getHistogramData();
        return map;
    } 
	
	/**
	 * 获取折线图人数数量
	 * @return
	 */
	@RequestMapping("/otherLineGettotal")
    @ResponseBody
    public Map<String, Object> getLineTotal(){
		Map<String, Object> map =  otherHosService.getLineData();
        return map;
    }
	
	/**
	 * 获取仪表盘数据
	 * @param area
	 * @return
	 */
	@RequestMapping("/otherGetPanel")
    @ResponseBody
    public Map<String, Object> getPanel(){
		
		Map<String, Object> map =  otherHosService.getPanelData();
        return map;
    }
	
	/**
	 * 获取漏斗图数据
	 * @return
	 */
	@RequestMapping("/otherGetFunnel")
    @ResponseBody
    public Map<String, Object> getFunnel(){
		Map<String, Object> map =  otherHosService.getFunnelData();
        return map;
    }
	
	/**
	 * 获取柱状图数据，按医院
	 * @return
	 */
	@RequestMapping("/otherGetHisHospital")
    @ResponseBody
    public Map<String, Object> getHisHospital(){
		Map<String, Object> map =  otherHosService.getHistogramData_hos();
        return map;
    }
	
	/**
	 * 获取柱状图数据，按部门
	 * @return
	 */
	@RequestMapping("/otherGetHisDepartment")
    @ResponseBody
    public Map<String, Object> getHisDepartment(){
		Map<String, Object> map =  otherHosService.getHistogramData_dep();
        return map;
    }
	
	/**
	 * 获取柱状图数据，医院占比
	 * @return
	 */
	@RequestMapping("/otherGetHisHosPercent")
    @ResponseBody
    public Map<String, Object> getHisHosPercent(@RequestParam String sTime, @RequestParam String eTime){
		Map<String, Object> map =  otherHosService.getHistogramData_hosPercent(sTime, eTime);
        return map;
    }
	
	
	/**获取数据，部门占比
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	@RequestMapping("/otherGetHisDepPercent")
    @ResponseBody
    public Map<String, Object> getHisDepPercent(@RequestParam String sTime,@RequestParam String eTime){
		Map<String, Object> map =  otherHosService.getHistogramData_depPercent(sTime, eTime);
        return map;
    }

}
