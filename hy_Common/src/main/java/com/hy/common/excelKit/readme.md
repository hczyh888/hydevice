# 导出使用方式
## 模版制作（jxtl）
使用方式与el表达式相同 在下方中map中的key可在模版中使用 ${xxx.(方法名|字段名)}
具体jxtl标签请查看教程 http://blog.csdn.net/yuliqi0429/article/details/41907711
## 导出
/**
response 用于输出
path     模版地址 一般模版存放resources目录下如果在当前目录下直接写文件名即可，如果多层template/xxx.xlxs
resultName 下载文件时默认命名
map 传入数据为一个map集合可以传入多个类如List<user>、DateUtils等
*/
ExportExcelTemplateKit.public static void renderExcelTempl(HttpServletResponse response, String path, String resultName,Map<String,Object> map)
/**
对上个方法的重构
datas 一般需要导出数据的集合
varName 变量名称（jstl中调用的key）
mapOther 需要传入的其他类如各种Utils
*/
ExportExcelTemplateKit.public static void renderExcelTempl(HttpServletResponse response,List<?> datas,String varName,String path, String resultName,Map<String,Object> mapOther) 
/**
    一般调用的方法 上面两个方法都是根据特殊需求开放的 而这个方法则是常用无特殊需求时调用
*/
ExportExcelTemplateKit.public static void renderExcelTempl(HttpServletResponse response,List<?> datas,String varName,String path, String resultName)
###对上放特殊需求讲解
如果时间类型为long 导出是需要进行格式化 则需要传入一个utils工具类 （注意：这个类的方法不能是static的需要new一个对象给他）
然后使用${dateUtils.format(user.createDate,'yyyy-MM-dd')}
方式进行转换
# 导入使用方式
##传入获取的提交文件与JavaBean的class如User.class
## JavaBean 导入 使用ImportExcelBeanKit.getImportData(File file,  Class<T> clazz);
## Jfinal Model 导入 使用ImportExcelModelKit.getImportData(File file,  Class<T> clazz);
## 获取值则为传入Class类型的list集合
使用案例 https://gitee.com/duaicxx/jfinal_ioc_example 在分支 导入导出封装实例 中 