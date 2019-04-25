
$(function(){
	//适配屏幕大小
	var iWidth = document.documentElement.getBoundingClientRect().width;
		iWidth = iWidth>640?640:iWidth;
		iWidth = iWidth<320?320:iWidth;
		document.getElementsByTagName("html")[0].style.fontSize = iWidth/6.4+"px";
});
