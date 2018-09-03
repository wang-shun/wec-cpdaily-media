package com.wisedu.wec.media.common.old.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DesUtil {
  // 向量
  private static final byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};

  /**
   * CBC解密
   *
   * @param key 密钥
   * @param data Base64编码的密文
   * @return 明文
   * @throws Exception
   */
  public static byte[] desDecodeCBC(byte[] key, byte[] data) throws Exception {
    Key deskey = null;
    DESKeySpec spec = new DESKeySpec(key);
    SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
    deskey = keyfactory.generateSecret(spec);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    IvParameterSpec ips = new IvParameterSpec(keyiv);
    cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
    byte[] bOut = cipher.doFinal(data);
    return bOut;
  }

  /**
   * CBC解密
   *
   * @param key 密钥
   * @param data Base64加密后的密文
   * @return 明文
   * @throws Exception
   */
  public static String desDecodeCBC(String key, String data) throws Exception {
    byte[] _data = Base64.decodeBase64(data);
    byte[] _key = key.getBytes(StandardCharsets.UTF_8);
    byte[] bOut = desDecodeCBC(_key, _data);

    return new String(bOut, StandardCharsets.UTF_8);
  }

  /**
   * CBC加密
   *
   * @param key 密钥
   * @param data 明文
   * @return 密文
   * @throws Exception
   */
  public static byte[] desEncodeCBC(byte[] key, byte[] data) throws Exception {
    Key deskey = null;
    DESKeySpec spec = new DESKeySpec(key);
    SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
    deskey = keyfactory.generateSecret(spec);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); // 加密方法／运算模式／填充模式
    IvParameterSpec ips = new IvParameterSpec(keyiv);
    cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
    byte[] bOut = cipher.doFinal(data);
    return bOut;
  }

  /**
   * CBC加密
   *
   * @param key 密钥
   * @param data 明文
   * @return Base64加密后的密文
   * @throws Exception
   */
  public static String desEncodeCBC(String key, String data) throws Exception {
    byte[] _data = data.getBytes(StandardCharsets.UTF_8);
    byte[] _key = key.getBytes(StandardCharsets.UTF_8);
    byte[] bOut = desEncodeCBC(_key, _data);

    return Base64.encodeBase64String(bOut); // Base64加密后的密文
  }

}
