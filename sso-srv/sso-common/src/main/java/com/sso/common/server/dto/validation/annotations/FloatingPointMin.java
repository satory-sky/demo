
package com.sso.common.server.dto.validation.annotations;

import com.sso.common.server.dto.validation.validators.MinFloatingPointValidatorForNumber;

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
@Constraint(validatedBy = MinFloatingPointValidatorForNumber.class)
public @interface FloatingPointMin {

	String message() default "{javax.validation.constraints.Min.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * @return value the element must be higher or equal to
	 */
	double value();

    double precision() default 1e-6;

	/**
	 * Defines several {@link FloatingPointMin} annotations on the same element.
	 *
	 * @see FloatingPointMin
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

        FloatingPointMin[] value();
	}
}
