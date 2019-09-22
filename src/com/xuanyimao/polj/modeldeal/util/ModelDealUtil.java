package com.xuanyimao.polj.modeldeal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xuanyimao.polj.modeldeal.bean.ModelDealMessage;
import com.xuanyimao.polj.modeldeal.bean.ModelDealMessage.Status;
import com.xuanyimao.polj.modeldeal.bean.ModelFile;
import com.xuanyimao.polj.modeldeal.bean.SpecialWord;

/**
 * 模板处理工具类
 * @author liuming
 *
 */
public class ModelDealUtil {
	/**
	 * 根据配置创建文件
	 * @param fileListJson 模板文件配置列表JSON字符串
	 * @param wordListJson 特殊词配置列表JSON字符串
	 * @return 消息对象
	 */
	public static ModelDealMessage createNewFile(String fileListJson,String wordListJson){
		Gson gson=new Gson();
		List<ModelFile> fileList = gson.fromJson(fileListJson,new TypeToken<List<ModelFile>>(){}.getType());
		List<SpecialWord> wordList = gson.fromJson(wordListJson,new TypeToken<List<SpecialWord>>(){}.getType());
//		for(ModelFile mf:fileList){
//			System.out.println(mf.toString());
//		}
//		for(SpecialWord sw:wordList){
//			System.out.println(sw.toString());
//		}
		return createNewFile(fileList, wordList);
	}
	/**
	 * 根据配置创建文件
	 * @param fileList  模板文件配置列表
	 * @param wordList  特殊词配置列表
	 * @return 消息对象
	 */
	public static ModelDealMessage createNewFile(List<ModelFile> fileList,List<SpecialWord> wordList){
		if(fileList==null){
			return new ModelDealMessage(Status.FAIL,"请设置需要创建的文件列表！");
		}
		try {
			//遍历文件列表
			for(ModelFile mf:fileList){
				File file=new File(mf.getSourcePath());
				String content=IOUtils.toString(new FileInputStream(file),mf.getSourceCode());
				String objPath=mf.getObjPath();
				if(wordList!=null){
					//字符串替换
					for(SpecialWord sw:wordList){
						objPath=objPath.replace(sw.getWord(),sw.getContent());
						content=content.replace(sw.getWord(),sw.getContent());
					}
				}
				IOUtils.write(content, new FileOutputStream(new File(objPath)),mf.getObjCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelDealMessage(Status.FAIL,"IO异常:"+e.getMessage());
		}
		return new ModelDealMessage();
	}
	
	
	
}
