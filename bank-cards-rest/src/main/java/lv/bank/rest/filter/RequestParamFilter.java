package lv.bank.rest.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import lv.bank.rest.CountryCheck;
import lv.bank.rest.exception.JsonErrorCode;

@Provider
@Priority(20)
public class RequestParamFilter implements ContainerRequestFilter {

	public static final String COUNTRY_HEADER_KEY = "country";
	public static final String PERSONAL_ID_HEADER_KEY = "personalId";
	public static final String CIF_HEADER_KEY = "cif";
	public static final String DEVICE_ID_HEADER_KEY = "deviceId";
	public static final String WALLET_ID_HEADER_KEY = "walletId";
	public static final String REQUEST_ID_HEADER_KEY = "X-Request-ID";
	
	public static final String START_TIME = "start_time";
	

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		RequestParamsUtility.setRequestParams(
				requestContext.getHeaderString(COUNTRY_HEADER_KEY),
				requestContext.getHeaderString(PERSONAL_ID_HEADER_KEY), 
				requestContext.getHeaderString(CIF_HEADER_KEY),
				requestContext.getHeaderString(REQUEST_ID_HEADER_KEY),
				requestContext.getHeaderString(DEVICE_ID_HEADER_KEY),
				requestContext.getHeaderString(WALLET_ID_HEADER_KEY));
	}
}
