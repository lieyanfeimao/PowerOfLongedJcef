package com.xuanyimao.polj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.xuanyimao.polj.index.bean.AnnoRepertory;
import com.xuanyimao.polj.index.bean.AppConfig;
import com.xuanyimao.polj.index.bean.DevRepertory;
import com.xuanyimao.polj.index.exception.ScannerPackageNotFoundException;
import com.xuanyimao.polj.index.scanner.AnnotationScanner;
import com.xuanyimao.polj.index.util.ToolUtil;

/**
 * @Description:力量获取入口，Run As > Java Application，沐浴在JCEF的光辉，跨入众神力量的殿堂。
 * 				众神雄伟而让你心潮澎湃的威严声音响彻殿堂：“你渴望力量吗！”
 * 				“不，我渴望……”
 * 				“滚——”
 * 				- 对待代码要像对待自己的情人一样，用心，细心。
 * @author liuming
 */
public class StartupApp {
	
	public final static String defaultPkg="com.xuanyimao.polj.index;com.xuanyimao.polj.*.jsimpl;";
	
	public static void main(String[] args) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, InstantiationException, ClassNotFoundException, ScannerPackageNotFoundException, NoSuchMethodException, SecurityException, InvocationTargetException {
		String path=System.getProperty("user.dir");
		DevRepertory dr = DevRepertory.getInstance();
		dr.setPath(path);
		//加载应用配置信息
		dr.loadAppConfig();
		
		List<AppConfig> appList = dr.getAppList();
		//加载应用需要的jar
		List<String> jarList=new ArrayList<String>();
		
		//扫描所有应用的注册类，包含jsimpl
		String scannerPkg="";
		
		for(AppConfig appConfig:appList) {
			if(StringUtils.isNotBlank(appConfig.getScannerpkg())) {
				scannerPkg+=appConfig.getScannerpkg();
				if(!appConfig.getScannerpkg().endsWith(";")) {
					scannerPkg+=";";
				}
			}else if(StringUtils.isNotBlank(appConfig.getLibjar())) {
				String[] jars=appConfig.getLibjar().split(";");
				for(String jar:jars) {
					if(StringUtils.isNotBlank(jar)) {
						jarList.add(ToolUtil.getTransData(jar,appConfig.getId()));
					}
				}
			}
		}
		scannerPkg+=defaultPkg;
//		System.out.println(scannerPkg);
		
		//得到系统类加载器
		URLClassLoader urlClassLoader= (URLClassLoader) ClassLoader.getSystemClassLoader();
		//加载jar，未做深度测试。run目录下的modeldeal即是以单独的jar文件引入的
		for(int i=0;i<jarList.size();i++) {
			System.out.println("测试加载jar:"+jarList.get(i));
			File file=new File(jarList.get(i));
			if(!file.exists()) {
				System.out.println(jarList.get(i)+"不存在...");
				jarList.remove(i);
				i--;
				continue;
			}
			URL url = file.toURI().toURL();
			
			Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			add.setAccessible(true);
			add.invoke(urlClassLoader, new Object[] {url });
		}
//		JarFile jarFile=new JarFile(file);
//		Enumeration<JarEntry> entry = jarFile.entries();
//		JarEntry jarEntry;
//		while (entry.hasMoreElements()) {
//			jarEntry = entry.nextElement();
//			System.out.println(jarEntry.getName());
//		}
//		URL[] urLs = urlClassLoader.getURLs();
//		for(URL u:urLs) {
//			System.out.println("加载的URL:"+u);
//		}
		
		//扫描包
		AnnoRepertory ar=AnnoRepertory.getInstance();
		ar.setScannerPackage(scannerPkg);
		ar.setExtraJars(jarList);
		AnnotationScanner.scannerMain();
	}
}
