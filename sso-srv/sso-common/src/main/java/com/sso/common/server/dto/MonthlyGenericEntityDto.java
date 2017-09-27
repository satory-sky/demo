package com.sso.common.server.dto;

import com.sso.common.server.enums.MonthName;

import java.io.Serializable;

/**
 * @author Original Author: Alexander Kontarero
 * @version 12.01.2015
 * @see © 2015 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public abstract class MonthlyGenericEntityDto<ID extends Serializable> implements GenericEntityDto<ID> {
    private MonthName month;

    public MonthName getMonth() {
        return month;
    }

    public void setMonth(MonthName month) {
        this.month = month;
    }
}
