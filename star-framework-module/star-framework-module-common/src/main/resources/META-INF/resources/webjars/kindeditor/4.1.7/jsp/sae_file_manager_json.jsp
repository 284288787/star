<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.json.simple.*" %>
<%@page import="com.sina.sae.storage.SaeStorage"%>
<%@page import="com.sina.sae.storage.FileAttribute"%>
<%@page import="com.star.framework.common.util.Parameters"%>
<%@page import="net.sf.json.JSONObject"%>
<%

/**
 * KindEditor JSP
 *
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 *
 */

 String domain = "atfool";
//图片扩展名
String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
SaeStorage ss = new SaeStorage();
List<String> list = ss.getList(domain);
//遍历目录取的文件信息
List<Hashtable> fileList = new ArrayList<Hashtable>();
if(null != list) {
	for (String file_ : list) {
		Hashtable<String, Object> hash = new Hashtable<String, Object>();
		FileAttribute fa = ss.getAttr(domain, file_);
		String fileName = fa.getFileName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		hash.put("is_dir", false);
		hash.put("has_file", false);
		hash.put("filesize", fa.getLength());
		hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
		hash.put("filetype", fileExt);
		hash.put("filename", fileName);
		hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fa.getDatetime()));
		fileList.add(hash);
	}
}

if ("size".equals(order)) {
	Collections.sort(fileList, new SizeComparator());
} else if ("type".equals(order)) {
	Collections.sort(fileList, new TypeComparator());
} else {
	Collections.sort(fileList, new NameComparator());
}
JSONObject result = new JSONObject();
result.put("moveup_dir_path", moveupDirPath);
result.put("current_dir_path", currentDirPath);
result.put("current_url", currentUrl);
result.put("total_count", fileList.size());
result.put("file_list", fileList);

response.setContentType("application/json; charset=UTF-8");
out.println(result.toJSONString());
%>
<%!
public class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
		}
	}
}
public class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
				return 1;
			} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
public class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable)a;
		Hashtable hashB = (Hashtable)b;
		if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
		}
	}
}
%>