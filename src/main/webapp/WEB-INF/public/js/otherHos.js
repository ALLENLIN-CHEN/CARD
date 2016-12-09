var echart, myChart, charts = { length: 6 };
var timer = null; //主要用于仪表盘等定时器的句柄，每当新的展示需要重置操作
//chart1, chart2, chart3, chart4, chart5 在相应的展示时初始化
var mapGeoData; //用于保存地图数据
var isInit = true; //用于初始化处理单独显示的div宽高获取不到的情况
var isAreaChange = false; //用于判断是否切换了地区
var option;


//初始化设置
$(function() {
	hideLoading();
	$('.area-wrap').hide();
	myChart = echarts.init(document.getElementById('chartMain'));
	$(".tablesorter").tablesorter();

	//When page loads...
	$(".tab_content").hide(); //Hide all content
	$("ul.tabs li:first").addClass("active").show(); //Activate first tab
	$(".tab_content:first").show(); //Show first tab content

	//On Click Event
	$("ul.tabs li").click(function() {

		$("ul.tabs li").removeClass("active ");//Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab_content").hide(); //Hide all tab content

		var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
		$(activeTab).fadeIn(); //Fade in the active ID content
		return false;
	});

	$('.column').equalHeight();
	
	

/**** 用于绑定处理点击事件 ****/
	//主题点击
	$('.item').on('click', function() {
		//清除定时器
		clearInterval(timer);
		
		$('.right-content .single').hide();
		$('.right-content .multi').show();
		$('.sub-item-wrap.active').removeClass('active');
		var self = $(this);
		if(!self.hasClass('active')) {
			$('.sub-' + $('.item.active').data('index')).slideToggle();
			$('.item.active').removeClass('active');
			self.addClass('active');
			$('.sub-' + self.data('index')).slideToggle();
		}
		
		//setMultiCharts();
	});
	
	//子主题的点击
	$(document).on('click', '.sub-item-wrap .type', function() {
		//清除定时器
		clearInterval(timer);
		
		
		if(isInit) {
			//这样写是为了能够让echarts能够得到所设置的width，而不是使用默认的width。 设置完毕后进行hide隐藏掉
			$('.right-content .single').css('visibility','visible').hide();
			
		}
		showLoading();
		
		$('.sub-item-wrap.active').removeClass('active');
		$(this).parent().addClass('active');
		var param = {};
		param.url = $(this).data('url');
		getAjax(param);
		
		$('.area-wrap').hide();
		
		
	
	});
	
/****     结束配置    ****/
	
/*** 配置滚动条 ***/
	$(window).on("load",function(){
		$(".left-content").mCustomScrollbar({
			autoHideScrollbar:true,
			theme:"dark-thick"
		});
		
	});
	
	/**
	 * 用于设定地区按钮的选择
	 */
	$(document).on('click', '.area-wrap .btn', function() {
		if(!$(this).hasClass('active')) {
			$('.area-wrap .btn.active').removeClass('active');
			$(this).addClass('active');
			
			isAreaChange = true;
		}
	});
	
/*** 结束配置 ***/
});

/*** 进行设置多图展示的函数 ***/
//function setMultiCharts() {
//	var active = $('.item.active');
//	var themeId = active.data('id');
//	
//	var options = chartsOption[themeId].options;
//	//将多余的展示div设置成不可见
//	if(options.length < charts.length) {
//		var diff = options.length + 1;
//		for(diff; diff <= charts.length; diff++ ) {
//			$('#chart' + diff).css('visibility','hidden');
//		}
//	}
//	
//	var index = 1;
//	$.each(options, function(key, value) {
//		$('#chart' + index).css('visibility','visible');
//		//初始化用于获得设置echarts的句柄
//		charts['chart'+index] = echart.init(document.getElementById('chart'+index));
//		charts['chart'+index].setOption(value);
//		index++;
//	});
//	
//	if(isInit) {
//		//这样写是为了能够让echarts能够得到所设置的width，而不是使用默认的width。 设置完毕后进行hide隐藏掉
//		$('.right-content .single').css('visibility','visible').hide();
//		isInit = false;
//	}
//	
//	hideLoading();
//	
//}
/*** 结束定义 ***/

/*** 设置进行ajax访问请求 ***/
function getAjax(param) {
	showLoading();

	$.get(param.url, function (res) {
        formatOptionConfig(res);
		hideLoading();
    });
}
/*** 结束设置 ***/


/*** 设置option处理函数 ***/
function formatOptionConfig(data) {
	$('.right-content .single').show();
	$('.right-content .multi').hide();
	
	//初始化用于获得设置echarts的句柄
	myChart.dispose();
	myChart = echarts.init(document.getElementById('chartMain'));
	
	if(!data.type) {
		myChart.setOption(eval('('+ data+')'));
	}else {
		switch(data.type) {
			case 'histogram': 
				setHistogramOption(data);
				break;
			case 'line':
				setLineOption(data);
				break;
			case 'funnel':
				setFunnelOption(data);
			    break;
			case 'panel':
				
				//setPanelOption(data);
				setTimer(data);
				break;
			case 'histogram_hos':
				option=setHostogramHosOption(data);
				myChart.setOption(option);
				myChart.on('timelinechanged',handleTimeLine);
				break;
			default:
		}
	}
}

