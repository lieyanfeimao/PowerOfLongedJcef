/**
 * common.js V1.0
 * author:玄翼猫
 */
/** 成功的状态码 **/
var SUCCESS=200;

/** 错误的状态码 **/
var ERROR=201;
/**
 * 公共执行Java方法的函数
 * name:内置的JAVA方法名/指令
 * params:传递的参数，如果是指令，不需要传参数。传入json字符串或对象，其他无效
 * onSuccess:成功后的回调函数
 * onFailure:失败后的回调函数
 * persistent:持久化，不知道什么用，不传
 */
function execJava(name,params,onSuccess,onFailure,persistent){
	if(name==null || typeof name!=="string"){
		alert("指令/方法名必须是个有效的字符串");
		return;
	}
	//需要传递参数
	if(params && (typeof params === "object" || typeof params === "string" )){
		if(typeof params === "object") params=JSON.stringify(params);
		name+=":"+params;
	}
	
	persistent=persistent?true:false;
	
	window.java({
		request:name
		,persistent:persistent
		,onSuccess:function(response){
			if(onSuccess && typeof onSuccess==="function"){
				onSuccess(JSON.parse(response));
			}
		}
		,onFailure:function(code,response){
			if(onFailure && typeof onFailure==="function"){
				onFailure(code,JSON.parse(response));
			}else{
				if(code==-1){//系统发送过来的错误信息
					var data=JSON.parse(response);
					$.messager.show({
						title:'您收到一条系统警告信息！',
						msg:data.msg.replace(/(\r\n)|(\n)/g,'<br/>'),
						showType:'show',
						width:'80%',
						height:'80%',
						timeout:0,
						style:{}
					});
				}
			}
		}
	});
}

$.extend($.messager.defaults,{  
    ok:"确定",  
    cancel:"取消"  
});
/**
 * 调用windows资源管理器打开指定目录
 * @param path
 * @returns
 */
function openExplorer(path){
	execJava("openExplorer",{path:path});
}
/**
	var curDown = false,
	  curYPos = 0,
	  curXPos = 0;
	$(".toolsContent").mousemove(function(m){
	if(curDown === true){
	 $(".toolsContent").scrollTop($(".toolsContent").scrollTop() + (curYPos - m.pageY)); 
	 $(".toolsContent").scrollLeft($(".toolsContent").scrollLeft() + (curXPos - m.pageX));
	}
	});

	$(".toolsContent").mousedown(function(m){
	curDown = true;
	curYPos = m.pageY;
	curXPos = m.pageX;
	});

	$(".toolsContent").mouseup(function(){
	curDown = false;
	});
**/