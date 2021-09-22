library wifi_connector;

import 'package:wifi_connector/src/bridge.dart';

class WifiConnector {
  factory WifiConnector() => _instance;

  WifiConnector._();

  static late final WifiConnector _instance = WifiConnector._();

  final _bridge = WifiConnectorHostApiBridge();

  Future<void> connect(String ssid, {String? password}){
    if (password != null) {
      return _bridge.secureConnect(ssid, password);
    }
    return _bridge.connect(ssid);
  }

  Future<void> connectByPrefix(String ssidPrefix) => _bridge.connectByPrefix(ssidPrefix);

  Future<String> get ssid => _bridge.getSSID();

  Future<void> disconnect() => _bridge.disconnect();

  Future<bool> get isWifiEnabled => _bridge.isEnabled();
}

class WifiConnectorErrorCode{
  WifiConnectorErrorCode._();

  static const alreadyConnected = 'AlreadyConnectedException';

  static const notFound = 'NotFoundException';

  static const notConnected = 'NotConnectedException';

  static const userDenied = 'PermissionDeniedException';
}