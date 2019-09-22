/**********************************    模板操作开始     ***************************************/
//新建模板
function createNewModel(){
	$("#namedesc").html("请输入模板名：");
	$("#modelName").val("");
	$('#newModel').dialog({
	    title: '新建模板',
	    width: 'auto',
	    height: 'auto',
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons:[{
			text:'&nbsp;保存&nbsp;',
			handler:function(){
				var r=$("#modelName").val();
				if(!r){
					infoMsg("请输入有效的模板名!");
					return;
				}
				//判断名称是否重复
				for(var i=0;i<modelDeal.length;i++){
					if(modelDeal[i].name==r){
						infoMsg("无法使用已存在的模板名!");
						return;
					}
				}
				//初始化模板对象
				modelDeal[modelDeal.length]={name:r,fileId:0,wordId:0,folderPath:'',modelFiles:[],specialWords:[]};
				//更新下拉框，切换到新模板，更新表格数据
				var ops=[{text:'请选择模板',id:'-1'}];
				for(var i=0;i<modelDeal.length;i++){
					if(i==modelDeal.length-1){
						ops[ops.length]={id:i,text:modelDeal[i].name,selected:true};
						selectModelIndex = i;
					}else{
						ops[ops.length]={id:i,text:modelDeal[i].name};
					}
				}
				$("#model").combobox('loadData',ops);
				
				saveData();
				$('#newModel').dialog("close");
				infoMsg("创建模板成功");
				loadModelData();
			}
		},{
			text:'&nbsp;取消&nbsp;',
			handler:function(){
				$('#newModel').dialog("close");
			}
		}]
	});
}
//修改模板名称
function updateModelName(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	$("#namedesc").html('请输入新的模板名：');
	$("#modelName").val(modelDeal[selectModelIndex].name);
	$('#newModel').dialog({
	    title: '重命名模板-'+modelDeal[selectModelIndex].name,
	    width: 'auto',
	    height: 'auto',
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons:[{
			text:'&nbsp;保存&nbsp;',
			handler:function(){
				var r=$("#modelName").val();
				if(!r){
					infoMsg("请输入有效的模板名!");
					return;
				}
				//判断名称是否重复
				for(var i=0;i<modelDeal.length;i++){
					if(i!=selectModelIndex && modelDeal[i].name==r){
						infoMsg("无法使用已存在的模板名!");
						return;
					}
				}
				//更新模板名
				modelDeal[selectModelIndex]["name"]=r;
				//更新下拉框，切换到新模板，更新表格数据
				var ops=[{text:'请选择模板',id:'-1'}];
				for(var i=0;i<modelDeal.length;i++){
					if(i==selectModelIndex){
						ops[ops.length]={id:i,text:modelDeal[i].name,selected:true};
					}else{
						ops[ops.length]={id:i,text:modelDeal[i].name};
					}
				}
				$("#model").combobox('loadData',ops);
				
				saveData();
				$('#newModel').dialog("close");
				infoMsg("模板重命名成功");
			}
		},{
			text:'&nbsp;取消&nbsp;',
			handler:function(){
				$('#newModel').dialog("close");
			}
		}]
	});
}
//复制模板
function copyModel(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	$("#namedesc").html('请输入新的模板名：');
	$("#modelName").val("");
	$('#newModel').dialog({
	    title: '模板复制-'+modelDeal[selectModelIndex].name,
	    width: 'auto',
	    height: 'auto',
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons:[{
			text:'&nbsp;保存&nbsp;',
			handler:function(){
				var r=$("#modelName").val();
				if(!r){
					infoMsg("请输入有效的模板名!");
					return;
				}
				//判断名称是否重复
				for(var i=0;i<modelDeal.length;i++){
					if(modelDeal[i].name==r){
						infoMsg("无法使用已存在的模板名!");
						return;
					}
				}
				//复制模板对象
				var len=modelDeal.length;
				modelDeal[len]=$.extend(true, {}, modelDeal[selectModelIndex]);
				modelDeal[len]["name"]=r;
				//更新下拉框，切换到新模板，更新表格数据
				var ops=[{text:'请选择模板',id:'-1'}];
				for(var i=0;i<modelDeal.length;i++){
					if(i==modelDeal.length-1){
						ops[ops.length]={id:i,text:modelDeal[i].name,selected:true};
						selectModelIndex = i;
					}else{
						ops[ops.length]={id:i,text:modelDeal[i].name};
					}
				}
				$("#model").combobox('loadData',ops);
				
				saveData();
				$('#newModel').dialog("close");
				infoMsg("模板复制成功");
				loadModelData();
			}
		},{
			text:'&nbsp;取消&nbsp;',
			handler:function(){
				$('#newModel').dialog("close");
			}
		}]
	});
}
//删除模板
function deleteModel(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	$.messager.confirm(infoTitle, '您真的要删除模板[ '+modelDeal[selectModelIndex].name+' ]?', function(r){
		if (r){
			modelDeal.splice(selectModelIndex,1);
			var ops=[{text:'请选择模板',id:'-1',selected:true}];
			for(var i=0;i<modelDeal.length;i++){
				ops[ops.length]={id:i,text:modelDeal[i].name};
			}
			$("#model").combobox('loadData',ops);
			infoMsg("成功删除模板!");
			saveData();
			loadModelData();
		}
	});
}


