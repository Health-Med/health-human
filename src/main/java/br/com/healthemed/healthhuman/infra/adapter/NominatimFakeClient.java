package br.com.healthemed.healthhuman.infra.adapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class NominatimFakeClient implements NominatimRestClient {
	
	private final ObjectMapper mapper;
	
	private static final Map<String, String> ADDRESSES = new HashMap<>();
	
	static {
		ADDRESSES.put("avenida sebastião davino dos réis", "[{\"place_id\":7179404,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":931540319,\"lat\":\"-23.4962111\",\"lon\":\"-46.8701695\",\"class\":\"highway\",\"type\":\"primary\",\"place_rank\":26,\"importance\":0.053396501869230145,\"addresstype\":\"road\",\"name\":\"Avenida Pastor Sebastião Davino dos Reis\",\"display_name\":\"Avenida Pastor Sebastião Davino dos Reis, Jardim Tupanci, Vila Dom José, Aldeia, Barueri, Região Imediata de São Paulo, Região Metropolitana de São Paulo, Região Geográfica Intermediária de São Paulo, São Paulo, Região Sudeste, 06414-007, Brasil\",\"boundingbox\":[\"-23.4964913\",\"-23.4954697\",\"-46.8727498\",\"-46.8675008\"]},{\"place_id\":7264854,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":38199633,\"lat\":\"-23.4959632\",\"lon\":\"-46.8672315\",\"class\":\"highway\",\"type\":\"primary_link\",\"place_rank\":27,\"importance\":0.04006316853589684,\"addresstype\":\"road\",\"name\":\"Avenida Pastor Sebastião Davino dos Reis\",\"display_name\":\"Avenida Pastor Sebastião Davino dos Reis, Jardim Tupanci, Vila Dom José, Aldeia, Barueri, Região Imediata de São Paulo, Região Metropolitana de São Paulo, Região Geográfica Intermediária de São Paulo, São Paulo, Região Sudeste, 06414-007, Brasil\",\"boundingbox\":[\"-23.4960232\",\"-23.4957478\",\"-46.8675008\",\"-46.8669969\"]}]");
		ADDRESSES.put("Av. Paulista, São Paulo São Paulo Brazil", "[{\"place_id\":7599590,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"node\",\"osm_id\":3621336519,\"lat\":\"-23.5620546\",\"lon\":\"-46.6554004\",\"class\":\"amenity\",\"type\":\"bank\",\"place_rank\":30,\"importance\":9.99999999995449e-06,\"addresstype\":\"amenity\",\"name\":\"Banco do Brasil Estilo\",\"display_name\":\"Banco do Brasil Estilo, 1500, Avenida Paulista, Morro dos Ingleses, Bela Vista, São Paulo, Região Imediata de São Paulo, Região Metropolitana de São Paulo, Região Geográfica Intermediária de São Paulo, São Paulo, Região Sudeste, 01310-100, Brasil\",\"boundingbox\":[\"-23.5621046\",\"-23.5620046\",\"-46.6554504\",\"-46.6553504\"]}]");
		ADDRESSES.put("Av. Paulistano, São Paulo São Paulo Brazil", "[{\"place_id\":7599590,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"node\",\"osm_id\":3621336519,\"lat\":\"-23.5620546\",\"lon\":\"-46.6554004\",\"class\":\"amenity\",\"type\":\"bank\",\"place_rank\":30,\"importance\":9.99999999995449e-06,\"addresstype\":\"amenity\",\"name\":\"Banco do Brasil Estilo\",\"display_name\":\"Banco do Brasil Estilo, 1500, Avenida Paulista, Morro dos Ingleses, Bela Vista, São Paulo, Região Imediata de São Paulo, Região Metropolitana de São Paulo, Região Geográfica Intermediária de São Paulo, São Paulo, Região Sudeste, 01310-100, Brasil\",\"boundingbox\":[\"-23.5621046\",\"-23.5620046\",\"-46.6554504\",\"-46.6553504\"]}]");
	}
	
	private String findKey(String query) {
		SortedMap<String, String> differences = new TreeMap<>();
		ADDRESSES.keySet().forEach(key -> {
			var difference = StringUtils.difference(key, query);
			differences.put(key, difference);
		});
		return differences.firstKey();
	}

	@Override
	public List<Location> getLocation(String query) {
		try {
			var key = findKey(query);
			var json = ADDRESSES.get(key);
			return mapper.readValue(json.getBytes(StandardCharsets.UTF_8), mapper.getTypeFactory().constructCollectionType(List.class, Location.class));
		} catch(IOException ioe) {
			return new ArrayList<Location>();
		}
	}
}
