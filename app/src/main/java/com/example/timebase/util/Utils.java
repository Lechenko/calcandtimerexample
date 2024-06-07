package com.example.timebase.util;

import com.example.timebase.data.model.PairObject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.reactivex.Observable;
import static com.example.timebase.common.Constans.*;

public class Utils {


    public static Observable<PairObject<List<PairObject<PairObject<String,Boolean>,PairObject<String,String>>>,String>> parserCalc(String val) {
        PairObject<List<PairObject<PairObject<String,Boolean>,PairObject<String,String>>>,String> pair = new PairObject<>();
        pair.setRight(val);
        List<PairObject<PairObject<String,Boolean>,PairObject<String,String>>> list = new ArrayList<>();
        if (val != null && val.length() != 0) {
            Matcher matcher = Pattern.compile(PATTERN_PARSER_CALC).matcher(val);
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    String groupVal = matcher.group(GROUP_PARSE);
                    list.add(new PairObject<>(new PairObject<>(checkOperator(groupVal),isNumberDouble(groupVal))
                            ,new PairObject<>(groupVal,val)));
                }
            }
            pair.setLeft(list);

            return Observable.just(pair);
        } else {
            return Observable.empty();
        }
    }

    private static String checkOperator(String val) {
        return Pattern.compile(PATTERN_OPER).matcher(val).matches() ? TAG_OPER : TAG_NUMBER;
    }

    private static boolean isNumberDouble(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public static boolean checOper(String val){
        String value ="";
        if (val != null && val.length() > 1){
            value = val.substring(1);
        }else {
            value = val != null ? val : "";
        }
        if(!value.isEmpty()) {
            int count = 0;
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == '=' ||
                        value.charAt(i) == '+' ||
                        value.charAt(i) == '-' ||
                        value.charAt(i) == '*' ||
                        value.charAt(i) == '/') {
                    ++count;
                    if(count == 1){
                        if(value.charAt(i) == '='){
                           return false;
                        }
                    }
                }
            }
            if (count == 0) {
                ++count;
            }
            if ((count % 2) == 0) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static PairObject<PairObject<Boolean,String>,PairObject<String,String>> notDubbing(String globalVal,String val) {
        int count = 0;
        if (globalVal != null && !globalVal.isEmpty()) {
            String vA = globalVal.substring(globalVal.length() - 1);
            String valA = checkOperator(vA);
            String valB = checkOperator(val);
            if(valA.equals(TAG_OPER) && valB.equals(TAG_OPER)){
                return new PairObject<>(new PairObject<>(true,DUBBING),new PairObject<>("",""));
            }
            if(vA.equals(".") && val.equals(".")){
                return new PairObject<>(new PairObject<>(true,DUBBING),new PairObject<>("",""));
            }

            String checVal = globalVal.concat(val);
            for (int i = 0; i < checVal.length(); i++) {
                if (checVal.charAt(i) == '=' ||
                        checVal.charAt(i) == '+' ||
                        checVal.charAt(i) == '-' ||
                        checVal.charAt(i) == '*' ||
                        checVal.charAt(i) == '/') {
                    ++count;
                    if(count == 1){
                        if(checVal.charAt(i) == '='){
                            return new PairObject<>(new PairObject<>(true,CANCEL),new PairObject<>("",""));
                        }
                    }
                }
            }
//            if (globalVal.length() > 2){
//                for (int i = 1; i < globalVal.length(); i++) {
//                    if(checVal.charAt(i-1) == '=' ||
//                            checVal.charAt(i-1) == '+' ||
//                            checVal.charAt(i-1) == '-' ||
//                            checVal.charAt(i-1) == '*' ||
//                            checVal.charAt(i-1) == '/') {
//                        if(checVal.charAt(i) == '.'){
//                            if(val.equals("=") ||
//                            val.equals("-") ||
//                            val.equals("*") ||
//                            val.equals("/")){
//                                return new PairObject<>(true,DUBBING);
//                            }
//                        }
//                    }
//                }
//            }

            return new PairObject<>(new PairObject<>(false,val),new PairObject<>(globalVal,val));
        }else {
            return new PairObject<>(new PairObject<>(false,val),new PairObject<>("",""));
        }
    }
}