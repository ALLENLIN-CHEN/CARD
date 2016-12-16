package org.scut.mychart.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scut.mychart.service.ClinicService;
import org.scut.mychart.util.DictionaryString;
import org.scut.mychart.model.ClinicModel;
import org.scut.mychart.mapper.ClinicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl implements ClinicService{
	
	@Autowired
	private ClinicMapper clinicMapper;
	
	@Override
	public Map<String, Object> getAmountBySex() {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		List<ClinicModel> data=clinicMapper.getAmountBySex(DictionaryString.BUSINESS_CLINIC);
	
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();
		
		for(ClinicModel m : data) {
			if(m.getSex().equals(DictionaryString.MALE)) {
				maleCount.add(m.getAmount());
			}else if(m.getSex().equals(DictionaryString.FEMALE)) {
				femaleCount.add(m.getAmount());
			}
		}
		
		result.put("type", DictionaryString.CLINIC_BAR_X);
		result.put("male", maleCount);
		result.put("female", femaleCount);
		return result;
	}
	
	@Override
	public Map<String, Object> getAmountBySexInLine() {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		List<ClinicModel> data=clinicMapper.getAmountBySex(DictionaryString.BUSINESS_CLINIC);
		
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();		
		
		for(ClinicModel m : data) {
			if(m.getSex().equals(DictionaryString.MALE)) {
				maleCount.add(m.getAmount());
			}else if(m.getSex().equals(DictionaryString.FEMALE)) {
				femaleCount.add(m.getAmount());
			}			
		}
		
		result.put("type", DictionaryString.CLINIC_LINE);
		result.put("male", maleCount);
		result.put("female", femaleCount);		
		return result;
	}

	@Override
	public Map<String, Object> getAreaCoverage() {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<ClinicModel> data = clinicMapper.getAmountByArea(DictionaryString.BUSINESS_CLINIC);
		Map<String, Integer> total = new HashMap<String, Integer>();
		String temp = "";
		for(ClinicModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				total.put(temp, total.get(temp) + m.getAmount());
			}else {
				total.put(temp, m.getAmount());
			}
		}
		
		Map<String, List<Double>> coverage = new HashMap<String, List<Double>>();
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		for(ClinicModel m : data) {
			temp = String.valueOf(m.getYear());
			m.setCoverage((double)m.getAmount() / total.get(temp) * 100);
			if(coverage.containsKey(m.getArea())) {
				coverage.get(m.getArea()).add(Double.valueOf(df.format(m.getCoverage())));
			}else {
				coverage.put(m.getArea(), new ArrayList<Double>());
				coverage.get(m.getArea()).add(Double.valueOf(df.format(m.getCoverage())));
			}
		}
		
		result.put("type", DictionaryString.CLINIC_GAUGE);
		result.put("coverage", coverage);
		return result;
	}

	@Override
	public Map<String, Object> getAgeRange() {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<ClinicModel> data = clinicMapper.getAmountByAge(DictionaryString.BUSINESS_CLINIC);		
		Map<String, Map<String, Integer>> dataSet = new HashMap<String, Map<String,Integer>>();		
		String temp = "";
		
		for(ClinicModel m : data) {
			temp = String.valueOf(m.getYear());
			if(dataSet.containsKey(temp)) {
				dataSet.get(temp).put("total", dataSet.get(temp).get("total") + m.getAmount());
			}else {
				dataSet.put(temp, new HashMap<String, Integer>());
				dataSet.get(temp).put("total", m.getAmount());
			}
			
			if(m.getYear() - m.getBirthYear() <= 6) {
				if(dataSet.get(temp).containsKey(DictionaryString.CHILD)) {
					dataSet.get(temp).put(DictionaryString.CHILD, dataSet.get(temp).get(DictionaryString.CHILD) + m.getAmount());
				}else {
					dataSet.get(temp).put(DictionaryString.CHILD, m.getAmount());
				}
			} else if(m.getYear() - m.getBirthYear() >= 7 && m.getYear() - m.getBirthYear() <= 40) {
				if(dataSet.get(temp).containsKey(DictionaryString.YOUTH)) {
					dataSet.get(temp).put(DictionaryString.YOUTH, dataSet.get(temp).get(DictionaryString.YOUTH) + m.getAmount());
				}else {
					dataSet.get(temp).put(DictionaryString.YOUTH, m.getAmount());
				}
			} else if(m.getYear() - m.getBirthYear() >= 41 && m.getYear() - m.getBirthYear() <= 65) {
				if(dataSet.get(temp).containsKey(DictionaryString.MIDLIFE)) {
					dataSet.get(temp).put(DictionaryString.MIDLIFE, dataSet.get(temp).get(DictionaryString.MIDLIFE) + m.getAmount());
				}else {
					dataSet.get(temp).put(DictionaryString.MIDLIFE, m.getAmount());
				}
			} else if(m.getYear() - m.getBirthYear() >= 66) {
				if(dataSet.get(temp).containsKey(DictionaryString.OLDER)) {
					dataSet.get(temp).put(DictionaryString.OLDER, dataSet.get(temp).get(DictionaryString.OLDER) + m.getAmount());
				}else {
					dataSet.get(temp).put(DictionaryString.OLDER, m.getAmount());
				}
			} 
		}
		
		result.put("type", DictionaryString.CLINIC_FUNNEL);
		result.put("ageRange", dataSet);
		
		return result;
	}

	@Override
	public Map<String, Object> getRankByHospital() {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		List<ClinicModel> data=clinicMapper.getAmountByHospital(DictionaryString.BUSINESS_CLINIC);
		Map<String,List<ClinicModel>> total=new HashMap<String,List<ClinicModel>>();
		String temp="";
		for(ClinicModel c:data){
			temp=String.valueOf(c.getYear());
			if(total.containsKey(temp)){
				if(total.get(temp).size()>=10)continue;
				total.get(temp).add(c);				
			}else{
				total.put(temp, new ArrayList<ClinicModel>());
				total.get(temp).add(c);
			}
		}
		
		result.put("type", DictionaryString.CLINIC_BAR_HOSPITAL_TOTAL);
		result.put("rank", total);
		
		return result;
	}

	@Override
	public Map<String, Object> getRankByDepartment() {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		List<ClinicModel> data=clinicMapper.getAmountByDepartment(DictionaryString.BUSINESS_CLINIC);
		Map<String,List<ClinicModel>> total=new HashMap<String,List<ClinicModel>>();
		String temp="";
		for(ClinicModel c:data){
			temp=String.valueOf(c.getYear());
			if(total.containsKey(temp)){
				if(total.get(temp).size()>=10)continue;
				else total.get(temp).add(c);
			}else{
				total.put(temp,new ArrayList<ClinicModel>());
				total.get(temp).add(c);
			}
		}
		
		result.put("type", DictionaryString.CLINIC_BAR_DEPARTMENT_TOTAL);
		result.put("rank", total);
		
		return result;
	}

	@Override
	public Map<String, Object> getRankByDoctor() {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		List<ClinicModel> data=clinicMapper.getAmountByDoctor(DictionaryString.BUSINESS_CLINIC);
		Map<String,List<ClinicModel>> total=new HashMap<String,List<ClinicModel>>();
		String temp="";
		for(ClinicModel c:data){
			temp=String.valueOf(c.getYear());
			if(total.containsKey(temp)){
				if(total.get(temp).size()>=10)continue;
				else total.get(temp).add(c);
			}else{
				total.put(temp,new ArrayList<ClinicModel>());
				total.get(temp).add(c);
			}
		}
		
		result.put("type", DictionaryString.CLINIC_BAR_DOCTOR_TOTAL);
		result.put("rank", total);
		
		return result;
	}
}
