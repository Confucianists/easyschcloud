
var x,y;
var lnglatXY,cpoint;
var map;
var address;
	 function aaa(){
			//加载地图，调用浏览器定位服务
			 map = new AMap.Map('', {
			    resizeEnable: true
			});
			
		 map.plugin('AMap.Geolocation', function() {
			 var  geolocation = new AMap.Geolocation({
			        enableHighAccuracy: true,//是否使用高精度定位，默认:true
			        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
			        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
			        zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
			        buttonPosition:'RB'
			    });
			
		
	     	    map.addControl(geolocation);
	     	    geolocation.getCurrentPosition();
	     	    AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
	     	   AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
		 });	
	 }
 	
 	
 	//解析定位结果
 	function onComplete(data) {
 	     x = data.position.getLng();
 	     y = data.position.getLat(); 
 	    regeocoder();
 	    
 	} 
    function regeocoder() {  //逆地理编码
 	    	
 	    	 AMap.service('AMap.Geocoder',function(){//回调函数
 	    	 	    //实例化Geocoder
 	    	 	   var geocoder = new AMap.Geocoder({
 	    	 	    	 radius: 1000,
 	    	              extensions: "all"
 	    	 	    });
 	    	   
 	    	   lnglatXY = [x, y];
 	    	   //lnglatXY = [120.299,31.568];
 	           geocoder.getAddress(lnglatXY, function(status, result) {
 	               if (status === 'complete' && result.info === 'OK') {
 	                   geocoder_CallBack(result);
 	               }else{
 	                   alert("获取位置失败！！！！！！！！！！");
 	               }
 	           });
 	           });  
 	        var marker = new AMap.Marker({  //加点
 	            map: map,
 	            position: lnglatXY
 	        });
 	        map.setFitView();
 	    }  
 	
    
    
    function doPlaceSearch(){
    	AMap.service(["AMap.PlaceSearch"], function() {
            var placeSearch = new AMap.PlaceSearch({ //构造地点查询类
                pageSize: 20,
                type: '政府机构及社会团体|科教文化服务|公司企业|公共设施',
                pageIndex: 1,
                //city: "0510", //城市
                map: map,
                panel: "panel"
            });
            
             cpoint = [x, y]; //中心点坐标
            placeSearch.searchNearBy('', cpoint, 2000, function(status, result) {
            	if (status === 'complete' && result.info === 'OK') {
            		placeSearch_CallBack(result);
                  }else{
                      alert("获取周边位置失败！");
                  } 
            });
        });
    }
    
    
    
   
    
    
    
    
    
    
 	//解析定位错误信息
 	function onError(data) {
 	    //document.getElementById('errortip').html("定位失败");
 	   document.getElementById('errortip').innerHTML='定位失败';
 	}
 	var getprovince,getcity,getdistrict;
 	//获得地址数据后，将地址显示
    function geocoder_CallBack(data) {
         address = data.regeocode.formattedAddress; //返回地址描述
        var pindex = address.indexOf("省");
         getprovince = address.substring(0,pindex+1);
        var cindex = address.indexOf("市");
        getcity = address.substring(pindex+1,cindex+1);
        var dindex = address.indexOf("区");
        getdistrict = address.substring(cindex+1,dindex+1);
        var getpcd= address.substring(0,dindex+1);
        $("#pcd").val(getpcd);
        //$("#province").val(getprovince);
        //$("#city").val(getcity);
        //$("#district").val(getdistrict);
       // $("#location").val(address);
        doPlaceSearch();
    }
    //获取周边的建筑后，将建筑显示
    function placeSearch_CallBack(data) {
    	//返回地址描述
    	for (var i = 0; i < data.poiList.pois.length; i++) {
			$("#building")
					.append(
							'<option value="' 
									+ data.poiList.pois[i].name
									+ '">'
									+ data.poiList.pois[i].name
									+ '</option>');
	} 
    } 
    
    
    
    
    
    
    
    
