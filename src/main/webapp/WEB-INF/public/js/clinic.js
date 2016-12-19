 var myChart;
 var option;
 var chartType;
 var timer = null; //主要用于仪表盘等定时器的句柄，每当新的展示需要重置操作
 var isInit = true; //用于初始化处理单独显示的div宽高获取不到的情况
 var isAreaChange = false; //用于判断是否切换了地区

$(function() {
	hideLoading();
	
	myChart = echarts.init(document.getElementById('chartMain'));
/***********************************************************************************************************/	
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
/***********************************************************************************************************/
	
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
		
	//	setMultiCharts();		
	});
		
	$(document).on('click', '.sub-item-wrap .type', function() {
		//清除定时器
		clearInterval(timer);
		myChart.dispose();
		myChart = echarts.init(document.getElementById('chartMain'));
		if(isInit) {
			//这样写是为了能够让echarts能够得到所设置的width，而不是使用默认的width。 设置完毕后进行hide隐藏掉
			$('.right-content .single').css('visibility','visible').hide();
		}
		
		showLoading();
		
		$('.sub-item-wrap.active').removeClass('active');
		$(this).parent().addClass('active');
		
		if(!$('.area-wrap').is(':hidden')) {
			$('.area-wrap').hide();
		}
		if(!$('.time_wrap').is(':hidden')) {
			$('.time_wrap').hide();
		}
		
		if(!$(this).data('no-init')) {
			var url = $(this).data('url');
			$.ajax({
				type: 'GET',
				url: url,
				dataType: 'json',
				success: function(res) {
					handleCharts(res);
				},
				error: function(err) {
					alert('获取数据出错，错误为：' + err);
				}
			});
		}else {
			hideLoading();
			$('.time_wrap').show();
		}		
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
	
	/**
	 * 绑定时间查询的确定按钮
	 */
	$(document).on('click', '.time_wrap .search', function() {
		showLoading();
		
		var startTime = $('.startTime').val() - 0;
		var endTime = $('.endTime').val() - 0;
		if(endTime < startTime) {
			alert('起始时间不能大于结束时间！');
			hideLoading();
			return;
		}
		
		var url = $('.sub-item-wrap.active .type').data('url');
		var params = {
				startTime: startTime,
				endTime: endTime
		}
				
		$.ajax({
			type: 'GET',
			url: url,
			dataType: 'json',
			data: params,
			success: function(res) {
				handleCharts(res);
			},
			error: function(err) {
				alert('获取数据出错，错误为：' + err);
			}
		});
	});
	
		
	/*** 配置滚动条 ***/
	$(window).on("load",function(){
		$(".left-content").mCustomScrollbar({
			autoHideScrollbar:true,
			theme:"dark-thick"
		});
		
	});
	/*** 结束配置 ***/
});


/**
 * 用于展示echarts图表
 */
function handleCharts(data) {
	$('.right-content .single').show();
	
	chartType = data.type;
	
	option = getChart(data);
	
	myChart.setOption(option);

	hideLoading();
	
	myChart.off('timelinechanged',changeLegendShowByTimeLine);
	
	if(data.type === 'CLINIC_GAUGE') {
		var dataIndex = 1; //用于记录数据的展示索引
		var year = 2011;
		var area = $('.area-wrap .btn.active').html();
		
		timer = setInterval(function() {
			if(isAreaChange) {
				area = $('.area-wrap .btn.active').html();
				isAreaChange = !isAreaChange;
				dataIndex = 0;
				year = 2010;
			}
			if(dataIndex >= data.coverage[area].length) {
				dataIndex = 0;
			}
			
			if(year >= 2015) {
				year = 2010;
			}
			
			option.series[0].data[0] = {
				value : data.coverage[area][dataIndex],
				name : area
			};
			option.series[0].detail.formatter = year + "年覆盖率{value}%";
			dataIndex++;
			year++;
			myChart.setOption(option, true);
		}, 2000);
	} else if(data.type === 'CLINIC_FUNNEL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} else if(data.type === 'CLINIC_BAR_HOSPITAL_TOTAL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} else if(data.type === 'CLINIC_BAR_DEPARTMENT_TOTAL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} else if(data.type === 'CLINIC_BAR_DOCTOR_TOTAL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} 
}

/**
 * 用于处理时间轴的为0的legend不显示的情况
 */
