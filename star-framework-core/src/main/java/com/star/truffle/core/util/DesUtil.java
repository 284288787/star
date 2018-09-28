/**create by liuhua at 2017年6月2日 上午11:00:01**/
package com.star.truffle.core.util;

public class DesUtil {

	public static boolean encrypt_args = false;
	public static boolean encrypt_result = false;
	
	private static Des desObj = new Des();
	private static String firstKey = "u1UHSRP~=tY7MDVEMUKg89q9-sgBOA!w";
	private static String secondKey = null; //"e52BvGo,~Xr1+)h*]^.pX9n(";
	private static String thirdKey = null; //"-6%MTQrCZuSJ2Z@in)6+xV!Y8ZCSk+MB";
	
	/**
	 * 加密
	 *
	 * @param str
	 * @return
	 */
	public static String encrypt(String str){
		String enc = desObj.strEnc(str, firstKey, secondKey, thirdKey);
		return enc;
	}
	
	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static String decrypt(String str){
		String dec = desObj.strDec(str, firstKey, secondKey, thirdKey);
		return dec;
	}
	
	public static void main(String[] args) {
		String str = "null";
		System.out.println(decrypt(str));
	}
}
