package com.sso.common.server.utils;

import com.sso.common.server.utils.Getter;

import java.util.*;

/**
 * @author Original Author: Alexander Kontarero
 * @version 23.10.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class EntityUtils {
    public static <K, T> Map<K, T> toIdMap(Collection<T> items, Getter<K, T> idGetter) {
        Map<K, T> map = new HashMap<>();
        for (T item : items) {
            map.put(idGetter.get(item), item);
        }
        return map;
    }

    public static <K, T> Set<K> getIds(Collection<T> items, Getter<K, T> idGetter) {
        Set<K> ids = new HashSet<>();
        for (T item : items) {
            ids.add(idGetter.get(item));
        }
        return ids;
    }
}
