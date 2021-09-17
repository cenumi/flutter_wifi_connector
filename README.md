# wifi_connector

This plugin allows Flutter apps to use wifi functionality on Android / iOS, and fetch infomation related.

## Requirements

### iOS

iOS 13 above

Capabilities needed

- Assess WiFi information
- Hotspot Configuration

## Attention

this package uses custom pigeon code generator to generate platform code.

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


```








