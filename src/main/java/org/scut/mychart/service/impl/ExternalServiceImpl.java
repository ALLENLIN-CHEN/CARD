package org.scut.mychart.service.impl;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.MapData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.*;
import com.github.abel533.echarts.series.force.Category;
import org.scut.mychart.mapper.ExternalMapper;

import org.scut.mychart.model.*;
import org.scut.mychart.redis.*;
import org.scut.mychart.service.IExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Allen on 2016/11/30.
 */
@Service("externalService")
public class ExternalServiceImpl implements IExternalService{
    @Resource
    private ExternalMapper externaldao;

    @Autowired
    private ExternalRedisDao externalRedisDao;

    @Autowired
    private External2RedisDao external2RedisDao;

    @Autowired
    private External3RedisDao external3RedisDao;

    @Autowired
    private External4RedisDao external4RedisDao;

    @Autowired
    private External5RedisDao external5RedisDao;

    @Autowired
    private External6RedisDao external6RedisDao;

    @Autowired
    private External7RedisDao external7RedisDao;

    //拼接标题
    public String getPlace(String place) {
        String text=" ";
        if (place.equals("xiaonanqu")){
            text="孝南区";
            return text;
        }else if (place.equals("xiaochangxian")){
            text="孝昌县";
            return text;
        }else if (place.equals("dawuxian")){
            text="大悟县";
            return text;
        }else if (place.equals("yunmengxian")){
            text="云梦县";
            return text;
        }else if (place.equals("anlushi")){
            text="安陆市";
            return text;
        }else if (place.equals("yingchengshi")){
            text="应城市";
            return text;
        }else if (place.equals("hanchuanshi")){
            text="汉川市";
            return text;
        }else {
            return null;
        }
    }

    //拼接标题
    public String getTime(String year) {
        String text=" ";
        if (year.equals("2010")){
            text="2010";
            return text;
        }else if (year.equals("2011")){
            text="2011";
            return text;
        }else if (year.equals("2012")){
            text="2012";
            return text;
        }else if (year.equals("2013")){
            text="2013";
            return text;
        }else if (year.equals("2014")){
            text="2014";
            return text;
        }else if (year.equals("2015")){
            text="2015";
            return text;
        }else {
            return null;
        }
    }

    /**
     * redis的柱状图图key值返回
     */
    public  String getExternalRedisKey(){ return ChartTypeConstant.EXTERNAL_REDIS;}

    /**
     * redis的折线图图key值返回
     */
    public String getExternal2RedisKey(){return ChartTypeConstant.EXTERNAL2_REDIS;}

    /**
     * redis的仪表盘图key值返回
     */
    public String getExternal3RedisKey(String place) {
        if(place.equals("xiaonanqu")) {
            return ChartTypeConstant.EXTERNAL3_XIAONANQU_REDIS;
        }else if(place.equals("xiaochangxian")){
            return ChartTypeConstant.EXTERNAL3_XIAOCHANGQU_REDIS;
        }else if(place.equals("dawuxian")){
            return ChartTypeConstant.EXTERNAL3_DAWUXIAN_REDIS;
        }else if(place.equals("yunmengxian")){
            return ChartTypeConstant.EXTERNAL3_YUNMENGXIAN_REDIS;
        }else if(place.equals("anlushi")){
            return ChartTypeConstant.EXTERNAL3_ANLUSHI_REDIS;
        } else if(place.equals("yingchengshi")){
            return ChartTypeConstant.EXTERNAL3_YINGCHENGSHI_REDIS;
        }  else {
            return ChartTypeConstant.EXTERNAL3_HANCHUANSHI_REDIS;
        }
    }

    /**
     * redis的漏斗图key值返回
     */
    public String getExternal4RedisKey(){return ChartTypeConstant.EXTERNAL4_REDIS;}

    /**
     * redis的医院low10图key值返回
     */
    public String getExternal5RedisKey(String year) {
        if (year.equals("2010")) {
            return ChartTypeConstant.EXTERNAL5_2010_REDIS;
        } else if (year.equals("2011")) {
            return ChartTypeConstant.EXTERNAL5_2011_REDIS;
        } else if (year.equals("2012")) {
            return ChartTypeConstant.EXTERNAL5_2012_REDIS;
        } else if (year.equals("2013")) {
            return ChartTypeConstant.EXTERNAL5_2013_REDIS;
        } else if (year.equals("2014")) {
            return ChartTypeConstant.EXTERNAL5_2014_REDIS;
        } else {
            return ChartTypeConstant.EXTERNAL5_2015_REDIS;
        }
    }

