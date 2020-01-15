//package com.smart.iot.supply;
//
//import com.smart.iot.SmartIotException;
//import java.util.Objects;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//
//public class SneakyTrow {
//
//  public static <T> Supplier<T> sneaky(Consumer<T> consumer) {
//    Objects.requireNonNull(consumer);
//    return x -> {
//      try {
//        consumer.accept(x);
//      } catch (Exception e) {
//        throw new SmartIotException(e.getMessage());
//      }
//    };
//    return null;
//  }
//}
