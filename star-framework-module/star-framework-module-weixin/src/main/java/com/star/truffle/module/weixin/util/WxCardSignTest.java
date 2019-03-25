package com.star.truffle.module.weixin.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class WxCardSignTest {
    public static void main(String[] args) throws Exception{
            WxCardSign signer = new WxCardSign();
            signer.addData("test1");
            signer.addData(12312312);
            signer.addData(55312312);
            signer.addData("test");
            signer.addData("test2");
            System.out.println(signer.getSignature());
    }

}