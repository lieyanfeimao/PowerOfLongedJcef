/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月2日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.jsimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.cef.callback.CefRunFileDialogCallback;
import org.cef.handler.CefDialogHandler.FileDialogMode;
import com.xuanyimao.polj.index.anno.JsClass;
import com.xuanyimao.polj.index.anno.JsFunction;
import com.xuanyimao.polj.index.bean.AppConfig;
import com.xuanyimao.polj.index.bean.Constants;
import com.xuanyimao.polj.index.bean.DevRepertory;
import com.xuanyimao.polj.index.bean.HandlerObject;
import com.xuanyimao.polj.index.bean.Message;
import com.xuanyimao.polj.index.util.ToolUtil;

/**
 * @Description: 公共方法类
 * @author liuming
 */
@JsClass
public class CommonFunction {
	
	/**
	 * 打开系统文件选择对话框
	 * @author:liuming
	 * @param ho HandlerObject对象
	 * @param model 对话框模式：FILE_DIALOG_OPEN   打开方式，单个文件 ;FILE_DIALOG_OPEN_MULTIPLE   打开方式，多个文件 ;FILE_DIALOG_SAVE   保存方式
	 * @param title 左上角标题
	 * @param filePath 默认路径
	 * @param filters  文件类型过滤器
	 * @param selectedFilter 默认选择的过滤器索引
	 */
	@JsFunction(name="fileDialog")
	public void fileDialog(HandlerObject ho,String model,String title,String filePath,Vector<String> filters,Integer selectedFilter) {
		/**
		 *  参数1：FileDialogMode  对话框模式
		 *      FileDialogMode.FILE_DIALOG_OPEN   打开方式，单个文件
		 *      FileDialogMode.FILE_DIALOG_OPEN_MULTIPLE   打开方式，多个文件
		 *      FileDialogMode.FILE_DIALOG_SAVE   保存方式
		 *  参数2：title   标题
		 *  参数3：defaultFilePath  默认路径
		 *  参数4：acceptFilters    文件类型过滤器
		 *  参数5：selectedAcceptFilter  默认选择的过滤器索引
		 *  参数6：callback    回调接口
		 */
//		Vector<String> acceptFilters=new Vector<String>();
//		acceptFilters.add("图片文件|.png;.jpg;.gif;.bmp");
//		acceptFilters.add("text/*");
		FileDialogMode fileDialogMode=FileDialogMode.valueOf(model==null?"FILE_DIALOG_OPEN":model);
		title=title==null?"请开始你的表演":title;
		
		ho.getBrowser().runFileDialog(fileDialogMode, title, filePath, filters, selectedFilter==null?0:selectedFilter, new CefRunFileDialogCallback() {
			
			@Override
			public void onFileDialogDismissed(int selectedAcceptFilter, Vector<String> filePaths) {
				// TODO Auto-generated method stub
//				System.out.println(selectedAcceptFilter);  使用的过滤器索引
//				for(String s:filePaths) { //选择的文件路径列表
//					System.out.println("选择了文件:"+s);
//				}
				ToolUtil.successMessage(ho, filePaths);
			}
		});
	}
	
	/**
	 * 获取应用列表
	 * @author:liuming
	 * @return
	 */
	@JsFunction(name="appList")
	public List<AppConfig> appList(){
		return DevRepertory.getInstance().getAppList();
	}
	
	/**
	 * 写入应用数据到指定文件
	 * @author:liuming
	 * @param appId 
	 * @param fileName 文件名
	 * @param data 数据名
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@JsFunction(name="writeAppData")
	public Message writeAppData(String appId,String fileName,String data) throws IOException {
		if(StringUtils.isBlank(appId)) {
			return Message.error("appId必须填写，appId是当前应用的ID");
		}
		if(StringUtils.isBlank(fileName)) {
			fileName=Constants.APPDEFAULTFILE;
		}
		String filePath=DevRepertory.getInstance().getPath()+File.separator+Constants.APPDATAFOLDER.replace("{appId}",appId);
		File file=new File(filePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		filePath+=File.separator+fileName;
		System.out.println(filePath);
		FileOutputStream fos=new FileOutputStream(new File(filePath));
		IOUtils.write(data, fos,"UTF-8");
		IOUtils.closeQuietly(fos);
		return Message.success("操作成功");
	}
	/**
	 * 读取应用的数据文件
	 * @author:liuming
	 * @param appId
	 * @param fileName
	 * @return 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@JsFunction(name="readAppData")
	public Message readAppData(String appId,String fileName) throws IOException {
		if(StringUtils.isBlank(appId)) {
			return Message.error("appId必须填写，appId是当前应用的ID");
		}
		if(StringUtils.isBlank(fileName)) {
			fileName=Constants.APPDEFAULTFILE;
		}
		String filePath=DevRepertory.getInstance().getPath()+File.separator+Constants.APPDATAFOLDER.replace("{appId}",appId)+File.separator+fileName;
		File file=new File(filePath);
		if(!file.exists()) {
			return Message.success("操作成功","");
		}
		FileInputStream fis=new FileInputStream(file);
		String content=IOUtils.toString(fis,"UTF-8");
		IOUtils.closeQuietly(fis);
		return Message.success("操作成功",content);
	}
	/**
	 * 获取应用的根目录
	 * @author:liuming
	 * @return
	 */
	@JsFunction(name="appPath")
	public Message getAppPath() {
		return Message.success("",DevRepertory.getInstance().getPath());
	}
	/**
	 * 打开文件资源管理器
	 * @author:liuming
	 * @param path 文件路径
	 */
	@JsFunction(name="openExplorer")
	public void openExplorer(String path) {
		try {
			path=path.replace("\\\\", "\\");
			if(!path.endsWith("\\")) {
				int i1=path.lastIndexOf("\\");
				if(i1!=-1) {
					path=path.substring(0,i1);
				}
			}
			System.out.println(path);
			Runtime.getRuntime().exec("explorer " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test() {
		System.out.println("test============");
	}
}
