<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.actiona{
	background-color:blue;
	color:white;
	width:50px;
	height:30px;
	
}
</style>

</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/manage/will/css/echarts.css">
  <div class="headdiv">
  <div class="labeltitle">数据统计</div>
	<div class="headtitle1">
	   <div class="eachdiv color1">
        <label>本月报名人数：</label>
        <input id="willcount" readonly="readonly"/>
        <label>人</label>
     </div>
    <div class="eachdiv color2">
      <label>本月咨询人数：</label>
      <input id="totalcount" readonly="readonly"/>
      <label>人</label>
    </div>
    <div class="eachdiv color3">
     <label>本月收费金额：</label>
      <input id="paymentcount" readonly="readonly"/>
      <label>元</label>
    </div>
    <div class="eachdiv color4">
     <label>本月新课程数：</label>
      <input id="coursecount" readonly="readonly"/>
      <label>门</label>
    </div>
  </div>
  </div>

	
	
	<div id="ldiv" class="leftdiv">
    <div class="maintitle">
        <a id="recentweek" href="javascript:void(0)">近7日</a>
        <a id="recentmonth" href="javascript:void(0)">近1月</a>
        <a id="recentsixmonth" href="javascript:void(0)">近6月</a>
        <a  id="excel" style="color:blue" href="${ctx}/manage/will/export/month">导出表格</a>
        <a  id="html" style="color:blue" href="${ctx}/manage/will/exporthtml/month">导出页面</a>
    </div>
   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->  
    <div id="main"></div> 
  </div> 

  <div id="rdiv" class="rightdiv">
    <div class="maintitle">
        <a id="recentweek2" href="javascript:void(0)">近7日</a>
        <a id="recentmonth2" href="javascript:void(0)">近1月</a>
        <a id="recentsixmonth2" href="javascript:void(0)">近6月</a>
        <a  id="excel2" style="color:blue" href="${ctx}/manage/payment/export/month">导出表格</a>
        <a  id="html2" style="color:blue" href="${ctx}/manage/payment/exporthtml/month">导出页面</a>
    </div>
   <!-- 为ECharts准备一个具备大小（宽高）的Dom -->  
    <div id="main2"></div> 
  </div> 
    
  


  <!-- ECharts单文件引入 -->  
  <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
  <script type="text/javascript">
   var dataurl='${ctx}/manage/will/recentmonthdata';
   var dataurl2='${ctx}/manage/payment/recentmonthdata';
   
   $(document).ready(function() {
	   getCucount();
     getCucount2();

     //will数据
	   $('#recentweek').click(function(){
		   dataurl='${ctx}/manage/will/recentweekdata';
		   $("#excel").attr("href","${ctx}/manage/will/export/week");
		   $("#html").attr("href","${ctx}/manage/will/exporthtml/week");
		   willecharts(); 
	   });
	   
 		$('#recentmonth').click(function(){
 			dataurl='${ctx}/manage/will/recentmonthdata';
 			$("#excel").attr("href","${ctx}/manage/will/export/month");
 			$("#html").attr("href","${ctx}/manage/will/exporthtml/month");
 			willecharts();
	   });
 		
 		$('#recentsixmonth').click(function(){
 			dataurl='${ctx}/manage/will/recentsixmonthdata';
 			$("#excel").attr("href","${ctx}/manage/will/export/sixmonth");
 			$("#html").attr("href","${ctx}/manage/will/exporthtml/sixmonth");
 			willecharts();
 		});

    //payment数据
    $('#recentweek2').click(function(){
       dataurl2='${ctx}/manage/payment/recentweekdata';
       $("#excel2").attr("href","${ctx}/manage/payment/export/week");
       $("#html2").attr("href","${ctx}/manage/payment/exporthtml/week");
       paymentecharts(); 
     });
     
    $('#recentmonth2').click(function(){
      dataurl2='${ctx}/manage/payment/recentmonthdata';
      $("#excel2").attr("href","${ctx}/manage/payment/export/month");
      $("#html2").attr("href","${ctx}/manage/payment/exporthtml/month");
      paymentecharts();
     });
    
    $('#recentsixmonth2').click(function(){
      dataurl2='${ctx}/manage/payment/recentsixmonthdata';
      $("#excel2").attr("href","${ctx}/manage/payment/export/sixmonth");
      $("#html2").attr("href","${ctx}/manage/payment/exporthtml/sixmonth");
      paymentecharts();
    });
   });
   
   



    /*************************will部分**********************************/

   function getCucount(){
	   $.post("${ctx}/manage/will/currentmonthcount",{},function(r){
		   $('#willcount').val(r.count);
		   willecharts();
	   });
   }
   
   
   var getdate=[];  
   var getcount=[]; 
   require.config({  
       paths: {  
           echarts: 'http://echarts.baidu.com/build/dist'    
       }  
   });   
   
  function willecharts(){
	  
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
                if(data.result==1){
                    if(data.datas.length>0){
                      for(var i=0;i<data.datas.length;i++){
                         getdate.push(data.datas[i][0]);
                        getcount.push(data.datas[i][1]);
                      }
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


      var maindiv = document.getElementById('main');
      var contain=document.getElementById('ldiv');

      //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
      var resizemaindiv = function () {
          maindiv.style.width = (contain.clientWidth/1.1)+'px';
          maindiv.style.height = (contain.clientHeight/1.2)+'px';
      };
      //设置容器高宽
      resizemaindiv();


       var myChart = ec.init(maindiv);  
       var option = {  
               title : {  
                   text: '报名学员数量（单位：人）',  
                   x:'left'  
               },tooltip : {  
                   trigger: 'axis'  
               },  
               legend: { 
            	   data:['报名学员'], 
                   //data:['成立','变更','注销'],  
                   y : 'bottom'  
               },  
                 
               // calculable : true,  
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
             					           max : 5,
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

       //用于使chart自适应高度和宽度
        window.onresize = function () {
          //重置容器高宽
          resizemaindiv();
          myChart.resize();
        }; 
   }  





   /*************************以下为payment部分**********************************/
    function getCucount2(){
     $.post("${ctx}/manage/payment/currentmonthcount",{},function(r){
       $('#paymentcount').val(r.count);
       paymentecharts();
     });
  }


  var getdate2=[];  
   var getcount2=[]; 
   require.config({  
       paths: {  
           echarts: 'http://echarts.baidu.com/build/dist'    
       }  
   });   
   
  function paymentecharts(){
    
   require([  
               'echarts',
               'echarts/chart/pie',  
               'echarts/chart/scatter',  
              'echarts/chart/bar',  
               'echarts/chart/line'  
           ],function (ec){
        getdate2=[];
        getcount2=[]; 
        $.post(dataurl2, 
              function(data) {
                if(data.result==1){
                    if(data.datas.length>0){
                      for(var i=0;i<data.datas.length;i++){
                        getdate2.push(data.datas[i][0]);
                        getcount2.push(data.datas[i][1]);
                      }
                    }
                }
            
          //绘制统计图  
            drawFrdjtj2(ec);  
          });
         
   }); 
  }
  
  
   function drawFrdjtj2(ec)  
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


      var maindiv2 = document.getElementById('main2');
      var contain2=document.getElementById('rdiv');

      //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
      var resizemaindiv2 = function () {
          maindiv2.style.width = (contain2.clientWidth/1.1)+'px';
          maindiv2.style.height = (contain2.clientHeight/1.2)+'px';
      };
      //设置容器高宽
      resizemaindiv2();


       var myChart2 = ec.init(maindiv2);  
       var option2 = {  
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
                 
               // calculable : true,  
               xAxis : [  
                        {  
                            type : 'category',  
                            boundaryGap : false, 
                             data :getdate2
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
                               data:getcount2,
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
       myChart2.setOption(option2);  

       // //用于使chart自适应高度和宽度
       //  window.onresize = function () {
       //    //重置容器高宽
       //    resizemaindiv2();
       //    myChart2.resize();
       //  }; 
   }   

   
   </script>
</body>
</html>