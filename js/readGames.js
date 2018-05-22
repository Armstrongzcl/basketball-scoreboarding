 function getGames(){
	$.getJSON("../games.json",function(result){	
		
		$.each(result, function(gaid,item) {
		    $("#game").append("<li class='mui-table-view-cell'><a  id='"+gaid+"' class='mui-navigate-right' style='text-align:center' onclick = 'open_detail(this.id)'>"+item.firstTeam +"&nbsp&nbspVS&nbsp&nbsp"+item.secondTeam +"<br><br>"+item.firstScore+"&nbsp&nbsp:&nbsp&nbsp"+item.secondScore+"</a></li>");
		});
	})
}
 

             /**
			 * 打开新闻详情
			 * 
			 * @param {Object} item 当前点击的新闻对象
			 */
			function open_detail(gaid) {
				
				//触发子窗口变更新闻详情
				mui.fire(webview_detail, 'get_detail', gaid);
				setTimeout(function() {
					webview_detail.show("slide-in-right", 300);
				}, 150);
			}
				
mui.plusReady(function() {
				//预加载详情页
				webview_detail = mui.preload({
					url: 'game-info.html',
					id: 'game-info',
					styles: {
						"render": "always",
						"popGesture": "hide",
						"bounce": "vertical",
						"bounceBackground": "#efeff4",
						"titleNView": titleNView
					}
				});
			});
//// 将js对象转成url jquery实现
//var parseParam=function(paramObj, key){
//var paramStr="";
//if(paramObj instanceof String||paramObj instanceof Number||paramObj instanceof Boolean){
//  paramStr+="&"+key+"="+encodeURIComponent(paramObj);
//}else{
//  $.each(paramObj,function(i){
//    var k=key==null?i:key+(paramObj instanceof Array?"["+i+"]":"."+i);
//    paramStr+='&'+parseParam(this, k);
//  });
//}
//return paramStr.substr(1);
//};
//
//
///**
// * paramObj 将要转为URL参数字符串的对象
// * key URL参数字符串的前缀
// * encode true/false 是否进行URL编码,默认为true
// * js实现
// * return URL参数字符串
// */
//var urlEncode = function (paramObj, key, encode) {
//if(paramObj==null) return '';
//var paramStr = '';
//var t = typeof (paramObj);
//if (t == 'string' || t == 'number' || t == 'boolean') {
//  paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(paramObj) : paramObj);
//} else {
//  for (var i in paramObj) {
//    var k = key == null ? i : key + (paramObj instanceof Array ? '[' + i + ']' : '.' + i);
//    paramStr += urlEncode(paramObj[i], k, encode);
//  }
//}
//return paramStr;
//};


