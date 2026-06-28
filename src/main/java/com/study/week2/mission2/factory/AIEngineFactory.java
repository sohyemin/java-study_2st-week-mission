package com.study.week2.mission2.factory;


import com.study.week2.mission2.engine.AIEngine;
import com.study.week2.mission2.engine.OllamaEngine;

public class AIEngineFactory {

    public static AIEngine create(String type){

        return new OllamaEngine();

    }
}
