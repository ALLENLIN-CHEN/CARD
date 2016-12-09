package org.scut.mychart.service;

import com.github.abel533.echarts.json.GsonOption;
import org.scut.mychart.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 2016/11/30.
 */
public interface IExternalService {

    public String getPlace(String place);
    public String getTime(String year);

    public List<External1> getExternal1List();
    public List<External3> getExternal3List(String place);
    public List<External4> getExternal4List();
    public List<External5> getExternal5List(String year);
    public List<External6> getExternal6List(String year);
    public List<External7> getExternal7List(String year);

    public String getExternal1Option();
    public String getExternal2Option();
    public String getExternal3Option(String place);
    public String getExternal4Option();
    public String getExternal5Option(String year);
    public String getExternal6Option(String year);
    public String getExternal7Option(String year);

}