//设置柱状图option
function setHistogramOption(obj){
	var data = obj.data;
	var years= [];
	var maleNum = [];
	var femaleNum = [];
	var total = [];
	
	for(var i= 0,j=0,k=0,h=0; i<data.length; i++){
		if(i%2==0)
		   years[j++]=data[i].year +'年';
		
		if(data[i].sex=="男") {
			maleNum[k++]=data[i].person_num;
		}
		else{
			femaleNum[h++]=data[i].person_num;
		}	
	}
	for(var i =0;i<maleNum.length;i++){
		total[i]=maleNum[i]+femaleNum[i];
	}
	
	
	
	//app.title = '堆叠柱状图';

	option = {
	    title: {text: "住院登记男女比例分析"},
			tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	        formatter: function(v) {
	        	var i = v[0].dataIndex;
	        	return '<span style="display:inline-block; width:9px; height:9px;border-radius:50%;background-color:red;margin-right:4px;line-height:9px;"></span>男: ' + maleNum[i] + '<br/><span style="display:inline-block; width:9px; height:9px;border-radius:50%;background-color:green;margin-right:4px;line-height:9px;"></span>女: ' + femaleNum[i] + '<br/><span style="display:inline-block; width:9px; height:9px;border-radius:50%;background-color:brown;margin-right:4px;line-height:9px;"></span>总和: ' + total[i];
	        }
	    },
	    legend: {
	        data:['男','女','总和']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data :years
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        
	        
	        {
	            name:'男',
	            type:'bar',
	            stack: '总和',
	            data:maleNum
	        },
	        {
	            name:'女',
	            type:'bar',
	            stack: '总和',
	            data:femaleNum
	        }
	       
	        
	    ]
	};
	myChart.setOption(option);
	
}

//设置折线图option
function setLineOption(obj){
	var data = obj.data;
	var years= [];
	var maleNum = [];
	var femaleNum = [];
	var total = [];
	
	for(var i= 0,j=0,k=0,h=0; i<data.length; i++){
		if(i%2==0)
		   years[j++]=data[i].year;
		
		if(data[i].sex=="男") {
			maleNum[k++]=data[i].person_num;
		}
		else{
			femaleNum[h++]=data[i].person_num;
		}	
	}
	for(var i =0;i<maleNum.length;i++){
		total[i]=maleNum[i]+femaleNum[i];
	}
	

	option = {
			   
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['男','女','总和']
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data :years
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'男',
		            type:'line',
		            stack: '总量',
		            areaStyle: {normal: {}},
		            data:maleNum
		        },
		        {
		            name:'女',
		            type:'line',
		            stack: '总量',
		            areaStyle: {normal: {}},
		            data:femaleNum
		        },
		       
		        {
		            name:'总和',
		            type:'line',
		            stack: '总量',
		            label: {
		                normal: {
		                    show: true,
		                    position: 'top'
		                }
		            },
		            areaStyle: {normal: {}},
		            data:total
		        }
		    ]
		};

	myChart.setOption(option);
}
	
	
//设置仪表盘option
function setPanelOption(obj){
	
	var str;
	var data=obj.data;
	
	var sum=obj.sum;
	var year=obj.year;
	
	var area = $('.area-wrap .btn.active').html();
	//alert(data[area][0]);

	var option = {
			tile:{
				text:"住院登记地区覆盖率"
			},
	
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        feature: {
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    series: [
		        {
		            name: '住院登记地区覆盖率',
		            type: 'gauge',
		            title:{
		            	textStyle:{
		            		fontSize:20
		            	}
		            },
		            detail: {
		            	formatter:'2010年覆盖率{value}%',
		            	textStyle: {
		            		fontSize:16
		            	}
		            	
		            },
		            
		            splitNumber: 2,
		            axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
		                show: true,
		                formatter:function(v) {
		                    if(v <= 25) {
		                        return '低';
		                    }else if(v > 25 && v <= 75) {
		                        return '中';
		                    } else {
		                        return '高';
		                    }
		                },
		                distance: 10,
		                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
		                    fontSize: 16
		                    
		                }
		            },
					axisTick: {
		                // 属性show控制显示与否，默认不显示
		                show: true,
		                // 每份split细分多少段
		                splitNumber: 1
		            },
		            data: [{value: data[area][0].toFixed(2), name: area}]
		        }
		    ]
		};
	return option;
//        return option
//		setInterval(function () {
//		    option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
//		    myChart.setOption(option, true);
//		},2000);

}

