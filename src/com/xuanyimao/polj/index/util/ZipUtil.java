/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月14日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.util;

import java.io.File;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * @Description: 压缩工具类
 * @author liuming
 */
public class ZipUtil {
	/**
	 * 解压文件到指定目录
	 * @author:liuming
	 * @param zipFile 指定zip文件
	 * @param extractFolder 解压的目录
	 * @throws ZipException
	 */
	public static void unzip(String zipFile,String extractFolder) throws ZipException {
		ZipFile zf = new ZipFile(zipFile);
//		zf.setFileNameCharset("GBK");
		if (!zf.isValidZipFile()) {
			throw new ZipException("压缩文件可能已被损坏。");
		}
		File extractDir = new File(extractFolder);
		//如果文件不存在或者不是目录
		if(!extractDir.exists() || !extractDir.isDirectory()) {
			extractDir.mkdirs();
		}
		zf.extractAll(extractFolder);
	}
	
//	public static void unzip(String zipFile,List<FileConfig> fileConfigs) throws ZipException {
//		ZipFile zf = new ZipFile(zipFile);
//		if (!zf.isValidZipFile()) {
//			throw new ZipException("压缩文件可能已被损坏。");
//		}
//		
//		List<FileHeader> fileHeaders = zf.getFileHeaders();
//		for(FileHeader fh:fileHeaders) {
//			System.out.println(fh.getFileName()+">>>"+fh.isDirectory());
//		}
//	}
	
	/**
	 * 压缩文件
	 * @author:liuming
	 * @param files 文件列表，目录/文件
	 * @param zipFile 输出的zip文件
	 * @return
	 * @throws ZipException
	 */
	public static String zip(List<String> files,String zipFile) throws ZipException {
		File file=new File(zipFile);
		if(file.exists()) {
			file.delete();
		}
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);//压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);//压缩级别
		ZipFile zf = new ZipFile(zipFile);
		for(int i=0;i<files.size();i++) {
			File f=new File(files.get(i));
			System.out.println(f.getAbsolutePath());
			if(!f.exists()) continue;
			if(f.isDirectory()) {
				zf.addFolder(f, parameters);
			}else {
				zf.addFile(f, parameters);
			}
		}
		return zipFile;
	}
	
//	public static void main(String[] args) throws ZipException {
////		List<String> files=new ArrayList<String>();
////		files.add("D:\\document\\jcef");
////		files.add("D:\\document\\装逼王是怎样炼成的.pdf");
////		ZipUtil.zip(files, "d:\\zipfile测试.zip");
////		ZipUtil.unzip("d:\\zipfile测试.zip", "d:\\zipfile测试");
//		ZipUtil.unzip("d:\\modeldeal.zip", null);
//		System.out.println("操作完成");
//	}
}
