package com.uvt.bachelor.pawfectmatch.api;

import com.uvt.bachelor.pawfectmatch.model.CountryInformation;
import com.uvt.bachelor.pawfectmatch.service.GeoDataFetcher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    private final GeoDataFetcher geoDataFetcher;

    public LocationController(GeoDataFetcher geoDataFetcher) {
        this.geoDataFetcher = geoDataFetcher;
    }

    @GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryInformation>> getCountries() {
        return ResponseEntity.ok(geoDataFetcher.getCountries());
    }

    @GetMapping(value = "/counties/{geonameId}")
    public ResponseEntity<Map<String, String>> getCounties(@PathVariable("geonameId") String geonameId) {
        return ResponseEntity.ok(geoDataFetcher.getCounties(geonameId));
    }

    @GetMapping(value = "/cities/{country}/{adminCode}")
    public ResponseEntity<List<String>> getCities(@PathVariable("country") String country, @PathVariable("adminCode") String adminCode) {
        return ResponseEntity.ok(geoDataFetcher.getCities(country, adminCode));
    }
}