//保存模板
function saveModel(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	batchModel=true;
	/**
	var modelFiles=modelDeal[selectModelIndex].modelFiles;
	if(modelFiles.length>0){
		//获取所有正在编辑的列
		for(var i=0;i<modelFiles.length;i++){
			$('#modelFileList').datagrid('endEdit', i);
		}
	}
	**/
	var specialWords=modelDeal[selectModelIndex].specialWords;
	if(specialWords.length>0){
		//获取所有正在编辑的列
		for(var i=0;i<specialWords.length;i++){
			$('#wordList').datagrid('endEdit', i);
		}
	}
	
	updateAllAbsObjPath();
	batchModel=false;
	saveData();
	infoMsg("保存成功!");
}
//使用帮助
function help(){
	$('#helpDoc').dialog({
	    title: '代码模板生成器-帮助文档',
	    width: '80%',
	    height: '90%',
	    closed: false,
	    modal: true,
	    content:"<iframe scrolling='auto' frameborder='0' src='doc/index.html' style='width:100%; height:100%; display:block;'></iframe>"
	});
}
//生成目标文件
function createObjFile(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	batchModel=true;
	var specialWords=modelDeal[selectModelIndex].specialWords;
	if(specialWords.length>0){
		//获取所有正在编辑的列
		for(var i=0;i<specialWords.length;i++){
			$('#wordList').datagrid('endEdit', i);
		}
	}
	
	updateAllAbsObjPath();
	batchModel=false;
	saveData();
	
	var modelFiles=modelDeal[selectModelIndex].modelFiles;
	$("#selectFileList").datagrid({
		data:modelFiles
	});
	//选中全部
	$("#selectFileList").datagrid("selectAll");
	
	$('#selectFile').dialog({
	    title: '生成目标文件',
	    width: '480',
	    height: 'auto',
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons:[{
			text:'&nbsp;生成文件&nbsp;',
			handler:function(){
				var list=$('#selectFileList').datagrid('getSelections');
				if(list.length==0){
					infoMsg("请选择要生成的文件!");
					return;
				}
				
				execJava("model.existsFile",{fileListJson:JSON.stringify(list)},function(data){
					if(data.code==SUCCESS){
						var $r=data.result;
						if($r.length>0){//包含已存在的文件
							var $info='下列文件已存在，是否覆盖?';
							for(var i=0;i<$r.length;i++){
								$info+='<font color="red">'+$r[i]+'</font><br/>';
							}
							$.messager.confirm(infoTitle,$info,function(r){
								if (r){
									//调用生成目标文件的方法
									execJava("model.createObjFile",{fileListJson:JSON.stringify(list),wordListJson:JSON.stringify(modelDeal[selectModelIndex].specialWords)},function(data){
										infoMsg(data.msg);
									});
								}
							});
						}else{
							//调用生成目标文件的方法
							execJava("model.createObjFile",{fileListJson:JSON.stringify(list),wordListJson:JSON.stringify(modelDeal[selectModelIndex].specialWords)},function(data){
								infoMsg(data.msg);
							});
						}
					}else{
						infoMsg(data.msg);
					}
				});
				
				//$('#selectFile').dialog("close");
			}
		},{
			text:'&nbsp;取消&nbsp;',
			handler:function(){
				$('#selectFile').dialog("close");
			}
		}]
	});
}

