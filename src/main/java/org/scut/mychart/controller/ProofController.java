package org.scut.mychart.controller;

import org.scut.mychart.service.ProofService;
import org.scut.mychart.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value="/charts/proof", produces="application/json;charset=UTF-8")
public class ProofController {

	@Autowired
	private ProofService proofService;

	@RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> getRegister(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",  proofService.getChartVennOption("挂号","就医服务"));
		return result;
    }

	@RequestMapping("/hospitalized")
	@ResponseBody
	public Map<String, Object> getHospitalized(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","住院登记"));
		return result;
	}
	@RequestMapping("/treatment")
	@ResponseBody
	public Map<String, Object> getTreatment(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",proofService.getChartVennOption("就医服务","异地就医申请"));
		return result;
	}
	@RequestMapping("/outpatient")
	@ResponseBody
	public Map<String, Object> getOutpatient(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","门诊统筹申请"));
		return result;
	}
	@RequestMapping("/special")
	@ResponseBody
	public Map<String, Object> getSpecial(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","特殊医疗待遇申请"));
		return result;
	}
	@RequestMapping("/wiped")
	@ResponseBody
	public Map<String, Object> getWiped(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","医疗费用报销申请"));
		return result;
	}
}
