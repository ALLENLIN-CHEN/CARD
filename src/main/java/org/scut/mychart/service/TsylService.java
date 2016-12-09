package org.scut.mychart.service;

import java.util.Map;

/**
 * Created by linqidi on 2016/12/8.
 */
public interface TsylService {
    public Map<String, Object> getCountByGender();

    public Map<String, Object> getCountByGenderLine();

    public Map<String, Object> getAreaCoverage();

    public Map<String, Object> getAgeRange();
    public Map<String, Object> getHospitalTotal();
    public Map<String, Object> getDepartmentTotal();
}
