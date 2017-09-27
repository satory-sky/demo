package au.com.qantas.ms.controller;

import au.com.qantas.ms.dto.RequestDto;
import au.com.qantas.ms.service.UrlTreeService;
import au.com.qantas.ms.validator.RequestDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UrlTreeController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UrlTreeService urlTreeService;
    @Autowired
    private RequestDtoValidator requestDtoValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (RequestDto.class.isInstance(binder.getTarget())) {
            binder.addValidators(requestDtoValidator);
        }
    }

    @RequestMapping(
        value = "/tree",
        method = RequestMethod.POST,
        produces = {"application/json"})
    public ResponseEntity getUrlTree(@RequestBody @Validated RequestDto requestDto) {
        log.info("getUrlTree>> ()", requestDto);
        return new ResponseEntity<>(urlTreeService.getUrlTree(requestDto), HttpStatus.OK);
    }
}


