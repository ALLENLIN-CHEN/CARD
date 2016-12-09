package org.scut.mychart.mapper;

import org.scut.mychart.model.TsylModel;

import java.util.List;

/**
 * Created by linqidi on 2016/12/8.
 */
public interface TsylMapper {
    public List<TsylModel> getCountByGender(String business);

    public List<TsylModel> getAreaCoverage(String business);
    public List<TsylModel> getAgeRange(String business);
    public List<TsylModel> getHospitalTotal(String business);
    public List<TsylModel> getDepartmentTotal(String business);

}