    /**
     * redis的科室low10图key值返回
     */
    public String getExternal6RedisKey(String year) {
        if (year.equals("2010")) {
            return ChartTypeConstant.EXTERNAL6_2010_REDIS;
        } else if (year.equals("2011")) {
            return ChartTypeConstant.EXTERNAL6_2011_REDIS;
        } else if (year.equals("2012")) {
            return ChartTypeConstant.EXTERNAL6_2012_REDIS;
        } else if (year.equals("2013")) {
            return ChartTypeConstant.EXTERNAL6_2013_REDIS;
        } else if (year.equals("2014")) {
            return ChartTypeConstant.EXTERNAL6_2014_REDIS;
        } else {
            return ChartTypeConstant.EXTERNAL6_2015_REDIS;
        }
    }

    /**
     * redis的医生low10图key值返回
     */
    public String getExternal7RedisKey(String year) {
        if (year.equals("2010")) {
            return ChartTypeConstant.EXTERNAL7_2010_REDIS;
        } else if (year.equals("2011")) {
            return ChartTypeConstant.EXTERNAL7_2011_REDIS;
        } else if (year.equals("2012")) {
            return ChartTypeConstant.EXTERNAL7_2012_REDIS;
        } else if (year.equals("2013")) {
            return ChartTypeConstant.EXTERNAL7_2013_REDIS;
        } else if (year.equals("2014")) {
            return ChartTypeConstant.EXTERNAL7_2014_REDIS;
        } else {
            return ChartTypeConstant.EXTERNAL7_2015_REDIS;
        }
    }

    public List<External1> getExternal1List() {
        return this.externaldao.selectExternal1();
    }

    //匹配不同的区，取出相关字段，返回各区人数统计数据对象
    public List<External3> getExternal3List(String place){
        HashMap<String,String> param = new HashMap<String,String>();

        if(place.equals("xiaonanqu")) {
            param.put("area","孝南区");
        }else if(place.equals("xiaochangxian")){
            param.put("area","孝昌县");
        }else if(place.equals("dawuxian")){
            param.put("area","大悟县");
        }else if(place.equals("yunmengxian")){
            param.put("area","云梦县");
        }else if (place.equals("anlushi")){
            param.put("area","安陆市");
        }else if (place.equals("yingchengshi")){
            param.put("area","应城市");
        }else if (place.equals("hanchuanshi")){
            param.put("area","汉川市");
        } else{
            return null;
        }
       return this.externaldao.selectExternal3(param);
    }

    public List<External4> getExternal4List() {
        return this.externaldao.selectExternal4();
    }

    //匹配不同的年，取出相关字段，返回各年人数统计数据对象
    public List<External5> getExternal5List(String year){
        HashMap<String,String> param = new HashMap<String,String>();

        if(year.equals("2010")) {
            param.put("year","2010");
        }else if(year.equals("2011")){
            param.put("year","2011");
        }else if(year.equals("2012")){
            param.put("year","2012");
        }else if(year.equals("2013")){
            param.put("year","2013");
        }else if(year.equals("2014")){
            param.put("year","2014");
        }else if(year.equals("2015")){
            param.put("year","2015");
        }else{
            return null;
        }
        return this.externaldao.selectExternal5(param);
    }

    public List<External6> getExternal6List(String year){
        HashMap<String,String> param = new HashMap<String,String>();

        if(year.equals("2010")) {
            param.put("year","2010");
        }else if(year.equals("2011")){
            param.put("year","2011");
        }else if(year.equals("2012")){
            param.put("year","2012");
        }else if(year.equals("2013")){
            param.put("year","2013");
        }else if(year.equals("2014")){
            param.put("year","2014");
        }else if(year.equals("2015")){
            param.put("year","2015");
        }else{
            return null;
        }
        return this.externaldao.selectExternal6(param);
    }

    public List<External7> getExternal7List(String year){
        HashMap<String,String> param = new HashMap<String,String>();

        if(year.equals("2010")) {
            param.put("year","2010");
        }else if(year.equals("2011")){
            param.put("year","2011");
        }else if(year.equals("2012")){
            param.put("year","2012");
        }else if(year.equals("2013")){
            param.put("year","2013");
        }else if(year.equals("2014")){
            param.put("year","2014");
        }else if(year.equals("2015")){
            param.put("year","2015");
        }else{
            return null;
        }
        return this.externaldao.selectExternal7(param);
    }

