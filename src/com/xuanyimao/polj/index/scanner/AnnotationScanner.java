/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月8日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.scanner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.xuanyimao.polj.index.anno.JsClass;
import com.xuanyimao.polj.index.anno.JsFunction;
import com.xuanyimao.polj.index.anno.JsObject;
import com.xuanyimao.polj.index.bean.AnnoClass;
import com.xuanyimao.polj.index.bean.AnnoMethod;
import com.xuanyimao.polj.index.bean.AnnoRepertory;
import com.xuanyimao.polj.index.bean.HandlerObject;
import com.xuanyimao.polj.index.bean.Message;
import com.xuanyimao.polj.index.bean.MethodParam;
import com.xuanyimao.polj.index.exception.ScannerPackageNotFoundException;
import com.xuanyimao.polj.index.util.ToolUtil;

/**
 * Description:注解扫描
 * 			叛军被击溃，向北方逃窜，大将军骑着战马走出城门，神色淡然。
 * 			副将走向前来，单膝跪下：“将军，我们要追吗？”
 * 			将军捋了一下胡子,缓缓说道：“追击吧。”
 * 			副将没有动，大军没有动。
 * 			将军一愣，又说一次：“追击吧！”
 * 			副将仍未动，大军仍未动。
 * @version 1.0
 * @author liuming
 */
public class AnnotationScanner {
	static URLClassLoader urlClassLoader= (URLClassLoader) ClassLoader.getSystemClassLoader();
	
