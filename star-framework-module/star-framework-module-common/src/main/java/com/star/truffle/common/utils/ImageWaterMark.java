/**create by liuhua at 2018年9月20日 下午3:14:03**/
package com.star.truffle.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageWaterMark {
  
  public static final int VERTICAL_ALIGN_TOP = 1;     //垂直居顶
  public static final int VERTICAL_ALIGN_CENTER = 2;  //垂直居中
  public static final int VERTICAL_ALIGN_BOTTOM = 3;  //垂直居底
  public static final int HORIZONTAL_ALIGN_LEFT = 4;    //水平居左
  public static final int HORIZONTAL_ALIGN_CENTER = 5;  //水平居中
  public static final int HORIZONTAL_ALIGN_RIGHT = 6;   //水平居右
  public static final String WATER_MARK_SUFFIX = "m"; 
      
  public static void main(String[] args) {
    String src = "20170622092937_192.jpg";
    String mark = "E:\\uploadimage\\20170622\\shuiyin.png";
    addWaterMarkImage("E:\\uploadimage\\20170622", src, mark, HORIZONTAL_ALIGN_RIGHT, VERTICAL_ALIGN_BOTTOM);
//    String src = "E:\\uploadimage\\20170622\\20170622092937_192.jpg";
//    Color color = Color.RED;
//    Font font = new Font("楷体", Font.PLAIN, 20);
//    String markContent = "你好，中国！";
//    addWaterMarkContent(src, color, font, markContent, HORIZONTAL_ALIGN_CENTER, VERTICAL_ALIGN_CENTER);
  }
  
  public static void addWaterMarkContent(String path, String name, String markContent, int horizontal, int vertical) {
    Color color = Color.WHITE;
    Font font = new Font("楷体", Font.PLAIN, 20);
    addWaterMarkContent(path, name, color, font, markContent, horizontal, vertical);
  }
  
  public static void addWaterMarkContent(String path, String name, Color color, Font font, String markContent, int horizontal, int vertical) {
    try {
      File srcImgFile = new File(path, name);
      Image srcImg = ImageIO.read(srcImgFile); 
      int srcImgWidth = srcImg.getWidth(null); 
      int srcImgHeight = srcImg.getHeight(null);
   
      BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bufImg.createGraphics();
      g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
      g.setColor(color);
      g.setFont(font);
      
      int x = 0;
      int markWidth = getMarkContentWidth(markContent, g);
      switch (horizontal) {
      case HORIZONTAL_ALIGN_CENTER :
        x = (srcImgWidth - markWidth) / 2;
        break;
      case HORIZONTAL_ALIGN_RIGHT :
        x = srcImgWidth - markWidth - 10;
        break;
      default:
        x = 10;
        break;
      }
      
      int y = 0;
      int markHeight = getMarkContentHeight(markContent, g);
      switch (vertical) {
      case VERTICAL_ALIGN_CENTER :
        y = (srcImgHeight - markHeight) / 2;
        break;
      case VERTICAL_ALIGN_BOTTOM :
        y = srcImgHeight - markHeight / 2;
        break;
      default:
        y = markHeight;
        break;
      }
      
      g.drawString(markContent, x, y); // 画出水印
      g.dispose();
      // 输出图片
      FileOutputStream outImgStream = new FileOutputStream(new File(path, getTargetFilePath(name)));
      ImageIO.write(bufImg, getSuffix(name), outImgStream);
      outImgStream.flush();
      outImgStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void addWaterMarkImage(String path, String name, String mark, int horizontal, int vertical) {
    try {
      File srcImgFile = new File(path, name);
      Image srcImg = ImageIO.read(srcImgFile); 
      int srcImgWidth = srcImg.getWidth(null); 
      int srcImgHeight = srcImg.getHeight(null);
   
      File markFile = new File(mark);
      Image markImg = null;
      if (! markFile.exists()) {
        URL url = new URL(mark);
        InputStream in = url.openStream();
        markImg = ImageIO.read(in);
      }else {
        markImg = ImageIO.read(markFile);
      }
      int markImgWidth = markImg.getWidth(null);
      int markImgHeight = markImg.getHeight(null);
      
      BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bufImg.createGraphics();
      g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
      
      int x = 0;
      switch (horizontal) {
      case HORIZONTAL_ALIGN_CENTER :
        x = (srcImgWidth - markImgWidth) / 2;
        break;
      case HORIZONTAL_ALIGN_RIGHT :
        x = srcImgWidth - markImgWidth - 10;
        break;
      default:
        x = 10;
        break;
      }
      
      int y = 0;
      switch (vertical) {
      case VERTICAL_ALIGN_CENTER :
        y = (srcImgHeight - markImgHeight) / 2;
        break;
      case VERTICAL_ALIGN_BOTTOM :
        y = srcImgHeight - markImgHeight - 10;
        break;
      default:
        y = 10;
        break;
      }
      g.drawImage(markImg, x, y, markImgWidth, markImgHeight, null);
      g.dispose();
      // 输出图片
      FileOutputStream outImgStream = new FileOutputStream(new File(path, getTargetFilePath(name)));
      ImageIO.write(bufImg, getSuffix(name), outImgStream);
      outImgStream.flush();
      outImgStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String getSuffix(String path) {
    int idx = path.lastIndexOf(".");
    if (idx != -1) {
      return path.substring(idx + 1);
    }
    return "";
  }
  
  private static String getTargetFilePath(String srcFilePath) {
    int idx = srcFilePath.lastIndexOf(".");
    String target = srcFilePath.substring(0, idx) + WATER_MARK_SUFFIX + srcFilePath.substring(idx);
    return target;
  }
  
  private static int getMarkContentWidth(String markContent, Graphics2D g) {
    return g.getFontMetrics(g.getFont()).charsWidth(markContent.toCharArray(), 0, markContent.length());
  }
  
  private static int getMarkContentHeight(String markContent, Graphics2D g) {
    return g.getFont().getSize();
  }
}
