package com.sso.common.server.enums;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public enum ErrorCode {
    SUCCESS(1, "success"),
    UNKNOWN_ERROR(2, "unknown error"),
    USER_ALREADY_EXISTS_ERROR(3, "user already exists"),
    USER_DOES_NOT_EXISTS_ERROR(4, "user does not exist"),
    USER_DETAILS_IS_NOT_ACCESSIBLE(5, "user details is not accessible"),
    TIMEOUT_ERROR(6, "timeout error"),
    VALIDATION_ERROR(7, "validation error"),
    INTERNAL_ERROR(8, "internal error"),
    USER_ALREADY_AUTHORIZED_ERROR(9, "user already authorized"),
    USER_NOT_AUTHORIZED_ERROR(10, "user not authorized"),
    INVALID_CREDENTIALS_ERROR(11, "invalid credentials"),
    ACCESS_DENIED_ERROR(12, "access denied"),
    METHOD_NOT_ALLOWED_ERROR(13, "method not allowed"),
    ENTITY_NOT_FOUND_ERROR(14, "entity not found"),
    ENTITY_EXISTS_ERROR(15, "entity is already existed"),
    OPERATION_IS_NOT_ALLOWED(16, "it is impossible to do requested operation for this resource"),
    CALCULATION_IS_ALREADY_IN_PLAN(17, "calculation is already in plan"),
    PLAN_IS_NOT_EDITABLE(18, "plan is in read-only mode"),
    PLAN_IS_NOT_WAIT_FOR_APPROVE(19, "plan is not wait for approve"),
    WRONG_PLAN_PERIOD(20, "plan period is wrong"),
    ORG_UNIT_IS_NOT_PARENT(21, "orgUnit of current user is not a parent for the passed ones"),
    WRONG_ORG_UNIT_FOR_CALCULATION(22, "orgUnit of current user is not related to the passed calculation"),
    WRONG_ORG_UNIT_FOR_PLAN(23, "orgUnit of current user is not related to the passed plan"),
    CALCULATION_IS_NOT_FOR_PLAN(24, "this calculation is marked as not 'fit for plan workflow' and cannot be included in plan"),
    PLAN_IS_NOT_IN_PREAPPROVE_STATUS(24, "plan is not in pre-approve status"),
    PLAN_IS_NOT_IN_DRAFT_STATUS(25, "plan is not in draft status"),
    PLAN_IS_IN_DRAFT_STATUS(26, "plan is in draft status"),
    WRONG_ROLE_FOR_REJECT(27, "wrong role for reject"),
    PLAN_PERIOD_IS_NOT_EDITABLE(28, "plan period is in read-only mode"),
    PLAN_PERIOD_IS_NOT_COMPLETE(29, "plan period is not complete or closed, cold not create approved report"),
    APPROVED_PLAN_CLEAR_ERROR(30, "approved plan clear error"),
    APPROVED_PLAN_CREATE_ERROR(31, "approved plan some org units create error");

    private String description;
    private Integer code;

    private ErrorCode(Integer code, String description) {
        this.description = description;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
