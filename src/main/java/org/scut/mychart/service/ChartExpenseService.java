package org.scut.mychart.service;

import org.scut.mychart.model.ChartExpense;

import java.util.List;
import java.util.Map;

public interface ChartExpenseService {

    public List<ChartExpense> getChartExpenseBarData();
    public Map<String, Object> getChartExpenseBarOption();
    public Map<String, Object> getChartExpenseLineOption();
    public Map<String, Object> getChartExpenseGaugeOption();
    public Map<String, Object> getChartExpenseFunnelOption();
    public Map<String, Object> getHospitalTotal();
    public Map<String, Object> getDepartmentTotal();
}
