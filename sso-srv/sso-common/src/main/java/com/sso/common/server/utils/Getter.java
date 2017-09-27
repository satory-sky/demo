package com.sso.common.server.utils;

/**
 * @author Original Author: Alexander Kontarero
 * @version 23.10.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public interface Getter<K, T> {

    K get(T object);
}
