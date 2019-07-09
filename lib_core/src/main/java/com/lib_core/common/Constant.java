package com.lib_core.common;

/**
 * date:2019/7/9
 * name:windy
 * function:  测试环境
 */
public class Constant {

    public static final boolean isRelease = false; //默认为测试环境

    public static final String BASE_URL =
            isRelease ? "http://mobile.bwstudent.com/small/" : "http://172.17.8.100/small/";

}
