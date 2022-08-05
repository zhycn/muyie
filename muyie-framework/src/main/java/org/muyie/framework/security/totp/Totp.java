package org.muyie.framework.security.totp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.jboss.aerogear.security.otp.api.Base32;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Totp implements Serializable {

  private final static long serialVersionUID = 1L;
  private final static int BLACK = 0xFF000000;
  private final static int WHITE = 0xFFFFFFFF;

  private final String user;

  private final String uri;

  private final String secret;

  private final org.jboss.aerogear.security.otp.Totp delegate;

  public Totp(String user) {
    this.user = user;
    this.secret = Base32.random();
    this.delegate = new org.jboss.aerogear.security.otp.Totp(this.secret);
    this.uri = this.delegate.uri(user);
  }

  private BufferedImage getImage(BitMatrix matrix) {
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
      }
    }
    return image;
  }

  public byte[] getImageBytes(int width, int height) throws IOException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      output(width, height, out);
      return out.toByteArray();
    }
  }

  public String getSecret() {
    return secret;
  }

  public String getUri() {
    return uri;
  }

  public String getUser() {
    return user;
  }

  public void output(int width, int height, File file) throws IOException {
    try (OutputStream out = new FileOutputStream(file)) {
      output(width, height, out);
    }
  }

  public void output(int width, int height, OutputStream out) throws IOException {
    MultiFormatWriter writer = new MultiFormatWriter();
    Map<EncodeHintType, String> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    try {
      BitMatrix matrix = writer.encode(uri, BarcodeFormat.QR_CODE, width, height, hints);
      BufferedImage image = getImage(matrix);
      ImageIO.write(image, "png", out);
    } catch (WriterException ex) {
      throw new IOException(ex);
    }
  }

}
