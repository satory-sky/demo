package au.com.serenity.ms.endpoint;

import au.com.serenity.ms.service.GatewayService;
import com.ingdirect.dg.core.GenericRequest;
import com.ingdirect.dg.core.GenericResponse;
import com.ingdirect.dg.core.endpoint.DirectGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DirectGatewayEndpoint  implements DirectGateway {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GatewayService gatewayService;

    @Override
    public GenericResponse execute(String arg00, String arg10, GenericRequest arg20) {
        log.info(">>execute (), (), ()", arg00, arg10, arg20);
        return gatewayService.execute();
    }
}