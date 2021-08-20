import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wifi_connector/pigeon.dart';

class WifiConnector extends NativeApi {
  factory WifiConnector() => _instance;

  WifiConnector._();

  static late final WifiConnector _instance = WifiConnector._();
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
