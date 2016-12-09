package org.scut.mychart.service.impl;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Gauge;
import com.github.abel533.echarts.series.Venn;
import org.scut.mychart.mapper.ProofMapper;
import org.scut.mychart.model.Chartvenn;
import org.scut.mychart.service.ProofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProofServiceImpl implements ProofService {
    @Autowired
    private ProofMapper chartsDao;


    public HashMap<String, Chartvenn> selectPersonnum(String type1, String type2) {
        HashMap<String, Chartvenn> result = new HashMap<String, Chartvenn>();
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("type1", type1);
        param.put("type2", type2);
        List<Chartvenn> list12 = this.chartsDao.selectPersonnum12(param);
        List<Chartvenn> list3 = this.chartsDao.selectPersonnum3(param);
        for (int i = 0; i < list12.size(); i++) {
            Chartvenn i3 = list3.get(i);
            Chartvenn i12 = list12.get(i);
            if (!i3.getyear().equals(i12.getyear())) return null;
            Chartvenn item = result.get(i3.getyear().toString());
            if (item == null) {
                item = i12;
                item.setperson_num3(i3.getperson_num3());
                result.put(item.getyear().toString(), item);
            }
        }
        return result;
    }

    public String getChartVennOption(String type1, String type2) {
        String title=type1 + "与" + type2;
        HashMap<String, Chartvenn> result = this.selectPersonnum(type1,type2);

        GsonOption option = new GsonOption();

        List yearlist = Arrays.asList(result.keySet().toArray());
        Collections.sort(yearlist);
        option.timeline().setData(yearlist);
        option.timeline().setAutoPlay(true);
        option.calculable(true);

        List<Option> optionlist = new ArrayList<Option>();
        for (int i =0;i< yearlist.size(); i++) {
            Chartvenn item = result.get(yearlist.get(i));
            Option option1 = new GsonOption();
            option1.title(title).title().x(X.center);
            option1.toolbox()
                    .show(true)
//                    .orient(Orient.vertical)
//                    .x(X.right)
//                    .y(Y.center)
                    .feature( Tool.dataView, Tool.restore, Tool.saveAsImage, Tool.mark);
            option1.setCalculable(true);
            Venn data1 = new Venn();
            Gauge data2 = new Gauge();

            HashMap<String, Object> tem = new HashMap<String, Object>();
            tem.put("value", item.getperson_num1());
            tem.put("name", type1);
            data1.data(tem);
            tem = new HashMap<String, Object>();
            tem.put("value", item.getperson_num2());
            tem.put("name", type2);
            data1.data(tem);
            tem = new HashMap<String, Object>();
            tem.put("value", item.getperson_num3());
            tem.put("name", "公共");
            data1.data(tem);

            data1.itemStyle().normal().label().show(true).textStyle().fontFamily("Arial, Verdana, sans-serif").fontSize(16);
            data1.itemStyle().normal().labelLine().show(false).length(10).lineStyle().width(1).type(LineType.solid);
            data1.itemStyle().emphasis().color("#cc99cc").borderWidth(3).borderColor("#996699");
            data1.name(title+"交集");

            tem = new HashMap<String, Object>();

            tem.put("value", new java.text.DecimalFormat("#.00").format(item.getperson_num3().doubleValue()/item.getperson_num1().doubleValue()*100));
            tem.put("name", "转化率");
            data2.data(tem);
            data2.itemStyle().normal().label().show(true).textStyle();
            data2.itemStyle().normal().labelLine().show(false).length(10).lineStyle().width(1).type(LineType.solid);
            data2.itemStyle().emphasis().color("#cc99cc").borderWidth(3).borderColor("#996699");
            data2.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}%");
            data2.detail().formatter("{value}%");
            data2.center("65%","50%");
            data2.radius(0,"65%");
            data2.startAngle(140).endAngle(-140);
            data2.name(title);
            option1.series(data1,data2);
            option1.tooltip().trigger(Trigger.item).formatter("{b}: {c}");
            optionlist.add(option1);
        }

        option.options(optionlist);
        System.out.println(option.toString());
        return option.toString();
    }

}

