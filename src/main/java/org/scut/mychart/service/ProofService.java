package org.scut.mychart.service;

import org.scut.mychart.model.Chartvenn;

import java.util.HashMap;

public interface ProofService {
    

    
    public HashMap<String,Chartvenn> selectPersonnum(String type1, String type2);
    public String getChartVennOption(String type1, String type2);
}