    public String getExternal1Option() {

        String type =getExternalRedisKey();

        String externalData=externalRedisDao.getExternalData(type);
        if(externalData != null && !externalData.isEmpty()) {
            return externalData;
        }

        GsonOption option = new GsonOption();


        option.title("用卡行为分析-异地就医申请男女数量统计");
        option.tooltip().trigger(Trigger.axis);
        option.legend().data("男", "女");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);

        //生成category 和 series

        CategoryAxis category = new CategoryAxis();
        Bar male = new Bar();
        Bar female = new Bar();

        List<External1> totalList = getExternal1List();
        for (External1 list:totalList){
            if (list.getSex().equals("男")){
                category.data(list.getYear()+"年");
                male.data(list.getNumsex());
            }else{
                female.data(list.getNumsex());
            }
        }

        male.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        male.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        female.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        female.markLine().data(new PointData().type(MarkType.average).name("平均值"));
        male.name("男");
        female.name("女");

        option.xAxis(category);
        option.yAxis(new ValueAxis());  //金额
        option.series(male, female);

        //插入缓存中
        externalRedisDao.setExternalData(type,option.toString());

        System.out.println(option.toString());

        return option.toString();



    }

    @Override
    public String getExternal2Option() {
        String type = getExternal2RedisKey();

        String external2Data=external2RedisDao.getExternal2Data(type);
        if(external2Data != null && !external2Data.isEmpty()) {
            return external2Data;
        }

        GsonOption option = new GsonOption();

        List<External1> linelist =getExternal1List();

        CategoryAxis categoryAxis=new CategoryAxis();

        option.title("用卡行为分析-异地就医申请男女数量统计");
        option.tooltip().trigger(Trigger.axis);
        option.legend().data("男", "女");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);

        Line male=new Line();
        Line female=new Line();


        Integer malenum =0;
        Integer femalenum=0;



        for (External1 list:linelist){
            if(list.getSex().equals("男")){
                malenum =list.getNumsex();
                male.data(malenum);
                categoryAxis.data(list.getYear()+"年");
            }else {
                femalenum=list.getNumsex();
                female.data(femalenum);
            }


        }

        male.name("男");
        female.name("女");
        //total.name("总数").stack("总量");


        option.xAxis(categoryAxis);
        option.yAxis(new ValueAxis());
        option.series(male,female);


        external2RedisDao.setExternal2Data(type,option.toString());

        System.out.println(option.toString());

        return option.toString();
    }

    @Override
    public String getExternal3Option(String place) {
        GsonOption optionGroup=new GsonOption();

        String redisKey = getExternal3RedisKey(place);

        /**
         * 获取redis缓存数据,调试时要实时更新数据进redis需要把return esternalData注释掉
         */
        String external3Data = external3RedisDao.getExternal3Data(redisKey);
        if(external3Data != null && !external3Data.isEmpty()) {
            return external3Data;
        }

        String PLACE =getPlace(place);

        List<String> yearlist =new ArrayList<>();

        List<External3> gaugeList =getExternal3List(place);

        Integer num=0;
        Integer numgu=0;
        double rat=0.0;

        java.util.Map<String,Double> mapdata=new HashMap<>();

        for (External3 gaugupicList:gaugeList){
            String year=gaugupicList.getYear();
            num=gaugupicList.getNum();
            numgu=gaugupicList.getNumgu();
            rat=(double)numgu/num;
            DecimalFormat df = new DecimalFormat("0.00");
            double rattar=Double.valueOf(df.format(rat*100)).doubleValue();
            if (!yearlist.contains(year)){
                yearlist.add(year);
            }

            mapdata.put(year,rattar);

        }

        optionGroup.timeline()
                .autoPlay(true)
                .playInterval(1000)
                .setData(yearlist);
        List<Option> options = new ArrayList<Option>();

        for(String year:yearlist){
            Option option=new Option();
            Gauge cover=new Gauge();
            option.title().text(PLACE+"异地就医的持卡人分布区域的覆盖率").subtext(year+"仪表盘分析");
            option.tooltip().formatter("{a} <br/>{b} : {c}%");
            option.toolbox().show(true).feature(Tool.mark, Tool.dataView,
                    Tool.restore, Tool.saveAsImage);
            option.calculable(true);
            cover.data(mapdata.get(year),"覆盖率");
            cover.name(PLACE);
            cover.detail().formatter("{value}%");
            option.series(cover);
            options.add(option);
        }
        optionGroup.options(options);

        /**
         * 加入redis缓存
         */
        external3RedisDao.setExternal3Data(redisKey,optionGroup.toString());
        return optionGroup.toString();
    }

    @Override
    public String getExternal4Option() {
        GsonOption optionGroup = new GsonOption();

        String rediskey=getExternal4RedisKey();

        /**
         * 首先获取redis缓存数据
         */
        String external4Data = external4RedisDao.getExternal4Data(ChartTypeConstant.EXTERNAL4_REDIS);
        if(external4Data != null && !external4Data.isEmpty()) {
            return external4Data;
        }

        List<External4> external4List=getExternal4List();
        java.util.Map<String,List<MapData>> data=new HashMap<String, List<MapData>>();
        List<String> yearList=new ArrayList();

        for(External4 funnelChart:external4List){
            String year=String.valueOf(funnelChart.getYear());
            String age=funnelChart.getAge();
            Integer count_person=funnelChart.getNum();
            List mapdataList=data.get(year);
            if(mapdataList==null){
                mapdataList=new ArrayList();
                data.put(year,mapdataList);
            }
            MapData mapData=new MapData(age,count_person);
            mapdataList.add(mapData);
            if(!yearList.contains(year)){
                yearList.add(year);
            }
        }

        java.util.Map<String,List<MapData>> data1=new HashMap<String, List<MapData>>();
        int sum=0,sum1=0,sum2=0,sum3=0,sum4=0;
        double rat1=0.0,rat2=0.0,rat3=0.0,rat4=0.0;

        for(String year:yearList){
            List <MapData>dataList=data.get(year);
            for(MapData list01:dataList){
                String age=list01.getName();
                Integer num=(Integer)list01.getValue();
                if(age.equals("童年")){
                    sum1+=num;
                }
                if(age.equals("青少年")){
                    sum2+=num;
                }
                if(age.equals("中年")){
                    sum3+=num;
                }
                if(age.equals("老年")){
                    sum4+=num;
                }
            }
            sum=sum1+sum2+sum3+sum4;
            rat1=(double)sum1/sum;
            rat2=(double)sum2/sum;
            rat3=(double)sum3/sum;
            rat4=(double)sum4/sum;
            DecimalFormat df1 = new DecimalFormat("0.00");
            String[] ageList={"0-7岁（童年）","7-40岁（青少年）","41-65岁（中年）","66岁以上（老年）"};
            String[] ratList={df1.format(rat1*100),df1.format(rat2*100),df1.format(rat3*100),df1.format(rat4*100)};
            List <MapData>dataList1=new ArrayList<MapData>();
            for(int i=0;i<4;i++){
                MapData nummap=new MapData(ageList[i],ratList[i]);
                dataList1.add(nummap);

            }
            data1.put(year, dataList1);
        }


        optionGroup.timeline()
                .autoPlay(true)
                .playInterval(1000)
                .setData(yearList);
        List<Option> options = new ArrayList<Option>();

        for (String year : yearList) {
            Option option = new Option();
            option.title().text("异地就医各年龄段人数占比统计").subtext(year + "年度漏斗图分析");
            option.legend().data("0-7岁（童年）","7-40岁（青少年）","41-65岁（中年）","66岁以上（老年）").show(true);
            option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c}%");
            option.toolbox().show(true).feature(Tool.mark, Tool.dataView,
                    Tool.restore, Tool.saveAsImage);
            option.calculable(true);

            Funnel funnel = new Funnel();
            funnel.itemStyle().normal().borderColor("#fff").borderWidth(1).label().show(true).position("inside");
            funnel.itemStyle().normal().borderColor("#fff").borderWidth(1).labelLine().show(false).length(10).lineStyle().width(1).type(LineType.solid);
            funnel.itemStyle().emphasis().borderColor("red").borderWidth(1).label().show(true).formatter("{b}:{c}%").textStyle().fontSize(20);
            funnel.itemStyle().emphasis().borderColor("red").borderWidth(1).labelLine().show(true);

            funnel.name("各年龄段人数百分比（单位：%）").type(SeriesType.funnel)
                    .x("10%").y(60).y2(60).
                    width("80%")
                    .min(0).max(100).maxSize("100%").minSize("0%").gap(10)
                    .sort(Sort.descending).setData(data1.get(year));

            option.series(funnel);
            //add option
            options.add(option);
        }
        optionGroup.options(options);

        /**
         * 加入redis缓存
         */
        external4RedisDao.setExternal4Data(rediskey,optionGroup.toString());
        return optionGroup.toString();
    }

    @Override
    public String getExternal5Option(String year) {
        GsonOption option=new GsonOption();

        String redisKey = getExternal5RedisKey(year);

        /**
         * 获取redis缓存数据,调试时要实时更新数据进redis需要把return esternalData注释掉
         */
        String external5Data = external5RedisDao.getExternal5Data(redisKey);
        if(external5Data != null && !external5Data.isEmpty()) {
            return external5Data;
        }

        String TIME =getTime(year);

        List<External5> external5List=getExternal5List(year);

        CategoryAxis categoryAxis=new CategoryAxis();

        option.title().text("用卡行为分析-异地就医申请医院low10统计").subtext(TIME+"年度分析");
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);

        Bar count =new Bar();

        for (External5 External5List:external5List){
            count.data(External5List.getNum());
            count.type(SeriesType.bar);
            //count.name(External5List.getHospital());
            categoryAxis.data(External5List.getHospital());
            //count.tooltip().trigger(Trigger.axis).formatter(External5List.getHospital()).axisPointer().type(shadow);
        }
        option.xAxis(new ValueAxis().boundaryGap(0d,0.01));
        option.yAxis(categoryAxis);
        option.grid().borderWidth(0);

        option.series(count);


        external5RedisDao.setExternal5Data(redisKey,option.toString());

        System.out.println(option.toString());

        return option.toString();

    }

    @Override
    public String getExternal6Option(String year) {
        GsonOption option=new GsonOption();

        String redisKey = getExternal6RedisKey(year);

        /**
         * 获取redis缓存数据,调试时要实时更新数据进redis需要把return esternalData注释掉
         */
        String external6Data = external6RedisDao.getExternal6Data(redisKey);
        if(external6Data != null && !external6Data.isEmpty()) {
            return external6Data;
        }

        String TIME =getTime(year);

        List<External6> external6List=getExternal6List(year);

        CategoryAxis categoryAxis=new CategoryAxis();

        option.title().text("用卡行为分析-异地就医申请科室low10统计").subtext(TIME+"年度分析");

        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        Bar count =new Bar();

        for (External6 External6List:external6List){

            count.data(External6List.getNum());
            count.type(SeriesType.bar);
            categoryAxis.data(External6List.getHospital()+"\n"+External6List.getDepartment());


        }

        option.series(count);

        option.xAxis(new ValueAxis().boundaryGap(0d,0.01));
        option.yAxis(categoryAxis);
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        external6RedisDao.setExternal6Data(redisKey,option.toString());

        System.out.println(option.toString());

        return option.toString();
    }

    @Override
    public String getExternal7Option(String year) {
        GsonOption option=new GsonOption();

        String redisKey = getExternal7RedisKey(year);

        /**
         * 获取redis缓存数据,调试时要实时更新数据进redis需要把return esternalData注释掉
         */
        String external7Data = external7RedisDao.getExternal7Data(redisKey);
        if(external7Data != null && !external7Data.isEmpty()) {
            return external7Data;
        }

        String TIME =getTime(year);

        List<External7> external7List=getExternal7List(year);

        CategoryAxis categoryAxis=new CategoryAxis();

        option.title().text("用卡行为分析-异地就医申请科室low10统计").subtext(TIME+"年度分析");
        option.tooltip().trigger(Trigger.axis).axisPointer().type(PointerType.shadow);
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.bar),
                Tool.restore, Tool.saveAsImage);
        option.calculable(true);

        Bar count =new Bar();

        for (External7 External7List:external7List){
            count.data(External7List.getNum());
            count.type(SeriesType.bar);
            categoryAxis.data(External7List.getHospital()+"\n"+External7List.getDepartment()+" "+External7List.getDoctor());
        }
        option.xAxis(new ValueAxis().boundaryGap(0d,0.01));
        option.yAxis(categoryAxis);
        option.series(count);


        external7RedisDao.setExternal7Data(redisKey,option.toString());

        System.out.println(option.toString());

        return option.toString();
    }
}



