import 'package:pigeon/pigeon.dart';

@HostApi()
abstract class NativeApi {
  @async
  void connect(String ssid);

  @async
  void connectByPrefix(String ssidPrefix);

  @async
  void disconnect();

  String getSSID();

  String getGatewayIP();
}
