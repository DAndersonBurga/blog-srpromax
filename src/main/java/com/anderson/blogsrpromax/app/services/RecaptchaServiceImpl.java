package com.anderson.blogsrpromax.app.services;

import com.anderson.blogsrpromax.app.controllers.response.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${secret.key}")
    private String RECAPTCHA_SECRET;
    @Override
    public boolean validateRecaptcha(String captcha) {

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();

        // Esos nombres deben estar tal cual así
        request.add("secret", RECAPTCHA_SECRET);
        request.add("response", captcha);

        RecaptchaResponse apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, request, RecaptchaResponse.class);

        if (apiResponse == null) {
            return false;
        }

        return Boolean.TRUE.equals(apiResponse.getSuccess());
    }
}
