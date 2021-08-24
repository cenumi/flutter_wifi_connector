import Flutter
import UIKit
import NetworkExtension
import SystemConfiguration.CaptiveNetwork

public class SwiftWifiConnectorPlugin: NSObject, FlutterPlugin, NativeApi{
    
    
    
    public static func register(with registrar: FlutterPluginRegistrar) {
        NativeApiSetup(registrar.messenger(), SwiftWifiConnectorPlugin.init())
    }
    
    public func connect(_ input: String?, completion: @escaping (FlutterError?) -> Void) {
        guard let ssid = input else {return}
        let hotspotConfig = NEHotspotConfiguration.init(ssid: ssid)
        connect(hotspotConfig: hotspotConfig, completion: completion)
    }
    
    public func secureConnect(_ input: WifiConfig?, completion: @escaping (FlutterError?) -> Void) {
        guard let ssid  = input?.ssid else { return }
        guard let password = input?.password else { return }
        let hotspotConfig = NEHotspotConfiguration.init(ssid: ssid, passphrase: password, isWEP: false)
        
        connect(hotspotConfig: hotspotConfig, completion: completion)
    }
    
    public func connect(byPrefix input: String?, completion: @escaping (FlutterError?) -> Void) {
        if #available(iOS 13.0, *) {
            guard let prefix = input else { return }
            let hotspotConfig = NEHotspotConfiguration.init(ssidPrefix: prefix)
            connect(hotspotConfig: hotspotConfig, completion: completion)
        }else{
            completion(FlutterError(code: "505", message: "iOS 13 above required", details: nil))
        }
        
    }
    
    private func connect(hotspotConfig:NEHotspotConfiguration,completion: @escaping (FlutterError?) -> Void){

        hotspotConfig.joinOnce = true

        NEHotspotConfigurationManager.shared.apply(hotspotConfig, completionHandler: { [weak self] (error) in
            
            guard let err = error as NSError? else{
                guard let this = self else{
                    completion(FlutterError(code: "500", message: "parse failed", details: error))
                    return
                }
                
                guard let ssid = this.getSSID() else{
                    completion(FlutterError(code: "404", message: "target wifi not found", details: nil))
                    return
                }
                
                if ssid.hasPrefix(hotspotConfig.ssid) {
                    completion(nil)
                }else{
                    completion(FlutterError(code: "405", message: "already connected", details: "ssidPrefix != currendSsidPrefix"))
                }
                return
            }
            
            switch err.code {
            case NEHotspotConfigurationError.alreadyAssociated.rawValue:
                completion(nil)
                break
            case NEHotspotConfigurationError.userDenied.rawValue:
                completion(FlutterError(code: "403", message: "permission denied", details: err))
                break
            default:
                completion(FlutterError(code: "500", message: "system error" , details: err))
                break
            }
            
        })
        
        
    }
    
    
    public func disconnect(_ completion: @escaping (FlutterError?) -> Void) {
        
        if #available(iOS 13.0, *) {
            let ssid = getSSID()
            if ssid == nil{
                completion(FlutterError(code: "not connected", message: nil, details: nil))
                return
            }
            NEHotspotConfigurationManager.shared.removeConfiguration(forSSID: ssid ?? "")
            completion(nil)
        } else {
            completion(FlutterError(code: "505", message: "iOS 13 above required", details: nil))
        }
    }
    
    public func getSSID(_ error: AutoreleasingUnsafeMutablePointer<FlutterError?>) -> String? {
        
        guard let ssid = getSSID() else {
            error.pointee = FlutterError(code: "404", message: "target wifi not found", details: nil)
            return nil
        }
        
        return ssid
    }
    
    
    private func getSSID() -> String?{
        var ssid: String?
        if let interfaces = CNCopySupportedInterfaces() as NSArray? {
            for interface in interfaces {
                if let interfaceInfo = CNCopyCurrentNetworkInfo(interface as! CFString) as NSDictionary? {
                    ssid = interfaceInfo[kCNNetworkInfoKeySSID as String] as? String
                    break
                }
            }
        }
        return ssid
    }
    
}
