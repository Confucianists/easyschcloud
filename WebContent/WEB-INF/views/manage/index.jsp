<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>首页</title>

</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/manage/index/index2.css">
	<div class="cwbody">
		<div class="hlechart">
			<div class="echarttitle">
				<div class="echartlabel"><label id="tcount">课程总数:</label><label>人</label></div>
				<div class="echartlabel"><label id="dcount">学生总数:</label><label>套</label></div>
			</div>
			<div class="echartdes">
				<label id="ccount">资源总数:</label>
			</div>
			<div class="hlechartcontainer" id="main"></div>
		</div>
		<div class="middletable">
			<div class="tabletitle">教师本月转化排名</div>
			<div class="tablecontent1">
				<table border="0" cellpadding="2" cellspacing="0">
					<thead>
						<tr>
							<th style="width: 80px;"></th>
							<th style="width: 80px;">老师</th>
							<th style="width: 160px;">本月课程数</th>
							<th style="width: 90px;">排名</th>
							
						</tr>
					</thead>
					<tbody id="list1">
						<!-- <tr><td></td><td>王老师</td><td>78</td><td>1</td></tr>
						<tr><td></td><td>王老师</td><td>75</td><td>2</td></tr>
						<tr><td></td><td>王老师</td><td>72</td><td>3</td></tr>
						<tr><td></td><td>王老师</td><td>71</td><td>4</td></tr>
						<tr><td></td><td>王老师</td><td>68</td><td>5</td></tr>
						<tr><td></td><td>王老师</td><td>55</td><td>6</td></tr>
						<tr><td></td><td>王老师</td><td>53</td><td>7</td></tr>
						<tr><td></td><td>王老师</td><td>31</td><td>8</td></tr>
						<tr><td></td><td>王老师</td><td>22</td><td>9</td></tr>
						<tr><td></td><td>王老师</td><td>12</td><td>10</td></tr> -->
					</tbody>
				</table>
			</div>
			<div class="tablefoot"></div>
		</div>
		<div class="righttable">
			<div class="tabletitle">销售排名</div>
			<div class="tablecontent2">
				<table border="0" cellpadding="2" cellspacing="0">
					<thead>
						<tr>
							<!-- <th style="width: 90px;"></th> -->
							<th style="width: 160px;">班级</th>
							<th style="width: 160px;">硬件使用次数</th>
							<th style="width: 90px;">排名</th>
							
						</tr>
					</thead>
					<tbody id="list2">
						<!-- <tr><td></td><td>初一二班</td><td>78</td><td>1</td></tr>
						<tr><td></td><td>初一二班</td><td>75</td><td>2</td></tr>
						<tr><td></td><td>初一二班</td><td>72</td><td>3</td></tr>
						<tr><td></td><td>初一二班</td><td>71</td><td>4</td></tr>
						<tr><td></td><td>初一二班</td><td>68</td><td>5</td></tr>
						<tr><td></td><td>初一二班</td><td>55</td><td>6</td></tr>
						<tr><td></td><td>初一二班</td><td>53</td><td>7</td></tr>
						<tr><td></td><td>初一二班</td><td>31</td><td>8</td></tr>
						<tr><td></td><td>初一二班</td><td>22</td><td>9</td></tr>
						<tr><td></td><td>初一二班</td><td>12</td><td>10</td></tr> -->
					</tbody>
				</table>
			</div>
			<div class="tablefoot"></div>
		</div>
	</div>
	<script src="${ctx}/static/echarts/echarts.common.min.js"></script>
	<script type="text/javascript">
        var lessondataurl="${ctx}/school/lesson/top10";
        var devicedataurl="${ctx}/school/recordtop10";
        var coursedataurl="${ctx}/manage/course/group";
        var counturl="${ctx}/school/index/getcount";
        var color=['#65F6F3','#71228B','#1F6086','#C23531','#D48265','#2F4554','#91C7AE','#749F83','#6E7074','#3398DB','#FB80A0','#E86A39','#D14A61','#C71969','#CA8622','#19C719'];
        var cwdata=[];
        $(function(){
            init();
        });

        function init(){
            //lessontop10();
        }


	    // 基于准备好的dom,初始化eChars实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
		
		//app.title = '嵌套环形图';

