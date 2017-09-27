
package com.sso.common.server.dto.validation.annotations;

import com.sso.common.server.dto.validation.validators.MaxFloatingPointValidatorForNumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Original Author: Alexander Kontarero
 * @version 24.10.2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MaxFloatingPointValidatorForNumber.class)
public @interface FloatingPointMax {

	String message() default "{javax.validation.constraints.Max.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * @return value the element must be less or equal to
	 */
	double value();

    double precision() default 1e-9;

	/**
	 * Defines several {@link FloatingPointMax} annotations on the same element.
	 *
	 * @see FloatingPointMax
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

        FloatingPointMax[] value();
	}
}
