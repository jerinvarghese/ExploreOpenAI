package com.jerin.conf;

public class TTSReqBody {
    private String model;
    private String input;
    private String voice;
    private String responseFormat;
    private double speed;

    public TTSReqBody(String model, String input, String voice, String responseFormat, double speed) {
        this.model = model;
        this.input = input;
        this.voice = voice;
        this.responseFormat = responseFormat;
        this.speed = speed;
    }
}
