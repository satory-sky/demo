package com.sso.common.server.dto.validation.validators;

import com.sso.common.server.dto.validation.annotations.FloatingPointMin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Original Author: Alexander Kontarero
 * @version 24.10.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class MinFloatingPointValidatorForNumber implements ConstraintValidator<FloatingPointMin, Number> {

    private double minValue;
    private double precision;

    public void initialize(FloatingPointMin minValue) {
        this.minValue = minValue.value();
        this.precision = minValue.precision();
    }

    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        //null values are valid
        if (value == null) {
            return true;
        }
        if (value instanceof BigDecimal) {
            BigDecimal diff = ((BigDecimal) value).subtract(BigDecimal.valueOf(minValue));
            BigDecimal precisionBigDecimal = new BigDecimal(this.precision);
            return diff.compareTo(precisionBigDecimal.negate()) >= 0;
        } else if (value instanceof BigInteger) {
            BigDecimal diff = (new BigDecimal((BigInteger) value)).subtract(BigDecimal.valueOf(minValue));
            BigDecimal precisionBigDecimal = new BigDecimal(this.precision);
            return diff.compareTo(precisionBigDecimal.negate()) >= 0;
        } else {
            double diff = value.doubleValue() - minValue;
            return diff >= -precision;
        }
    }
}
