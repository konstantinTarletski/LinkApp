package lv.bank.rest.filter;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lv.bank.rest.dto.ErrorResponse;
import lv.bank.rest.exception.BusinessException;
import lv.bank.rest.exception.JsonErrorCode;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		if(exception instanceof BusinessException) {
			BusinessException businessException = (BusinessException) exception;
			return getErrorResponse(businessException.getErrorCode().getStatusCode(),
					businessException.getErrorCode().getCode(), businessException.getMessage(), businessException.getTarget());
		} else if(exception instanceof WebApplicationException) {
			WebApplicationException wae = (WebApplicationException) exception;
			StatusType type = wae.getResponse().getStatusInfo();
			String code = type.getClass().isEnum() ? ((Enum<?>) type).name().toLowerCase() : JsonErrorCode.APPLICATION_ERROR.name().toLowerCase();
			return getErrorResponse(wae.getResponse().getStatus(), code, wae.getMessage(), null);
		} else {
			JsonErrorCode errorCode = exception instanceof NotFoundException ? JsonErrorCode.NOT_FOUND : JsonErrorCode.APPLICATION_ERROR;
			return getErrorResponse(errorCode.getStatusCode(), errorCode.getCode(), exception.getMessage(), null);
		}
	}
	
	public static Response getErrorResponse(int status, String code, String message, String target) {
		ErrorResponse error = new ErrorResponse(status, code, message, target);
		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(error).build();
	}


}
