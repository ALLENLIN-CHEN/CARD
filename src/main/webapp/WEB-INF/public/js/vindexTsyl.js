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
			isInit = !isInit;
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
	
	option = getCharts(data);
	
	myChart.setOption(option);

	hideLoading();
	
	//非挂号年龄段分析时进行timeline函数的解绑
	myChart.off('timelinechanged',changeLegendShowByTimeLine);
	
	if(data.type === 'TSYL_GAUGE') {
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
	} else if(data.type === 'TSYL_FUNNEL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} else if(data.type === 'TSYL_BAR_HOSPITAL_TOTAL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} else if(data.type === 'TSYL_BAR_DEPARTMENT_TOTAL') {
		myChart.on('timelinechanged',changeLegendShowByTimeLine);
	} 
}

/**
 * 用于处理时间轴的为0的legend不显示的情况
 */
function changeLegendShowByTimeLine(timeLineData) {
	if(chartType === 'TSYL_FUNNEL') {
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
	} else if(chartType === 'TSYL_BAR_HOSPITAL_TOTAL') {
		var legends = [];
		var index = timeLineData.currentIndex;
		legends = option.extended[2010 + index];
		option.baseOption.xAxis[0].data = legends;
		myChart.setOption(option);
	} else if(chartType === 'TSYL_BAR_DEPARTMENT_TOTAL') {
		var legends = [];
		var index = timeLineData.currentIndex + 2010;
		legends = option.extended.departments[index];
		option.baseOption.xAxis[0].data = legends;
		option.baseOption.tooltip.formatter = function(v) {
			var i = v[0].dataIndex;
			return option.extended.hospitals[index][i] + "-" + option.extended.departments[index][i] + "</br>数量: " + option.options[index-2010].series[0].data[i];
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