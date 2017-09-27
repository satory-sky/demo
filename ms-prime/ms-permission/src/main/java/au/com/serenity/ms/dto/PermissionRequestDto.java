package au.com.serenity.ms.dto;

import javax.validation.constraints.NotNull;

public class PermissionRequestDto {
    @NotNull
    private String identity;
    @NotNull
    private String clientApplication;

    private String context;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getClientApplication() {
        return clientApplication;
    }

    public void setClientApplication(String clientApplication) {
        this.clientApplication = clientApplication;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
