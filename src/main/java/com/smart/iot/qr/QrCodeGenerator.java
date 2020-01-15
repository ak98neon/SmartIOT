package com.smart.iot.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.smart.iot.config.QrCodeConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class QrCodeGenerator {

  private QrCodeConfiguration qrCodeConfiguration;

  public QrCodeGenerator(QrCodeConfiguration qrCodeConfiguration) {
    this.qrCodeConfiguration = qrCodeConfiguration;
  }

  public byte[] generateQRCodeImage(String text)
      throws WriterException, IOException {
    return generateQRCodeImage(
        text, qrCodeConfiguration.getWidth(), qrCodeConfiguration.getHeight()
    );
  }

  public byte[] generateQRCodeImage(String text, int width, int height)
      throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
    return pngOutputStream.toByteArray();
  }
}
