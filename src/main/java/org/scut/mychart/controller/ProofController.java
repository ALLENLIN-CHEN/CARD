package org.scut.mychart.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.scut.mychart.redis.RedisBaseDao;
import org.scut.mychart.service.ProofService;
import org.scut.mychart.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value="/charts/proof", produces="application/json;charset=UTF-8")
public class ProofController {

	@Autowired
	private ProofService proofService;
	@Autowired
	private RedisBaseDao redisBaseDao;
	private ObjectMapper mapper=new ObjectMapper();
	@RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> getRegister(){
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("data",  proofService.getChartVennOption("挂号","就医服务"));
//		return result;
		Map<String, Object> result =null;
		String json=null;
		try {
			json=redisBaseDao.getRedisData("/charts/proof/register");
			if(json != null && !json.isEmpty()) {
				return mapper.readValue(json,Map.class);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		result = new HashMap<String, Object>();
		result.put("data",  proofService.getChartVennOption("挂号","就医服务"));
		try {
			json = mapper.writeValueAsString(result);
			redisBaseDao.setRedisData("/charts/proof/register", json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  result;
    }

	@RequestMapping("/hospitalized")
	@ResponseBody
	public Map<String, Object> getHospitalized(){
		Map<String, Object> result =null;
		String json=null;
		try {
			json=redisBaseDao.getRedisData("/charts/proof/hospitalized");
			if(json != null && !json.isEmpty()) {
				return mapper.readValue(json,Map.class);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","住院登记"));
		try {
			json = mapper.writeValueAsString(result);
			redisBaseDao.setRedisData("/charts/proof/hospitalized", json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  result;
	}
	@RequestMapping("/treatment")
	@ResponseBody
	public Map<String, Object> getTreatment(){
		Map<String, Object> result =null;
		String json=null;
		try {
			json=redisBaseDao.getRedisData("/charts/proof/treatment");
			if(json != null && !json.isEmpty()) {
				return mapper.readValue(json,Map.class);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		result = new HashMap<String, Object>();
		result.put("data",proofService.getChartVennOption("就医服务","异地就医申请"));
		try {
			json = mapper.writeValueAsString(result);
			redisBaseDao.setRedisData("/charts/proof/treatment", json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  result;
	}
	@RequestMapping("/outpatient")
	@ResponseBody
	public Map<String, Object> getOutpatient(){
		Map<String, Object> result =null;
		String json=null;
		try {
			json=redisBaseDao.getRedisData("/charts/proof/outpatient");
			if(json != null && !json.isEmpty()) {
				return mapper.readValue(json,Map.class);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","门诊统筹申请"));
		try {
			json = mapper.writeValueAsString(result);
			redisBaseDao.setRedisData("/charts/proof/outpatient", json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  result;
	}
	@RequestMapping("/special")
	@ResponseBody
	public Map<String, Object> getSpecial(){
		Map<String, Object> result =null;
		String json=null;
		try {
			json=redisBaseDao.getRedisData("/charts/proof/special");
			if(json != null && !json.isEmpty()) {
				return mapper.readValue(json,Map.class);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","特殊医疗待遇申请"));
		try {
			json = mapper.writeValueAsString(result);
			redisBaseDao.setRedisData("/charts/proof/special", json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  result;
	}
	@RequestMapping("/wiped")
	@ResponseBody
	public Map<String, Object> getWiped(){
		Map<String, Object> result =null;
		String json=null;
		try {
			json=redisBaseDao.getRedisData("/charts/proof/wiped");
			if(json != null && !json.isEmpty()) {
				return mapper.readValue(json,Map.class);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		result = new HashMap<String, Object>();
		result.put("data", proofService.getChartVennOption("就医服务","医疗费用报销申请"));
		try {
			json = mapper.writeValueAsString(result);
			redisBaseDao.setRedisData("/charts/proof/wiped", json);
		}catch (IOException e){
			e.printStackTrace();
		}
		return  result;
	}
}
