package au.com.serenity.ms.service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import au.com.serenity.ms.service.ServiceDefines;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@Component
public class CorrelatedThreadName implements Filter {

	private static final Logger log = LoggerFactory.getLogger(CorrelatedThreadName.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String correlator = request.getHeader(ServiceDefines.CORRELATION_HEADER);
		if (correlator != null) {
			Thread.currentThread().setName(correlator);
			if (log.isDebugEnabled()) {
				log.debug("Set thread name to:" + correlator);
			}
		}
		String method=request.getMethod();
		log.info("\tService:\t"+method+" \"" + request.getRequestURI() + "\" \tSTART\t@\t" + new Date().getTime());
		try {
			chain.doFilter(req, res);
			log.info("\tService:\t"+method+" \"" + request.getRequestURI() + "\" \tCOMPLETE(OK)\t@\t" + new Date().getTime());
		} catch (Exception e) {

			log.info(("\tService:\t"+method+" \"" + request.getRequestURI() + "\" \tCOMPLETE(EXCEPTION)\t@\t" + new Date().getTime()));

			throw e;

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
