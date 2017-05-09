package com.swein.pattern.strategy.calculate.arithmetic.calculator;

import com.swein.pattern.strategy.calculate.arithmetic.base.Arithmetic;
import com.swein.pattern.strategy.calculate.arithmetic.methods.Add;
import com.swein.pattern.strategy.calculate.arithmetic.methods.Div;
import com.swein.pattern.strategy.calculate.arithmetic.methods.Multi;
import com.swein.pattern.strategy.calculate.arithmetic.methods.Sub;

/**
 * Created by seokho on 08/05/2017.
 */

public class Calculator implements Arithmetic {

    final private String TAG = "Calculator";
    private String operate;
    private Arithmetic arithmetic;

    public Calculator() {
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    @Override
    public String calculateResultWithTowNumber(String number, String beNumber) {
        switch (operate) {
            case "+":
                arithmetic = new Add();
                return arithmetic.calculateResultWithTowNumber(number, beNumber);

            case "-":
                arithmetic = new Sub();
                return arithmetic.calculateResultWithTowNumber(number, beNumber);

            case "*":
                arithmetic = new Multi();
                return arithmetic.calculateResultWithTowNumber(number, beNumber);

            case "/":
                arithmetic = new Div();
                return arithmetic.calculateResultWithTowNumber(number, beNumber);

        }

        return "";
    }
}
