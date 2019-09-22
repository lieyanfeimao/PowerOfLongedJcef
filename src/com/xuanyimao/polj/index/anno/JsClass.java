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
 * javascript类注解，程序启动时扫描器会扫描指定包下的包含JsClass注解的类
 * 只有包含此注解，扫描器才会去扫描里面的方�?
 * @author liuming
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JsClass {
	/**类实例名，可根据这个名称获取保存在扫描器的类实例对象*/
	String name() default "";
}
