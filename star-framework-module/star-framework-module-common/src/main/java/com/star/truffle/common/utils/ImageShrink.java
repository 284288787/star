/**create by liuhua at 2018年9月26日 上午10:43:56**/
package com.star.truffle.common.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * 图片缩小
 * @author liuhua
 *
 */
public class ImageShrink {
  
  public static void main(String[] args) {
    String src = "t2.png";
    shrink("E:\\uploadimage\\20170622", src, 200);
  }

  public static final String SHRINK_SUFFIX = "s";
  
  public static void shrink(String path, String name, int width) {
    try {
      File srcImgFile = new File(path, name);
      Image srcImg = ImageIO.read(srcImgFile); 
      int srcImgWidth = srcImg.getWidth(null); 
      int srcImgHeight = srcImg.getHeight(null);
      int height = Double.valueOf(width * (srcImgHeight / (srcImgWidth * 1.0))).intValue();
      
      BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bufImg.createGraphics();
      g.drawImage(srcImg, 0, 0, width, height, null);
      g.dispose();
      
      FileOutputStream outImgStream = new FileOutputStream(new File(path, getTargetFilePath(name)));
      ImageIO.write(bufImg, getSuffix(name), outImgStream);
      outImgStream.flush();
      outImgStream.close();
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
  
  private static String getSuffix(String path) {
    int idx = path.lastIndexOf(".");
    return path.substring(idx + 1);
  }
  
  private static String getTargetFilePath(String srcFilePath) {
    int idx = srcFilePath.lastIndexOf(".");
    String target = srcFilePath.substring(0, idx) + SHRINK_SUFFIX + srcFilePath.substring(idx);
    return target;
  }
}
