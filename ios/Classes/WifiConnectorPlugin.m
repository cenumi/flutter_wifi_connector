#import "WifiConnectorPlugin.h"
#if __has_include(<wifi_connector/wifi_connector-Swift.h>)
#import <wifi_connector/wifi_connector-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "wifi_connector-Swift.h"
#endif

@implementation WifiConnectorPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWifiConnectorPlugin registerWithRegistrar:registrar];
}
@end
