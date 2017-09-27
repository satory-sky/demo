package au.com.qantas.ms.service;

import au.com.qantas.ms.dto.RequestDto;
import au.com.qantas.ms.dto.ResponseDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class UrlTreeService {
    private Logger log = LoggerFactory.getLogger(UrlTreeService.class);

    public List<ResponseDto> getUrlTree(RequestDto requestDto) {
        log.info("getUrlTree>> ()", requestDto);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setUrl(requestDto.getBaseUrl());
        responseDto = urlTreeHandle(0, responseDto, requestDto.getSearchDepth());

        log.info("getUrlTree<<");
        return responseDto.getNodes();
    }

    private ResponseDto urlTreeHandle(int depth, ResponseDto parentResponseDto, int searchDepth) {
        log.info("urlTreeHandle>> (), (), ()", depth, parentResponseDto, searchDepth);
        int currentDepth = depth + 1;

        if (currentDepth <= searchDepth) {
            List<ResponseDto> childResponseDtos = getChildResponseDto(parentResponseDto);
            if (!CollectionUtils.isEmpty(childResponseDtos)) {
                childResponseDtos.stream()
                    .filter(childResponseDto -> childResponseDto != null && !StringUtils.isEmpty(childResponseDto))
                    .forEach(childResponseDto -> {
                        List<ResponseDto> result = new LinkedList<>(Arrays.asList(childResponseDto));
                        if (parentResponseDto.getNodes() != null) {
                            result.addAll(parentResponseDto.getNodes());
                        }
                        parentResponseDto.setNodes(result);

                        urlTreeHandle(currentDepth, childResponseDto, searchDepth);
                    });
            }
        }

        log.info("urlTreeHandle<<");
        return parentResponseDto;
    }

    private List<ResponseDto> getChildResponseDto(ResponseDto parentResponseDto) {
        log.info("getChildResponseDto>> ()", parentResponseDto);
        List<ResponseDto> responseDtos = new LinkedList<>();
        try {
            Document doc = Jsoup.connect(parentResponseDto.getUrl()).get();
            Elements httpsLinks = doc.select("a[href*=https]");
            Elements httpLinks = doc.select("a[href*=http]");
            httpsLinks.forEach(link -> responseDtos.add(getFilledResponseDto(link)));
            httpLinks.forEach(link -> responseDtos.add(getFilledResponseDto(link)));
        } catch (IOException e) {
            log.error("Link ***->" + parentResponseDto.getUrl() + "<-*** is broken");
        }

        log.info("getChildResponseDto<<");
        return responseDtos;
    }

    private ResponseDto getFilledResponseDto(Element link) {
        log.info("getFilledResponseDto>> ()", link);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setUrl(link.attr("abs:href"));
        responseDto.setTitle(link.attr("title"));

        log.info("getFilledResponseDto<<");
        return responseDto;
    }
}
