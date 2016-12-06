package org.scut.mychart.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.plaf.DesktopIconUI;

import org.scut.mychart.mapper.RegisterMapper;
import org.scut.mychart.model.RegisterModel;
import org.scut.mychart.service.RegisterService;
import org.scut.mychart.util.DictionaryString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private RegisterMapper registerMapper;
	
	@Override
	public Map<String, Object> getCountByGender() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RegisterModel> data = registerMapper.getCountByGender(DictionaryString.BUSINESS_REGISTER);
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();
		
		for(RegisterModel m : data) {
			if(m.getSex().equals(DictionaryString.MALE)) {
				maleCount.add(m.getSum());
			}else if(m.getSex().equals(DictionaryString.FEMALE)) {
				femaleCount.add(m.getSum());
			}
		}
		
		result.put("type", DictionaryString.REGISTER_BAR_X);
		result.put("male", maleCount);
		result.put("female", femaleCount);
		return result;
	}

	@Override
	public Map<String, Object> getCountByGenderLine() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RegisterModel> data = registerMapper.getCountByGender(DictionaryString.BUSINESS_REGISTER);
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();
		
		for(RegisterModel m : data) {
			if(m.getSex().equals(DictionaryString.MALE)) {
				maleCount.add(m.getSum());
			}else if(m.getSex().equals(DictionaryString.FEMALE)) {
				femaleCount.add(m.getSum());
			}
		}
		
		result.put("type", DictionaryString.REGISTER_LINE);
		result.put("male", maleCount);
		result.put("female", femaleCount);
		return result;
	}

	@Override
	public Map<String, Object> getAreaCoverage() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RegisterModel> data = registerMapper.getAreaCoverage(DictionaryString.BUSINESS_REGISTER);
		Map<String, Integer> total = new HashMap<String, Integer>();
		String temp = "";
		for(RegisterModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				total.put(temp, total.get(temp) + m.getSum());
			}else {
				total.put(temp, m.getSum());
			}
		}
		
		Map<String, List<Double>> coverage = new HashMap<String, List<Double>>();
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		for(RegisterModel m : data) {
			temp = String.valueOf(m.getYear());
			m.setCoverage((double)m.getSum() / total.get(temp) * 100);
			if(coverage.containsKey(m.getArea())) {
				coverage.get(m.getArea()).add(Double.valueOf(df.format(m.getCoverage())));
			}else {
				coverage.put(m.getArea(), new ArrayList<Double>());
				coverage.get(m.getArea()).add(Double.valueOf(df.format(m.getCoverage())));
			}
		}
		
		result.put("type", DictionaryString.REGISTER_GAUGE);
		result.put("coverage", coverage);
		return result;
	}

	@Override
	public Map<String, Object> getAgeRange() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RegisterModel> data = registerMapper.getAgeRange(DictionaryString.BUSINESS_REGISTER);
		
		Map<String, Map<String, Integer>> dataSet = new HashMap<String, Map<String,Integer>>();
		String temp = "";
		for(RegisterModel m : data) {
			temp = String.valueOf(m.getYear());
			if(dataSet.containsKey(temp)) {
				dataSet.get(temp).put("total", dataSet.get(temp).get("total") + m.getSum());
			}else {
				dataSet.put(temp, new HashMap<String, Integer>());
				dataSet.get(temp).put("total", m.getSum());
			}
			
			if(m.getYear() - m.getBirth() <= 6) {
				if(dataSet.get(temp).containsKey(DictionaryString.CHILD)) {
					dataSet.get(temp).put(DictionaryString.CHILD, dataSet.get(temp).get(DictionaryString.CHILD) + m.getSum());
				}else {
					dataSet.get(temp).put(DictionaryString.CHILD, m.getSum());
				}
			} else if(m.getYear() - m.getBirth() >= 7 && m.getYear() - m.getBirth() <= 40) {
				if(dataSet.get(temp).containsKey(DictionaryString.YOUTH)) {
					dataSet.get(temp).put(DictionaryString.YOUTH, dataSet.get(temp).get(DictionaryString.YOUTH) + m.getSum());
				}else {
					dataSet.get(temp).put(DictionaryString.YOUTH, m.getSum());
				}
			} else if(m.getYear() - m.getBirth() >= 41 && m.getYear() - m.getBirth() <= 65) {
				if(dataSet.get(temp).containsKey(DictionaryString.MIDLIFE)) {
					dataSet.get(temp).put(DictionaryString.MIDLIFE, dataSet.get(temp).get(DictionaryString.MIDLIFE) + m.getSum());
				}else {
					dataSet.get(temp).put(DictionaryString.MIDLIFE, m.getSum());
				}
			} else if(m.getYear() - m.getBirth() >= 66) {
				if(dataSet.get(temp).containsKey(DictionaryString.OLDER)) {
					dataSet.get(temp).put(DictionaryString.OLDER, dataSet.get(temp).get(DictionaryString.OLDER) + m.getSum());
				}else {
					dataSet.get(temp).put(DictionaryString.OLDER, m.getSum());
				}
			} 
		}
		
		result.put("type", DictionaryString.REGISTER_FUNNEL);
		result.put("ageRange", dataSet);
		
		return result;
	}

	@Override
	public Map<String, Object> getHospitalTotal() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RegisterModel> data = registerMapper.getHospitalTotal(DictionaryString.BUSINESS_REGISTER);
		Map<String, List<RegisterModel>> total = new HashMap<String, List<RegisterModel>>();
		String temp = "";
		for(RegisterModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				if(total.get(temp).size() >= 10) {
					continue;
				}
				
				total.get(temp).add(m);
			}else {
				total.put(temp, new ArrayList<RegisterModel>());
				total.get(temp).add(m);
			}
		}
		
		result.put("rank", total);
		result.put("type", DictionaryString.REGISTER_BAR_HOSPITAL_TOTAL);
		return result;
	}

	@Override
	public Map<String, Object> getHospitalPercent(int startTime, int endTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		String stime = startTime + "-01-01";
		String etime = endTime + "-12-31";
		List<RegisterModel> total = registerMapper.getHospitalByTime(DictionaryString.BUSINESS_REGISTER, stime, etime);
		List<RegisterModel> day = registerMapper.getHospitalMaxByDay(DictionaryString.BUSINESS_REGISTER, stime, etime);
		Map<String, Double> per = new HashMap<String, Double>();
		
		DecimalFormat df = new DecimalFormat("#.##");
		double calPer = 0.0;
		RegisterModel d = null;
		int index = 0;
		for(RegisterModel m : total) {
			d = day.get(index);
			calPer = (double)m.getSum() / (d.getMaxNum() * 365 * (endTime - startTime + 1)) * 100;
			per.put(m.getHospital(), Double.valueOf(df.format(calPer)));
			index++;
		}
		
		/**
		 * 用于排序
		 */
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(per.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> o1,
					Entry<String, Double> o2) {
				if(o2.getValue() - o1.getValue() >= 0) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		result.put("type", DictionaryString.REGISTER_BAR_HOSPITAL_PERCENT);
		result.put("percent", list);
		return result;
	}

	@Override
	public Map<String, Object> getDepartmentTotal() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RegisterModel> data = registerMapper.getDepartmentTotal(DictionaryString.BUSINESS_REGISTER);
		Map<String, List<RegisterModel>> total = new HashMap<String, List<RegisterModel>>();
		String temp = "";
		for(RegisterModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				if(total.get(temp).size() >= 10) {
					continue;
				}
				
				total.get(temp).add(m);
			}else {
				total.put(temp, new ArrayList<RegisterModel>());
				total.get(temp).add(m);
			}
		}
		
		result.put("rank", total);
		result.put("type", DictionaryString.REGISTER_BAR_DEPARTMENT_TOTAL);
		return result;
	}

	@Override
	public Map<String, Object> getDepartmentPercent(int startTime, int endTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		String stime = startTime + "-01-01";
		String etime = endTime + "-12-31";
		List<RegisterModel> total = registerMapper.getDepartmentByTime(DictionaryString.BUSINESS_REGISTER, stime, etime);
		List<RegisterModel> day = registerMapper.getDepartmentMaxByDay(DictionaryString.BUSINESS_REGISTER, stime, etime);
		Map<String, Double> per = new HashMap<String, Double>();
		Map<String, Integer> totalMap = new HashMap<String, Integer>();
		for(RegisterModel m : total) {
			String key = m.getHospital() + "-" + m.getDepartment();
			if(!totalMap.containsKey(key)) {
				totalMap.put(key, m.getSum());
			}
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		double calPer = 0.0;
		for(RegisterModel m : day) {
			String key = m.getHospital() + "-" + m.getDepartment();
			if(totalMap.containsKey(key)) {
				calPer = (double)totalMap.get(key) / (m.getMaxNum() * 365 * (endTime - startTime + 1)) * 100;
				per.put(key, Double.valueOf(df.format(calPer)));
			}
		}

		/**
		 * 用于排序
		 */
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(per.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> o1,
					Entry<String, Double> o2) {
				if(o2.getValue() - o1.getValue() >= 0) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		result.put("type", DictionaryString.REGISTER_BAR_DEPARTMENT_PERCENT);
		result.put("percent", list);
		return result;
	}

}
