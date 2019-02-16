package com.fakefantasy.framework.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    //load Property File
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); // 查找当前运行类目录下的资源文件
            if (is == null) {
                throw new FileNotFoundException(fileName + "file is not found");
            }
            props = new Properties();
            props.load(is);// 从输入流中读取属性列表
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    // String Property(default is "")
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }
    // String Property
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    // Int Property(default is 0)
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }
    // Int Property
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = (Integer) ConvertUtils.convert(props.getProperty(key), Integer.class);
        }
        return value;
    }

    //  Bool Property(default is false)
    public static boolean getBool(Properties props, String key) {
        return getBool(props, key, false);
    }
    // Bool Property
    public static boolean getBool(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = (Boolean) ConvertUtils.convert(props.getProperty(key), Boolean.class);
        }
        return value;
    }
}