option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        // data:['课件数','微课数','笔记数']
        // data:[{name:'课件数',textStyle:{color:"red",fontSize: 20 }  },
        // {name:'微课数',textStyle:{color:"red",fontSize: 20 }  },
        // {name:'笔记数',textStyle:{color:"red",fontSize: 20 }  }
        // ]
        
    },
    series: [
        {
            name:'',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],

            label: {
                normal: {
                    position: 'inner'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                // {value:335, name:'直达', selected:true},
                // {value:679, name:'营销广告'},
                // {value:1548, name:'搜索引擎'}
            ]
        },
        {
            name:'',
            type:'pie',
            radius: ['40%', '55%'],
            label: {
                normal: {
                    // formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                    formatter: "{b}: {c}",
                    backgroundColor: '#eee',
                    borderColor: '#aaa',
                    borderWidth: 1,
                    borderRadius: 4,
                    shadowBlur:3,
                    shadowOffsetX: 2,
                    shadowOffsetY: 2,
                    shadowColor: '#999',
                    padding: [0, 7],
                    textStyle: {  
                		fontWeight: 'normal',              //标题颜色  
                		fontSize: 15
           	 		},  
                    rich: {
                        a: {
                            color: '#999',
                            lineHeight: 22,
                            align: 'center'
                        },
                        abg: {
                            backgroundColor: '#333',
                            width: '100%',
                            align: 'right',
                            height: 22,
                            borderRadius: [4, 4, 0, 0]
                        },
                        hr: {
                            borderColor: '#aaa',
                            width: '100%',
                            borderWidth: 0.5,
                            height: 0
                        },
                        b: {
                            fontSize: 16,
                            lineHeight: 33
                        },
                        per: {
                            color: '#eee',
                            backgroundColor: '#334455',
                            padding: [2, 4],
                            borderRadius: 2
                        }
                    }
                }
            },
            // data:[
            //     {value:335, name:'课件数',itemStyle:{ normal:{color:'#65F6F3'} }},
            //     {value:310, name:'微课数',itemStyle:{ normal:{color:'#71228B'} }},
            //     {value:234, name:'笔记数',itemStyle:{ normal:{color:'#1F6086'} }},
                
            // ]
            data:(function(){
              var arr=[];
              $.ajax({
                   type : "post",
                   async : false, //同步执行
                   url : coursedataurl,
                 data : {},
                  dataType : "json", //返回数据形式为json
                  success : function(r) {
                
                   
                     if (r.content.length>1) {
                          for(var i=0;i<r.content.length;i++){
                     
                         //alert(result.listCont[i]+" "+result.listName[i]);
                         arr.push( {value:r.content[i][1], 
                            name:(r.content[i][2]),
                            itemStyle:{ normal:{color:color[i]
                            } }});
                        
                        }    
                    }
                                          
                  },
                    error : function(errorMsg) {
                         alert("不好意思,图表请求数据失败啦!");
                           myChart.hideLoading();
                         }
                 })
                   return arr;
                })() 
        }
    ]
};


		  
        // 使用刚指定的配置项和数据显示图表
        myChart.setOption(option);



            function lessontop10(){
                showload();
                $("#list1").empty();
                $.post(lessondataurl,function(r){
                    if(r.result==1){
                        for(var i=0;i<r.data.length;i++){
                            $("#list1").append(
                                '<tr><td>'
                                +'<div class="tlogo" style="background: url('
                                        +(r.data[i][0]==null?("${ctx}/static/teacher/image/defaultlogo.png"):r.data[i][0])
                                        +') center;"></div>'
                                +'</td><td>'
                                +r.data[i][1]
                                +'</td><td>'
                                +r.data[i][2]
                                +'</td><td>'
                                +(i+1)
                                +'</td></tr>'
                            );
                        }   
                    }else{

                    }
                    devicetop10();
                });
            }

            function devicetop10(){
                $("#list2").empty();
                   $.post(devicedataurl,function(r){
                    if(r.result==1){
                        for(var i=0;i<r.data.length;i++){
                            $("#list2").append(
                                '<tr><td>'
                                +r.data[i][0]
                                +'</td><td>'
                                +r.data[i][1]
                                +'</td><td>'
                                +(i+1)
                                +'</td></tr>'
                            );
                        }   
                    }else{

                    }
                    getcount();
                });
            }

         
            function getcount(){
                $.post(counturl,function(r){
                    hidload();
                    if(r.result==1){
                        $("#tcount").html('教师人数：'+r.tercount);
                        $("#dcount").html('硬件数量：'+r.devcount);
                        $("#ccount").html('资源总数：'+r.cwcount);
                    }else{

                    }
                });
            }



            var loadhtml = '<div id="loadingToast" class="" ><div class="mask_transparent"></div><div class="toast"><div class="pagin_loading"><div class="loading_leaf loading_leaf_0"></div><div class="loading_leaf loading_leaf_1"></div><div class="loading_leaf loading_leaf_2"></div><div class="loading_leaf loading_leaf_3"></div><div class="loading_leaf loading_leaf_4"></div><div class="loading_leaf loading_leaf_5"></div><div class="loading_leaf loading_leaf_6"></div><div class="loading_leaf loading_leaf_7"></div><div class="loading_leaf loading_leaf_8"></div><div class="loading_leaf loading_leaf_9"></div><div class="loading_leaf loading_leaf_10"></div><div class="loading_leaf loading_leaf_11"></div></div></div></div>'
            var loading;
            function showload() {
                loading = $(loadhtml).appendTo($(document.body));
            }
            function hidload() {
                loading.remove();
            }
	</script>
</body>
</html> 