package com.sso.common.server.dto.validation.validators;

import com.sso.common.server.dto.validation.annotations.FloatingPointMax;

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
public class MaxFloatingPointValidatorForNumber implements ConstraintValidator<FloatingPointMax, Number> {

    private double maxValue;
    private double precision;

    public void initialize(FloatingPointMax maxValue) {
        this.maxValue = maxValue.value();
        this.precision = maxValue.precision();
    }

    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        //null values are valid
        if (value == null) {
            return true;
        }
        if (value instanceof BigDecimal) {
            BigDecimal diff = BigDecimal.valueOf(maxValue).subtract((BigDecimal) value);
            BigDecimal precisionBigDecimal = new BigDecimal(this.precision);
            return diff.compareTo(precisionBigDecimal.negate()) >= 0;
        } else if (value instanceof BigInteger) {
            BigDecimal diff = BigDecimal.valueOf(maxValue).subtract(new BigDecimal((BigInteger) value));
            BigDecimal precisionBigDecimal = new BigDecimal(this.precision);
            return diff.compareTo(precisionBigDecimal.negate()) >= 0;
        } else {
            double diff = maxValue- value.doubleValue();
            return diff >= -precision;
        }
    }
}
