$(document).ready(function(){
	var $sys=getQueryString("sys");
	switch($sys){
	case "4":$('#leftDoor').addClass('LeftMenuDivChosen');break;
	case "6":$('#leftGas').addClass('LeftMenuDivChosen');break;
	case "5":$('#leftPower').addClass('LeftMenuDivChosen');break;
	default:$('#leftCurrent').addClass('LeftMenuDivChosen');
	}
});
