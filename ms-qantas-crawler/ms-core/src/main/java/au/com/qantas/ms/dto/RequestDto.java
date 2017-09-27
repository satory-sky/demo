package au.com.qantas.ms.dto;

import javax.validation.constraints.NotNull;

public class RequestDto {
    @NotNull
    private String baseUrl;
    @NotNull
    private Integer searchDepth;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getSearchDepth() {
        return searchDepth;
    }

    public void setSearchDepth(Integer searchDepth) {
        this.searchDepth = searchDepth;
    }
}
