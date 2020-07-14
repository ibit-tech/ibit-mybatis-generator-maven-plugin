package tech.ibit.mybatis.generator.plugin.utils;

import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * PropertyUtils
 *
 * @author IBIT程序猿
 */
public class PropertyUtils {

    /**
     * 获取字符串
     *
     * @param properties 属性
     * @param name       名称
     * @return 属性值
     */
    public static String getString(Properties properties, String name) {
        String value = properties.getProperty(name, "");
        return value.isEmpty() ? value : convertString(value);
    }

    /**
     * 获取boolen值
     *
     * @param properties 属性
     * @param name       名称
     * @return 属性值
     */
    public static boolean getBoolean(Properties properties, String name) {
        String value = properties.getProperty(name, "");
        return !value.isEmpty() && ("1".equals(value) || "true".equalsIgnoreCase(value));
    }


    /**
     * 字符串转化（"ISO-8859-1" -> "UTF-8")
     *
     * @param value 值
     * @return 转换后的字符串
     */
    private static String convertString(String value) {
        if (null == value) {
            return null;
        }
        return StringUtils.trim(new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
    }
}
