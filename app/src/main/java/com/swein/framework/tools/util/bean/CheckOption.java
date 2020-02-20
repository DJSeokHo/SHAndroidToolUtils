package com.swein.framework.tools.util.bean;

import java.util.HashMap;

public class CheckOption {

    private HashMap<String, String> hashMap = new HashMap<>();

    private CheckOption(Builder builder) {
        this.hashMap = builder.hashMap;
    }

    public HashMap<String, String> getCheckOption() {
        return this.hashMap;
    }

    public static class Builder {

        private HashMap<String, String> hashMap = new HashMap<>();

        public Builder() {

        }

        public CheckOption build(){
            return new CheckOption(this);
        }

        public Builder addCheckValue(String field, String value) {
            this.hashMap.put(field, value);
            return this;
        }
    }
}
