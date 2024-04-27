package com.jerin.conf;

import java.util.List;

public class DallEResBody {
    private Long created;
    private List<DallEImageString> data;

    public DallEResBody(Long created, List<DallEImageString> data) {
        this.created = created;
        this.data = data;
    }

    public List<DallEImageString> getData() {
        return data;
    }

    public class DallEImageString{
        private String b64_json;

        public DallEImageString(String b64_json) {
            this.b64_json = b64_json;
        }

        public String getB64_json() {
            return b64_json;
        }
    }
}
