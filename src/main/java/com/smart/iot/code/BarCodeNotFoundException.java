package com.smart.iot.code;

import com.smart.iot.SmartIotException;

public class BarCodeNotFoundException extends SmartIotException {

  public BarCodeNotFoundException() {
  }

  @Override
  public String getMessage() {
    return "Barcode not found";
  }
}