/**********************************    模板操作结束     ***************************************/


/**********************************    模板文件操作开始     ***************************************/
//添加模板文件
function addModelFile(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	execJava("fileDialog",{'title':'文件选择框',model:'FILE_DIALOG_OPEN_MULTIPLE',filePath:modelDeal[selectModelIndex].folderPath},function(data){
		var r=data.result;
		//alert(JSON.stringify(r));
		var modelFiles=modelDeal[selectModelIndex].modelFiles;
		for(var i=0;i<r.length;i++){
			modelFiles[modelFiles.length]={id:modelDeal[selectModelIndex].fileId++,sourcePathName:getFileNameByPath(r[i]),sourcePath:r[i],objPath:'',objAbsPath:'',sourceCode:'UTF-8',objCode:'UTF-8',edit:false};
		}
		if(r.length>0){//设置文件夹路径
			modelDeal[selectModelIndex].folderPath=r[0];
		}
		//alert(JSON.stringify(modelDeal[selectModelIndex].modelFiles));
		$('#modelFileList').datagrid({
			data:modelDeal[selectModelIndex].modelFiles
		});
		saveData();
	});
}
//删除模板文件
function deleteModelFile(){
	//获取选中的数据
	var list=$('#modelFileList').datagrid('getSelections');
	//infoMsg(JSON.stringify(list));
	//弹窗询问是否删除
	if(list.length==0){
		infoMsg("请选择要删除的数据!");
		return;
	}
	$.messager.confirm(infoTitle,'你真的要删除选中的模板文件记录吗?',function(r){
		if (r){
			for(var i=0;i<list.length;i++){
			//	deleteDataById(list[i].id);
				var index=$('#modelFileList').datagrid('getRowIndex',list[i]);
				$('#modelFileList').datagrid('deleteRow', index);
			}
			saveData();
			//$('#modelFileList').datagrid({
			//	data:modelDeal[selectModelIndex].modelFiles
			//});
			infoMsg("删除成功！");
		}
	});
}
//删除单条模板文件数据
function deleteModelFileRow(target){
	$.messager.confirm(infoTitle,'你真的要删除这条模板文件记录吗?',function(r){
		if (r){
			var rowIndex=getRowIndex(target);
			$('#modelFileList').datagrid('deleteRow', rowIndex);
			saveData();
			infoMsg("删除成功！");
		}
	});
}

//更新模板文件
function changeModelFile(rowIndex){
	var row=modelDeal[selectModelIndex].modelFiles[rowIndex];
	if(!row){
		return;
	}
	execJava("fileDialog",{'title':'文件选择框',model:'FILE_DIALOG_OPEN',filePath:row.sourcePath},function(data){
		var r=data.result;
		if(r.length>0){
			var data=modelDeal[selectModelIndex].modelFiles[rowIndex];
			data["sourcePath"]=r[0];
			data["sourcePathName"]=getFileNameByPath(r[0]);
			modelDeal[selectModelIndex].folderPath=r[0];
			data.objAbsPath=getAbsObjPath(data.objPath+"\\"+data.sourcePathName);
			//$('#modelFileList').datagrid({
			//	data:modelDeal[selectModelIndex].modelFiles
			//});
			$('#modelFileList').datagrid('updateRow',{
				index: rowIndex,
				row:{data:data}
			});
			saveData();
		}
	});
}

