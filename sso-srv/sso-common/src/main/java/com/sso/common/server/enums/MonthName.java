package com.sso.common.server.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/30/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public enum MonthName {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    public int numberInYear() {
        return this.ordinal() + 1;
    }

    private static Map<String, Integer> map = new HashMap<>();

    static {
        Integer index = 0;
        for (MonthName item : MonthName.values()) {
            map.put(item.name(), index++);
        }
    }

    public static Map<String, Integer> getMap() {
        return map;
    }
}