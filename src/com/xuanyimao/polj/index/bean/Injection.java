/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月3日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.xuanyimao.polj.index.jsimpl.IndexJs;
/**
 * @Description: 注入类
 * @author liuming
 */
public class Injection {
	public final static String CONFIG_FILE="scconfig";
	
	private final static Injection inj=new Injection();
	
	private Injection() {};
	/**
	 * 获取注入类实例
	 * @author:liuming
	 * @return
	 */
	public static Injection getInstance() {
		return inj;
	}
	
	/**注入配置：url->配置文件名*/
//	private Map<String,String> config;
	private List<ScpConfig> configs;
	
	/**配置相关内容*/
	private Map<String,Scp> scpInfo;
	
//	/**当前应用的路径*/
//	private String appPath;
	
	
	
//	/**配置文件名**/
//	private final static String FILE_NAME="config";
	
//	/**
//	 * config
//	 * @return config
//	 */
//	public Map<String, String> getConfig() {
//		return config;
//	}
//
//	/**
//	 * 设置 config
//	 * @param config config
//	 */
//	public void setConfig(Map<String, String> config) {
//		this.config = config;
//	}

	/**
	 * scpInfo
	 * @return scpInfo
	 */
	public Map<String, Scp> getScpInfo() {
		return scpInfo;
	}

	/**
	 * configs
	 * @return configs
	 */
	public List<ScpConfig> getConfigs() {
		return configs;
	}
	/**
	 * 设置 configs
	 * @param configs configs
	 */
	public void setConfigs(List<ScpConfig> configs) {
		this.configs = configs;
		reloadScript();
	}
	
	public void reloadScript() {
		scpInfo=new HashMap<String,Scp>();
		if(this.configs!=null && !this.configs.isEmpty()) {
			for(ScpConfig sc:this.configs) {
				if( StringUtils.isNotBlank(sc.getName()) ) {
					File file=new File(DevRepertory.getInstance().getPath()+File.separator+Constants.SCP_PATH+File.separator+sc.getFile());
					if(!file.exists()) continue;
					try {
						FileInputStream fis=new FileInputStream(file);
						String data=IOUtils.toString(fis,"UTF-8");
						IOUtils.closeQuietly(fis);
						Scp scp=new Scp();
						scp.setContent(data);
						System.out.println("读取脚本："+sc.getUrl()+">>>"+file.getAbsolutePath());
//						System.out.println(data);
//						System.out.println("......");
						scpInfo.put(sc.getFile(), scp);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * 设置 scpInfo
	 * @param scpInfo scpInfo
	 */
	public void setScpInfo(Map<String, Scp> scpInfo) {
		this.scpInfo = scpInfo;
	}
	/**
	 * 保存配置
	 * @author:liuming
	 * @throws IOException
	 */
	public void saveConfig() throws IOException {
		String filePath=DevRepertory.getInstance().getPath()+File.separator+Constants.APPDATAFOLDER.replace("{appId}",IndexJs.APP_ID)+File.separator+Injection.CONFIG_FILE;
		FileOutputStream fos=new FileOutputStream(filePath);
		IOUtils.write(new Gson().toJson(configs), fos);
		IOUtils.closeQuietly(fos);
	}
//	/**
//	 * appPath
//	 * @return appPath
//	 */
//	public String getAppPath() {
//		return appPath;
//	}
//
//	/**
//	 * 设置 appPath
//	 * @param appPath appPath
//	 */
//	public void setAppPath(String appPath) {
//		this.appPath = appPath;
//	}
//	
//	/**
//	 * 初始化配置
//	 * @author:liuming
//	 */
//	public void initConfig() {
//		appPath=System.getProperty("user.dir");
////		System.out.println(appPath);
////		File file=new File(appPath+File.separator+FILE_NAME);
////		System.out.println(file.getAbsolutePath());
////		if(!file.exists()) {
////			return;
////		}
////		try {
////			FileInputStream fis = new FileInputStream(file);
////			String data=IOUtils.toString(fis,"UTF-8");
////			IOUtils.closeQuietly(fis);
////			
////			if(StringUtils.isNotBlank(data)) {
////				config=new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){}.getType());
////			}
////		} catch (FileNotFoundException e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		
////		System.out.println("读取配置成功:"+config);
////		
////		if(config!=null && !config.isEmpty()) {
////			file=new File(appPath+SCP_PATH);
////			if(!file.exists()) {
////				file.mkdirs();
////				return;
////			}
////			scpInfo=new HashMap<String, Scp>();
////			Iterator<Entry<String, String>> it = config.entrySet().iterator();
////			while(it.hasNext()) {
////				Entry<String, String> next = it.next();
////				String key=next.getKey();
////				file=new File(appPath+SCP_PATH+next.getValue());
////				if(!file.exists()) continue;
////				try {
////					FileInputStream fis=new FileInputStream(file);
////					String data=IOUtils.toString(fis,"UTF-8");
////					IOUtils.closeQuietly(fis);
////					Scp scp=new Scp();
////					scp.setContent(data);
////					System.out.println("读取脚本："+key+">>>"+file.getAbsolutePath());
//////					System.out.println(data);
//////					System.out.println("......");
////					scpInfo.put(key, scp);
////				} catch (FileNotFoundException e) {
////					e.printStackTrace();
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			}
////		}
//		
//	}
}
