package au.com.serenity.ms.service;

import au.com.ingdirect.soa.IKeypadServiceGetPINFaultExceptionFaultFaultMessage;
import org.datacontract.schemas._2004._07.ing_soa_contract_keypad_datacontract.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;

@Service
public class PinService {
    private Logger log = LoggerFactory.getLogger(getClass());

    public GetAccessiblePINsResponse getAccessiblePINsResponse() {
        log.info(">>getAccessiblePINsResponse");

        ObjectFactory factory = new ObjectFactory();
        String myObject = "secret_value";
        JAXBElement<String> secret = factory.createGetAccessiblePINsResponseSecret(myObject);

        GetAccessiblePINsResponse response = new GetAccessiblePINsResponse();
        response.setSecret(secret);

        log.info("<<getAccessiblePINsResponse");
        return response;
    }

    public GetPinResponse getPinResponse(GetPINRequest request) throws IKeypadServiceGetPINFaultExceptionFaultFaultMessage {
        log.info(">>getPinResponse ()", request);

        ObjectFactory factory = new ObjectFactory();
        String myObject = "pin_value";
        JAXBElement<String> pin = factory.createGetPinResponsePin(myObject);

        GetPinResponse response = new GetPinResponse();
        response.setPin(pin);

        log.info("<<getPinResponse");
        return response;
    }

    public GetPINImagesResponse getPINImagesResponse() {
        log.info(">>getPINImagesResponse");

        ObjectFactory factory = new ObjectFactory();
        String myObject = "PemEncryptionKey_value";
        JAXBElement<String> pemEncryptionKey = factory.createGetPINImagesResponsePemEncryptionKey(myObject);

        GetPINImagesResponse response = new GetPINImagesResponse();
        response.setPemEncryptionKey(pemEncryptionKey);

        log.info("<<getPINImagesResponse");
        return response;
    }
}
