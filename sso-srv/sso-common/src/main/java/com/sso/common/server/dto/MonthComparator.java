package com.sso.common.server.dto;

import java.util.Comparator;

/**
 * @author Original Author: Alexander Kontarero
 * @version 12.01.2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class MonthComparator implements Comparator<MonthlyGenericEntityDto<?>> {
    @Override
    public int compare(MonthlyGenericEntityDto<?> o1, MonthlyGenericEntityDto<?> o2) {
        return o1.getMonth().compareTo(o2.getMonth());
    }
}
