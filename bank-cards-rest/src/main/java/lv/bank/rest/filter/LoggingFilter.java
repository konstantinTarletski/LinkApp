package lv.bank.rest.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Provider
@Priority(10)
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	
	private static final List<String> EXCLUDED_HEADERS = Arrays.asList("token");
	
	@Context
	private HttpServletRequest servletRequest;
	
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		Long startTime = (Long)requestContext.getProperty(RequestParamFilter.START_TIME);
		if(startTime == null) {
			startTime = System.currentTimeMillis() - 9999;
		}
		long time = System.currentTimeMillis() - startTime;
		log.info(new StringBuilder("Sending response : ").append("\n")
		.append("Status : ").append(responseContext.getStatus()).append("\n")
		.append("Headers : ").append(headersAsString(responseContext.getStringHeaders())).append("\n")
		.append("Body: ").append(isJson(responseContext) ? getResponseBodyAsString(responseContext) : responseContext.getMediaType()).append("\n")
		.append("Response in ").append(time).toString());
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requestContext.setProperty(RequestParamFilter.START_TIME, System.currentTimeMillis());
		log.info(new StringBuilder("From ").append(servletRequest.getRemoteAddr())
		.append(" received request : \n")
		.append("Method : ").append(requestContext.getMethod()).append("\n")
		.append("URI : ").append(requestContext.getUriInfo().getPath()).append("\n")
		.append("Headers : ").append(headersAsString(requestContext.getHeaders())).append("\n")
		.append("Body : ").append(isText(requestContext) ? getRequestBodyAsString(requestContext) : requestContext.getMediaType()).toString());	
	}

	private String headersAsString(MultivaluedMap<String, String> headers) {
		final StringBuilder result = new StringBuilder("[");
		headers.keySet().forEach(h -> {
			if(!EXCLUDED_HEADERS.contains(h.toLowerCase())) {
				result.append("  \"").append(h).append("\" : \"")
					.append(headers.get(h).stream().collect(Collectors.joining("\", \"")))
					.append("\"").append("\n");
			}
		});
		return result.append("]").toString();
	}
	
	private boolean isText(ContainerRequestContext requestContext) {
		return requestContext.getMediaType() != null && (
				requestContext.getMediaType().toString().contains(MediaType.APPLICATION_JSON) || 
				requestContext.getMediaType().toString().contains(MediaType.TEXT_PLAIN));
	}
	
	private String getRequestBodyAsString(ContainerRequestContext requestContext) {
		try {
			if(requestContext.getEntityStream() == null) {
				return null;
			}
			final String requestBody = convertStreamToString(requestContext.getEntityStream());
			requestContext.setEntityStream(new ByteArrayInputStream(requestBody.getBytes("UTF-8")));
			return requestBody;
		} catch(IOException e) {
			return "Unparsable : " + e.getMessage();
		}
	}
	
	private String convertStreamToString(java.io.InputStream is) {
	    @SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	private boolean isJson(ContainerResponseContext responseContext) {
		return responseContext.getMediaType() != null && responseContext.getMediaType().toString().contains(MediaType.APPLICATION_JSON);
	}
	
	private String getResponseBodyAsString(ContainerResponseContext responseContext) {
		try {
			return responseContext.getEntity() == null ? null : new ObjectMapper().writeValueAsString(responseContext.getEntity());
		} catch(JsonProcessingException e) {
			return "Unparsable : " + e.getMessage();
		}
	}

}
