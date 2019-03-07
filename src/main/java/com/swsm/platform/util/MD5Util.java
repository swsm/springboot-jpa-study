package com.swsm.platform.util;

import org.springframework.util.DigestUtils;

/**
 * @author tinel
 * @Title: MD5Util
 * @ProjectName frame
 * @Description: MD5加密
 * @date 2018/12/1211:05
 */
public class MD5Util {
    /**
     * @api {method} MD5Util.getDigest  md5加密类
     * @apiGroup MD5Util
     * @apiName MD5Util.getDigest([str])
     * @apiVersion 1.0.0
     *
     * @apiDescription 对字符串进行md5加密， 底层调用spring digestUtils方法
     *
     * @apiParam {String} str  字符串
     *
     * @apiSuccess {String} status    md5后的字符串
     *
     * @Author dlb
     * @Description
     * @Date 17:41 2019/2/20
     * @Param [str]
     * @return java.lang.String
    */
    public static String getDigest(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes()).toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println("MD5Util(\"zw\"):" + getDigest("admin").toLowerCase());
    }
}
