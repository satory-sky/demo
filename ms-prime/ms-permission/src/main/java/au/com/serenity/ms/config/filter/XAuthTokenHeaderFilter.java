package au.com.serenity.ms.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XAuthTokenHeaderFilter implements Filter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info(">>doFilter(), (), ()", request, response, chain);

        String header = ((HttpServletRequest) request).getHeader("X-AuthToken");
        if (header == null || "secret".equals(header))
            chain.doFilter(request, response);
        else {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        log.info("<<doFilter");
    }

    @Override
    public void destroy() {
    }
}