function changeLegendShowByTimeLine(timeLineData) {
	if(chartType === 'CLINIC_FUNNEL') {
		var legends = ['0-6岁（儿童）', '7-40（青少年）', '41-65（中年）', '66以上（老年）'];
		var setting = {};
		var index = timeLineData.currentIndex;
		var sData = option.options[index].series[0].data;
		for(var i = 0; i < sData.length; i++) {
			if(sData[i].value <= 0) {
				setting[sData[i].name] = false;
			}else {
				setting[sData[i].name] = true;
			}
		}
		option.baseOption.legend.selected = setting;
		myChart.setOption(option);
	} else if(chartType === 'CLINIC_BAR_HOSPITAL_TOTAL') {
		var legends = [];
		var index = timeLineData.currentIndex;
		legends = option.extended[2010 + index];
		option.baseOption.xAxis[0].data = legends;
		myChart.setOption(option);
	} else if(chartType == 'CLINIC_BAR_DEPARTMENT_TOTAL') {
		var legends = [];
		var index = timeLineData.currentIndex + 2010;		
		option.baseOption.tooltip.formatter = function(v) {
			var i = v[0].dataIndex;
			return option.extended.tipcontent[index][i];
		};
		myChart.setOption(option);
	} else if(chartType =='CLINIC_BAR_DOCTOR_TOTAL'){
		var legends = [];
		var index = timeLineData.currentIndex + 2010;		
		option.baseOption.tooltip.formatter = function(v) {
			var i = v[0].dataIndex;
			return option.extended.tipcontent[index][i];
		};
		myChart.setOption(option);
	}
}

/*** loading动画 ***/
//加载loading
function showLoading() {
	$('.spinner').show();
}
//结束loading
function hideLoading() {
	$('.spinner').hide();
}
/*** 结束设置 ***/



/**
 * 用于处理不同类型的图表
 */
function getChart(data) {
	if(data.type === 'CLINIC_BAR_X') {
		return getBar(data);
	}else if(data.type === 'CLINIC_LINE') {
		return getLine(data);
	} else if(data.type === 'CLINIC_GAUGE') {
		$('.area-wrap').show();
		return getGauge(data);
	} else if(data.type === 'CLINIC_FUNNEL') {
		return getFunnel(data);
	} else if(data.type === 'CLINIC_BAR_HOSPITAL_TOTAL') {
		return getHospitalTotal(data);
	} else if(data.type === 'CLINIC_BAR_DEPARTMENT_TOTAL') {
		return getDepartmentTotal(data);
	}  else if(data.type === 'CLINIC_BAR_DOCTOR_TOTAL') {
		return getDoctorTotal(data);
	}
}
/***********************************/

/**
 * 生成各图表的方法
 */

function getBar(data) {
	var option = {
			title: {
		        text: '男女申请人数统计'
		    },
			tooltip : {
				show:true,
		        trigger: 'axis',
		        axisPointer : {        
		            type : 'shadow'     
		        }
		    },
		    legend: {
		        data:['男', '女']
		    },
		    toolbox:{
		    	show:true,
		        feature: {	        	
		            dataView : {show: true, readOnly: false},	                     
		            saveAsImage : {show: true}	           
		        }
		    },		  
		    grid:{
				height:'73%'
			},
		    xAxis : [
	             {
	            	 type : 'category',
	            	 axisTick : {show: true},
			         data : ['2010年', '2011年', '2012年', '2013年', '2014年', '2015年']
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
		            label: {
		            	normal:{
		            		show: true,
		            		position: 'top'
		            	}
		            },
		            data: data.male
		        },
		        {
		            name:'女',
		            type:'bar',
		            label: {
		            	normal:{
		            		show: true,
		            		position: 'top'
		            	}
		            },
		            data: data.female
		        }
		    ]
		};
	
	return option;
}


