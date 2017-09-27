package au.com.serenity.ms.service;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class PermissionIntegrationTest extends GenericUnitTest {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    private RestTemplate customRestTemplate;

    @Before
    public void setup() {
        customRestTemplate = new RestTemplate();
        BeanUtils.copyProperties(restTemplate, customRestTemplate);
        DefaultResponseErrorHandler defaultResponseErrorHandler = new DefaultResponseErrorHandler() {
            protected boolean hasError(HttpStatus statusCode) {
                return false;
            }
        };
        customRestTemplate.setErrorHandler(defaultResponseErrorHandler);
    }
/*
    @Test
    public void shouldSuccessfullyGetNonEmptyPermissions() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AuthToken", "secret");
        PermissionRequestDto permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setClientApplication("123");
        permissionRequestDto.setContext("context_value");
        permissionRequestDto.setIdentity("-8");
        HttpEntity<PermissionRequestDto> requestEntity = new HttpEntity<>(permissionRequestDto, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        UriComponents result = builder.scheme("http")
            .host("localhost")
            .port(8080)
            .path("ms-permission/v1/security/permissions")
            .build();
        ResponseEntity<?> response =
            customRestTemplate.exchange(result.toUriString(), HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<String>>(){});
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), notNullValue());

        List<String> permissions = (List<String>) response.getBody();
        assertThat(permissions.size(), is(4));
        assertThat(permissions.get(0), is("READ"));
        assertThat(permissions.get(1), is("WRITE"));
        assertThat(permissions.get(2), is("UPDATE"));
        assertThat(permissions.get(3), is("DELETE"));
    }

    @Test
    public void shouldSuccessfullyGetEmptyPermissions() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AuthToken", "secret");
        PermissionRequestDto permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setClientApplication("123");
        permissionRequestDto.setContext("context_value");
        permissionRequestDto.setIdentity("-1");
        HttpEntity<PermissionRequestDto> requestEntity = new HttpEntity<>(permissionRequestDto, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        UriComponents result = builder.scheme("http")
            .host("localhost")
            .port(8080)
            .path("ms-permission/v1/security/permissions")
            .build();
        ResponseEntity<?> response =
            customRestTemplate.exchange(result.toUriString(), HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<String>>(){});
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), notNullValue());

        List<String> permissions = (List<String>) response.getBody();
        assertThat(permissions.size(), is(0));
    }

    @Test
    public void shouldFailWithoutXAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        PermissionRequestDto permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setClientApplication("");
        permissionRequestDto.setContext("context_value");
        permissionRequestDto.setIdentity("-8");
        HttpEntity<PermissionRequestDto> requestEntity = new HttpEntity<>(permissionRequestDto, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        UriComponents result = builder.scheme("http")
            .host("localhost")
            .port(8080)
            .path("ms-permission/v1/security/permissions")
            .build();
        ResponseEntity<?> response =
            customRestTemplate.exchange(result.toUriString(), HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<String>>(){});
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }

    @Test
    public void shouldFailForInvalidXAuthToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AuthToken", "token_value");
        PermissionRequestDto permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setClientApplication("");
        permissionRequestDto.setContext("context_value");
        permissionRequestDto.setContext("context_value");
        permissionRequestDto.setIdentity("-8");
        HttpEntity<PermissionRequestDto> requestEntity = new HttpEntity<>(permissionRequestDto, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        UriComponents result = builder.scheme("http")
            .host("localhost")
            .port(8080)
            .path("ms-permission/v1/security/permissions")
            .build();
        ResponseEntity<?> response =
            customRestTemplate.exchange(result.toUriString(), HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<String>>(){});
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }

    @Test
    public void shouldFailForInvalidClientApplication() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AuthToken", "secret");
        PermissionRequestDto permissionRequestDto = new PermissionRequestDto();
        permissionRequestDto.setClientApplication("invalid");
        permissionRequestDto.setContext("context_value");
        permissionRequestDto.setIdentity("-8");
        HttpEntity<PermissionRequestDto> requestEntity = new HttpEntity<>(permissionRequestDto, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        UriComponents result = builder.scheme("http")
            .host("localhost")
            .port(8080)
            .path("ms-permission/v1/security/permissions")
            .build();
        ResponseEntity<?> response =
            customRestTemplate.exchange(result.toUriString(), HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<List<String>>(){});
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        assertThat(response, notNullValue());
        assertThat(response.getBody(), nullValue());
    }
*/
}