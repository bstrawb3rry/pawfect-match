package com.uvt.bachelor.pawfectmatch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uvt.bachelor.pawfectmatch.model.CountryInformation;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeoDataFetcher {

    @Value("${geonames.username}")
    private String geonamesUsername;

    private static final String GEONAMES_URL = "http://api.geonames.org/";

    public List<CountryInformation> getCountries() {
        String url = GEONAMES_URL + "countryInfoJSON?username=" + geonamesUsername;
        try {
            return extractCountryInformation(fetchJson(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getCounties(String geonameId) {
        String url = GEONAMES_URL + "childrenJSON?geonameId=" + geonameId + "&username=" + geonamesUsername;
        try {
            return extractCountyMap(fetchJson(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getCities(String country, String adminCode) {
        String url = GEONAMES_URL + "searchJSON?formatted=true&country=" + country + "&adminCode1=" + adminCode + "&username=" + geonamesUsername;
        try {
            return extractCityNames(fetchJson(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode fetchJson(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(result);
            }
        }
    }
        private List<CountryInformation> extractCountryInformation(JsonNode jsonNode) {
            List<CountryInformation> countryInformationList = new ArrayList<>();
            JsonNode geonames = jsonNode.path("geonames");
            if (geonames.isArray()) {
                for (JsonNode country : geonames) {
                    String geonameId = country.path("geonameId").asText();
                    String countryName = country.path("countryName").asText();
                    String countryCode = country.path("countryCode").asText();
                    if (!geonameId.isEmpty() && !countryName.isEmpty() && !countryCode.isEmpty()) {
                        countryInformationList.add(new CountryInformation(geonameId, countryName, countryCode));
                    }
                }
            }
            return countryInformationList;
        }

    private Map<String, String> extractCountyMap(JsonNode jsonNode) {
        Map<String, String> countryMap = new HashMap<>();
        JsonNode geonames = jsonNode.path("geonames");
        if (geonames.isArray()) {
            for (JsonNode county : geonames) {
                String adminCode1 = county.path("adminCode1").asText();
                String toponymName = county.path("toponymName").asText();
                if (!adminCode1.isEmpty() && !toponymName.isEmpty()) {
                    countryMap.put(adminCode1, toponymName);
                }
            }
        }
        return countryMap;
    }

    private List<String> extractCityNames(JsonNode jsonNode) {
        List<String> cityList = new ArrayList<>();
        JsonNode geonames = jsonNode.path("geonames");
        if (geonames.isArray()) {
            for (JsonNode city : geonames) {
                String name = city.path("name").asText();
                if (!name.isEmpty()) {
                    cityList.add(name);
                }
            }
        }
        return cityList;
    }
}