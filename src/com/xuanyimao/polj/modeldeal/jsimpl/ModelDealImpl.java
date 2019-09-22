/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月25日
 * @version V1.0 
 */
package com.xuanyimao.polj.modeldeal.jsimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuanyimao.polj.index.anno.JsClass;
import com.xuanyimao.polj.index.anno.JsFunction;
import com.xuanyimao.polj.index.bean.Message;
import com.xuanyimao.polj.modeldeal.bean.ModelFile;
import com.xuanyimao.polj.modeldeal.bean.SpecialWord;


/**
 * @Description: 模板处理js接口
 * @author liuming
 */
@JsClass(name="modelDealImpl")
public class ModelDealImpl {
	/**
	 * 创建目标文件
	 * @author:liuming
	 * @param fileListJson 模板文件配置列表JSON字符串
	 * @param wordListJson 特殊词配置列表JSON字符串
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	@JsFunction(name="model.createObjFile")
	public Message createObjFile(String fileListJson,String wordListJson) throws FileNotFoundException, IOException{
		Gson gson=new Gson();
		List<ModelFile> fileList = gson.fromJson(fileListJson,new TypeToken<List<ModelFile>>(){}.getType());
		List<SpecialWord> wordList = gson.fromJson(wordListJson,new TypeToken<List<SpecialWord>>(){}.getType());
		
		if(fileList==null){
			return Message.error("请设置模板文件配置列表！");
		}
		
		//遍历文件列表
		for(ModelFile mf:fileList){
			File file=new File(mf.getSourcePath());
			FileInputStream fis=new FileInputStream(file);
			String content=IOUtils.toString(fis,mf.getSourceCode());
			IOUtils.closeQuietly(fis);
			String objPath=mf.getObjAbsPath();
			int i1=objPath.lastIndexOf("\\");
			if(i1!=-1) {
				String folder=objPath.substring(0, i1);
				File f=new File(folder);
				if(!f.exists()) {
					f.mkdirs();
				}
			}
			
			if(wordList!=null){
				//字符串替换
				for(SpecialWord sw:wordList){
//					objPath=objPath.replace(sw.getWord(),sw.getContent());
					content=content.replace(sw.getWord(),sw.getContent());
				}
			}
			FileOutputStream fos=new FileOutputStream(new File(objPath));
			IOUtils.write(content, fos,mf.getObjCode());
			IOUtils.closeQuietly(fos);
		}
		
		return Message.success("在您的配合下，成功生成了文件，请进相应的目录下查看！");
	}
	
	/**
	 * 
	 * @author:liuming
	 * @param fileListJson 模板文件配置列表JSON字符串
	 * @return Message
	 */
	@JsFunction(name="model.existsFile")
	public Message existsFile(String fileListJson) {
		Gson gson=new Gson();
		List<ModelFile> fileList = gson.fromJson(fileListJson,new TypeToken<List<ModelFile>>(){}.getType());
		
		if(fileList==null){
			return Message.error("请设置模板文件配置列表！");
		}
		
		//遍历文件列表，返回一个List<Map<>>，包含数据的索引，目标文件的路径
		List<String> list=new ArrayList<String>();
		for(ModelFile mf:fileList){
			String objAbsPath=mf.getObjAbsPath();
			File file=new File(objAbsPath);
			if(file.exists()) {
				list.add(objAbsPath);
			}
			
		}
		
		return Message.success("操作成功",list);
	}
}
