package com.smart.iot.home.entity;

public enum TypeProduct {
  LIQUID("luquid"),
  SOLID("solid"),
  NONE("none");

  private String name;

  TypeProduct(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
