/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2018年11月8日
 * @version V1.0 
 */
package com.xuanyimao.polj.index.exception;

/**
 * @Description: 扫描包路径未配置异常
 * @author liuming
 */
public class ScannerPackageNotFoundException extends Exception {

    /**
     * id
     */
    private static final long serialVersionUID = 2540766252483785192L;
    
    public ScannerPackageNotFoundException(String errmsg){
        super(errmsg);
    }
}
