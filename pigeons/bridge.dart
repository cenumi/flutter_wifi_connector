import 'package:pigeon/pigeon.dart';

class WifiConfig{
  String? ssid;
  String? password;
}

@HostApi()
abstract class NativeApi {
  @async
  void connect(String ssid);

  @async
  void secureConnect(WifiConfig config);

  @async
  void connectByPrefix(String ssidPrefix);

  @async
  void disconnect();

  String getSSID();

  String getGatewayIP();
}
