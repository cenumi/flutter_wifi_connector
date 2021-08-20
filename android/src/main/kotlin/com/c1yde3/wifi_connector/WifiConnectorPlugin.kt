package com.c1yde3.wifi_connector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PatternMatcher
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.c1yde3.wifi_connector.Pigeon.*

import io.flutter.embedding.engine.plugins.FlutterPlugin


/** WifiConnectorPlugin */
class WifiConnectorPlugin : FlutterPlugin, NativeApi {


    private lateinit var applicationContext: Context

    private val connectivityManager: ConnectivityManager by lazy(LazyThreadSafetyMode.NONE) {
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private var networkCallback: ConnectivityManager.NetworkCallback? = null


    private val wifiManager: WifiManager by lazy(LazyThreadSafetyMode.NONE) {
        applicationContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    var networkId: Int? = null


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        NativeApi.setup(flutterPluginBinding.binaryMessenger, this)
        applicationContext = flutterPluginBinding.applicationContext
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        NativeApi.setup(binding.binaryMessenger, null)

        if (networkCallback != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectivityManager.unregisterNetworkCallback(networkCallback!!)
            networkCallback = null
        }
    }

    override fun connect(arg: String, result: Result<Void>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectAboveQ(arg, null, result)
        } else {
            connectUnderQ(arg, null, result)
        }

    }

    override fun secureConnect(arg: WifiConfig, result: Result<Void>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectAboveQ(arg.ssid, arg.password, result)
        } else {
            connectUnderQ(arg.ssid, arg.password, result)
        }
    }

    override fun connectByPrefix(arg: String, result: Result<Void>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            connectByPrefixAboveQ(arg, result)
        } else {
            connectByPrefixUnderQ(arg, result)
        }
    }

    override fun disconnect(result: Result<Void>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            disconnectAboveQ(result)
        } else {
            disconnectUnderQ(result)
        }
    }

    override fun getSSID(): String = wifiManager.connectionInfo.ssid

    override fun getGatewayIP(): String {
        val gateway = wifiManager.dhcpInfo.gateway
        return String.format(
            "%d.%d.%d.%d", gateway and 0xFF,
            gateway shr 8 and 0xFF,
            gateway shr 16 and 0xFF,
            gateway shr 24 and 0xFF
        )
    }

    @Suppress("DEPRECATION")
    private fun createWifiConfig() = WifiConfiguration().apply {
        allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)

        allowedProtocols.set(WifiConfiguration.Protocol.RSN)
        allowedProtocols.set(WifiConfiguration.Protocol.WPA)

        allowedAuthAlgorithms.clear()

        allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
        allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)

        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
        allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
    }

    @Suppress("DEPRECATION")
    private fun connectUnderQ(ssid: String, password: String?, result: Result<Void>) {
        val config = createWifiConfig().apply {
            SSID = "\"$ssid\""
            if (password != null) {
                preSharedKey = "\"$password\""
                status = WifiConfiguration.Status.ENABLED

                allowedKeyManagement.clear()
                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            }
        }
        connectUnderQ(config, result)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectAboveQ(ssid: String, password: String?, result: Result<Void>) {
        val specifier = WifiNetworkSpecifier.Builder().apply {
            setSsid(ssid)
            if (password != null) {
                setWpa3Passphrase(password)
            }
        }.build()
        connectAboveQ(specifier, result)
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectAboveQ(specifier: WifiNetworkSpecifier, result: Result<Void>) {
        if (networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback!!)
        }

        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .setNetworkSpecifier(specifier)
            .build()


        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                connectivityManager.bindProcessToNetwork(network)
                result.success(null)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                result.error("404", "target wifi not found", "NetworkCallback.onUnavailable()")
            }
        }
        connectivityManager.requestNetwork(
            request,
            networkCallback!!,
            Handler(Looper.getMainLooper())
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun connectByPrefixAboveQ(arg: String, result: Result<Void>) {
        val specifier = WifiNetworkSpecifier.Builder()
            .setSsidPattern(PatternMatcher(arg, PatternMatcher.PATTERN_PREFIX))
            .build()
        connectAboveQ(specifier, result)
    }

    @Suppress("DEPRECATION")
    private fun connectByPrefixUnderQ(arg: String, result: Result<Void>) {
        val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val ssid =
                    wifiManager.scanResults.filter { scanResult -> scanResult.SSID.startsWith(arg) }
                        .maxByOrNull { scanResult -> scanResult.level }?.SSID

                if (ssid != null) {
                    connectUnderQ(createWifiConfig().apply { SSID = "\"$ssid\"" }, result)
                } else {
                    result.error("404", "target wifi not found", "nothing satisfied in scanResults")
                }
                context?.unregisterReceiver(this)
            }
        }

        applicationContext.registerReceiver(
            wifiScanReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )

        if (!wifiManager.startScan()) {
            wifiScanReceiver.onReceive(null, null)
        }
    }

    @Suppress("DEPRECATION")
    private fun connectUnderQ(conf: WifiConfiguration, result: Result<Void>) {

        val network = wifiManager.addNetwork(conf)
        if (network == -1) {
            result.error("404", "target wifi not found", "wifiManager.addNetwork(conf) returned -1")
            return
        }

        wifiManager.saveConfiguration()

        val wifiChangeReceiver = object : BroadcastReceiver() {
            var count = 0
            override fun onReceive(context: Context?, intent: Intent?) {
                val info = intent?.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                if (info == null || !info.isConnected) {
                    return
                }
                count++
                if (info.extraInfo == conf.SSID || ssid == conf.SSID) {
                    result.success(null)
                    context?.unregisterReceiver(this)
                } else if (count > 2) {
                    result.error(
                        "409",
                        "already connected",
                        "multiple callback in NETWORK_STATE_CHANGED_ACTION broadcast receiver"
                    )
                    context?.unregisterReceiver(this)
                }
            }
        }
        applicationContext.registerReceiver(
            wifiChangeReceiver,
            IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        )
        wifiManager.enableNetwork(network, true)
        networkId = network
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun disconnectAboveQ(result: Result<Void>) {
        if (networkCallback == null) {
            result.error("405", "not connected", "connect one first")
            return
        } else {
            connectivityManager.unregisterNetworkCallback(networkCallback!!)
            connectivityManager.bindProcessToNetwork(null)
            networkCallback = null
            result.success(null)
        }
    }

    @Suppress("DEPRECATION")
    private fun disconnectUnderQ(result: Result<Void>) {
        if (networkId == null) {
            result.error("405", "not connected", "networkId == null")
            return
        }

        val wifiChangeBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val info = intent?.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                if (info != null && !info.isConnected) {
                    result.success(null)
                    context?.unregisterReceiver(this)
                }
            }
        }

        applicationContext.registerReceiver(
            wifiChangeBroadcastReceiver,
            IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        )

        wifiManager.removeNetwork(networkId!!)
        wifiManager.reconnect()
        networkId = null
    }

}

