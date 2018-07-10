<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<title>真会云课堂</title>
</head>
<body>
	<div class="contentdiv">
		<div class="leftmenu">
			<div class="menutitle">
				<div>NAVIGATION</div>
				<div>关于我们</div>
			</div>
			<div class="submenu">
				<a href="#" class="menua actmenu">公司介绍</a> <a href="#" class="menua">公司动态</a>
				<a href="#" class="menua">加入我们</a>

			</div>
		</div>
		<div class="rightcontent">
			<div class="contenttitle">公司简介</div>
			<div class="zwcontent">
				<p>翰林博德，智能投影系列产品的制造者，智能教室及智慧教学解决方案的提供者，于2014年9月创立于杭州市滨江区浙大科技园。</p>
				<p>从创立之初，翰林博德便将“在教者和学者之中，搭建一座平等、便捷、环保、健康的互动桥梁”作为自己的使命，成立以来，公司秉承“以用户为导向”的理念，不断坚持技术创新，致力于为用户提供“简单，智能，轻量”的智能投影产品及服务，其中包括：3LCD投影一体机、激光投影一体机、光控机等产品，大部分产品技术已达到国际领先水平，填补了国内市场教学需求的空白，代表了国际人机智能交互技术的最新发展方向。为更便捷地服务用户，翰林博德还创新性地开设了“真会云课堂”，及时录制老师课堂授课笔记，供老师日后完善课件及学生复习之用。</p>
				<p>
					随着互联网+教育从传统的教室空间向工作生活空间渗透，以K12基础教育为主向终身学习发展，翰林博德也在积极围绕核心战略加大对泛教学空间领域的投入和布局，不断推出适用于各空间的产品形态。</p>
				<p>
					短短两年间，翰林博德已经成长为国内投影一体机行业的领军企业，系国家级、省级教育装备行业协会会员单位，浙江省智能硬件优秀企业、浙江省科技型企业、杭州市级高新技术企业、杭州市“雏鹰”企业、杭州市温州商会理事单位。</p>

				<div class="pdiv">核心价值：</div>
				<div class="pdiv">1、给用户提供最环保、最简单、最便捷的互动教学方式</div>
				<div class="pdiv">2、认真听取和看待每一条建议和意见</div>
				<div class="pdiv">3、好好学习，天天向上</div>

				<div class="pdiv">公司使命：</div>
				<div class="pdiv">1、人文翰林，追求卓越；博览群书，德行天下</div>
				<div class="pdiv">2、在教者和学者之中，搭建一座平等、便捷、环保、健康的互动桥梁</div>
				<div class="pdiv">3、使翰林博德在品牌、用户满意度、经营业绩方面成为同行业的领先者</div>
				<div class="pdiv">核心文化：</div>
				<div class="pdiv">“适应”，即适者生存，而非大者生存；</div>
				<div class="pdiv">“专注”，即笃定方向，每天都“归零”向前</div>
				<div class="pdiv">“创新”，即没有最好，只有更好</div>
				<div class="pdiv">“合作”，即一个人的事业不是事业，众人拾柴火焰高；</div>
				<div class="pdiv">“诚信”，即使是口头承诺，神也可以听见</div>
				<img
					style="position: absolute; right: 0; bottom: -80px; width: 443px;"
					alt="" src="${ctx}/static/homepage/image/aboutus.png">
			</div>

		</div>
	</div>
	<script type="text/javascript">
		$($(".navbar")[0]).addClass("actnavbar");
	</script>
</body>
</html>
