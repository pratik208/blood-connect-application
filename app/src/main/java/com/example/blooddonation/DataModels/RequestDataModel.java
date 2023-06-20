package com.example.blooddonation.DataModels;

import java.lang.reflect.Constructor;

public class RequestDataModel {
    private String number;
    private String message;

    public RequestDataModel(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }
}

