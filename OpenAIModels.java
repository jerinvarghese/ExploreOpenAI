package com.jerin.actions;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.jerin.Main.OPENAI_URL;
import static com.jerin.Main.API_KEY;

public class OpenAIModels {
    public void listModels() {
        URI openaiURI = URI.create(OPENAI_URL + "models");

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(openaiURI)
                .header("Authorization", "Bearer " + System.getenv(API_KEY))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
