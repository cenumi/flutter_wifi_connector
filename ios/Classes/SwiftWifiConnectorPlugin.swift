import Flutter
import UIKit
import NetworkExtension
import SystemConfiguration.CaptiveNetwork

public class SwiftWifiConnectorPlugin: NSObject, FlutterPlugin, WifiConnectorHostApiBridge {


    public static func register(with registrar: FlutterPluginRegistrar) {
        WifiConnectorHostApiBridgeSetup(registrar.messenger(), SwiftWifiConnectorPlugin())
    }


    public func connect(byPrefixSsidPrefix ssidPrefix: String?, completion: @escaping (FlutterError?) -> Void) {
        guard let ssidPrefix = ssidPrefix else {
            return
        }
        let hotspotConfig = NEHotspotConfiguration(ssidPrefix: ssidPrefix)
        connect(hotspotConfig: hotspotConfig, completion: completion)
    }


    public func connectSsid(_ ssid: String?, completion: @escaping (FlutterError?) -> Void) {
        guard let ssid = ssid else {
            return
        }
        let hotspotConfig = NEHotspotConfiguration(ssid: ssid)
        connect(hotspotConfig: hotspotConfig, completion: completion)
    }

    public func secureConnectSsid(_ ssid: String?, password: String?, completion: @escaping (FlutterError?) -> Void) {
        guard let ssid = ssid, let password = password else {
            return
        }

        let hotspotConfig = NEHotspotConfiguration(ssid: ssid, passphrase: password, isWEP: false)
        connect(hotspotConfig: hotspotConfig, completion: completion)
    }

    public func disconnect(completion: @escaping (FlutterError?) -> Void) {
        var error: FlutterError?

        guard let ssid = getSSIDWithError(&error) else {
            completion(error)
            return
        }

        NEHotspotConfigurationManager.shared.removeConfiguration(forSSID: ssid)

    }


    public func getSSIDWithError(_ error: AutoreleasingUnsafeMutablePointer<FlutterError?>) -> String? {
        var ssid: String?
        if let interfaces = CNCopySupportedInterfaces() as NSArray? {
            for interface in interfaces {
                if let interfaceInfo = CNCopyCurrentNetworkInfo(interface as! CFString) as NSDictionary? {
                    ssid = interfaceInfo[kCNNetworkInfoKeySSID as String] as? String
                    break
                }
            }
        }
        if ssid == nil {
            error.pointee = NotFoundException(message: "no such ssid in interfaces")
        }
        return ssid
    }


    private func connect(hotspotConfig: NEHotspotConfiguration, completion: @escaping (FlutterError?) -> Void) {

        hotspotConfig.joinOnce = true


        NEHotspotConfigurationManager.shared.apply(hotspotConfig, completionHandler: { [weak self] (error) in
            var flutterError: FlutterError?

            guard let err = error as NSError? else {
                guard let this = self else {
                    return
                }

                guard let ssid = this.getSSIDWithError(&flutterError) else {
                    completion(flutterError)
                    return
                }

                if ssid.hasPrefix(hotspotConfig.ssid) {
                    completion(nil)
                } else {
                    completion(AlreadyConnectedException(message: "ssid prefix != currentSsidPrefix"))
                }
                return
            }

            switch err.code {
            case NEHotspotConfigurationError.alreadyAssociated.rawValue:
                completion(nil)
                break
            case NEHotspotConfigurationError.userDenied.rawValue:
                completion(PermissionDeniedException(message: "user denied"))
                break
            default:
                completion(FlutterError(code: "unknown", message: "unknown err.code", details: err.description))
                break
            }
        })
    }


    class AlreadyConnectedException: FlutterError {
        convenience init(message: String) {
            self.init(code: type(of: self).description(), message: message, details: nil)
        }
    }

    class NotFoundException: FlutterError {
        convenience init(message: String) {
            self.init(code: type(of: self).description(), message: message, details: nil)
        }
    }

    class NotConnectedException: FlutterError {
        convenience init(message: String) {
            self.init(code: type(of: self).description(), message: message, details: nil)
        }
    }

    class PermissionDeniedException: FlutterError {
        convenience init(message: String) {
            self.init(code: type(of: self).description(), message: message, details: nil)
        }
    }
}
