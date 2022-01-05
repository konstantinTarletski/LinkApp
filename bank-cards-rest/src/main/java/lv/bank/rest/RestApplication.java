package lv.bank.rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import lv.bank.rest.filter.GeneralExceptionMapper;
import lv.bank.rest.filter.LoggingFilter;

@ApplicationPath("")
public class RestApplication extends Application {

	protected void addResources(Set<Class<?>> resources) {
		resources.add(CardsInterfaceRest.class);
		resources.add(StatusInterfaceRest.class);
		resources.add(DocumentationService.class);
		resources.add(GeneralExceptionMapper.class);
		resources.add(LoggingFilter.class);
	}
}
