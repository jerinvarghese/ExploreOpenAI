package com.jerin.conf;

public class DallEReqBody {
    private String prompt;
    private String model;
    private int n;
    private String quality;
    private String responseFormat;
    private String size;
    private String style;
    private String user;

    public DallEReqBody(String prompt, String model, int n, String quality, String responseFormat, String size, String style, String user) {
        this.prompt = prompt;
        this.model = model;
        this.n = n;
        this.quality = quality;
        this.responseFormat = responseFormat;
        this.size = size;
        this.style = style;
        this.user = user;
    }
}
