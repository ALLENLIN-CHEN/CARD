package org.scut.mychart.mapper;


import org.scut.mychart.model.*;

import java.util.List;
import java.util.Map;

public interface ExternalMapper {
	List<External1> selectExternal1();
	List<External3>selectExternal3(Map<String,String>param);
	List<External4>selectExternal4();
	List<External5>selectExternal5(Map<String,String>param);
	List<External6>selectExternal6(Map<String,String>param);
	List<External7>selectExternal7(Map<String,String>param);

}	

