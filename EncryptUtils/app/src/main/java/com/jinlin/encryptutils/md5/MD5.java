package com.jinlin.encryptutils.md5;

import java.security.MessageDigest;

public class MD5 {
	static {
		System.loadLibrary("encrypt");
	}

	public static native String encryptByMD5(String str);

	public static String getMD5(String info) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(info.getBytes("UTF-8"));
			byte[] encryption = md5.digest();

			StringBuilder strBuf = new StringBuilder();
			for (byte anEncryption : encryption) {
				if (Integer.toHexString(0xff & anEncryption).length() == 1) {
					strBuf.append("0").append(Integer.toHexString(0xff & anEncryption));
				} else {
					strBuf.append(Integer.toHexString(0xff & anEncryption));
				}
			}
			return strBuf.toString();
		} catch (Exception e) {
			return "";
		}
	}

}
