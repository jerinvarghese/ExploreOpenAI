package com.jerin.actions;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jerin.conf.DallEReqBody;
import com.jerin.conf.DallEResBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;

import static com.jerin.Main.API_KEY;
import static com.jerin.Main.OPENAI_URL;

public class GenerateImage {

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    public void generateImageFromText(String prompt) {
        URI openaiDallE = URI.create(OPENAI_URL + "images/generations");
        HttpClient httpClient = HttpClient.newHttpClient();

        DallEReqBody dallEReqBody = new DallEReqBody(prompt, "dall-e-2", 1, null, "b64_json", "1024x1024", null, null);
        String dallEBodyPublisher = gson.toJson(dallEReqBody);
        System.out.println(dallEBodyPublisher);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(openaiDallE)
                .header("Authorization", "Bearer " + System.getenv(API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(dallEBodyPublisher))
                .build();
        try {
            HttpResponse<String> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(httpResponse.statusCode());
            if (httpResponse.statusCode()==200) {
                processDallEResponse(httpResponse);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void processDallEResponse(HttpResponse<String> httpResponse){
        try {
            DallEResBody dallEResBody = gson.fromJson(httpResponse.body(), DallEResBody.class);
            List<DallEResBody.DallEImageString> imageStrings = dallEResBody.getData();
            if (imageStrings.size() > 0) {
                String fileName = "C:\\Users\\jerin\\OneDrive\\Documents\\Learning\\Java\\Java21\\ExploreOpenAI\\src\\main\\resources\\ImageFile.png";
                Path filePath = Paths.get(fileName);
                if (Files.deleteIfExists(filePath))
                    System.out.println("Existing image file deleted");
                DallEResBody.DallEImageString firstImageString = imageStrings.get(0);
                byte[] imageAsBytes = Base64.getDecoder().decode(firstImageString.getB64_json());
                Files.write(filePath, imageAsBytes, StandardOpenOption.CREATE_NEW);
                System.out.println("Image file created");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}