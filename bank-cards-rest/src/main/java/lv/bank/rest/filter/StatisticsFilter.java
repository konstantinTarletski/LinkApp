package lv.bank.rest.filter;

import lv.bank.cards.base.utils.StatisticCounter;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(12)
public class StatisticsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        String ip = servletRequest.getRemoteAddr();
        if (ip != null) {
            Long startTime = (Long) requestContext.getProperty(RequestParamFilter.START_TIME);
            if (startTime == null) {
                startTime = System.currentTimeMillis() - 9999;
            }
            long time = System.currentTimeMillis() - startTime;
            boolean error = responseContext.getStatus() < 200 || responseContext.getStatus() >= 300;
            StatisticCounter.countResponse(ip, time, error);
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String ip = servletRequest.getRemoteAddr();
        if (ip != null) {
            StatisticCounter.countRequest(ip);
        }
    }

}
