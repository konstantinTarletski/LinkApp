package lv.bank.rest.filter;

import lombok.extern.slf4j.Slf4j;
import lv.bank.rest.CountryCheck;
import lv.bank.rest.exception.JsonErrorCode;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Slf4j
@Provider
@Priority(21)
@CountryCheck
public class CountryCheckFilter implements ContainerRequestFilter {

	public static final String COUNTRY_HEADER_KEY = "country";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String country = requestContext.getHeaderString(COUNTRY_HEADER_KEY);
		log.info("country = {}", country);
		if(country == null || country.isEmpty()) {
			requestContext.abortWith(GeneralExceptionMapper.getErrorResponse(JsonErrorCode.BAD_REQUEST.getStatusCode(), 
					JsonErrorCode.BAD_REQUEST.getCode(), "Request missing request country", COUNTRY_HEADER_KEY));
			return;
		}
	}
}
