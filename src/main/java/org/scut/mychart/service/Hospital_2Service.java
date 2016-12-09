package org.scut.mychart.service;

import com.github.abel533.echarts.json.GsonOption;
import org.scut.mychart.model.Hospital_2;

import java.util.List;
import java.util.Map;

public interface Hospital_2Service {
    public String getHospital_2RedisKey(int title);
    public List<Hospital_2> getHospital_2(int title,String[] p);
    public String getHospital_2_2ChartOption();
    public String getHospital_2_3ChartOption();
    public String getHospital_2_4ChartOption(String[] p);
    public String getHospital_2_5ChartOption();
    public String getHospital_2_6810ChartOption(int title);
    public String getHospital_2_7911ChartOption(int title,String[] p);
}
