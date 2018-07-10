<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <link rel="stylesheet" href="${ctx}/static/manage/will/css/echarts.css">
  
	<div><label>本月收费额：</label><input id="willcount" readonly="readonly"/></div>
	<div><a id="recentweek" href="javascript:void(0)">近一周</a><br></div>
	<div><a id="recentmonth" href="javascript:void(0)">近一个月</a><br></div>
	<div><a id="recentsixmonth" href="javascript:void(0)">近六个月</a><br></div>
	<div><a  id="excel" style="color:blue" href="${ctx}/manage/payment/export/month">导出表格</a></div>
	<div><a  id="html" style="color:blue" href="${ctx}/manage/payment/exporthtml/month">导出页面</a></div>

   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->  
    <div id="main" style="height:500px;width:1100px;"></div>  
    <!-- ECharts单文件引入 -->  
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>  
    
   <script type="text/javascript">
 	var dataurl='${ctx}/manage/payment/recentmonthdata';
   
   $(document).ready(function() {
	   getCucount();
	   $('#recentweek').click(function(){
		   dataurl='${ctx}/manage/payment/recentweekdata';
		   $("#excel").attr("href","${ctx}/manage/payment/export/week");
		   $("#html").attr("href","${ctx}/manage/payment/exporthtml/week");
		   aa(); 
	   });
	   
 		$('#recentmonth').click(function(){
 			dataurl='${ctx}/manage/payment/recentmonthdata';
 			$("#excel").attr("href","${ctx}/manage/payment/export/month");
 			$("#html").attr("href","${ctx}/manage/payment/exporthtml/month");
 			aa();
	   });
 		
 		$('#recentsixmonth').click(function(){
 			dataurl='${ctx}/manage/payment/recentsixmonthdata';
 			$("#excel").attr("href","${ctx}/manage/payment/export/sixmonth");
 			$("#html").attr("href","${ctx}/manage/payment/exporthtml/sixmonth");
 			aa();
 		});
   });
   
   
   function getCucount(){
	   $.post("${ctx}/manage/payment/currentmonthcount",{},function(r){
		   $('#willcount').val(r.count);
		   aa();
	   });
  }
   
   var getdate=[];  
   var getcount=[]; 
   require.config({  
       paths: {  
           echarts: 'http://echarts.baidu.com/build/dist'    
       }  
   });   
   
  function aa(){
	  
   require([  
               'echarts',
               'echarts/chart/pie',  
               'echarts/chart/scatter',  
              'echarts/chart/bar',  
               'echarts/chart/line'  
           ],function (ec){
	   		getdate=[];
	  		getcount=[]; 
	   		$.post(dataurl, 
		          function(data) {
		        if(data.datas.length>0){
		          for(var i=0;i<data.datas.length;i++){
		            getdate.push(data.datas[i][0]);
		            getcount.push(data.datas[i][1]);
		          }
		        }
		      //绘制统计图  
		        drawFrdjtj(ec);  
		      });
         
   }); 
  }
  
  
   function drawFrdjtj(ec)  
   {  
       /*  $.post('${ctx}/manage/will/data1', 
          function(data) {
        if(data.datas.length>0){
          for(var i=0;i<data.datas.length;i++){
            getdate.push(data.datas[i][0]);
            getcount.push(data.datas[i][1]);
          }
        }
      }); */

       var myChart = ec.init(document.getElementById('main'));  
       var option = {  
               title : {  
                   text: '收费额（单位：元）',  
                   x:'left'  
               },tooltip : {  
                   trigger: 'axis'  
               },  
               legend: { 
            	   data:['报名学员'], 
                   //data:['成立','变更','注销'],  
                   y : 'bottom'  
               },  
                 
               calculable : true,  
               xAxis : [  
                        {  
                            type : 'category',  
                            boundaryGap : false, 
                             data :getdate 
                            //data : ['2000','2001','2002','2003','2004','2005','2006','2007','2008','2009','2010','2011','2012','2013','2014']
                        }  
                    ],  
                    yAxis : [  
                             {  
                                 type : 'value', 
                                 min : 0,
             					           max : 200,
                                 axisLabel : {  
                                     formatter: '{value}'  
                                 },  
                             }  
                ],  
                series : [  
                           // {  
                           //     name:"成立",  
                           //     type:'line',  
                           //     smooth:true,  
                           //     data:['8000','20000','10000','30000','11000'],  
                           //     itemStyle: {  
                           //         normal: {  
                           //             areaStyle: {type: 'default',  
                           //                 //填充颜色  
                           //                 color : 'rgba(95, 165, 85,0.8)'  
                           //                 },  
                           //             }  
                           //     },  
                           //     markPoint : {  
                           //         data : [  
                           //             {type : 'max', name: '最大值'},  
                           //             {type : 'min', name: '最小值'}  
                           //         ]  
                           //     }  
                           // },  
                           // {  
                           //     name:"变更",  
                           //     type:'line',  
                           //     smooth:true,  
                           //     data:['20000','5000','30000','10000','5000'],   
                           //     itemStyle: {  
                           //         normal: {  
                           //             areaStyle: {type: 'default',  
                           //                 //填充颜色  
                           //                 color : 'rgba(255, 127, 80,0.8)'  
                           //                 },  
                           //             }  
                           //     },  
                           //     markPoint : {  
                           //         data : [  
                           //             {type : 'max', name: '最大值'},  
                           //             {type : 'min', name: '最小值'}  
                           //         ]  
                           //     }  
                           // },  
                           {  
                               name:"报名学员",  
                               type:'line',  
                               smooth:true,  
                               data:getcount,
                               //data:['7000','20000','30000','6000','3000','7000','20000','30000','6000','3000','7000','20000','30000','6000','3000'],  
                               itemStyle: {  
                                   normal: {  
                                       areaStyle: {type: 'default',  
                                           //填充颜色  
                                           color : 'rgba(135, 206, 250,0.8)'  
                                           },  
                                       }  
                               },  
                               markPoint : {  
                                   data : [  
                                       {type : 'max', name: '最大值'},  
                                       {type : 'min', name: '最小值'}  
                                   ]  
                               }  
                           }  
               ]  
       };  
       myChart.setOption(option);  
   }   
   	
   </script>
</body>
</html>