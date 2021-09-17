// Autogenerated from Pigeon (v1.0.4), do not edit directly.
// See also: https://pub.dev/packages/pigeon
#import <Foundation/Foundation.h>
@protocol FlutterBinaryMessenger;
@protocol FlutterMessageCodec;
@class FlutterError;
@class FlutterStandardTypedData;

NS_ASSUME_NONNULL_BEGIN


/// The codec used by WifiConnectorHostApiBridge.
NSObject<FlutterMessageCodec> *WifiConnectorHostApiBridgeGetCodec(void);

@protocol WifiConnectorHostApiBridge
- (void)connectSsid:(nullable NSString *)ssid completion:(void(^)(FlutterError *_Nullable))completion;
- (void)secureConnectSsid:(nullable NSString *)ssid password:(nullable NSString *)password completion:(void(^)(FlutterError *_Nullable))completion;
- (void)connectByPrefixSsidPrefix:(nullable NSString *)ssidPrefix completion:(void(^)(FlutterError *_Nullable))completion;
- (void)disconnectWithCompletion:(void(^)(FlutterError *_Nullable))completion;
- (void)isEnabledWithCompletion:(void(^)(NSNumber *_Nullable, FlutterError *_Nullable))completion;
- (nullable NSString *)getSSIDWithError:(FlutterError *_Nullable *_Nonnull)error;
@end

extern void WifiConnectorHostApiBridgeSetup(id<FlutterBinaryMessenger> binaryMessenger, NSObject<WifiConnectorHostApiBridge> *_Nullable api);

NS_ASSUME_NONNULL_END