package com.example.myrestfulservice.helloWord.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloWorldBean {
    private String message;

//    public HelloWorldBean(String message) {
//        this.message = message;
//    }
}