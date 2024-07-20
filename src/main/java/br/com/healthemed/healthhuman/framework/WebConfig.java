package br.com.healthemed.healthhuman.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
public class WebConfig {

	@Bean
	WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
	                WebEndpointsSupplier webEndpointsSupplier, 
	                ServletEndpointsSupplier servletEndpointsSupplier, 
	                ControllerEndpointsSupplier controllerEndpointsSupplier, 
	                EndpointMediaTypes endpointMediaTypes,
	                CorsEndpointProperties corsProperties, 
	                WebEndpointProperties webEndpointProperties, 
	                Environment environment) {
	    List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
	    Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
	    allEndpoints.addAll(webEndpoints);
	    allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
	    allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
	    String basePath = webEndpointProperties.getBasePath();
	    EndpointMapping endpointMapping = new EndpointMapping(basePath);
	    boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(
	                webEndpointProperties, environment, basePath);
	    return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, 
	                endpointMediaTypes, corsProperties.toCorsConfiguration(), 
	                new EndpointLinksResolver(allEndpoints, basePath), 
	                shouldRegisterLinksMapping);
	}

	private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, 
	                                           Environment environment, String basePath) {
	    return webEndpointProperties.getDiscovery().isEnabled() && 
	                (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
	}
	
//	@CacheEvict(value = { "all_doctors", "all_doctors_with_speciality", "all_doctors_with_rating" }, allEntries = true)
//	@Scheduled(fixedRateString = "1", timeUnit = TimeUnit.MINUTES)
//	public void emptyCache() {
//	    log.info("emptying cache");
//	}
}
