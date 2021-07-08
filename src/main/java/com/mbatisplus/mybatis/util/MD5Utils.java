package com.mbatisplus.mybatis.util;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public MD5Utils() {
    }

    public static String getMD5(String src) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5bytes = messageDigest.digest(src.getBytes("UTF-8"));
            //String encrypt = Hex.toHexString(md5bytes);
            return "";
        } catch (Exception var4) {
            logger.info("MD5Utils.getMD5", var4);
            return null;
        }
    }
}
