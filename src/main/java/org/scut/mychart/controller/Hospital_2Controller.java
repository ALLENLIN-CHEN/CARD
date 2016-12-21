package org.scut.mychart.controller;

import com.github.abel533.echarts.json.GsonOption;
import org.scut.mychart.service.Hospital_2Service;
import org.scut.mychart.util.DictionaryString;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//echarts字符云
@Controller  
@RequestMapping(value = "/charts/hos_2", produces="application/json;charset=UTF-8")
public class Hospital_2Controller {
    @Autowired
    private Hospital_2Service hospital_2Service;

    @RequestMapping("/hospital_2_2")
    @ResponseBody
    public Map<String, Object> getHos_2(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_2ChartOption());
        return  result;
    }

    @RequestMapping("/hospital_2_3")
    @ResponseBody
    public Map<String, Object> getHos_3(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_3ChartOption());
        return  result;
    }

    @RequestMapping("/hospital_2_4/{place}")
    @ResponseBody
    public Map<String, Object> getHos_4(@PathVariable String place){
        String[] p = new String[1];
        if(place.equals("xiaonanqu")){
            p[0]="孝南区";
        }else if(place.equals("xiaochangxian")){
            p[0]="孝昌县";
        }else if(place.equals("yingchengshi")){
            p[0]="应城市";
        }else if(place.equals("yunmengxian")){
            p[0]="云梦县";
        }else if(place.equals("hanchuanshi")){
            p[0]="汉川市";
        }else if(place.equals("dawuxian")){
            p[0]="大悟县";
        }else if(place.equals("anlushi")){
            p[0]="安陆市";
        }else p[0] = place;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", "HOS_2_4");
        result.put("data", this.hospital_2Service.getHospital_2_4ChartOption(p));
        return  result;
    }

    @RequestMapping("/hospital_2_5")
    @ResponseBody
    public Map<String, Object> getHos_5(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_5ChartOption());
        return  result;
    }

    @RequestMapping("/hospital_2_6")
    @ResponseBody
    public Map<String, Object> getHos_6(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_6810ChartOption(6));
        return  result;
    }

    @RequestMapping("/hospital_2_8")
    @ResponseBody
    public Map<String, Object> getHos_8(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_6810ChartOption(8));
        return  result;
    }

    @RequestMapping("/hospital_2_10")
    @ResponseBody
    public Map<String, Object> getHos_10(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_6810ChartOption(10));
        return  result;
    }

    @RequestMapping("/hospital_2_7")
    @ResponseBody
    public Map<String, Object> getHos_7(@RequestParam String startTime,@RequestParam String endTime){
        String[] p = new String[2];
        p[0]=startTime;
        p[1]=endTime;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_7911ChartOption(7,p));
        return  result;
    }

    @RequestMapping("/hospital_2_9")
    @ResponseBody
    public Map<String, Object> getHos_9(@RequestParam String startTime,@RequestParam String endTime){
        String[] p = new String[2];
        p[0]=startTime;
        p[1]=endTime;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_7911ChartOption(9,p));
        return  result;
    }

    @RequestMapping("/hospital_2_11")
    @ResponseBody
    public Map<String, Object> getHos_11(@RequestParam String startTime,@RequestParam String endTime){
        String[] p = new String[2];
        p[0]=startTime;
        p[1]=endTime;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", this.hospital_2Service.getHospital_2_7911ChartOption(11,p));
        return  result;
    }
}   

