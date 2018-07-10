(function($) {
	$(function() {
		$('a[href*=#]').on("click", function(e) {
			var anchor = $(this);
			$('html, body').stop().animate({
				scrollTop : $(anchor.attr('href')).offset().top - 80
			}, 1000);
			e.preventDefault();
		});

		$(".prev,.next").hover(function() {
			$(this).stop(true, false).fadeTo("show", 0.9);
		}, function() {
			$(this).stop(true, false).fadeTo("show", 0.4);
		});

		$(".banner-box").slide({
			titCell : ".hd ul",
			mainCell : ".bd ul",
			effect : "fold",
			interTime : 3000,
			delayTime : 0,
			autoPlay : true,
			autoPage : true,
			trigger : "click"
		});

		$(".banner-box .hd").css("margin-left",
				"-" + ($(".banner-box .hd").width() / 2 - 10) + "px");
	})
})(jQuery)