<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- context path -->
<%-- <t:base type="jquery,easyui"></t:base> --%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.2.5/js/modules/exporting.src.js"></script>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			zhengfu();
		});
	});
	function zhengfu(){
		var fromDate=$("input[name='operatetime_begin']").val();
		var toDate=$("input[name='operatetime_end']").val();
		var chart;
		$.ajax({
			type : "POST",
			url : "reportDemoController.do?zhengfuCount&reportType=column&fromDate="+fromDate+"&toDate="+toDate,
			success : function(jsondata) {
				
				data = eval(jsondata);
				console.log(data);
				chart = new Highcharts.Chart({
					chart : {
						renderTo : 'containerCol',
						plotBackgroundColor : null,
						plotBorderWidth : null,
						plotShadow : false
					},
					title : {
						text : '市局发文分析-<b>柱状图</b>'
					},
					xAxis : {
						categories : []
					},
				    yAxis: {
				        title: {
				            text: "发文数"
				        },
				    },			
					tooltip : {
						 percentageDecimals : 1,
						 formatter: function() {
        					return  '<b>'+this.point.name + '</b>:' +  Highcharts.numberFormat(this.percentage, 1) +'%';
     					}

					},
					exporting:{ 
		                filename:'column',  
		                url:'${ctxPath}/reportDemoController.do?export'//
		            },
					plotOptions : {
						column : {
							allowPointSelect : true,
							cursor : 'pointer',
							showInLegend : true,
							dataLabels : {
								enabled : true,
								color : '#000000',
								connectorColor : '#000000',
								formatter : function() {
//									return Highcharts.numberFormat(this.percentage, 1)+"%";
									return this.point.y;
								}
							}
						}
					},
					series : data
				});
				var tmp=data[0];
		        var tmpdata= data[0].data;
		        var xdata=[];
		        for(var i=0;i<tmpdata.length;i++){
		        	xdata.push(tmpdata[i].name)
		        }
		        chart.xAxis[0].setCategories(xdata);
			}
		});
	}
</script>


<script type="text/javascript">
// 	$(function() {
// 		$(document).ready(function() {
// 			var chart;
// 			$.ajax({
// 				type : "POST",
// 				url : "reportDemoController.do?zhengfuCount&reportType=pie",
// 				success : function(jsondata) {
// 					data = eval(jsondata);
// 					chart = new Highcharts.Chart({
// 						chart : {
// 							renderTo : 'containerPie',
// 							plotBackgroundColor : null,
// 							plotBorderWidth : null,
// 							plotShadow : false
// 						},
// 						title : {
// 							text : '班级学生人数比例分析-<b>饼状图</b>'
// 						},
// 						xAxis : {
// 							categories : [ '1班', '2班', '3班', '4班', '5班']
// 						},
// 						tooltip : {
// 							shadow: false,
// 							percentageDecimals : 1,
// 							formatter: function() {
//             					return  '<b>'+this.point.name + '</b>:' +  Highcharts.numberFormat(this.percentage, 1) +'%';
//          					}

// 						},
// 						exporting:{  
// 			                filename:'pie',  
// 			                 url:'${ctxPath}/reportDemoController.do?export'  
// 			            },  
// 						plotOptions : {
// 							pie : {
// 								allowPointSelect : true,
// 								cursor : 'pointer',
// 								showInLegend : true,
// 								dataLabels : {
// 									enabled : true,
// 									color : '#000000',
// 									connectorColor : '#000000',
// 									formatter : function() {
// 										return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 1)+"%";
// 									}
// 								}
// 							}
// 						},
// 						series : data
// 					});
// 				}
// 			});
// 		});
// 	});
</script>
<!-- <span id="containerline" style="float: left; width: 30%; height: 60%"></span> -->
<span id="containerCol" style="float: left; width: 98%; height: 80%"></span>

<!-- <span id="containerPie" style="width: 40%; height: 60%"></span> -->
	<div id="logListtb" style="padding: 3px; height: 25px">
	    <!-- update---Author:赵俊夫  Date:20130507 for：需要加name=searchColums属性 -->
	    <div name="searchColums" style="float: right; padding-right: 15px;">
	       <%--add-begin--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
	        <span>
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="查询时间 ">查询时间: </span>
	            <input type="text" name="operatetime_begin" style="width: 100px; height: 24px;">~
	            <input type="text" name="operatetime_end" style="width: 100px; height: 24px; margin-right: 20px;">
	        </span>
	        <%--add-end--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="zhengfu();">查询</a>
	    </div>
	</div>

<%-- <div style="width: 98%; height: 280px"><t:datagrid name="studentStatisticList" title="市局发文统计" actionUrl="reportDemoController.do?listAllzfStatisticByJdbc" idField="id" fit="true"> --%>
<%-- 	<t:dgCol title="编号" field="id" hidden="false"></t:dgCol> --%>
<%-- 	<t:dgCol title="市局" field="name" width="130"></t:dgCol> --%>
<%-- 	<t:dgCol title="文数" field="y" width="130"></t:dgCol> --%>
<%-- 	<t:dgCol title="比例" field="rate" width="130"></t:dgCol> --%>
<%-- </t:datagrid></div> --%>

<%--add-begin--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
<script type="text/javascript">
$(function() {
    $(document).ready(function(){
        $("input[name='operatetime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("input[name='operatetime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

        $("input").css("height", "24px");
    });
});
</script>
<%--add-end--Author:zhangguoming  Date:20140427 for：添加查询条件  操作时间--%>
