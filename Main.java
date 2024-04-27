package com.jerin;

import com.jerin.actions.GenerateImage;
import com.jerin.actions.OpenAIModels;
import com.jerin.actions.TextToSpeech;

public class Main
{
    public static final String OPENAI_URL = "https://api.openai.com/v1/";
    public static final String API_KEY = "KEY4_OPENAI";
    public static void main( String[] args ) {
        OpenAIModels openAIModels = new OpenAIModels();
        openAIModels.listModels();

            //Pricing: $15.00 / 1M characters
        TextToSpeech textToSpeech = new TextToSpeech();
        textToSpeech.convertTextToSpeech("Hello Jerin, How are you? I hope you are fine!");

            //Pricing: $0.020 / image for DALL-E 2
        GenerateImage generateImage = new GenerateImage();
        generateImage.generateImageFromText("A french knight riding on a horse, in a forest, facing forward");
    }

}