//根据路径获取路径名
function getFileNameByPath(path){
	if(!path) return '';
	var ind=path.lastIndexOf("\\");
	if(ind!=-1){
		return path.substring(ind+1);
	}
	return path;
}

//根据ID删除数据
function deleteDataById(id){
	var modelFiles=modelDeal[selectModelIndex].modelFiles;
	for(var i=0;i<modelFiles.length;i++){
		if(modelFiles[i].id==id){
			modelFiles.splice(i,1);
			return;
		}
	}
}

//批量保存模板文件数据
function saveAllModelFile(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	var modelFiles=modelDeal[selectModelIndex].modelFiles;
	if(modelFiles.length==0){
		infoMsg("保存成功！");
		return;
	}
	batchModel=true;
	try{
		//获取所有正在编辑的列
		for(var i=0;i<modelFiles.length;i++){
			$('#modelFileList').datagrid('endEdit', i);
		}
	}catch(e){
	}
	
	batchModel=false;
	saveData();
	infoMsg("保存成功!");
}

//编辑页面打开资源管理器
function openExplorerByEdit(){
	var path=$("#objAbsPath").html();
	openExplorer(path);
}

//编辑页面更新模板文件
function changeModelFileByEdit(){
	var sourcePath=$("#sourcePath").html();
	execJava("fileDialog",{'title':'文件选择框',model:'FILE_DIALOG_OPEN',filePath:sourcePath},function(data){
		var r=data.result;
		if(r.length>0){
			$("#sourcePath").html(r[0]);
			$("#sourcePathName").html(getFileNameByPath(r[0]));
			changeObjAbsPath();
		}
	});
}

//更新目标真实路径
function changeObjAbsPath(){
	var objPath=$("#objPath").val();
	var sourcePathName=$("#sourcePathName").html();
	$("#objAbsPath").html(getAbsObjPath(objPath+"\\"+sourcePathName));
}

//编辑模板文件
function editModelFileRow(target){
	//$('#modelFileList').datagrid('beginEdit', getRowIndex(target));
	var rowIndex=getRowIndex(target);
	var rows = $('#modelFileList').datagrid('getRows');//获得所有行
    var row = rows[rowIndex];
	$("#modelFileId").val(row.id);
	for (let key in row) {
		$("#"+key).html(row[key]);
		$("#"+key).val(row[key]);
    }
	
	$('#editModel').dialog({
	    title: '模板文件编辑',
	    width: '60%',
	    height: 'auto',
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons:[{
			text:'&nbsp;保存&nbsp;',
			handler:function(){
				var id=$("#modelFileId").val();
				var sourcePathName=$("#sourcePathName").html();
				var sourcePath=$("#sourcePath").html();
				if(sourcePathName==""){
					infoMsg("模板文件不能为空！");
					return;
				}
				var objPath=$("#objPath").val();
				var objAbsPath=$("#objAbsPath").html();
				var sourceCode=$("#sourceCode").val();
				if(sourceCode==""){
					sourceCode="UTF-8";
				}
				var objCode=$("#objCode").val();
				if(objCode==""){
					objCode="UTF-8";
				}
				var modelFiles=modelDeal[selectModelIndex].modelFiles;
				if(!modelFiles){
					infoMsg("模板数据异常，修改失败！");
					return;
				}
				for(var i=0;i<modelFiles.length;i++){
					if(modelFiles[i].id==id){
						modelFiles[i].sourcePathName=sourcePathName;
						modelFiles[i].sourcePath=sourcePath;
						modelFiles[i].objPath=objPath;
						modelFiles[i].objAbsPath=objAbsPath;
						modelFiles[i].sourceCode=sourceCode;
						modelFiles[i].objCode=objCode;
						
						modelFiles[i]["edit"]=false;
						//更新数据
						$("#modelFileList").datagrid('updateRow',{
							index: rowIndex,
							row:{data:modelFiles[i]}
						});
						break;
					}
				}
				saveData();
				
				infoMsg("保存成功！");
				$('#editModel').dialog("close");
			}
		},{
			text:'&nbsp;取消&nbsp;',
			handler:function(){
				$('#editModel').dialog("close");
			}
		}]
	});
}

