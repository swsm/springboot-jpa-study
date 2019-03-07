package com.swsm.platform.util.converter.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 提供针对特殊字符串的处理功能
 *
 * @author Thomas Wu
 * @since 3.0 2008-4-22
 */
public class StringHelper {

    /**
     * 日志信息
     */
    private static Logger logger = LoggerFactory.getLogger(StringHelper.class);

    /**
     * 根据指定的分隔符 sepetator 分隔字符串
     */
    public static String[] splitStrArray(String str, String seperator) {
        if (str != null && !str.trim().equals("") && seperator != null) {
            StringTokenizer st = new StringTokenizer(str, seperator);
            List strList = new ArrayList();
            int index = str.indexOf(seperator);
            String temp = null;
            while (index > -1) {
                temp = str.substring(0, index);
                strList.add(temp);
                str = str.substring(index + seperator.length());
                index = str.indexOf(seperator);
            }
            strList.add(str);
            String[] s = new String[strList.size()];
            Iterator it = strList.iterator();
            int i = 0;
            while (it.hasNext()) {
                s[i++] = (String) it.next();
            }
            return s;
        } else {
            return null;
        }
    }

    /**
     * 根据指定的分隔符 sepetator 分隔字符串
     */
    public static List<String> splitStrList(String str, String seperator) {
        String[] arr = splitStrArray(str,seperator);
        return Arrays.asList(arr);
    }

    /**
     * 截取指定长度的字符串，中文算2个长度，若过长，返回xxx...，否则返回原串
     *
     * @param s   Original String.
     * @param max Max length.
     * @return getSubString part of string.
     */
    public static String getSubString(String s, int max) {
        char[] cs = s.toCharArray();
        int count = 0;
        int last = cs.length;
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] > 255)
                count += 2;
            else
                count++;
            if (count > max) {
                last = i + 1;
                break;
            }
        }
        if (count <= max) // string is short or just the size!
            return s;
        // string is too long:
        max -= 3;
        for (int i = last - 1; i >= 0; i--) {
            if (cs[i] > 255)
                count -= 2;
            else
                count--;
            if (count <= max) {
                return s.substring(0, i) + "...";
            }
        }
        return "...";
    }

    /**
     * <p>Description: 转换成utf8</p>
     *
     * @param desStr 需要转换的字符
     * @return 返回转换后的字符串
     */
    public static String parsorUtf8Str(String desStr) {
        String parsored = null;
        if (desStr != null) {
            String currentEncode = System.getProperty("file.encoding");
            try {
                parsored = new String(desStr.getBytes("utf-8"), currentEncode);
            } catch (UnsupportedEncodingException e) {
                logger.error("encode:{}" + currentEncode);
                logger.error(e.getMessage(), e);
            }
        }
        return parsored;
    }


    /**
     * 把数组转化成特定分割符的字符串
     * @param arrays
     * @param seperator
     * @return
     */
    public static String arraySplitString(String[] arrays,String seperator){
        if(arrays==null || arrays.length==0){
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for(String e : arrays){
            builder.append(e).append(seperator);
        }
        if(builder.length()>0){
           int index = builder.lastIndexOf(seperator);
           return builder.substring(index);
        }
        return builder.toString();
    }
    /**
     *
     * <p>
     * Description: 根据输入的sql列以及该列值的集合,whrValues中不允许有特殊字符，而且不能超过1000个值
     * </p>
     *
     * @param whrField sql列
     * @param whrValues 该列值的集合
     * @return sql条件语句
     */
    public static String createInWhr(String whrField, String[] whrValues) {
        if (whrField == null || whrValues == null) {
            return " and 1=1 ";
        }
        if (whrValues.length == 0) {
            return " and 1=2 ";
        }
        StringBuilder builder;
        builder = new StringBuilder();
        for (String value : whrValues) {
            builder.append("'").append(value).append("'").append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        return " and " + whrField + " in (" + builder.toString() + ")";
    }

    /**
     * <p>
     * Description: 根据输入的sql列以及该列值的集合,whrValues中不允许有特殊字符，而且不能超过1000个值
     * </p>
     *
     * @param whrField sql列
     * @param whrValues 该列值的集合
     * @return sql条件语句
     */
    public static String createNotInWhr(String whrField, String[] whrValues) {
        if (whrField == null || whrValues == null) {
            return " and 1=1 ";
        }
        if (whrValues.length == 0) {
            return " and 1=2 ";
        }
        StringBuilder builder;
        builder = new StringBuilder();
        for (String value : whrValues) {
            builder.append("'").append(value).append("'").append(",");
        }
        builder.delete(builder.length() - 1, builder.length());
        return " and " + whrField + " not in (" + builder.toString() + ")";
    }
}
