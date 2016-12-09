package org.scut.mychart.service.impl;

import org.scut.mychart.mapper.TsylMapper;
import org.scut.mychart.model.TsylModel;
import org.scut.mychart.service.TsylService;
import org.scut.mychart.util.DictionaryString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
/**
 * Created by linqidi on 2016/12/9.
 */

@Service
public class TsylServiceImpl implements TsylService {

	@Autowired
	private TsylMapper tsylMapper;

	@Override
	public Map<String, Object> getCountByGender() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TsylModel> data = tsylMapper.getCountByGender(DictionaryString.BUSINESS_TSYL);
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();

		for(TsylModel m : data) {
			if(m.getSex().equals(DictionaryString.MALE)) {
				maleCount.add(m.getSum());
			}else if(m.getSex().equals(DictionaryString.FEMALE)) {
				femaleCount.add(m.getSum());
			}
		}

		result.put("type", DictionaryString.TSYL_BAR_X);
		result.put("male", maleCount);
		result.put("female", femaleCount);
		return result;
	}

	@Override
	public Map<String, Object> getCountByGenderLine() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TsylModel> data = tsylMapper.getCountByGender(DictionaryString.BUSINESS_TSYL);
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();

		for(TsylModel m : data) {
			if(m.getSex().equals(DictionaryString.MALE)) {
				maleCount.add(m.getSum());
			}else if(m.getSex().equals(DictionaryString.FEMALE)) {
				femaleCount.add(m.getSum());
			}
		}

		result.put("type", DictionaryString.TSYL_LINE);
		result.put("male", maleCount);
		result.put("female", femaleCount);
		return result;
	}

	@Override
	public Map<String, Object> getAreaCoverage() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TsylModel> data = tsylMapper.getAreaCoverage(DictionaryString.BUSINESS_TSYL);
		Map<String, Integer> total = new HashMap<String, Integer>();
		String temp = "";
		for(TsylModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				total.put(temp, total.get(temp) + m.getSum());
			}else {
				total.put(temp, m.getSum());
			}
		}

		Map<String, List<Double>> coverage = new HashMap<String, List<Double>>();

		DecimalFormat df = new DecimalFormat("#.##");

		for(TsylModel m : data) {
			temp = String.valueOf(m.getYear());
			m.setCoverage((double)m.getSum() / total.get(temp) * 100);
			if(coverage.containsKey(m.getArea())) {
				coverage.get(m.getArea()).add(Double.valueOf(df.format(m.getCoverage())));
			}else {
				coverage.put(m.getArea(), new ArrayList<Double>());
				coverage.get(m.getArea()).add(Double.valueOf(df.format(m.getCoverage())));
			}
		}

		result.put("type", DictionaryString.TSYL_GAUGE);
		result.put("coverage", coverage);
		return result;
	}
	public Map<String, Object> getAgeRange() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TsylModel> data = tsylMapper.getAgeRange(DictionaryString.BUSINESS_REGISTER);

		Map<String, Map<String, Integer>> dataSet = new HashMap<String, Map<String,Integer>>();
		String temp = "";
		for(TsylModel m : data) {
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

		result.put("type", DictionaryString.TSYL_FUNNEL);
		result.put("ageRange", dataSet);

		return result;
	}

	@Override
	public Map<String, Object> getHospitalTotal() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TsylModel> data = tsylMapper.getHospitalTotal(DictionaryString.BUSINESS_TSYL);
		Map<String, List<TsylModel>> total = new HashMap<String, List<TsylModel>>();
		String temp = "";
		for(TsylModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				if(total.get(temp).size() >= 10) {
					continue;
				}

				total.get(temp).add(m);
			}else {
				total.put(temp, new ArrayList<TsylModel>());
				total.get(temp).add(m);
			}
		}

		result.put("rank", total);
		result.put("type", DictionaryString.TSYL_BAR_HOSPITAL_TOTAL);
		return result;
	}
	@Override
	public Map<String, Object> getDepartmentTotal() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TsylModel> data = tsylMapper.getDepartmentTotal(DictionaryString.BUSINESS_TSYL);
		Map<String, List<TsylModel>> total = new HashMap<String, List<TsylModel>>();
		String temp = "";
		for(TsylModel m : data) {
			temp = String.valueOf(m.getYear());
			if(total.containsKey(temp)) {
				if(total.get(temp).size() >= 10) {
					continue;
				}

				total.get(temp).add(m);
			}else {
				total.put(temp, new ArrayList<TsylModel>());
				total.get(temp).add(m);
			}
		}

		result.put("rank", total);
		result.put("type", DictionaryString.TSYL_BAR_DEPARTMENT_TOTAL);
		return result;
	}


}
