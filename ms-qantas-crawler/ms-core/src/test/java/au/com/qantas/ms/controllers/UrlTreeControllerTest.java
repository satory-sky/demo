package au.com.qantas.ms.controllers;

import au.com.qantas.ms.Application;
import au.com.qantas.ms.controller.UrlTreeController;
import au.com.qantas.ms.dto.RequestDto;
import au.com.qantas.ms.dto.ResponseDto;
import au.com.qantas.ms.service.UrlTreeService;
import au.com.qantas.ms.utils.Node;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UrlTreeControllerTest  {
    @Autowired
    private UrlTreeController urlTreeController;

    @Autowired
    private UrlTreeService urlTreeService;

    @Test
    public void ShouldSuccessfullyReturnUrlTreeForMockedUrlTreeService() throws Exception {
        final String url = "https://trello.com/";
        ResponseDto responseDto = new ResponseDto();
        responseDto.setUrl(url);
        List<ResponseDto> responseDtos = Arrays.asList(responseDto);

        RequestDto requestDto = new RequestDto();
        requestDto.setBaseUrl("any_url");
        requestDto.setSearchDepth(2);
        when(urlTreeService.getUrlTree(any(RequestDto.class))).thenReturn(responseDtos);

        ResponseEntity<List<ResponseDto>> responseEntity = urlTreeController.getUrlTree(requestDto);

        Assert.assertEquals(responseEntity.getBody().get(0).getUrl(), url);
    }
}
