package com.sso.common.server.utils;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class VaderNumberUtils {
    private VaderNumberUtils() {
    }

    public static int nvl(Integer val) {
        return val == null ? 0 : val;
    }

    public static long nvl(Long val) {
        return val == null ? 0 : val;
    }

    public static double nvl(Double val) {
        return val == null ? 0D : val;
    }
    public static double nvl(Double val, double defaultVal) {
        return val == null ? defaultVal : val;
    }

    public static Double nullOrDouble(Long value) {
        return value==null? null: value.doubleValue();
    }
    public static Double nullOrDouble(Integer value) {
        return value==null? null: value.doubleValue();
    }
}