function getLine(data) {
	var option = {
			title:{
		    	text:'门诊统筹人数随时间变化情况'
		    },
		    legend:{
		    	data:['男','女']
		    },
		    toolbox:{
		    	show:true,
		    	feature:{
		    		dataView:{show:true,readonly:true},
		    		saveAsImage : {show: true}
		    	}
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    calculable:true,
		    grid:{
				height:'73%'
			},
	    	xAxis : [
	             {	            	 
	                 type : 'category',
	                 boundaryGap : false,
	                 data : ['2010年','2011年','2012年','2013年','2014年','2015年']
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
	 	 	                 data:data.male,	                
	 	 	                 label: {
	 	 	 	                normal: {
	 	 	 	                    show: true,
	 	 	 	                    position: 'top'
	 	 	 	                }
	 	 	 	             }	             
	 	 	             },
	 	 	             {
	 	 	                 name:'女',
	 	 	                 type:'line',
	 	 	                 data:data.female,	                
	 	 	                 label: {
	 	 		 	                normal: {
	 	 		 	                    show: true,
	 	 		 	                    position: 'top'
	 	 		 	                }
	 	 		 	         }                   
	 	 	             }
	 	 	         ]	    
	};	
	return option;
}


function getGauge(data) {
	var area = $('.area-wrap .btn.active').html();
	var option = {
		title: {
	        text: '门诊统筹申请地区覆盖率'
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
	    grid:{
			height:'73%'
		},
	    series: [
	        {	           
	            type: 'gauge',
	            title : {
	                textStyle: { 
	                    fontSize: 20,
	                }
	            },
	            detail: {
	            	formatter:'2010年覆盖率{value}%',
	            	textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontSize: 16	                    
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
	            data: [{value: data.coverage[area][0], name: area}]
	        }
	    ]
	};
	
	return option;
}







function getFunnel(data) {
	var age = {};
	var range= [];
	var arr = [];
	var timeLineOptions = [];
	var props = Object.getOwnPropertyNames(data.ageRange);
	for(var index in props) {
		age = data.ageRange[props[index]];
		arr = [
		    {
		    	name: '0-6岁（儿童）',
		    	value: age.child?(age.child / age.total * 100).toFixed(2):0
		    },
		    {
		    	name: '7-40（青少年）',
		    	value: age.youth?(age.youth / age.total * 100).toFixed(2):0
		    },
		    {
		    	name: '41-65（中年）',
		    	value: age.midlife?(age.midlife / age.total * 100).toFixed(2):0
		    },
		    {
		    	name: '66以上（老年）',
		    	value: age.older?(age.older / age.total * 100).toFixed(2):0
		    }
		];
		
		timeLineOptions.push({
			title : {text: props[index] + '年门诊统筹申请年龄段占比'},
			series: [
			   {
				  data: arr
			   }
			]
		});
	}
	
	var option = {
			baseOption: {
				timeline: {
					axisType: 'category',
					autoPlay: true,
					playInterval: 2000,
					data: [
						'2010年','2011年','2012年','2013年','2014年','2015年'
					]
				},				
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c}%"
				},
				toolbox: {
					feature: {
						dataView: {readOnly: true},
						restore: {},
						saveAsImage: {}
					}
				},
				legend: {
					itemGap:5,
					padding:[40,20,20,20],
					data: ['0-6岁（儿童）','7-40（青少年）','41-65（中年）','66以上（老年）'],
					selected:{
						'0-6岁（儿童）':true,
						'7-40（青少年）':true,
						'41-65（中年）':true,
						'66以上（老年）':true
					}
				},
				grid:{
					height:'73%'					
				},
				calculable: true,
				series: [
			         {
			        	 name:'年龄段占比',
			        	 type: 'funnel'
			         }
				]
			},
			options: timeLineOptions
	};
	
	return option;
}





function getHospitalTotal(data) {
	var timeLineOptions = [];
	var props = Object.getOwnPropertyNames(data.rank);
	var num;
	var hospitals;
	var yRank;
	
	for(var index in props) {
		num = [];
		hospitals=[];		
		yRank = data.rank[props[index]];			
		for(var i = 0; i < yRank.length; i++) {
			num.push(yRank[i].amount);		
			hospitals.push(yRank[i].hospital);	
		}
		
		timeLineOptions.push({
			title : {text: props[index] + '年医院门诊统筹申请数量'},			
			xAxis:{				
				data:hospitals			
			},			
			series: [
			   {
				  name: "年总量",
				  type:'bar',
				  label: {
		            	normal:{
		            		show: true,
		            		position: 'top'
		            	}
		          },
				  data: num
			   }
			]
		});
	}
	
	var option = {
			timeline: {
				axisType: 'category',
				autoPlay: true,
				playInterval: 3000,
				loop:true,
				orient:'horizontal',
				height:'6%',
                data: ['2010年','2011年','2012年','2013年','2014年','2015年']                                        
            },
			baseOption: {								
				tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }			        
			    },
				toolbox: {
					feature: {
						dataView: {show:true,readOnly: true},
						restore: {},
						saveAsImage: {}
					}
				},
				grid:{
					height:'70%'
				},
				xAxis : [
			       {
			    	   type : 'category',
			           axisTick : {show: false},
			           axisLabel:{
			        	  interval:0,
	                      rotate:20
	                   }				           
			       }
			    ],
			    yAxis : [
			       {
			    	   type : 'value'			    	  	         
			       }
			    ],				
				calculable: true,
				series: [
			         {
			        	type: 'bar'
			         }
				]
			},
			options: timeLineOptions
	};
	
	return option;
}



