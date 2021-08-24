// Autogenerated from Pigeon (v0.3.0), do not edit directly.
// See also: https://pub.dev/packages/pigeon

package com.c1yde3.wifi_connector;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/** Generated class from Pigeon. */
@SuppressWarnings({"unused", "unchecked", "CodeBlock2Expr", "RedundantSuppression"})
public class Pigeon {

  /** Generated class from Pigeon that represents data sent in messages. */
  public static class WifiConfig {
    private String ssid;
    public String getSsid() { return ssid; }
    public void setSsid(String setterArg) { this.ssid = setterArg; }

    private String password;
    public String getPassword() { return password; }
    public void setPassword(String setterArg) { this.password = setterArg; }

    Map<String, Object> toMap() {
      Map<String, Object> toMapResult = new HashMap<>();
      toMapResult.put("ssid", ssid);
      toMapResult.put("password", password);
      return toMapResult;
    }
    static WifiConfig fromMap(Map<String, Object> map) {
      WifiConfig fromMapResult = new WifiConfig();
      Object ssid = map.get("ssid");
      fromMapResult.ssid = (String)ssid;
      Object password = map.get("password");
      fromMapResult.password = (String)password;
      return fromMapResult;
    }
  }

  public interface Result<T> {
    void success(T result);
    void error(String code, String message, Object details);
  }
  private static class NativeApiCodec extends StandardMessageCodec {
    public static final NativeApiCodec INSTANCE = new NativeApiCodec();
    private NativeApiCodec() {}
    @Override
    protected Object readValueOfType(byte type, ByteBuffer buffer) {
      switch (type) {
        case (byte)128:         
          return WifiConfig.fromMap((Map<String, Object>) readValue(buffer));
        
        default:        
          return super.readValueOfType(type, buffer);
        
      }
    }
    @Override
    protected void writeValue(ByteArrayOutputStream stream, Object value)     {
      if (value instanceof WifiConfig) {
        stream.write(128);
        writeValue(stream, ((WifiConfig) value).toMap());
      } else 
{
        super.writeValue(stream, value);
      }
    }
  }

  /** Generated interface from Pigeon that represents a handler of messages from Flutter.*/
  public interface NativeApi {
    void connect(String arg, Result<Void> result);
    void secureConnect(WifiConfig arg, Result<Void> result);
    void connectByPrefix(String arg, Result<Void> result);
    void disconnect(Result<Void> result);
    String getSSID();

    /** The codec used by NativeApi. */
    static MessageCodec<Object> getCodec() {
      return NativeApiCodec.INSTANCE;
    }

    /** Sets up an instance of `NativeApi` to handle messages through the `binaryMessenger`. */
    static void setup(BinaryMessenger binaryMessenger, NativeApi api) {
      {
        BasicMessageChannel<Object> channel =
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.NativeApi.connect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              @SuppressWarnings("ConstantConditions")
              String input = (String)message;
              if (input == null) {
                throw new NullPointerException("Message unexpectedly null.");
              }
              api.connect(input, new Result<Void>() {
                @Override
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }

                @Override
                public void error(String code, String message, Object details) {
                  wrapped.put("error", wrapUserError(code, message, details));
                  reply.reply(wrapped);
                }
              });
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
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.NativeApi.secureConnect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              @SuppressWarnings("ConstantConditions")
              WifiConfig input = (WifiConfig)message;
              if (input == null) {
                throw new NullPointerException("Message unexpectedly null.");
              }
              api.secureConnect(input, new Result<Void>() {
                @Override
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }

                @Override
                public void error(String code, String message, Object details) {
                  wrapped.put("error", wrapUserError(code, message, details));
                  reply.reply(wrapped);
                }
              });
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
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.NativeApi.connectByPrefix", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              @SuppressWarnings("ConstantConditions")
              String input = (String)message;
              if (input == null) {
                throw new NullPointerException("Message unexpectedly null.");
              }
              api.connectByPrefix(input, new Result<Void>() {
                @Override
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }

                @Override
                public void error(String code, String message, Object details) {
                  wrapped.put("error", wrapUserError(code, message, details));
                  reply.reply(wrapped);
                }
              });
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
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.NativeApi.disconnect", getCodec());
        if (api != null) {
          channel.setMessageHandler((message, reply) -> {
            Map<String, Object> wrapped = new HashMap<>();
            try {
              api.disconnect( new Result<Void>() {
                @Override
                public void success(Void result) {
                  wrapped.put("result", null);
                  reply.reply(wrapped);
                }

                @Override
                public void error(String code, String message, Object details) {
                  wrapped.put("error", wrapUserError(code, message, details));
                  reply.reply(wrapped);
                }
              });
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
            new BasicMessageChannel<>(binaryMessenger, "dev.flutter.pigeon.NativeApi.getSSID", getCodec());
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
  private static Map<String, Object> wrapUserError(String code, String message, Object details) {
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("message", message);
    errorMap.put("code", code);
    errorMap.put("details", details.toString());
    return errorMap;
  }
}
