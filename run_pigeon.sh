#!/bin/zsh

flutter pub run pigeon \
  --input pigeons/bridge.dart \
  --dart_out lib/pigeon.dart \
  --objc_header_out ios/Classes/pigeon.h \
  --objc_source_out ios/Classes/pigeon.m \
  --java_out ./android/src/main/kotlin/com/c1yde3/wifi_connector/Pigeon.java \
  --java_package "com.c1yde3.wifi_connector"
