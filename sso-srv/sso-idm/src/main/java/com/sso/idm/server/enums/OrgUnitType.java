package com.sso.idm.server.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/18/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public enum OrgUnitType {
    CENTRAL(1, "central"),
    BRANCH(2, "branch"),
    DEPOT(3, "depot");

//    FILIAL(2, "filial"),
//    SUBDIVISION(3, "structure subdivision"),
//    LINEAR_UNIT(4, "line subdivision");

    private String description;
    private Integer code;

    private static Map<Integer, OrgUnitType> map = new HashMap<>();

    static {
        for (OrgUnitType item : OrgUnitType.values()) {
            map.put(item.getCode(), item);
        }
    }

    private OrgUnitType(Integer code, String description) {
        this.description = description;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Map<Integer, OrgUnitType> getMap() {
        return map;
    }
}