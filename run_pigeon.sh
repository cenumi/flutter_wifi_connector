flutter pub run pigeon \
  --input pigeon/bridge.dart \
  --dart_out lib/src/bridge.dart \
  --objc_header_out ios/Classes/bridge.h \
  --objc_source_out ios/Classes/bridge.m \
  --java_out ./android/src/main/kotlin/com/c1yde3/wifi_connector/Bridge.java \
  --java_package "com.c1yde3.wifi_connector"
