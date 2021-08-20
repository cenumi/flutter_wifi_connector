# wifi_connector

This plugin allows Flutter apps to use wifi functionality on Android / iOS, and fetch infomation related.



## Attention

`connect(String ssid)` is not implemented yet  
use `connectByPrefix(String ssidPrefix)` instead

## Usage

**Runtime location premission Required**

```dart
import 'package:wifi_connector/wifi_connector.dart';

Future<void> func() async {
  final wifi = WifiConnector();

  await Permission.locaion.request();
  
  await wifi.connectByPrefix('prefix');

  final ssid = await wifi.getSSID();
  final gateway = await wifi.getGatewayIP();

  await wifi.disconnect();
}

// Exceptions
class WifiConnectorExceptionCodes {
  WifiConnectorExceptionCodes._();

  static const wifiNotFound = '404';
  static const alreadyConnected = '409';
  static const notConnected = '405';
  static const permissionDenied = '403';
  static const systemUpgradeRequired = '505';
  static const systemError = '500';
}

```

## Requirements

### iOS

iOS 13 above

Capabilities needed

- Assess WiFi information
- Hotspot Configuration







