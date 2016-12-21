package org.scut.mychart.service.impl;

import com.github.abel533.echarts.Chart;
import com.github.abel533.echarts.Polar;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import org.scut.mychart.mapper.ChartExpenseMapper;
import org.scut.mychart.model.ChartExpense;
import org.scut.mychart.model.ChartTypeConstant;
//import org.scut.mychart.redis.ExpenseBarRedisDao;
import org.scut.mychart.service.ChartExpenseService;
import org.scut.mychart.util.DictionaryString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Map;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChartExpenseServiceImpl implements ChartExpenseService {
	@Autowired
	private ChartExpenseMapper chartsBarDao;

	@Override
	public List<ChartExpense> getChartExpenseBarData(){
		return this.chartsBarDao.selectChartExpenseBar();
	}

	@Override
	public Map<String, Object> getChartExpenseBarOption() {

		Map<String, Object> result = new HashMap<String, Object>();
		List<ChartExpense> list = getChartExpenseBarData();
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();

		for(ChartExpense m : list) {
			if(m.getsex().equals("男")) {
				maleCount.add(m.getperson_num());
			}else if(m.getsex().equals("女")) {
				femaleCount.add(m.getperson_num());
			}
		}

		result.put("type","EXPENSE_BAR_X");
		result.put("male",maleCount);
		result.put("female",femaleCount);

		return result;
	}

	@Override
	public Map<String, Object> getChartExpenseLineOption() {

		Map<String, Object> result = new HashMap<String, Object>();
		List<ChartExpense> list = getChartExpenseBarData();
		List<Integer> maleCount = new ArrayList<Integer>();
		List<Integer> femaleCount = new ArrayList<Integer>();

		for(ChartExpense m : list) {
			if(m.getsex().equals("男")) {
				maleCount.add(m.getperson_num());
			}else if(m.getsex().equals("女")) {
				femaleCount.add(m.getperson_num());
			}
		}

		result.put("type","EXPENSE_LINE");
		result.put("male",maleCount);
		result.put("female",femaleCount);

		return result;
	}

	@Override
	public Map<String, Object> getChartExpenseGaugeOption() {

		Map<String, Object> result = new HashMap<String, Object>();
		List<ChartExpense> list = chartsBarDao.selectChartExpenseAreaCoverage();
		Map<String, Integer> total = new HashMap<String, Integer>();
		String temp = "";
		for(ChartExpense m : list) {
			temp = String.valueOf(m.getyear());
			if(total.containsKey(temp)) {
				total.put(temp, total.get(temp) + m.getperson_num());
			}else {
				total.put(temp, m.getperson_num());
			}
		}

		Map<String, List<Double>> coverage = new HashMap<String, List<Double>>();

		DecimalFormat df = new DecimalFormat("#.##");

		for(ChartExpense m : list) {
			temp = String.valueOf(m.getyear());
			m.setCoverage((double)m.getperson_num()/ total.get(temp) * 100);
			if(coverage.containsKey(m.getarea())) {
				coverage.get(m.getarea()).add(Double.valueOf(df.format(m.getCoverage())));
			}else {
				coverage.put(m.getarea(), new ArrayList<Double>());
				coverage.get(m.getarea()).add(Double.valueOf(df.format(m.getCoverage())));
			}
		}

		result.put("type", "EXPENSE_GAUGE");
		result.put("coverage", coverage);
		return result;
	}

	@Override
	public Map<String, Object> getChartExpenseFunnelOption() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ChartExpense> data = chartsBarDao.getAgeRange();

		Map<String, Map<String, Integer>> dataSet = new HashMap<String, Map<String,Integer>>();
		String temp = "";
		for(ChartExpense m : data) {
			temp = String.valueOf(m.getyear());
			if(dataSet.containsKey(temp)) {
				dataSet.get(temp).put("total", dataSet.get(temp).get("total") + m.getperson_num());
			}else {
				dataSet.put(temp, new HashMap<String, Integer>());
				dataSet.get(temp).put("total", m.getperson_num());
			}

			if(m.getyear() - m.getBirth() <= 6) {
				if(dataSet.get(temp).containsKey(DictionaryString.CHILD)) {
					dataSet.get(temp).put(DictionaryString.CHILD, dataSet.get(temp).get(DictionaryString.CHILD) + m.getperson_num());
				}else {
					dataSet.get(temp).put(DictionaryString.CHILD, m.getperson_num());
				}
			} else if(m.getyear() - m.getBirth() >= 7 && m.getyear() - m.getBirth() <= 40) {
				if(dataSet.get(temp).containsKey(DictionaryString.YOUTH)) {
					dataSet.get(temp).put(DictionaryString.YOUTH, dataSet.get(temp).get(DictionaryString.YOUTH) + m.getperson_num());
				}else {
					dataSet.get(temp).put(DictionaryString.YOUTH, m.getperson_num());
				}
			} else if(m.getyear() - m.getBirth() >= 41 && m.getyear() - m.getBirth() <= 65) {
				if(dataSet.get(temp).containsKey(DictionaryString.MIDLIFE)) {
					dataSet.get(temp).put(DictionaryString.MIDLIFE, dataSet.get(temp).get(DictionaryString.MIDLIFE) + m.getperson_num());
				}else {
					dataSet.get(temp).put(DictionaryString.MIDLIFE, m.getperson_num());
				}
			} else if(m.getyear() - m.getBirth() >= 66) {
				if(dataSet.get(temp).containsKey(DictionaryString.OLDER)) {
					dataSet.get(temp).put(DictionaryString.OLDER, dataSet.get(temp).get(DictionaryString.OLDER) + m.getperson_num());
				}else {
					dataSet.get(temp).put(DictionaryString.OLDER, m.getperson_num());
				}
			}
		}

		result.put("type", "EXPENSE_FUNNEL");
		result.put("ageRange", dataSet);

		return result;
	}

	@Override
	public Map<String, Object> getHospitalTotal() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ChartExpense> data = chartsBarDao.getHospitalTotal();
		Map<String, List<ChartExpense>> total = new HashMap<String, List<ChartExpense>>();
		String temp = "";
		for(ChartExpense m : data) {
			temp = String.valueOf(m.getyear());
			if(total.containsKey(temp)) {
				if(total.get(temp).size() >= 10) {
					continue;
				}

				total.get(temp).add(m);
			}else {
				total.put(temp, new ArrayList<ChartExpense>());
				total.get(temp).add(m);
			}
		}

		result.put("rank", total);
		result.put("type", "EXPENSE_BAR_HOSPITAL_TOTAL");
		return result;
	}

	@Override
	public Map<String, Object> getDepartmentTotal() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ChartExpense> data = chartsBarDao.getDepartmentTotal();
		Map<String, List<ChartExpense>> total = new HashMap<String, List<ChartExpense>>();
		String temp = "";
		for(ChartExpense m : data) {
			temp = String.valueOf(m.getyear());
			if(total.containsKey(temp)) {
				if(total.get(temp).size() >= 10) {
					continue;
				}

				total.get(temp).add(m);
			}else {
				total.put(temp, new ArrayList<ChartExpense>());
				total.get(temp).add(m);
			}
		}

		result.put("rank", total);
		result.put("type", "EXPENSE_BAR_DEPARTMENT_TOTAL");
		return result;
	}

}

