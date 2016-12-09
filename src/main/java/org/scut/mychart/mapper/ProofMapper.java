package org.scut.mychart.mapper;

import org.scut.mychart.model.Chartvenn;

import java.util.HashMap;
import java.util.List;

public interface ProofMapper {
	List<Chartvenn> selectPersonnum12(HashMap<String, String> param);
	List<Chartvenn> selectPersonnum3(HashMap<String, String> param);
	
}
