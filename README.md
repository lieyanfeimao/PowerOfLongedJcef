## 工程导入
编译环境：JDK1.8  
将lib下的jar包Add to build path，解压binary_win64目录下的binary_win64.zip，解压时请选择“解压到当前文件夹”（所有二进制文件加起来160M）  
项目>Properties(属性)>Java Build Path，展开Jdk，选中Native library location，点击Edit，选择当前项目目录下的binary_win64  
运行启动类 StartupApp  
注意，本工程只在Windows 64位电脑上测试，如需其他版本的，请自行编译JCEF。  

## 目录说明
src：JAVA代码目录  
app：HTML文件存放目录，可以理解为应用目录。各个应用的HTML部分应以单个目录的形式存放在这里。  
bin：class文件目录  
binary_win64：JCEF二进制文件目录  
data：数据储存目录，以应用ID生成相应的目录。data数据以json格式数据储存，方便修改  
lib：JAR文件目录  
scp：脚本文件目录，对应首页的脚本管理    
run：里面有一个可使用run.bat运行的项目  

## 创建一个JS与JAVA交互的接口
在com.xuanyimao.polj下面创建一个自己的包，比如：com.xuanyimao.polj.test，在下面继续创建一个jsimpl包：com.xuanyimao.polj.test.jsimpl。  
新建一个普通JAVA类，加上注解 @JsClass ，创建一个方法，加上注解：@JsFunction(name="test1")。里面随便写一些代码。可参考各jsimpl包下的代码。  
前台HTML页面引入 app\static\js\common.js，执行以下代码 execJava("test1",null,function(data){}); ，建议直接在index.html做测试。  
common.js中的execJava函数弹层使用的是easyui，如果想换成其他ui，请参照该代码进行改写。  
  
从StartupApp中可以看出，程序在启动时扫描了指定目录下的jsimpl包，如果你的应用想要用其他的包名，请在这里指定，或在打包成应用包的时候指定扫描的包名。  
有时候你可能需要传List对象，很抱歉，不支持。不过你把它再次包成一个大对象就支持了。参考com.xuanyimao.polj.index.jsimpl.IndexJs的createInstallPkg方法  
**注解说明：**  
**@JsClass** 表示这个类是个JS接口类  
**@JsFunction** 标明前台该如何调用这个JAVA方法，name属性是JS调用时使用的名称  
**@JsObject** 可用此注解动态注入@JsClass的类对象，不建议使用，建议用：AnnotationScanner.getJsClassInstance(JsClass名)  

## **代码相关说明**
因为PowerOfLongedJCEF的作者拥有良好的编码规范，几乎所有方法都有注释，所以，你可以尽情的研究源码。PowerOfLongedJCEF的核心代码都在com.xuanyimao.polj.index 下。  
不要纳闷我为什么没有new MainFrame()，窗口却启动了。注解扫描器扫描时会自动实例化一个对象到内存中。它会被打开的原因就是因为注解扫描器扫描了它，而它有JsClass这个注解。  
**com.xuanyimao.polj.StartupApp**  
项目启动类，加载配置，扫描JS接口，启动窗口  
**com.xuanyimao.polj.index.MainFrame**  
主窗口类，从这里初始化CefManager数据  
**com.xuanyimao.polj.index.CefManager**  
JCEF对象管理类，和浏览器相关的事件都在里面，JS事件注册也在这里  
**com.xuanyimao.polj.index.scanner.AnnotationScanner**  
注解扫描器主类，扫描注解，执行JS和Java代码交互  
**com.xuanyimao.polj.index.bean.HandlerObject**  
这是JS和Java方法交互中的一个特殊对象，它包含了完整的原始交互信息。如果你在你方法的参数中使用了它，它会被自动注入。示例：**com.xuanyimao.polj.index.jsimpl.CommonFunction**中的**fileDialog**  
**com.xuanyimao.polj.index.util.ZipUtil**  
文件压缩工具类  
**com.xuanyimao.polj.index.util.ToolUtil**  
乱七八糟的工具类  

## **二次开发规范**
如果你想使用PowerOfLongedJCEF直接开发，我建议遵循以下规则：  
尽量不修改 com.xuanyimao.polj 下的代码，自立门户，创建自己的包。在StratupApp中添加自己的包的扫描路径。这样是为了防止我万一吃饱了没事干去更新一个比较好的新版本，你手足无措。  
src下的包名和app下的包名与你的应用ID保持一致，这样方便你自己开发  
JS交互接口的名字以 应用ID.方法名 的形式，以免和我的产生冲突。  
总之，你按规定，我随意。