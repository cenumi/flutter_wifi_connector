import 'package:wifi_connector/bridge.dart';

class WifiConnector extends WifiConnectorHostApiBridge {
  factory WifiConnector() => _instance;

  WifiConnector._();

  static late final WifiConnector _instance = WifiConnector._();
}
