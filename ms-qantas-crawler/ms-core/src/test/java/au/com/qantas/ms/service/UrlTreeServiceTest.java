package au.com.qantas.ms.service;

import au.com.qantas.ms.dto.RequestDto;
import au.com.qantas.ms.dto.ResponseDto;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UrlTreeService.class)
@PowerMockIgnore("javax.management.*")
public class UrlTreeServiceTest {
    private UrlTreeService urlTreeService;

    @Test
    public void ShouldSuccessfullyReturnUrlTreeForMockedUrlTreeHandle() throws Exception {
        urlTreeService = PowerMockito.spy(new UrlTreeService());

        final String url = "https://trello.com/";
        ResponseDto responseDtoChild = new ResponseDto();
        responseDtoChild.setUrl(url);
        ResponseDto responseDtoParentMock = new ResponseDto();
        responseDtoParentMock.setNodes(Arrays.asList(responseDtoChild));

        PowerMockito.doReturn(responseDtoParentMock)
            .when(urlTreeService, "urlTreeHandle", anyInt(), anyObject(), anyInt());

        RequestDto requestDto = new RequestDto();
        requestDto.setSearchDepth(2);
        List<ResponseDto> result = urlTreeService.getUrlTree(requestDto);

        Assert.assertThat(result, IsCollectionWithSize.hasSize(1));
        Assert.assertEquals(result.get(0).getUrl(), url);
    }
}
