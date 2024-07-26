package com.uvt.bachelor.pawfectmatch.model;

public class CountryInformation {
    private String geonameId;
    private String countryName;
    private String countryCode;

    public CountryInformation(String geonameId, String countryName, String countryCode) {
        this.geonameId = geonameId;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public String getGeonameId() {
        return geonameId;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

}
