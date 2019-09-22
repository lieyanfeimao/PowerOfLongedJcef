/**
 * 
 */
package com.xuanyimao.polj.index.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * javascript函数注解，程序启动时扫描器会扫描指定包下的包含JsClass注解的类，收集包含JsFunction注解的方�?
 * 如果有个方法的注解为 
 * @author liuming
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JsFunction {
	/**函数�?*/
    String name();
}
