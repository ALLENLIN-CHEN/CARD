package org.scut.mychart.controller;

import com.github.abel533.echarts.json.GsonOption;

import org.scut.mychart.service.IExternalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Allen on 2016/11/30.
 */
@Controller
@RequestMapping(value = "/charts",produces = "text/html;charset=UTF-8")
public class ExternalController {
    @Resource
    private IExternalService externalService;

    @RequestMapping("/external1")
    @ResponseBody
    public String getExternal1(){
          String data = this.externalService.getExternal1Option();
          System.out.println(data);
          return data;  //view?
    }

    @RequestMapping("/external2")
    @ResponseBody
    public String getExternal2(){
        String data =this.externalService.getExternal2Option();
        System.out.println(data);
        return data;
    }

    @RequestMapping("/external3/{place}")
    @ResponseBody
    public String getExternal3(@PathVariable String place){
        String data =this.externalService.getExternal3Option(place);
        System.out.println(data);
        return data;
    }

    @RequestMapping("/external4")
    @ResponseBody
    public String getExternal4(){
        String data =this.externalService.getExternal4Option();
        System.out.println(data);
        return data;
    }

    @RequestMapping("/external5/{year}")
    @ResponseBody
    public String getExternal5(@PathVariable String year){
        String data =this.externalService.getExternal5Option(year);
        System.out.println(data);
        return data;
    }

    @RequestMapping("/external6/{year}")
    @ResponseBody
    public String getExternal6(@PathVariable String year){
        String data =this.externalService.getExternal6Option(year);
        System.out.println(data);
        return data;
    }

    @RequestMapping("/external7/{year}")
    @ResponseBody
    public String getExternal7(@PathVariable String year){
        String data =this.externalService.getExternal7Option(year);
        System.out.println(data);
        return data;
    }

}
