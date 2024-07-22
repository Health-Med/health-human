package br.com.healthemed.healthhuman.infra.adapter;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "nomination")
@Profile("!dev")
public interface NominatimRestClient {

	@GetMapping(value = "/search?format=json&q={query}")
	List<Location> searchLocation(@PathVariable("query") String query);
}
