package com.star.truffle.module.weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class WxCardSign {

  public WxCardSign() {
    signParams = new ArrayList<String>();
  }

  public void addData(String value) {
    signParams.add(value);
  }

  public void addData(Object value) {
    signParams.add(value.toString());
  }

  public String getSignature() {
    Collections.sort(signParams);
    StringBuilder string_to_sign = new StringBuilder();
    for (String str : signParams) {
      string_to_sign.append(str);
    }
    System.out.println("string_to_sign:" + string_to_sign);
    try {
      MessageDigest hasher = MessageDigest.getInstance("SHA-1");
      byte[] digest = hasher.digest(string_to_sign.toString().getBytes());
      return byteToHexString(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return "";
    }
  }

  public String byteToHexString(byte[] data) {
    StringBuilder str = new StringBuilder();
    for (byte b : data) {
      String hv = Integer.toHexString(b & 0xFF);
      if (hv.length() < 2)
        str.append("0");
      str.append(hv);
    }
    return str.toString();
  }

  private ArrayList<String> signParams;
}
