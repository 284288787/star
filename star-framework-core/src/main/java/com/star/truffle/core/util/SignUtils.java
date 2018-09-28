package com.star.truffle.core.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签名
 * 
 * @author sq
 * 
 *         2016年5月11日
 */
public class SignUtils {
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 创建签名
	 * 
	 * @param parameters
	 * @param key
	 * @return
	 */
	public static String createSign(Map<String, Object> parameters, String key) {
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(parameters.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String k = keys.get(i);
			String v = String.valueOf(parameters.get(k));
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Encode(sb.toString(), "utf-8").toUpperCase();
		return sign;
	}

	/**
	 * 
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	/**
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 
	 * @param origin
	 * @param charsetname
	 * @return
	 */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openId", 40);
		params.put("appId", 0);
		params.put("bppId", 0);
		params.put("dppId", 0);
		System.out.println(createSign(params, "hnsdfasdf"));
	}
}
