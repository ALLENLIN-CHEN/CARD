package org.scut.mychart.controller;

import org.scut.mychart.service.ProofService;
import org.scut.mychart.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/charts/proof", produces="application/json;charset=UTF-8")
public class ProofController {

	@Autowired
	private ProofService proofService;

	@RequestMapping("/register")
    @ResponseBody
    public String getRegister(){
		String result = proofService.getChartVennOption("挂号","就医服务");
		return result;
    }

	@RequestMapping("/hospitalized")
	@ResponseBody
	public String getHospitalized(){
		String result = proofService.getChartVennOption("就医服务","住院登记");
		return result;
	}
	@RequestMapping("/treatment")
	@ResponseBody
	public String getTreatment(){
		String result = proofService.getChartVennOption("就医服务","异地就医申请");
		return result;
	}
	@RequestMapping("/outpatient")
	@ResponseBody
	public String getOutpatient(){
		String result = proofService.getChartVennOption("就医服务","门诊统筹申请");
		return result;
	}
	@RequestMapping("/special")
	@ResponseBody
	public String getSpecial(){
		String result = proofService.getChartVennOption("就医服务","特殊医疗待遇申请");
		return result;
	}
	@RequestMapping("/wiped")
	@ResponseBody
	public String getWiped(){
		String result = proofService.getChartVennOption("就医服务","医疗费用报销申请");
		return result;
	}
}
