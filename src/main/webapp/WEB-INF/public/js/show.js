var easingFn = function (t, b, c, d) {
  var ts = (t /= d) * t;
  var tc = ts * t;
  return b + c * (tc + -3 * ts + 3 * t);
}

var countOptions = {
  useEasing : true, 
  easingFn: easingFn, 
  useGrouping : true, 
  separator : ',', 
  decimal : '.', 
  prefix : '', 
  suffix : '' 
};

var hospIndex = 0;
var depIndex = 0;

var mapChart, areaLineChart, sexPieChart, ageRingChart, count;

var globalData; //保存全局数据
var yearIndex = 2010;

$(function() {
	mapChart = echarts.init(document.getElementById('map-content'));
//	mapChart.setOption(getMap());

	areaLineChart = echarts.init(document.getElementById('area-line'));
//	areaLineChart.setOption(getAreaLine());

	sexPieChart = echarts.init(document.getElementById('sex-pie'));
//	sexPieChart.setOption(getSexPie());

	ageRingChart = echarts.init(document.getElementById('age-ring'));
//	ageRingChart.setOption(getAgeRing());

	initData();
	
	count = new CountUp("count", 0, 0, 0, 1, countOptions);
	count.start();

	scrollHosp();

	scrollDep();
});

/******************************************* 初始化获取全部的数据 *************************************************/
function initData() {
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: 'charts/register/screen',
		async: true,
		success: function(res) {
			globalData = res;
			updateAllData();
			$('.loading').hide();
		},
		error: function() {
			alert('获取数据错误');
		}
	})
}

//用于更新每一年的数据
function updateAllData() {
	var total = 0;
	var index = yearIndex - 2010;
	var keys = Object.getOwnPropertyNames(globalData.area);
	for(var i = 0; i < keys.length; i++) {
		total += globalData.area[keys[i]][index];
	}
	count.update(total);
	
	$('.year').html(yearIndex);
	
	mapChart.setOption(getMap());
	areaLineChart.setOption(getAreaLine());
	sexPieChart.setOption(getSexPie());
	ageRingChart.setOption(getAgeRing());
	
	updateHospList();
	updateDepList();
	
	setTimeout(function() {
		yearIndex++;
		if(yearIndex > 2015) {
			yearIndex = 2010;
		}
		
		updateAllData();
	},17000);
}
/******************************************* 结束初始化获取全部的数据 ***********************************************/

/********************************************* 更新到排行列表 *************************************************/
//更新前10医院
function updateHospList() {
	var hospRange = globalData.hosp[yearIndex];
	var content = [];
	var total = 0;
	var pers = [];
	
	for(var j = 0; j < hospRange.length; j++){
		total += hospRange[j]['sum'];
	}

	for(var z = 0; z < hospRange.length; z++) {
		pers.push((hospRange[z]['sum'] / total * 100).toFixed(2));
	}
	
	var min = Math.min.apply(null, pers);
	var max = Math.max.apply(null, pers);
	var index = 0;
	index = (min + '').indexOf('.');
	min = (min - (0.1 + (min + '')[index+2] - 0)).toFixed(2);
	max = parseFloat((max + 0.1).toFixed(1)).toFixed(2);
	var dis = max - min;
	
	for(var i = 0; i < hospRange.length; i++) {
		content.push('<div class="column-content">\
								<div class="index-list">\
									<div class="index">' + (i+1) + '</div>\
								</div>\
								<div class="column hosp-name">'+ hospRange[i]['hospital'] +'</div>\
								<div class="column hosp-business">'+ hospRange[i]['sum'] +'</div>\
								<div class="column hosp-percent">\
									<div class="wrap-progress">\
										<div class="progress" style="width: '+ ((pers[i] - min) / dis * 100).toFixed(2) +'%"></div>\
									</div>\
								</div>\
							</div>');
	}
	
	$('.wrap-hosp-table .mask').html(content.join(''));
	hospIndex = 0;
}

//更新前10医院科室
function updateDepList() {
	var depRange = globalData.dep[yearIndex];
	var content = [];
	
	for(var i = 0; i < depRange.length; i++) {
		content.push('<div class="column-content">\
						<div class="index-list">\
						<div class="index">'+ (i+1) +'</div>\
					</div>\
					<div class="column hosp-name">'+ depRange[i].hospital +'</div>\
					<div class="column dep-name">' + depRange[i].department + '</div>\
					<div class="column dep-business">'+ depRange[i].sum +'</div>\
				</div>');
	}
	
	$('.wrap-dep-table .mask').html(content.join(''));
	depIndex = 0;
}

/********************************************* 结束更新到排行列表 ***********************************************/

