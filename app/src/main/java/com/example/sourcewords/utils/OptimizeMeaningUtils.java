package com.example.sourcewords.utils;

public class OptimizeMeaningUtils {
    public static String OptimizeMeaning(String meaning){
        boolean isPoint = false;
        StringBuilder res = new StringBuilder();
        for(char c : meaning.toCharArray()){
            if(c == '.') isPoint = true;
            if(isPoint && Character.isLowerCase(c)){
                res.append("\n");
                isPoint = false;
            }
            res.append(String.valueOf(c));
        }
        return res.toString();
    }
}
