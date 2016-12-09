/**
 * 用于处理不同类型的图表
 */
function getCharts(data) {
	if(data.type === 'EXPENSE_BAR_X') {
		return getExpenseBarX(data);
	}else if(data.type === 'EXPENSE_LINE') {
		return getExpenseLine(data);
	} else if(data.type === 'EXPENSE_GAUGE') {
		$('.area-wrap').show();
		return getExpenseGauge(data);
	} else if(data.type === 'EXPENSE_FUNNEL') {
		return getExpenseFunnel(data);
	} else if(data.type === 'EXPENSE_BAR_HOSPITAL_TOTAL') {
		return getExpenseHospitalTotal(data);
	} else if(data.type === 'EXPENSE_BAR_DEPARTMENT_TOTAL') {
		return getExpenseDepartmentTotal(data);
	}
}
/***********************************/

/**
 * 生成各图表的方法
 */
// 以X轴为值轴
function getExpenseBarX(data) {
	var option = {
			title: {
		        text: '男女报销比例'
		    },
			tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:['男', '女']
		    },
			toolbox: {
				show : true,
				feature : {
					mark : {show: true},
					dataView : {show: true, readOnly: true},
					restore : {show: true},
					saveAsImage : {show: true}
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
	                 type : 'value'
	             }
	        ],
		    yAxis : [
		        {
		            type : 'category',
		            axisTick : {show: false},
		            data : ['2010年', '2011年', '2012年', '2013年', '2014年', '2015年']
		        }
		    ],
		    series : [
		        {
		            name:'男',
		            type:'bar',
		            label: {
		                normal: {
		                    show: true,
		                    position: 'inside'
		                }
		            },
		            data: data.male
		        },
		        {
		            name:'女',
		            type:'bar',
		            stack: '总量',
		            label: {
		                normal: {
		                    show: true
		                }
		            },
		            data: data.female
		        }
		    ]
		};
	
	return option;
}


function getExpenseLine(data) {
	var option = {
	    title: {
	        text: '男女报销人数统计'
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	    	data:['男', '女']
	    },
		toolbox: {
			show : true,
			feature : {
				dataView : {show: true, readOnly: true},
				restore : {show: true},
				saveAsImage : {show: true}
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
	            data: ['2010年', '2011年', '2012年', '2013年', '2014年', '2015年']
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
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            areaStyle: {normal: {}},
	            data: data.male
	        },
	        {
	            name:'女',
	            type:'line',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'top'
	                }
	            },
	            areaStyle: {normal: {}},
	            data: data.female
	        }
	    ]
	};
	
	return option;
}

function getExpenseGauge(data) {
	var area = $('.area-wrap .btn.active').html();
	var option = {
		title: {
	        text: '报销地区覆盖率'
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
	            name: '报销地区覆盖率',
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

function getExpenseFunnel(data) {
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
			title : {text: props[index] + '年年龄段占比'},
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
				title: {
					text: '报销年龄段统计'
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
					 data: ['0-6岁（儿童）','7-40（青少年）','41-65（中年）','66以上（老年）']
				},
				calculable: true,
				series: [
			         {
			        	type: 'funnel'
			         }
				]
			},
			options: timeLineOptions
	};
	
	return option;
}

function getExpenseHospitalTotal(data) {
	var timeLineOptions = [];
	var props = Object.getOwnPropertyNames(data.rank);
	var arr;
	var hospitals = [];
	var allHospitals = {};
	
	for(var index in props) {
		arr = [];
		yRank = data.rank[props[index]];
		allHospitals[props[index]] = [];
		for(var i = 0; i < yRank.length; i++) {
			arr.push(yRank[i].person_num);
			if(parseInt(index) === 0) {
				hospitals.push(yRank[i].hospital);
			}
			
			allHospitals[props[index]].push(yRank[i].hospital);
		}
		
		timeLineOptions.push({
			title : {text: props[index] + '年医院报销数量统计'},
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
		});
	}
	
	var option = {
			extended: allHospitals,
			baseOption: {
//				timeline: {
//					axisType: 'category',
//					autoPlay: true,
//					playInterval: 3000,
//					data: [
//						'2010年','2011年','2012年','2013年','2014年','2015年'
//					]
//				},
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
                    data: ['2010年','2011年','2012年','2013年','2014年','2015年']
                },
				title: {
					text: '医院报销数量统计'
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
			           data : hospitals
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
			options: timeLineOptions
	};
	
	return option;
}

function getExpenseDepartmentTotal(data) {
	var timeLineOptions = [];
	var props = Object.getOwnPropertyNames(data.rank);
	var arr;
	var departments = [];
	var allDepartments = {};
	var allHospitals = {};
	
	for(var index in props) {
		arr = [];
		yRank = data.rank[props[index]];
		allDepartments[props[index]] = [];
		allHospitals[props[index]] = [];
		for(var i = 0; i < yRank.length; i++) {
			arr.push(yRank[i].person_num);
			if(parseInt(index) === 0) {
				departments.push(yRank[i].department);
			}
			
			allDepartments[props[index]].push(yRank[i].department);
			allHospitals[props[index]].push(yRank[i].hospital);
		}
		
		timeLineOptions.push({
			title : {text: props[index] + '年医院科室报销数量统计'},
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
		});
	}
	
	var option = {
			extended: {hospitals: allHospitals, departments: allDepartments},
			baseOption: {
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
                    data: ['2010年','2011年','2012年','2013年','2014年','2015年']
                },
				title: {
					text: '医院科室报销数量统计'
				},
				tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }, 
			        formatter: function(v) {
			        	var i = v[0].dataIndex;
			        	return allHospitals[2010][i] + '-' + allDepartments[2010][i] + "</br>数量: " + timeLineOptions[0].series[0].data[i];
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
			           data : departments
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
			options: timeLineOptions
	};
	
	return option;
}