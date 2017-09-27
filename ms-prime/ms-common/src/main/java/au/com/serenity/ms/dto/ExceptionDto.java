package au.com.serenity.ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionDto {
    private final String fieldName;
    private final String fieldValue;
    private final String detail;

    public ExceptionDto() {
        this(null, null, null);
    }

    public ExceptionDto(String detail) {
        this(null, null, detail);
    }

    public ExceptionDto(String fieldName, String fieldValue) {
        this(fieldName, fieldValue, null);
    }

    public ExceptionDto(String fieldName, String fieldValue, String detail) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.detail = detail;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDetail() {
        return detail;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
