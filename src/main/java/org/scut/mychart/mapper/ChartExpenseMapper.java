package org.scut.mychart.mapper;

import org.scut.mychart.model.ChartExpense;

import java.util.List;

public interface ChartExpenseMapper {
	List<ChartExpense> selectChartExpenseBar();
	List<ChartExpense> selectChartExpenseAreaCoverage();
	List<ChartExpense> getAgeRange();
	List<ChartExpense> getHospitalTotal();
	List<ChartExpense> getDepartmentTotal();
}	

