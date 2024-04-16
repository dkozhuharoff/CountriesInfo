package com.countries.info.service;

import com.countries.info.exception.FailedRestApiRequestException;
import com.countries.info.model.Country;
import com.countries.info.repository.CountryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class FetchService {
    private static final String FETCH_COUNTRY_INFO_URL = "https://restcountries.com/v3.1/name/%s?fields=name,capital,currencies";
    private RestTemplate restTemplate;
    private ObjectMapper mapper;
    @Autowired
    CountryRepository countryRepository;
    public FetchService(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }
    public String transformResponse(String response) throws JsonProcessingException {
        JsonNode rootNode = mapper.readTree(response);
        String officialName = rootNode.get(0).get("name").get("official").asText();
        JsonNode currenciesNode = rootNode.get(0).get("currencies");
        String currencyName = currenciesNode.fields().next().getValue().get("name").asText();
        String currencySymbol = currenciesNode.fields().next().getValue().get("symbol").asText();
        String capital = rootNode.get(0).get("capital").get(0).asText();

        String finalResponse = "Official name of the country " + officialName + " official currency " + currencyName + " " + currencySymbol + " capital " + capital;

        return finalResponse;
    }

    public String fetchCountriesInformation(String countryName) {
        try {
            Optional<Country> country = countryRepository.findById(countryName);
            if (!country.isEmpty() && country.get() != null && country.get().getName() != null && !country.get().getName().equals("")) {
                return country.get().getInfo();
            }

            String url = String.format(FetchService.FETCH_COUNTRY_INFO_URL, countryName);
            String response = restTemplate.getForObject(url, String.class);
            String finalResponse = transformResponse(response);
            Country newCountry = new Country(countryName, finalResponse);
            countryRepository.save(newCountry);

            return finalResponse;
        } catch (JsonProcessingException e) {
            throw new FailedRestApiRequestException("The request to fetch information about this country has failed due to problems with processing the JSON response.");
        } catch (RuntimeException e) {
            throw new FailedRestApiRequestException("The request to fetch information about this country has failed.");
        }
    }
}
