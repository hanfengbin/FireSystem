$(document).ready(setInterval(function(){
	var date = new Date();
	year = date.getFullYear();
	month = date.getMonth()+1;
	date1 = date.getDate();
	hour = date.getHours();
	minute = date.getMinutes();
	second = date.getSeconds();
	if(minute<10){
		minute='0'+minute;
	}
	if(second<10){
		second='0'+second;
	}
	if(hour<10){
		hour='0'+hour;
	}
	week = date.getDay();
	switch(week){
		case 0:
			weekday='日';
			break;
		case 1:
			weekday='一';
			break;
		case 2:
			weekday='二';
			break;
		case 3:
			weekday='三';
			break;
		case 4:
			weekday='四';
			break;
		case 5:
			weekday='五';
			break;
		case 6:
			weekday='六';
			break;
	}
	$(".hour").html(hour+':'+minute+':'+second);
	$(".week").html(year+'年'+month+'月'+date1+'日'+'&nbsp'+'星期'+weekday);
	},1000)
)