//保存当前行
function saveModelFileRow(target){
	var rowIndex=getRowIndex(target);
	$('#modelFileList').datagrid('endEdit', rowIndex);
	
}

//取消保存当前行
function cancelModelFileRow(target){
	$('#modelFileList').datagrid('cancelEdit', getRowIndex(target));
}


/**********************************    模板文件操作结束     ***************************************/

/**********************************    特殊词操作开始     ***************************************/

//保存所有特殊词
function saveAllWord(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	var specialWords=modelDeal[selectModelIndex].specialWords;
	if(specialWords.length==0){
		infoMsg("保存成功！");
		return;
	}
	batchModel=true;
	try{
		//获取所有正在编辑的列
		for(var i=0;i<specialWords.length;i++){
			$('#wordList').datagrid('endEdit', i);
		}
	}catch(e){
	}
	
	updateAllAbsObjPath();
	batchModel=false;
	saveData();
	infoMsg("保存成功!");
}

//编辑特殊词配置
function editWordRow(target){
	//$('#wordList').datagrid('beginEdit', getRowIndex(target));
	var rowIndex=getRowIndex(target);
	var rows = $('#wordList').datagrid('getRows');//获得所有行
    var row = rows[rowIndex];
    $("#wordId").val(row.id);
	for (let key in row) {
		$("#"+key).val(row[key]);
    }
	
	$('#editWord').dialog({
	    title: '模板文件编辑',
	    width: 'auto',
	    height: 'auto',
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons:[{
			text:'&nbsp;保存&nbsp;',
			handler:function(){
				var id=$("#wordId").val();
				var word=$("#word").val();
				var content=$("#content").val();
				var desc=$("#desc").val();

				var specialWords=modelDeal[selectModelIndex].specialWords;
				if(!specialWords){
					infoMsg("模板数据异常，修改失败！");
					return;
				}
				for(var i=0;i<specialWords.length;i++){
					if(specialWords[i].id==id){
						specialWords[i].word=word;
						specialWords[i].content=content;
						specialWords[i].desc=desc;
						
						specialWords[i]["edit"]=false;
						//更新数据
						$("#wordList").datagrid('updateRow',{
							index: rowIndex,
							row:{data:specialWords[i]}
						});
						break;
					}
				}
				batchModel=true;
				//更新所有目标文件真实路径
				updateAllAbsObjPath();
				batchModel=false;
				saveData();
				
				infoMsg("保存成功！");
				$('#editWord').dialog("close");
			}
		},{
			text:'&nbsp;取消&nbsp;',
			handler:function(){
				$('#editWord').dialog("close");
			}
		}]
	});
}

//删除选中的行
function deleteWord(){
	//获取选中的数据
	var list=$('#wordList').datagrid('getSelections');
	//infoMsg(JSON.stringify(list));
	//弹窗询问是否删除
	if(list.length==0){
		infoMsg("请选择要删除的数据!");
		return;
	}
	$.messager.confirm(infoTitle,'你真的要删除选中的特殊词配置记录吗?',function(r){
		if (r){
			for(var i=0;i<list.length;i++){
				deleteWrodDataById(list[i].id);
			}
			saveData();
			$('#wordList').datagrid({
				data:modelDeal[selectModelIndex].specialWords
			});
			infoMsg("删除成功！");
		}
	});
}
//根据ID删除关键词数据
function deleteWrodDataById(id){
	var specialWords=modelDeal[selectModelIndex].specialWords;
	for(var i=0;i<specialWords.length;i++){
		if(specialWords[i].id==id){
			specialWords.splice(i,1);
			return;
		}
	}
}

//创建关键词记录行
function createWordRow(){
	if(selectModelIndex==-1){
		infoMsg("请先选择一个模板!");
		return;
	}
	var row={id:modelDeal[selectModelIndex].wordId++,word:'',content:'',desc:'',edit:false};
	var index=$('#wordList').datagrid('appendRow',row).datagrid('getRows').length-1;
	$('#wordList').datagrid('beginEdit', index);
}

