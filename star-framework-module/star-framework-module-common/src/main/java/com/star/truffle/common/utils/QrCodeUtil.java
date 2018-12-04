package com.star.truffle.common.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtil {

//  public static void main(String[] args) throws WriterException, FileNotFoundException {
//    String logoPath = "E:\\uploadimage\\20170622\\icon64.png";
//    File QrCodeFile = new File("E:\\uploadimage\\20170622\\ab.png");
//    String url = "二维码内容";
//    create(logoPath, url, new FileOutputStream(QrCodeFile));
//    
//    String c = read(new FileInputStream(QrCodeFile));
//    System.out.println(c);
//  }
  
  private static final int QRCOLOR = 0xFF000000; // 默认是黑色
  private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色

  private static final int WIDTH = 480; // 二维码宽
  private static final int HEIGHT = 480; // 二维码高

  private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
    private static final long serialVersionUID = 1L;
    {
      put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
      put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
      put(EncodeHintType.MARGIN, 0);
    }
  };

  public static void create(String logoPath, String content, OutputStream out) {
    try {
      MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
      // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
      BitMatrix bm = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
      BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
      // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
      for (int x = 0; x < WIDTH; x++) {
        for (int y = 0; y < HEIGHT; y++) {
          image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
        }
      }

      if (StringUtils.isNotBlank(logoPath)) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        // 读取Logo图片
        File logoFile = new File(logoPath);
        BufferedImage logo = null;
        if (! logoFile.exists()) {
          URL url = new URL(logoPath);
          InputStream in = url.openStream();
          logo = ImageIO.read(in);
        }else {
          logo = ImageIO.read(logoFile);
        }
        if (Objects.nonNull(logo)) {
          // 构建绘图对象
          Graphics2D g = image.createGraphics();
          // 开始绘制logo图片
          g.drawImage(logo, width * 3 / 10, height * 3 / 10, width * 4 / 10, height * 4 / 10, null);
          g.dispose();
          logo.flush();
        }
      }
      image.flush();
      ImageIO.write(image, "jpeg", out);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String read(InputStream inputStream) {
    try {
      BufferedImage image = ImageIO.read(inputStream);
      LuminanceSource source = new BufferedImageLuminanceSource(image);
      BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
      QRCodeReader reader = new QRCodeReader();
      Result result = reader.decode(bitmap);
      return result.getText();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}