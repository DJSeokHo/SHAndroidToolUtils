package com.swein.pattern.strategy.calculate.arithmetic.methods;

import com.swein.pattern.strategy.calculate.arithmetic.base.Arithmetic;

import java.math.BigDecimal;

/**
 * Created by seokho on 08/05/2017.
 */

public class Add implements Arithmetic {

    @Override
    public String calculateResultWithTowNumber(String number, String beNumber) {
        return new BigDecimal(number).add(new BigDecimal(beNumber)).toString();
    }
}
