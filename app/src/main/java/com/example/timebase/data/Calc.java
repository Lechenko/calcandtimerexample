package com.example.timebase.data;

import com.example.timebase.data.model.PairObject;
import com.example.timebase.util.Utils;
import java.util.List;

import io.reactivex.Observable;

import static com.example.timebase.common.Constans.NOT_ZERO;

public class Calc {
    private static Calc instance;

    private Calc() {
    }

    public static synchronized Calc getInstance() {
        if (instance == null) {
            instance = new Calc();
        }
        return instance;
    }


    public Observable<String> calc(String val) {
        return Observable.just(val)
                .flatMap(Utils::parserCalc)
                .flatMap(this::preparationCalculations);
    }

    private Observable<String> preparationCalculations(PairObject<List<PairObject<PairObject<String, Boolean>, PairObject<String, String>>>, String> pair) {
        String value = "";
        if(pair.getLeft().size() >=3 ) {
            if (pair.getLeft().get(0).getLeft().getRight() || pair.getLeft().get(2).getLeft().getRight()) {
                value = calculations(pair.getLeft().get(0).getRight().getLeft(),
                        pair.getLeft().get(1).getRight().getLeft(),
                        pair.getLeft().get(2).getRight().getLeft(),
                        true);
            } else {
                value = calculations(pair.getLeft().get(0).getRight().getLeft(),
                        pair.getLeft().get(1).getRight().getLeft(),
                        pair.getLeft().get(2).getRight().getLeft(),
                        false);
            }
            String oper = pair.getRight().substring(pair.getRight().length() - 1);
            if (NOT_ZERO.equals(value)) {

            }
            if (!oper.equals("=") && !NOT_ZERO.equals(value)) {
                value = value.concat(pair.getLeft().get(3).getRight().getLeft());
            }
            return Observable.just(value);
        }
        return Observable.empty();
    }

    private String calculations(String numA, String oper, String numB, boolean isNumberDouble) {
        switch (oper) {
            case "+" :
                if (isNumberDouble){
                 return String.valueOf((Double.valueOf(numA) + Double.valueOf(numB)));
                }else {
                    return String.valueOf((Long.valueOf(numA) + Long.valueOf(numB)));
                }
            case "-" :
                if (isNumberDouble){
                    return String.valueOf((Double.valueOf(numA) - Double.valueOf(numB)));
                }else {
                    return String.valueOf((Long.valueOf(numA) - Long.valueOf(numB)));
                }
            case "/" :
                if(Long.valueOf(numB) == 0){
                    return NOT_ZERO;
                }
                if (isNumberDouble){
                    return String.valueOf((Double.valueOf(numA) / Double.valueOf(numB)));
                }else {
                    if ((Long.valueOf(numA) < Long.valueOf(numB)) || (Long.valueOf(numA) % Long.valueOf(numB) != 0)){
                        return String.valueOf((Double.valueOf(numA) / Double.valueOf(numB)));
                    }
                    return String.valueOf((Long.valueOf(numA) / Long.valueOf(numB)));
                }
            case "*" :
                if (isNumberDouble){
                    return String.valueOf((Double.valueOf(numA) * Double.valueOf(numB)));
                }else {
                    return String.valueOf((Long.valueOf(numA) * Long.valueOf(numB)));
                }
                default: return "";

        }
    }
}
