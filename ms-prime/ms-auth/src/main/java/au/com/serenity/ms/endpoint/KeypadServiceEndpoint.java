package au.com.serenity.ms.endpoint;

import au.com.ingdirect.soa.IKeypadService;
import au.com.ingdirect.soa.IKeypadServiceGetPINFaultExceptionFaultFaultMessage;
import au.com.serenity.ms.service.PinService;
import org.datacontract.schemas._2004._07.ing_soa_contract_keypad_datacontract.GetAccessiblePINsResponse;
import org.datacontract.schemas._2004._07.ing_soa_contract_keypad_datacontract.GetPINImagesResponse;
import org.datacontract.schemas._2004._07.ing_soa_contract_keypad_datacontract.GetPINRequest;
import org.datacontract.schemas._2004._07.ing_soa_contract_keypad_datacontract.GetPinResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public class KeypadServiceEndpoint implements IKeypadService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PinService pinService;

    @Override
    public GetAccessiblePINsResponse getAccessiblePINs() {
        log.info(">>getAccessiblePINs");
        return pinService.getAccessiblePINsResponse();
    }

    @Override
    public GetPinResponse getPIN(GetPINRequest request) throws IKeypadServiceGetPINFaultExceptionFaultFaultMessage {
        log.info(">>getPIN ()", request);
        return pinService.getPinResponse(request);
    }

    @Override
    public GetPINImagesResponse getPINImages() {
        log.info(">>GetPINImagesResponse");
        return pinService.getPINImagesResponse();
    }
}
