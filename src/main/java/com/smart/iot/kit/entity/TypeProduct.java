package com.smart.iot.kit.entity;

public enum TypeProduct {
  LIQUID("liquid"),
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
