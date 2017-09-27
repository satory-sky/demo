package au.com.serenity.ms.config;

import au.com.ingdirect.soa.IKeypadService;
import au.com.ingdirect.soa.KeypadService;
import au.com.serenity.ms.endpoint.DirectGatewayEndpoint;
import au.com.serenity.ms.endpoint.KeypadServiceEndpoint;
import com.ingdirect.dg.core.endpoint.DirectGateway;
import com.ingdirect.dg.core.endpoint.DirectGatewayService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
    public static final String BASE_URL = "/soap-api";
    public static final String IKEYPAD_SERVICE_URL = "/KeypadService";
    public static final String DIRECT_GATEWAY_SERVICE_URL = "/DirectGateway";

    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), BASE_URL + "/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    // IKeypadService
    @Bean
    public IKeypadService iKeypadService() {
        return new KeypadServiceEndpoint();
    }

    @Bean
    public KeypadService keypadService() {
        return new KeypadService();
    }

    @Bean
    public Endpoint iKeypadServiceEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), iKeypadService());
        endpoint.setServiceName(keypadService().getServiceName());
        endpoint.publish(IKEYPAD_SERVICE_URL);
        endpoint.setWsdlLocation(keypadService().getWSDLDocumentLocation().toString());

        return endpoint;
    }

    // DirectGateway
    @Bean
    public DirectGateway directGateway() {
        return new DirectGatewayEndpoint();
    }

    @Bean
    public DirectGatewayService directGatewayService() {
        return new DirectGatewayService();
    }

    @Bean
    public Endpoint directGatewayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), directGateway());
        endpoint.setServiceName(directGatewayService().getServiceName());
        endpoint.publish(DIRECT_GATEWAY_SERVICE_URL);
        endpoint.setWsdlLocation(directGatewayService().getWSDLDocumentLocation().toString());

        return endpoint;
    }

    // testing
    @Bean
    public IKeypadService keypadServiceSystemTestClient() {
        JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
        jaxWsProxyFactory.setServiceClass(IKeypadService.class);
        jaxWsProxyFactory.setAddress("http://localhost:8090" + WebServiceConfig.BASE_URL + WebServiceConfig.IKEYPAD_SERVICE_URL);

        return (IKeypadService) jaxWsProxyFactory.create();
    }
}
