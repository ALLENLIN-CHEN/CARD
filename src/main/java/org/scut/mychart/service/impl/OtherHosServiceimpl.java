package org.scut.mychart.service.impl;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.derby.iapi.error.ExceptionSeverity;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser.booleanValue_return;
import org.apache.tools.ant.taskdefs.Length;
import org.eclipse.jetty.jndi.java.javaNameParser;
import org.scut.mychart.mapper.OtherHosMapper;
import org.scut.mychart.model.OtherHosModel;
import org.scut.mychart.service.OtherHosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;
import org.w3c.dom.ls.LSInput;

import com.mysql.fabric.xmlrpc.base.Array;

import groovy.beans.ListenerListASTTransformation;

/**
 * 实现数据的获取
 * @author spiden
 *
 */
@Service
public class OtherHosServiceimpl implements OtherHosService{
	

	@Autowired
	private OtherHosMapper otherhos;
	
	
	@Override
	
	public Map<String, Object> getHistogramData() {
		// TODO Auto-generated method stub
		Map<String,Object> data = new HashMap<String,Object>();
		List<OtherHosModel> list =this.otherhos.selectTotalNum();
		data.put("data", list);
		data.put("type", "histogram");
		return data;
	}

	@Override
	public Map<String, Object> getLineData() {
		Map<String,Object> data = new HashMap<String,Object>();
		List<OtherHosModel> list =this.otherhos.selectTotalNum();
		data.put("data", list);
		data.put("type", "line");
		return data;
	}

	@Override
	public Map<String, Object> getPanelData() {
	   
	    
	    Map<String, Object> haha =new HashMap<String,Object>();
	    List<OtherHosModel> list =this.otherhos.selectArea();
	    List<OtherHosModel> sum =this.otherhos.selectYearNum();
	    
	    List<String> area = new ArrayList<String>();
	    
	    for(int i=0;i<list.size();i++){
	    	if(!area.contains(list.get(i).getArea())){
	    		area.add(list.get(i).getArea());
	    	}
	    }
	    
	    
	    
	    for(int i=0;i<area.size();i++){
	    	List<Double> per =new ArrayList<Double>();
	    	for(int j=0;j<sum.size();j++){
	    		for(int k=0;k<list.size();k++){
	    			if(list.get(k).getYear().equals(sum.get(j).getYear())&&list.get(k).getArea().equals(area.get(i))){
	    				double x = (double)list.get(k).getPerson_num()/sum.get(j).getPerson_num()*100;
	    				per.add(x);
	    			}
	    		}
	    	}
	    	
	    	haha.put(area.get(i), per);
	    	
	    }
	    
		Map<String,Object> data = new HashMap<String,Object>();
		
		
	
		data.put("data", haha);
		data.put("type", "panel");
		data.put("sum", sum);
		return data;
	}

	@Override
	public Map<String, Object> getFunnelData() {
		Map<String,Object> data = new HashMap<String,Object>();
		
		List<OtherHosModel> list =this.otherhos.selectAge();
		List<OtherHosModel> llll=new ArrayList<OtherHosModel>();
		
		List<String> temp =new ArrayList<String>();
		temp.add("儿童");
		temp.add("青年");
		temp.add("中年");
		temp.add("老年");
		
		List<Integer> year=new ArrayList<Integer>();
		List<Integer> sum=new ArrayList<Integer>();
		
		for(int i=0;i<list.size();i++){
			String str=list.get(i).getYear();
			if(!year.contains(Integer.valueOf(str))){
				year.add(Integer.valueOf(str));
			}
		}
		
		for(int m=0;m<year.size();m++){
		
			int cnt=0;
			int cur=0;
			int k=0;
			for(int i =0;i<list.size();i++){
				if(list.get(i).getYear().equals(year.get(m).toString())){
					cnt+=list.get(i).getPerson_num();
					cur++;
					llll.add(list.get(i));
					k=i;
				}
			}
			
			if(cur<4){
				List<String> exist=new ArrayList<String>();
				
				for(int j=k;j>k-cur;j--){
					exist.add(list.get(j).getAge());
				
				}
			    for(int n=0;n<temp.size();n++){
			    	if(!exist.contains(temp.get(n))){
						OtherHosModel otherHosModel =new OtherHosModel();
						otherHosModel.setAge(temp.get(n));
						otherHosModel.setYear(year.get(m).toString());
						otherHosModel.setPerson_num(0);
						
						llll.add(otherHosModel);
			    	}
			    }
			}
			sum.add(cnt);	
		}
		
	
		data.put("data", llll);
		data.put("type", "funnel");
		data.put("total", sum);
		return data;
		
	}

	
	@Override
	public Map<String, Object> getHistogramData_hos() {
		// TODO Auto-generated method stub
		Map<String,Object> data = new HashMap<String,Object>();
		List<OtherHosModel> list =this.otherhos.selectHospital();
		List<OtherHosModel> sum  = this.otherhos.selectYearNum();
		
		Map<String, Object> name = new HashMap<String,Object>();
		Map<String, Object> num = new HashMap<String,Object>();
		
		
		
		for(int i=0;i<sum.size();i++){
			List<String> hosName=new ArrayList<String>();
			List<Integer> hosNum=new ArrayList<Integer>();
			for(int j=0,k=0;j<list.size();j++){
				if(list.get(j).getYear().equals(sum.get(i).getYear())){
					hosName.add(list.get(j).getHos_name());
					hosNum.add(list.get(j).getPerson_num());
					k++;
				}
				if(k>=10){
					break;
				}
				
			}
			name.put(sum.get(i).getYear(), hosName);
			num.put(sum.get(i).getYear(), hosNum);
		}
		
		data.put("name", name);
		data.put("num", num);
		data.put("sum", sum);
		data.put("type", "histogram_hos");
		return data;
	}

	@Override
	public Map<String, Object> getHistogramData_dep() {
		// TODO Auto-generated method stub
		Map<String,Object> data = new HashMap<String,Object>();
		List<OtherHosModel> list =this.otherhos.selectDepartment();
		data.put("data", list);
		data.put("type", "histogram_dep");
		return data;
	}

	@Override
	public Map<String, Object> getHistogramData_doc() {
		// TODO Auto-generated method stub
		Map<String,Object> data = new HashMap<String,Object>();
		List<OtherHosModel> list =this.otherhos.selectDoctor();
		data.put("data", list);
		data.put("type", "histogram_doc");
		return data;
	}

}
