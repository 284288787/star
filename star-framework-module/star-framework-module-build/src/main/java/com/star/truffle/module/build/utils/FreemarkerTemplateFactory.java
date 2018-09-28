/**create by liuhua at 2016年2月17日 下午2:31:58**/
package com.star.truffle.module.build.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import freemarker.cache.MruCacheStorage;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 * 利用 freemarker 做的模板生成器
 * @author liuhua
 *
 */
public class FreemarkerTemplateFactory {
	private static FreemarkerTemplateFactory factory = null;
	private static FreemarkerTemplateFactory factory_folder = null;
	private Configuration configuration = null;
	
	/**
	 * 使用字符串作为模板
	 * @author liuhua
	 *
	 * @return
	 */
	public static FreemarkerTemplateFactory getInstance(){
		if (null == factory) {
			try {
				factory = new FreemarkerTemplateFactory();
			} catch (TemplateException | IOException e) {
				e.printStackTrace();
			}
		}
		return factory;
	}
	
	/**
	 * 使用模板文件作为模板，模板文件存放在 folder文件夹下
	 * @author liuhua
	 *
	 * @param folder
	 * @return
	 */
	public static FreemarkerTemplateFactory getInstance(String folder){
		if (null == factory_folder) {
			try {
				factory_folder = new FreemarkerTemplateFactory(folder);
			} catch (TemplateException | IOException e) {
				e.printStackTrace();
			}
		}
		return factory_folder;
	}
	
	private FreemarkerTemplateFactory() throws TemplateException, IOException{
		InputStream inputStream = FreemarkerTemplateFactory.class.getResourceAsStream("/freemarker.properties");
		Version version = new Version("2.3.26");
		configuration = new Configuration(version);
		configuration.setObjectWrapper(new DefaultObjectWrapper(version));
		configuration.setTemplateLoader(new StringTemplateLoader());
		configuration.setCacheStorage(new MruCacheStorage(20, 250));
		configuration.setSettings(inputStream);
	}

	private FreemarkerTemplateFactory(String folder) throws TemplateException, IOException{
		this();
		configuration.setDirectoryForTemplateLoading(new File(folder));
	}
	
	/**
	 * 该方法对应 无参getInstance方法，用来添加模板标识及模板
	 * @author liuhua
	 *
	 * @param templateName
	 * @param templateCode
	 */
	public void addTemplate(String templateName, String templateCode){
		TemplateLoader loader = configuration.getTemplateLoader();
		if (null == loader || ! (loader instanceof StringTemplateLoader)) {
			loader = new StringTemplateLoader();
			configuration.setTemplateLoader(loader);
		}
		StringTemplateLoader stringTemplateLoader = (StringTemplateLoader) loader;
		stringTemplateLoader.putTemplate(templateName, templateCode);
	}
	
	/**
	 * 该方法对应 无参getInstance方法，用来批量添加模板标识及模板
	 * @author liuhua
	 *
	 * @param templates
	 */
	public void setTemplates(Map<String, String> templates){
		for (Iterator<String> iterator = templates.keySet().iterator(); iterator.hasNext();) {
			String templateName = iterator.next();
			String templateCode = templates.get(templateName);
			addTemplate(templateName, templateCode);
		}
	}
	
	/**
	 * 根据数据 和 模板标识 生成一个模板实例(最终结果)
	 * @author liuhua
	 * 
	 * @param data 数据
	 * @param templateName 模板标识 如果是模板文件，则为文件名，如果是字符串模板，则为模板标识
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public <T> String getTemplateInstance(T data, String templateName) throws IOException, TemplateException{
		Template temp = configuration.getTemplate(templateName);
		StringWriter fw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(fw);
		temp.process(data, bw);
		bw.flush();
		fw.close();
//		TemplateLoader loader = configuration.getTemplateLoader();
//		if (null == loader || ! (loader instanceof StringTemplateLoader)) {
//			loader = new StringTemplateLoader();
//			configuration.setTemplateLoader(loader);
//		}
//		StringTemplateLoader stringTemplateLoader = (StringTemplateLoader) loader;
//		boolean bool = stringTemplateLoader.removeTemplate(templateName);
//		System.out.println(bool);
//		configuration.removeTemplateFromCache(templateName);
		StringBuffer stringBuffer = fw.getBuffer();
		return stringBuffer.toString();
	}
	
//	public static void main(String[] args) throws IOException, TemplateException {
//		FreemarkerTemplateFactory factory = FreemarkerTemplateFactory.getInstance();
//		factory.addTemplate("test", "12312312323${lh1!lh}");
//		Map<String, Object> map = new HashMap<>();
//		map.put("lh", "nihao");
//		String str = factory.getTemplateInstance(map, "test");
//		System.out.println("...............................");
//		str = factory.getTemplateInstance(map, "test");
//		System.out.println(str);
//	}
}