	/**
     * Description:注解扫描入口
     * @author:liuming
     * @since 2017-12-4
     * @return void
     * @throws ScannerPackageNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws UnsupportedEncodingException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
     */
	public static void scannerMain() throws ScannerPackageNotFoundException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException, InstantiationException, ClassNotFoundException{
		AnnoRepertory aRepertory=AnnoRepertory.getInstance();
		if(StringUtils.isBlank(aRepertory.getScannerPackage())){
            throw new ScannerPackageNotFoundException("扫描路径未配置");
        }
		//解析所有需要扫描的包，获取类注解
		getScannerPackages(aRepertory.getScannerPackage());
//		for(String str:pgs) {
//			System.out.println(str);
//		}
		//解析类中的方法和字段
		analysisAnnoMethodField();
	}
	/**
	 * 解析注解类中的方法
	 * @author:liuming
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void analysisAnnoMethodField() throws IllegalArgumentException, IllegalAccessException {
		List<AnnoClass> annoClassList=AnnoRepertory.getInstance().getAnnoClassList();
		Map<String,AnnoMethod> methodMap=new HashMap<String,AnnoMethod>();
		if(annoClassList!=null && !annoClassList.isEmpty()) {
			for(AnnoClass annoClass:annoClassList) {
//				System.out.println(annoClass);
				//为类中含有@JsObject注解的字段注入实例，只能注入有@JsClass注解的对象，如果@JsObject标注的字段不是注解类对象集合中，抛出注入失败异常
				Field[] fields=annoClass.getCls().getDeclaredFields();
				if(fields.length>0) {
					for(int i=0;i<fields.length;i++) {
						fields[i].setAccessible(true);
						JsObject jsObject=fields[i].getAnnotation(JsObject.class);
						if(jsObject!=null) {
//							System.out.println(fields[i].getGenericType().getTypeName());
							//为属性赋值，以后根据需要做优化
							for(AnnoClass ac:annoClassList) {
								if(fields[i].getGenericType().getTypeName().equals(ac.getClsName())) {//如果与列表的类名一致
									fields[i].set(annoClass.getObj(), ac.getObj());
									break;
								}
							}
						}
					}
				}
				//解析含有@JsFunction注解的方法，获取方法中的参数
				Method[] methods=annoClass.getCls().getDeclaredMethods();
				if(methods.length>0) {
					for(int i=0;i<methods.length;i++) {
						methods[i].setAccessible(true);
						JsFunction jsFunction=methods[i].getAnnotation(JsFunction.class);
						if(jsFunction!=null) {//方法含有jsFunction注解
//							System.out.println(jsFunction.name());//函数名
//							System.out.println("方法名:"+methods[i].getName());//方法名，不需要
							AnnoMethod annoMethod=new AnnoMethod(methods[i], annoClass);
							//获取方法的所有参数
							Class<?>[] paramClass=methods[i].getParameterTypes();
							if(paramClass.length>0) {//存在参数
								List<MethodParam> paramList=new ArrayList<MethodParam>();
								//使用spring LocalVariableTableParameterNameDiscoverer获取参数名
								ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
								String[] pn=parameterNameDiscoverer.getParameterNames(methods[i]);
								for(int j=0;j<paramClass.length;j++) {
									paramList.add(new MethodParam(paramClass[j], pn[j]));
								}
								annoMethod.setMethodParam(paramList);
							}
							
							methodMap.put(jsFunction.name(),annoMethod);
							
						}
					}
				}
			}
		}
		
		AnnoRepertory.getInstance().setMethodMap(methodMap);
	}
	
	/**
     * Description:获取所有需要扫描的包列表
     * @author:liuming
     * @since 2017-12-4
     * @param packagePath 扫描包路径
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
     */
    public static void getScannerPackages(String packagePath) throws UnsupportedEncodingException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        //获取一共要扫描多少包
        String[] packages=packagePath.split(";");
        //获取所有需要扫描的包
        List<String> fpg=new ArrayList<String>();
        for(int i=0;i<packages.length;i++){
        	if(StringUtils.isBlank(packages[i])) continue;
            fpg.add(packages[i].replace(".","/"));
        }
        getScannerPackage(fpg);
    }
    /**
     * Description:递归获取所有的包，将*号转换成具体的包名，遍历里面的类
     * @author:liuming
     * @since 2017-12-4
     * @param pgs
     * @return List<String>
     * @throws UnsupportedEncodingException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static void getScannerPackage(List<String> pgs) throws UnsupportedEncodingException, ClassNotFoundException, InstantiationException, IllegalAccessException{
    	List<AnnoClass> annoClassList=new ArrayList<AnnoClass>();
    	/***********************扫描指定jar包***********************/
    	//获取包名的正则，用于jar扫描时做目录匹配
    	List<String> regPgs=new ArrayList<String>();
    	for(String pg:pgs) {
    		regPgs.add(getPkgReg(pg));
    	}
    	
    	List<String> jarList = AnnoRepertory.getInstance().getExtraJars();
    	for(String jar:jarList) {
    		try {
				JarFile jarFile=new JarFile(jar);
				Enumeration<JarEntry> entry = jarFile.entries();
				JarEntry jarEntry;
				while (entry.hasMoreElements()) {
					jarEntry = entry.nextElement();
					String name = jarEntry.getName();
					if (name.charAt(0) == '/') {
						name=name.substring(1);
					}
					for(String reg:regPgs) {
						if(name.matches(reg)) {//匹配成功
							System.out.println(jar+"扫描的类："+name);
//							System.out.println(name.matches(".*?\\$\\d+.*?"));
							if(name.toLowerCase().endsWith(".class")  && !jarEntry.isDirectory()) {//如果是class文件，加载
								AnnoClass ac = loadJsClass(name.replace("/",".").substring(0,name.length()-6));
								if(ac!=null) annoClassList.add(ac);
							}
							break;
						}
					}
					
				}
				jarFile.close();
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println(jar+"文件加载失败,跳过扫描...");
			}
    	}
    	
    	/***********************扫描未在jar包的class**********************/
        for(String pg:pgs){
        	analysisAnnoClass(pg,annoClassList);
        }
        AnnoRepertory.getInstance().setAnnoClassList(annoClassList);
    }
    
    /**
     * 扫描非jar包内的class，工程的bin目录
     * @author:liuming
     * @param pg
     * @param annoClassList
     * @throws UnsupportedEncodingException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void analysisAnnoClass(String pg,List<AnnoClass> annoClassList) throws UnsupportedEncodingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    	int sindex=pg.indexOf("*");
    	String pgPath=pg;
        if(sindex!=-1){//如果存在*号
        	pgPath=pg.substring(0,sindex);
        }
    	
    	String protocol ="";//协议名称
        String filePath ="";//资源物理路径
        File file;//文件对象
        URL url = urlClassLoader.getResource(pgPath);
        if(url==null){
            return;
        }
        
        // 得到协议的名称
        protocol = url.getProtocol();
        if("file".equals(protocol)){
            filePath = URLDecoder.decode(url.getFile(), "UTF-8");
            file=new File(filePath);
            if(file.isDirectory()){//如果是目录才处理
            	if(pg.indexOf("*")!=-1) {//获取当前包下所有目录，继续向下探查
            		for(File f:file.listFiles()){
                        if(f.isDirectory()){
                        	analysisAnnoClass(pgPath+f.getName()+pg.substring(sindex+1), annoClassList);
                        }
                    }
            		return;
            	}
            	//获取所有的class文件
                 File[] fList=file.listFiles(new FileFilter() {
                     @Override
                     public boolean accept(File f) {
//                    	 System.out.println("扫描的文件:"+f.getAbsolutePath());
                         return !f.isDirectory() && f.getName().endsWith(".class");
                     }
                 });
                 if(fList!=null){
                     for(File f:fList){
                    	 AnnoClass ac = loadJsClass((pg+"/"+f.getName().substring(0,f.getName().length()-6)).replace("/","."));
						 if(ac!=null) annoClassList.add(ac);
                     }
                 }
            }
        }
    }
    
    /**
     * 扫描注解文件
     * @author:liuming
     * @param clsName Class名
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static AnnoClass loadJsClass(String clsName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    	Class<?> cls=Class.forName(clsName);
//	   	 System.out.println("处理的类文件："+cls);
		 //获取类上的JsClass注解
		 JsClass jsClass=cls.getAnnotation(JsClass.class);
		 if(jsClass!=null) {
	   		 System.out.println("扫描到的注解类:"+cls+"..."+clsName);
//	   		 System.out.println("注解name:"+jsClass.name());
			 String className=jsClass.name();
			 if(StringUtils.isBlank(className)) {
//	   			 System.out.println("扫描到的注解>>"+cls.getName());  // 包名.类名
				 className=cls.getSimpleName();
				 String s1=className.substring(0,1).toLowerCase();
				 className=s1+className.substring(1);
	//   			 System.out.println(className);
			 }
			 return new AnnoClass(cls, cls.newInstance(), className,cls.getName());
		 }
		 return null;
    }
    
    /**
     * 获取包名的正则表达式
     * @author:liuming
     * @param pkg
     * @return
     */
    public static String getPkgReg(String pkg) {
    	if(!pkg.endsWith("*") && !pkg.endsWith("/")) {
			pkg+="/";
		}
		if(pkg.endsWith("*")) {
			pkg=pkg.substring(0,pkg.length()-1)+"[^/]*?/[^/]*?";
		}else if(pkg.endsWith("/")) {
			pkg=pkg+"[^/]*?";
		}
		pkg=pkg.replace("/*/", "/[^/]*?/");
		return pkg;
    }
    /**
     * js调用Java代码 执行器主程序
     * @author:liuming
     * @param funcName    JS函数名
     * @param jsonObject  前端传入的参数对象
     * @param ho          js交互数据重新封装的对象
     */
    public static void execMain(String funcName,JsonObject jsonObject,HandlerObject ho) {
    	Gson gson=new GsonBuilder().serializeNulls().create();
    	try {
	    	AnnoMethod annoMethod = AnnoRepertory.getInstance().getMethodMap().get(funcName);
	    	if(annoMethod==null) {
	    		System.out.println("函数未在Java代码中声明，调用失败!");
	    		if(ho!=null) ho.getCallback().failure(-1, gson.toJson(Message.error("函数未在Java代码中声明，调用失败!")));
	    		return;
	    	}
	    	//方法参数中是否有HandlerObject对象，如果方法传入了此对象，由方法处理返回值
	    	boolean hasHo=false;
	//    	System.out.println(annoMethod.getMethod().getReturnType());
	    	//获取方法的参数列表
	    	List<MethodParam> methodParam = annoMethod.getMethodParam();
	    	Method method=annoMethod.getMethod();
	    	method.setAccessible(true);
	    	Object result=null;
	    	if(methodParam==null || methodParam.isEmpty()) {//不需要传递参数
	    		result=method.invoke(annoMethod.getAnnoClass().getObj());
	//    		System.out.println(gson.toJson(result));
	    	}else {//对传入的参数进行处理
	    		Object[] objs=new Object[methodParam.size()];
	    		//遍历参数数组是否存在ho类型的参数，标记位置
	    		for(int i=0;i<methodParam.size();i++) {
	    			MethodParam mp=methodParam.get(i);
	    			if(mp.getCls()==HandlerObject.class) {
	    				objs[i]=ho;
	    				hasHo=true;
	//    				System.out.println("需要传入内置ho对象");
	    			}else {
	    				if(jsonObject!=null && jsonObject.get(mp.getName())!=null) {
	    					objs[i]=gson.fromJson(jsonObject.get(mp.getName()), mp.getCls());
	    				}else {
	    					objs[i]=null;
	    				}
	    			}
	    		}
	    		
	    		result=method.invoke(annoMethod.getAnnoClass().getObj(),objs);
	    	}
//	    	System.out.println(gson.toJson(Message.success("操作成功", result)));
	    	if(!hasHo) ToolUtil.successMessage(ho, result);
    	}catch(Exception e) {
//    		e.printStackTrace();
//    		System.out.println(ToolUtil.getExceptionMessage(e));
    		System.out.println(ho);
    		if(ho!=null) ho.getCallback().failure(-1,gson.toJson(Message.error("程序异常:<br/>"+ToolUtil.getExceptionMessage(e)+"<br/><font color='red'>[一位优秀的程序员准备甩锅](๑＞ڡ＜)✿ </font>")));
    	}
    }
    /** 
        *  根据JsClass注解的name获取扫描器保存的实体对象
     * @author:liuming
     * @param name
     * @return
     */
    public static Object getJsClassInstance(String name) {
    	List<AnnoClass> annoClassList = AnnoRepertory.getInstance().getAnnoClassList();
    	if(annoClassList!=null && !annoClassList.isEmpty()) {
    		for(AnnoClass ac:annoClassList) {
    			if(name.equals(ac.getName())) {
    				return ac.getObj();
    			}
    		}
    	}
    	return null;
    }
    
    
}