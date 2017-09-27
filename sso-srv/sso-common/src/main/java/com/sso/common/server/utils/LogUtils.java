package com.sso.common.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class LogUtils {

    private static final String LOG_DATE_FORMAT = "yyyy/MM/dd HH:mm:ssZ";
    private static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat(LOG_DATE_FORMAT);

    public static String formatDate(Date date) {
        return DATE_FORMATER.format(date);
    }
}
