package au.com.serenity.ms.service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;


@Component
public class RequestDumper implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(RequestDumper.class);
	
	@Value("${writeServiceCallToDirectory:}")
	private String dumpReqRespToDirectory = "";
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		if (dumpReqRespToDirectory!="")
		{
		
			
			HttpServletRequest request = (HttpServletRequest) req;
			
			
			long ourDate = new Date().getTime();
			String fileName = Thread.currentThread().getName() + "_" + Long.toHexString(ourDate) + "_"
					+  "httpReq";
			
			File output = new File(dumpReqRespToDirectory, fileName);
			if (output.exists()) {
				output.createNewFile();
			}
			
			
			CommonsRequestLoggingFilter filter =new CommonsRequestLoggingFilter();
			
		}
		chain.doFilter(req, res);
		if (dumpReqRespToDirectory!="")
		{
			HttpServletResponse response = (HttpServletResponse) res;
			long ourDate = new Date().getTime();
			String fileName = Thread.currentThread().getName() + "_" + Long.toHexString(ourDate) + "_"
					+  "httpResp";
			
			File output = new File(dumpReqRespToDirectory, fileName);
			if (output.exists()) {
				output.createNewFile();
			}
			
			Properties p=new Properties();
			//traverse headers
			p.setProperty("httpReponse:",""+response.getStatus());
			for (String headerName : response.getHeaderNames())
			{
				p.setProperty("header."+headerName , response.getHeader(headerName));
			}
			//p.setProperty("body", response.)
			
			
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	
}