/*********************************** 获取echarts option ****************************************/
function getMap() {
	var i = yearIndex - 2010;
	var option = {
		tooltip: {
        	trigger: 'item',
        	formatter: function(item) {
        		return '<span style="display:inline-block;width:10px;height:10px;border-radius:50%;background-color:rgba(51, 253, 191, 1);margin-right:5px;"></span>' + item.data.name + '<br/>' + '业务数: ' + item.data.value[2];
        	}
        },
		geo: {
            map: 'xiaogan',
            roam: true,
            aspectScale: 1,
            zoom: 1.2, //放大的倍数
            label: {
            	normal: {
	                show: false,
	                textStyle: {
                        color: '#fff',
                        fontSize: 16
                    }
	            },
              	emphasis: {
                  show: false
              	}
	        },
            itemStyle: {
	            normal: {
	                areaColor: 'rgba(255, 255, 255, 0.1)',
	                borderColor: '#fff'
	            },
	            emphasis: {
	                areaColor: 'rgba(255, 255, 255, 0.1)'
	            }
       		}
        },
	    series: [
	        {
	            name: 'Top 5',
	            type: 'effectScatter',
	            coordinateSystem: 'geo',
	            data: [
		            {
		            	name: '大悟县', value: [114.126249,31.565483, globalData.area['大悟县'][i]]
		            },
		            {
		            	name: '孝昌县', value: [113.988964,31.251618, globalData.area['孝昌县'][i]]
		            },
		            {
		            	name: '云梦县', value: [113.750616,31.021691, globalData.area['云梦县'][i]]
		            },
		            {
		            	name: '孝南区', value: [113.925849,30.925966, globalData.area['孝南区'][i]]
		            },
		            {
		            	name: '应城市', value: [113.573842,30.939038, globalData.area['应城市'][i]]
		            },
		            {
		            	name: '安陆市', value: [113.690401,31.26174, globalData.area['安陆市'][i]]
		            },
		            {
		            	name: '汉川市', value: [113.835301,30.652165, globalData.area['汉川市'][i]]
		            }
	            ],
	            symbolSize: function (val) {
	                return val[2] / 150;
	            },
	            showEffectOn: 'render',
	            rippleEffect: {
	                brushType: 'stroke',
	                scale: 2
	            },
	            hoverAnimation: true,
	            label: {
	                normal: {
	                    formatter: '{b}',
	                    position: 'right',
	                    show: true,
	                    textStyle: {
	                    	fontSize: 18
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    color: 'rgba(51, 253, 191, 0.8)',
	                    shadowBlur: 10,
	                    shadowColor: '#333'
	                }
	            },
	            zlevel: 1111
	        }
	    ]
	};

	return option;
}

function getAreaLine() {
	var option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	    	textStyle: {
                    color: '#fff',
                },
	        data: ['孝南区','孝昌县','应城市','云梦县','汉川市', '大悟县', '安陆市']
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: false,
	        axisLabel: {
	        	textStyle: {
                    color: '#fff',
                    fontSize: 13
                }
	        },
	        axisTick: {
	        	show: false
	        },
	        axisLine: {
	        	show: false
	        },
	        data: ['2010年','2011年','2012年','2013年','2014年','2015年']
	    },
	    yAxis: {
	        type: 'value',
	        axisLabel: {
	        	textStyle: {
                    color: '#fff',
                    fontSize: 13
                },
	            formatter: '{value}'
	        },
	        splitLine: {
	            show: false
	        },
	        axisLine: {
	        	show: false
	        }
	    },
	    series: [
	        {
	            name:'孝南区',
	            type:'line',
	            data: globalData.area["孝南区"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'孝昌县',
	            type:'line',
	            data: globalData.area["孝昌县"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'应城市',
	            type:'line',
	            data: globalData.area["应城市"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'云梦县',
	            type:'line',
	            data: globalData.area["云梦县"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'汉川市',
	            type:'line',
	            data: globalData.area["汉川市"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'大悟县',
	            type:'line',
	            data: globalData.area["大悟县"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        },
	        {
	            name:'安陆市',
	            type:'line',
	            data: globalData.area["安陆市"],
	            markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            }
	        }
	    ]
	};

	return option;
}

function getSexPie() {
	var i = yearIndex - 2010;
	var femaleNum = globalData.female[i];
	var maleNum = globalData.male[i];
	var total = femaleNum + maleNum;
	
	var option = {
		title: {
			text: '男女占比分析',
			textStyle: {
                color: '#fff'
            }
		},
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    series : [
	    	{
	            type: 'pie',
	            radius : ['0%', '5%'],
	            center: ['50%', '60%'],
	            data:[ 0 ],
	            itemStyle: {
	                normal: {
	                    color: '#fff'
	                }
	            }
	        },
	        {
	            name: '男女占比分析',
	            type: 'pie',
	            radius : ['5%','80%'],
	            center: ['50%', '60%'],
	            data:[
	                {value: femaleNum, name:'女('+ (femaleNum/total*100).toFixed(1) +'%)'},
	                {value: maleNum, name:'男('+ (maleNum/total*100).toFixed(1) +'%)'}
	            ],
	            label: {
	                normal: {
                        position: 'inner',
                        textStyle: {
                            fontSize: 14
                        }
                    },
                },
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};

	return option;
}

function getAgeRing() {
	var range = globalData.age[yearIndex];
	var ageData = [];

	if(range['child']) {
		ageData.push({
			value: range['child'], 
			name:'儿童'
		});
	}
	
	if(range['youth']) {
		ageData.push({
			value: range['youth'], 
			name:'青少年'
		});
	}
	
	if(range['midlife']) {
		ageData.push({
			value: range['midlife'], 
			name:'中年'
		});
	}
	
	if(range['older']) {
		ageData.push({
			value: range['older'], 
			name:'老年'
		});
	}
	
	var option = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    title: {
	        text: '挂号年龄分布',
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    series : [
	        {
	            name:'年龄占比',
	            type:'pie',
	            radius: ['50%', '79%'],
	            center: ['49%', '60%'],
	            // width: '35%',
            	funnelAlign: 'left',
	            itemStyle : {
	                normal : {
	                    label : {
	                        position : 'inner',
	                        formatter: '{d}%'
	                    },
	                    labelLine : {
	                        show : false
	                    }
	                }
	            },
	            data: ageData
	        },
	        {
	            name:'年龄占比',
	            type:'pie',
	            radius: ['45%', '79%'],
	            center: ['49%', '60%'],
            	funnelAlign: 'left',
	            itemStyle : {
	                normal : {
	                    label : {
	                        position : 'outer',
	                        textStyle: {
		                		color: '#fff',
		                		fontSize: 14
		                	},
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                	lineStyle: {
	                        color: 'rgba(255, 255, 255, .5)'
	                    },
	                    smooth: 0.2,
	                    length: 0,
	                    length2: 18
	                }
	            },
	            data: ageData
	        }
	    ]
	};

	return option;
}

function getHospBar() {
	var hospRange = globalData.hosp[yearIndex];
	var hosps = [];
	var hospData = [];
	for(var i = 0; i < hospRange.length; i++) {
		hosps.push(hospRange[i]['hospital']);
		hospData.push({
			value: hospRange[i]['sum'],
			name: hospRange[i]['hospital']
		});
	}
	
	var option = {
			tooltip: {
	        trigger: 'item'
	    },
	    yAxis: {
	        type: 'value',
	        splitLine: {
	            show: false
	        }
	    },
	    xAxis: {
	        type: 'category',
	        data: hosps,
	        axisLabel: {
	            interval: 0,
	            rotate: 30,
	            textStyle: {
	            	color: '#fff'
	            }
	        },
	        splitLine: {
	            show: false
	        }
	    },
	    series: [{
	        type: 'bar',
	        z: 3,
	        label: {
	            normal: {
	                show: true
	            }
	        },
	        data: hospData 
	    }]
	};

	return option;
}
function getHospPie() {
	var option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            name:'医院排名',
	            type:'pie',
	            selectedMode: 'single',
	            radius: [0, '30%'],

	            label: {
	                normal: {
	                    position: 'inner',
	                    textStyle: {
	                        fontSize: 12
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                {value:0, name:'医院排名'},
	                
	            ]
	        },
	        {
	            name:'医院排名',
	            type:'pie',
	            radius: ['40%', '55%'],
	            label: {
	                normal: {
	                    formatter: '{b}-{d}%',
	                    textStyle: {
	                        color: '#fff'
	                    }
	                }
	            },
	            data:[
	                
	                {value:14, name:'汉川市人民医院'},
	                {value:12, name:'孝感现代俪人医院'},
	                {value:11, name:'大悟县人民医院'},
	                {value:10, name:'云梦县人民医院'},
	                {value:9, name:'安陆市第二人民医院'},
	                {value:9, name:'孝感市妇幼保健院'},
	                {value:6, name:'汉川市中医院'},
	                {value:29, name:'孝昌县人民医院'},
	                {value:29, name:'应城市妇幼保健院'},
	                {value:29, name:'孝感市中医医院'}
	            ]
	        }
	    ]
	};

	return option;
}
/***************************************************************************/


/*********************** 用于定时更新的函数 ************************/

//滚动前10医院
function scrollHosp() {
	var hospContents = $('.wrap-hosp-table .column-content');
	var scrollNum = 0;
	var offset = 44;
	var top;
	if(hospContents.length > 4) {
		scrollNum = hospContents.length - 4;
		if(hospIndex <= scrollNum) {
			top = 0 - offset * hospIndex;
			$('.wrap-hosp-table .mask').css({top: top + 'px'});
			hospIndex++;
		}else {
			hospIndex = 0;
		}
	}

	setTimeout(function() {
		scrollHosp();
	}, 2000);
}

//滚动前10科室
function scrollDep() {
	var depContents = $('.wrap-dep-table .column-content');
	var scrollNum = 0;
	var offset = 44;
	var top;
	if(depContents.length > 4) {
		scrollNum = depContents.length - 4;
		if(depIndex <= scrollNum) {
			top = 0 - offset * depIndex;
			$('.wrap-dep-table .mask').css({top: top + 'px'});
			depIndex++;
		}else {
			depIndex = 0;
		}
	}

	setTimeout(function() {
		scrollDep();
	}, 2000);
}

/***********************************************/