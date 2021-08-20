import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wifi_connector/pigeon.dart';

class WifiConnector extends NativeApi {
  factory WifiConnector() => _instance;

  WifiConnector._();

  static late final WifiConnector _instance = WifiConnector._();

  @deprecated
  @override
  Future<void> secureConnect(_) async {}

  @override
  Future<void> connect(String ssid, [String? password]) async {
    if (password == null) {
      return super.connect(ssid);
    } else {
      return super.secureConnect(
        WifiConfig()
          ..ssid = ssid
          ..password = password,
      );
    }
  }
}

class WifiConnectorExceptionCodes {
  WifiConnectorExceptionCodes._();

  static const wifiNotFound = '404';
  static const alreadyConnected = '409';
  static const notConnected = '405';
  static const permissionDenied = '403';
  static const systemUpgradeRequired = '505';
  static const systemError = '500';
}
