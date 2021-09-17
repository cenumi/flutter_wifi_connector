// Autogenerated from Pigeon (v1.0.4), do not edit directly.
// See also: https://pub.dev/packages/pigeon

package com.c1yde3.wifi_connector;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/** Generated class from Pigeon. */
@SuppressWarnings({"unused", "unchecked", "CodeBlock2Expr", "RedundantSuppression"})
public class Bridge {

  public interface Result<T> {
    void success(T result);
    void error(Throwable error);
  }
  private static class WifiConnectorHostApiBridgeCodec extends StandardMessageCodec {
    public static final WifiConnectorHostApiBridgeCodec INSTANCE = new WifiConnectorHostApiBridgeCodec();
    private WifiConnectorHostApiBridgeCodec() {}
  }

  /** Generated interface from Pigeon that represents a handler of messages from Flutter.*/
  public interface WifiConnectorHostApiBridge {
    void connect(String ssid, Result<Void> result);
    void secureConnect(String ssid, String password, Result<Void> result);
    void connectByPrefix(String ssidPrefix, Result<Void> result);
    void disconnect(Result<Void> result);
    void isEnabled(Result<Boolean> result);
    String getSSID();

    /** The codec used by WifiConnectorHostApiBridge. */
    static MessageCodec<Object> getCodec() {
      return WifiConnectorHostApiBridgeCodec.INSTANCE;
    }

    /** Sets up an instance of `WifiConnectorHostApiBridge` to handle messages through the `binaryMessenger`. */
    static void setup(BinaryMessenger binaryMessenger, WifiConnectorHostApiBridge api) {
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.WifiConnectorHostApiBridge.connect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              String ssidArg = (String)args.get(0);
              if (ssidArg == null) {
                throw new NullPointerException("ssidArg unexpectedly null.");
              }
              Result<Void> resultCallback = new Result<Void>() {
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  wrapped.put("error", wrapError(error));
                  reply.reply(wrapped);
                }
              };

              api.connect(ssidArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              wrapped.put("error", wrapError(exception));
              reply.reply(wrapped);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.WifiConnectorHostApiBridge.secureConnect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              String ssidArg = (String)args.get(0);
              if (ssidArg == null) {
                throw new NullPointerException("ssidArg unexpectedly null.");
              }
              String passwordArg = (String)args.get(1);
              if (passwordArg == null) {
                throw new NullPointerException("passwordArg unexpectedly null.");
              }
              Result<Void> resultCallback = new Result<Void>() {
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  wrapped.put("error", wrapError(error));
                  reply.reply(wrapped);
                }
              };

              api.secureConnect(ssidArg, passwordArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              wrapped.put("error", wrapError(exception));
              reply.reply(wrapped);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.WifiConnectorHostApiBridge.connectByPrefix", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              ArrayList<Object> args = (ArrayList<Object>)message;
              String ssidPrefixArg = (String)args.get(0);
              if (ssidPrefixArg == null) {
                throw new NullPointerException("ssidPrefixArg unexpectedly null.");
              }
              Result<Void> resultCallback = new Result<Void>() {
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  wrapped.put("error", wrapError(error));
                  reply.reply(wrapped);
                }
              };

              api.connectByPrefix(ssidPrefixArg, resultCallback);
            }
            catch (Error | RuntimeException exception) {
              wrapped.put("error", wrapError(exception));
              reply.reply(wrapped);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.WifiConnectorHostApiBridge.disconnect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              Result<Void> resultCallback = new Result<Void>() {
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  wrapped.put("error", wrapError(error));
                  reply.reply(wrapped);
                }
              };

              api.disconnect(resultCallback);
            }
            catch (Error | RuntimeException exception) {
              wrapped.put("error", wrapError(exception));
              reply.reply(wrapped);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.WifiConnectorHostApiBridge.isEnabled", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              Result<Boolean> resultCallback = new Result<Boolean>() {
                public void success(Boolean result) {
                  wrapped.put("result", result);
                  reply.reply(wrapped);
                }
                public void error(Throwable error) {
                  wrapped.put("error", wrapError(error));
                  reply.reply(wrapped);
                }
              };

              api.isEnabled(resultCallback);
            }
            catch (Error | RuntimeException exception) {
              wrapped.put("error", wrapError(exception));
              reply.reply(wrapped);
            }
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.WifiConnectorHostApiBridge.getSSID", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              String output = api.getSSID();
              wrapped.put("result", output);
            }
            catch (Error | RuntimeException exception) {
              wrapped.put("error", wrapError(exception));
            }
            reply.reply(wrapped);
          });
        } else {
          channel.setMessageHandler(null);
        }
      }
    }
  }
  private static Map<String, Object> wrapError(Throwable exception) {
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("message", exception.toString());
    errorMap.put("code", exception.getClass().getSimpleName());
    errorMap.put("details", null);
    return errorMap;
  }
}
