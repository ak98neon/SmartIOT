package com.smart.iot.home.entity;

public enum TypeProduct {
  LIQUID("luquid"),
  SOLID("solid");

  private String name;

  TypeProduct(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
