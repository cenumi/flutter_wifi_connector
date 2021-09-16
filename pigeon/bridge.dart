import 'package:pigeon/pigeon.dart';

@HostApi()
abstract class WifiConnectorHostApiBridge {
  @async
  void connect(String ssid);

  @async
  void secureConnect(String ssid, String password);

  @async
  void connectByPrefix(String ssidPrefix);

  @async
  void disconnect();

  String getSSID();
}