function getDepartmentTotal(data) {
	var timeLineOptions = [];
	var props = Object.getOwnPropertyNames(data.rank);
	var yRank;
	var num;	
	var departments;
	var tipcontent={};
	
	for(var index in props) {
		num = [];		
		departments=[];		
		yRank = data.rank[props[index]];
		tipcontent[props[index]]=[];
		for(var i = 0; i < yRank.length; i++) {
			num.push(yRank[i].amount);	
			departments.push(yRank[i].department);
			tipcontent[props[index]].push(yRank[i].department+'('+yRank[i].hospital+')'+'<br/>总量：'+yRank[i].amount);
		}
		
		timeLineOptions.push({			//添加timeline每个点所对应的图表
			title : {text: props[index] + '年医院医生门诊统筹申请数量'},		
			xAxis:{						
				data:departments			
			},			
			series: [
			   {
				  type:'bar',
				  label: {
		            	normal:{
		            		show: true,
		            		position: 'top'
		            	}
		            },
				  data: num
			   }
			]
		});
	}
	
	var option = {
			extended:{tipcontent:tipcontent},			
			baseOption: {		
				timeline: {
					axisType: 'category',
					autoPlay: true,
					playInterval: 3000,
					loop:true,
					orient:'horizontal',
	                data: ['2010年','2011年','2012年','2013年','2014年','2015年']                                        
	            },
				toolbox: {
					feature: {
						dataView: {show:true,readOnly: true},
						restore: {},
						saveAsImage: {}
					}
				},							    
				tooltip:{
					show:true,
					trigger: 'axis',
				    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				    },
				    formatter: function(params) {
				    	var i=params[0].dataIndex;
			        	return tipcontent[2010][i];
			        }
				},				
				grid:{
					height:'73%'
				},
				xAxis : [
			       {
			           type : 'category',
			           axisTick : {show: true},
			           axisLabel:{
			        	  interval:0,
			        	  rotate:20
	                   },
			       }
			    ],
			    yAxis : [
			       {
			           type : 'value'
			       }
			    ],				
				calculable: true,
				series: [
			         {
			        	type: 'bar'
			         }
				]
			},
			options: timeLineOptions
	};
	
	return option;
	
}



function getDoctorTotal(data){
	var timeLineOptions = [];
	var props = Object.getOwnPropertyNames(data.rank);
	var yRank;
	var num;	
	var doctors;
	var hospitals;
	var departments;
	var tipcontent={};
	
	for(var index in props) {
		num = [];
		doctors=[];
		hospitals=[];
		departments=[];		
		yRank = data.rank[props[index]];
		tipcontent[props[index]]=[];
		for(var i = 0; i < yRank.length; i++) {
			num.push(yRank[i].amount);	
			doctors.push(yRank[i].doctor);		
			tipcontent[props[index]].push(yRank[i].doctor+'('+yRank[i].hospital+'-'+yRank[i].department+')'+'<br/>总量：'+yRank[i].amount);
		}
		
		timeLineOptions.push({			//添加timeline每个点所对应的图表
			title : {text: props[index] + '年医院科室门诊统筹申请数量'},		
			xAxis:{						
				data:doctors			
			},			
			series: [
			   {
				  type:'bar',
				  label: {
		            	normal:{
		            		show: true,
		            		position: 'top'
		            	}
		            },		       
				  data: num
			   }
			]
		});
	}
	
	var option = {
			extended:{tipcontent:tipcontent},			
			baseOption: {		
				timeline: {
					axisType: 'category',
					autoPlay: true,
					playInterval: 3000,
					loop:true,
					orient:'horizontal',
	                data: ['2010年','2011年','2012年','2013年','2014年','2015年']                                        
	            },
				toolbox: {
					feature: {
						dataView: {show:true,readOnly: true},
						restore: {},
						saveAsImage: {}
					}
				},							    
				tooltip:{
					show:true,
					trigger: 'axis',
				    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				    },
				    formatter: function(params) {
				    	var i=params[0].dataIndex;
			        	return tipcontent[2010][i];
			        }
				},				
				grid:{
					height:'73%'
				},
				xAxis : [
			       {
			           type : 'category',
			           axisTick : {show: true},
			           axisLabel:{
			        	  interval:0			
	                   },
			       }
			    ],
			    yAxis : [
			       {
			           type : 'value'
			       }
			    ],				
				calculable: true,
				series: [
			         {
			        	type: 'bar'
			         }
				]
			},
			options: timeLineOptions
	};
	
	return option;
}