//删除特殊词记录行
function deleteWordRow(target){
	$.messager.confirm(infoTitle,'你真的要删除这条特殊词配置记录吗?',function(r){
		if (r){
			$('#wordList').datagrid('deleteRow', getRowIndex(target));
			saveData();
			infoMsg("删除成功！");
		}
	});
}
//保存特殊词记录行
function saveWordRow(target){
	$('#wordList').datagrid('endEdit', getRowIndex(target));
}
//取消特殊词记录行
function cancelWordRow(target){
	$('#wordList').datagrid('cancelEdit', getRowIndex(target));
}

/**********************************    特殊词操作结束     ***************************************/

/**********************************    公共操作开始     ***************************************/
//保存模板数据
function saveData(msg){
	execJava('writeAppData',{appId:appId,data:JSON.stringify(modelDeal)},function(data){
		if(msg) infoMsg(msg);
	});
}

//读取模板数据
function readData(func){
	execJava('readAppData',{appId:appId},function(data){
		if(data.result){
			modelDeal=JSON.parse(data.result);
		}
		if(typeof func=="function"){
			func(modelDeal);
		}
	});
}
//显示info信息
function infoMsg(msg){
	$.messager.alert(infoTitle,msg,'info');
}

//加载指定的索引的数据，渲染页面
function loadModelData(val){
	if(!val) val=selectModelIndex;
	if(val==-1){
		$('#modelFileList').datagrid({
			data:[]
		});
		$('#wordList').datagrid({
			data:[]
		});
	}else{
		for(var i=0;i<modelDeal[val].modelFiles.length;i++){
			if(modelDeal[val].modelFiles[i]["edit"]) modelDeal[val].modelFiles[i]["edit"]=false;
		}
		for(var i=0;i<modelDeal[val].specialWords.length;i++){
			if(modelDeal[val].specialWords[i]["edit"]) modelDeal[val].specialWords[i]["edit"]=false;
		}
		
		$('#modelFileList').datagrid({
			data:modelDeal[val].modelFiles
		});
		$('#wordList').datagrid({
			data:modelDeal[val].specialWords
		});
	}
}
//获取数据索引
function getRowIndex(target){
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}

//获取模板文件真实路径
function getAbsObjPath(path){
	if(selectModelIndex==-1){
		return path;
	}
	var words=modelDeal[selectModelIndex].specialWords;
	for(var i=0;i<words.length;i++){
		if(words[i].word){
			var rgx=new RegExp(words[i].word,"g");
			path=path.replace(rgx,words[i].content);
		}
	}
	return path;
}

//更新所有模板文件真实路径
function updateAllAbsObjPath(){
	if(selectModelIndex==-1){
		return;
	}
	var modelFiles=modelDeal[selectModelIndex].modelFiles;
	for(var i=0;i<modelFiles.length;i++){
		if(modelFiles[i]["edit"]){
			$('#modelFileList').datagrid('endEdit', i);
		}
		modelFiles[i].objAbsPath=getAbsObjPath(modelFiles[i].objPath+"\\"+modelFiles[i].sourcePathName);
		$('#modelFileList').datagrid('updateRow',{
			index: i,
			row:{data:modelFiles[i]}
		});
	}
}

function updateActions(obj,index,rowData){
	$(obj).datagrid('updateRow',{
		index: index,
		row:{data:rowData}
	});
}
/**********************************    公共操作结束     ***************************************/

/**
//显示右键菜单
	$(document).contextmenu(function(e){
		
		$('#menu').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		
		return false;
	});
	//文件对话框调用示例
	function openFloder(){
		execJava("fileDialog",{'title':'文件选择框',filters:['图片文件|.png;.jpg;.gif;.bmp','text/*'],model:'FILE_DIALOG_OPEN_MULTIPLE'},function(data){
			var r=data.result;
			alert(JSON.stringify(r));
		});
	}
	**/