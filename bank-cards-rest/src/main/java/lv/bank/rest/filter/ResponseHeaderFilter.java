package lv.bank.rest.filter;
import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(11)
public class ResponseHeaderFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		if(RequestParamsUtility.getRequestId() != null) {
			responseContext.getHeaders().add(RequestParamFilter.REQUEST_ID_HEADER_KEY, RequestParamsUtility.getRequestId());
		} 
		if(RequestParamsUtility.getPersonalId() != null) {
			responseContext.getHeaders().add(RequestParamFilter.PERSONAL_ID_HEADER_KEY, RequestParamsUtility.getPersonalId());
		}
		if(RequestParamsUtility.getCif() != null) {
			responseContext.getHeaders().add(RequestParamFilter.CIF_HEADER_KEY, RequestParamsUtility.getCif());
		}
	}

}
