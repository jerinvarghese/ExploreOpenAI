package com.jerin.actions;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jerin.conf.TTSReqBody;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.jerin.Main.API_KEY;
import static com.jerin.Main.OPENAI_URL;

public class TextToSpeech {
    public void convertTextToSpeech(String text){
        URI openaiTTS = URI.create(OPENAI_URL + "audio/speech");
        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        TTSReqBody ttsReqBody = new TTSReqBody("tts-1", text, "fable", "wav", 1.0);
        String ttsBodyPublisher = gson.toJson(ttsReqBody);
        System.out.println(ttsBodyPublisher);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(openaiTTS)
                .header("Authorization", "Bearer " + System.getenv(API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(ttsBodyPublisher))
                .build();

        try {
            HttpResponse<byte[]> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            System.out.println(httpResponse.statusCode());
            if (httpResponse.statusCode()==200) {
                byte[] resonseBody = httpResponse.body();
                String fileName = "C:\\Users\\jerin\\OneDrive\\Documents\\Learning\\Java\\Java21\\ExploreOpenAI\\src\\main\\resources\\SoundFile.wav";
                Path filePath = Paths.get(fileName);
                if (Files.deleteIfExists(filePath))
                    System.out.println("Existing sound file deleted");
                Files.write(filePath, resonseBody, StandardOpenOption.CREATE_NEW);
                System.out.println("Sound file created");
                //playAudio(fileName);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void playAudio(String fileName){
        File file = new File(fileName);

        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(5);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
