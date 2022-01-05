package lv.bank.rest.filter;

import java.io.IOException;
import java.net.InetAddress;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import lv.bank.rest.exception.JsonErrorCode;
import lv.bank.cards.core.utils.LinkAppProperties;;

@Provider
@Priority(13)
public class WhiteListFilter implements ContainerRequestFilter {

	private static final Logger LOG = Logger.getLogger(WhiteListFilter.class);
	
	@Context
	private HttpServletRequest servletRequest;
	
	private String getIPFromHostname(String hostname) {
		if(!Character.isDigit(hostname.toCharArray()[0])) {
			try {
				return InetAddress.getByName(hostname).getHostAddress();
			} catch(Exception e) {}
		}
		return "";
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String whiteList = LinkAppProperties.getIPAddressWhiteList();
    	if(!StringUtils.isBlank(whiteList) && !"*".equals(whiteList)){
    		String ipAddress = servletRequest.getRemoteAddr();
    		// Need to check white list
    		boolean inList = false;
    		for(String ip : whiteList.split(",")){
    			if (ip != null && (ip.equals(ipAddress) || (getIPFromHostname(ip).equals(ipAddress)))) {
    				inList = true;
    				break;
    			}
    		}
    		if(!inList){
    			LOG.info("IP address " + ipAddress + " is not in white list, deny access.");
    			requestContext.abortWith(GeneralExceptionMapper.getErrorResponse(JsonErrorCode.FORBIDDEN.getStatusCode(), 
    					JsonErrorCode.FORBIDDEN.getCode(), "IP address " + ipAddress + " is not in white list, deny access", "ipAddress"));
    			return;
    		}
    	}
	}
}
