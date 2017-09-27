package au.com.serenity.ms.service;

import au.com.ingdirect.dg.account.AccountDetailsResponse;
import com.ingdirect.dg.core.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {
    private Logger log = LoggerFactory.getLogger(getClass());

    public GenericResponse execute() {
        log.info(">>execute");

        GenericResponse response = new AccountDetailsResponse();
        response.setResultMessage("result_message_value");

        log.info("<<execute");
        return response;
    }
}
