package com.smart.iot.code;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public final class BarcodeReader {

  private BarcodeReader() {
    throw new UnsupportedOperationException();
  }

  public static String readBarcodeFromImage(InputStream inputStream)
      throws NotFoundException, IOException {
    LuminanceSource source = new BufferedImageLuminanceSource(ImageIO.read(inputStream));
    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

    MultiFormatReader multiFormatReader = new MultiFormatReader();
    Map<DecodeHintType, Object> hints = new HashMap();
    hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
    multiFormatReader.setHints(hints);
    Result result = multiFormatReader.decode(bitmap, hints);

    return result.getText();
  }
}
