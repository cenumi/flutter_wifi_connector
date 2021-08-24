import 'package:flutter/material.dart';

import 'package:wifi_connector/wifi_connector.dart';

import 'package:permission_handler/permission_handler.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? _prefix;

  String? _ssidPrefix;

  String? _ssid;


  @override
  void initState() {
    super.initState();

    () async {
      final state = await Permission.locationWhenInUse.request();
      print(state);
    }();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Text('SSID Prefix: $_prefix'),
            Text('Current SSID: $_ssid'),
            TextField(
              decoration: InputDecoration(hintText: 'please input the ssid prefix'),
              onChanged: (s) => _ssidPrefix = s,
            ),
            ElevatedButton(
              onPressed: () async {
                if (_ssidPrefix != null) {
                  await WifiConnector().connectByPrefix(_ssidPrefix!);
                  setState(() {
                    _prefix = _ssidPrefix;
                  });
                }
              },
              child: Text('Connect'),
            ),
            ElevatedButton(
              onPressed: () async {
                final ip = await WifiConnector().getSSID();
                if (mounted) {
                  setState(() {
                    _ssid = ip;
                  });
                }
              },
              child: Text('Fetch SSID'),
            ),
            ElevatedButton(
              onPressed: () => WifiConnector().disconnect(),
              child: Text('Disconnect'),
            ),
          ],
        ),
      ),
    );
  }
}