function setTimer(obj){
	$('.area-wrap').show();
	option=setPanelOption(obj);
	myChart.setOption(option);
	var dataIndex =1;
	var sum=obj.sum;
	var data=obj.data;
	var y=sum[dataIndex].year;
	
	var area = $('.area-wrap .btn.active').html();
	
	timer=setInterval(function(){
		if(isAreaChange) {
			area = $('.area-wrap .btn.active').html();
			isAreaChange = !isAreaChange;
			dataIndex = 0;
			y=sum[dataIndex].year;
		}
		if(dataIndex>=sum.length){
			dataIndex=0;
			y=sum[dataIndex].year;
		}
		
		option.series[0].data[0] = {
				value : data[area][dataIndex].toFixed(2),
				name : area
		};
		option.series[0].detail.formatter =sum[dataIndex].year + "年覆盖率{value}%",
		dataIndex++;
		myChart.setOption(option, true);
		
	},2000);
	
}
//设置漏斗图option
function setFunnelOption(obj){
	var data = obj.data;
	var sum = obj.total;
	var timeLine=[];
	
	
	for (var i=0,j=0;i<data.length;j++,i=i+4){
		var age=[];
		var num=[];
		
		age=[data[i].age,data[i+1].age,data[i+2].age,data[i+3].age];
		num=[data[i].person_num,data[i+1].person_num,data[i+2].person_num,data[i+3].person_num];
		
		arr = [
			{
				name:age[0],
			    value:num[0]?(num[0]/sum[j]*100).toFixed(2):0
			},
			{
				name:age[1],
			    value:num[1]?(num[1]/sum[j]*100).toFixed(2):0
			},
			{
				name:age[2],
			    value:num[2]?(num[2]/sum[j]*100).toFixed(2):0
			},
			{
				name:age[3],
			    value:num[3]?(num[3]/sum[j]*100).toFixed(2):0
			}
		]
		
		timeLine.push({
			title : {text :data[i].year+"年年龄段数据" },
			series: [
			   {
				  data: arr
			   }
			]
		}
		);
		
	}
	

	
	option = {
	        baseOption: {
	            timeline: {
	                axisType: 'category',
	                autoPlay: true,
	                playInterval: 2000,
	                data: [
	                    '2010', '2011', '2012',
	                    '2013', '2014','2015'
	                ],
	                
	                label: {
	                    formatter : function(s) {
	                        return s+'年';
	                    }
	                }
	            },
	            
	            title: {
	                subtext: '住院登记年龄段统计'
	            },
	            
	            tooltip: {
	                trigger: 'item',
	                formatter: "{a} <br/>{b} : {c}%"
	            },
	            toolbox: {
	                feature: {
	                    dataView: {readOnly: false},
	                    restore: {},
	                    saveAsImage: {}
	                }
	            },
	            legend: {
	                data: ['儿童','青年','中年','老年']
	            },
	            calculable: true,
	            
	            series: [
	                { type: 'funnel'},
	               
	            ]
	        },
	        options:timeLine
	};
	
	myChart.setOption(option);
	
}
//设置仪表盘option
function setHostogramHosOption(obj){
	var name = obj.name;
	var num=obj.num;
	var sum=obj.sum;
	//alert("jdfajd");
	
	
	var years=[];
	for(var i=0;i<sum.length;i++){
		years[i]=sum[i].year;
	}
	var timeLine=[];
	var hosName=[];
	var cur=0;
	for(var i=0;i<years.length;i++){
		var arr =[];
		arr=num[years[i]];
		hosName=name[years[i]];
		timeLine.push({
			title: {text: years[i]+'年医院住院登记数量统计'},
			series: [
				   {
					  name: "年总量",
					  label: {
		                normal: {
		                    show: true
		                }
					  },
					  data: arr
				   }
				]
		})
	}
	
	var option = {
			extended: name,
			baseOption: {
//			
				timeline: {
					axisType: 'category',
					autoPlay: true,
					playInterval: 3000,
                    orient: 'vertical',
                    inverse: true,
                    right: 10,
                    top: 150,
                    bottom: 10,
                    width: 60,
                    data: years,
                    label: {
	                    formatter : function(s) {
	                        return s+'年';
	                    }
	                }
                },
				title: {
					text: '医院住院登记数量统计'
				},
				tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
				toolbox: {
					feature: {
						dataView: {readOnly: true},
						restore: {},
						saveAsImage: {}
					}
				},
				xAxis : [
			       {
			           type : 'category',
			           axisTick : {show: false},
			           axisLabel:{
			        	  interval:0,
	                      rotate:30
	                   },
			           data : name[2010]
	                   
	                   
			       }
			    ],
			    yAxis : [
			       {
			           type : 'value'
			       }
			    ],
				grid: {
			        left: '3%',
			        right: '7%',
			        containLabel: true
			    },
				calculable: true,
				series: [
			         {
			        	type: 'bar'
			         }
				]
			},
			options: timeLine
	};
	
	return option;
	
}
/*** 结束设置 ***/

/*** loading动画 ***/
//加载loading

function handleTimeLine(timeLineData){
	var legends=[];
	var index= timeLineData.currentIndex;
	legends = option.extended[2010+index];
	option.baseOption.xAxis[0].data = legends;
	myChart.setOption(option);

}
function showLoading() {
	$('.spinner').show();
}
//结束loading
function hideLoading() {
	$('.spinner').hide();
}
/*** 结束设置 ***/

