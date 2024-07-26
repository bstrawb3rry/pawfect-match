package com.uvt.bachelor.pawfectmatch.service;

import com.uvt.bachelor.pawfectmatch.model.GeocodingResponse;
import com.uvt.bachelor.pawfectmatch.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";

    public double[] getCoordinates(String postalCode, String city, String county, String country) {
        StringBuilder address = new StringBuilder();
        if (postalCode != null && !postalCode.isEmpty()) {
            address.append(postalCode).append(",");
        }
        if (city != null && !city.isEmpty()) {
            address.append(city).append(",");
        }
        if (county != null && !county.isEmpty()) {
            address.append(county).append(",");
        }
        if (country != null && !country.isEmpty()) {
            address.append(country);
        }

        String url = String.format(GEOCODING_API_URL, address, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);

        if (response != null && !response.getResults().isEmpty()) {
            GeocodingResult result = response.getResults().get(0);
            double latitude = result.getGeometry().getLocation().getLat();
            double longitude = result.getGeometry().getLocation().getLng();
            return new double[]{latitude, longitude};
        } else {
            throw new RuntimeException("Unable to fetch coordinates for the address: " + address);
        }
    }
}
