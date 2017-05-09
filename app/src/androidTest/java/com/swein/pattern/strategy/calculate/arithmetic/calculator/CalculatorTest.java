package com.swein.pattern.strategy.calculate.arithmetic.calculator;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.MainActivity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by seokho on 09/05/2017.
 */
public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void setAndCalculate() {
        addTest();
        subTest();
        multiTest();
        divTest();
    }

    public void addTest() {
        calculator.setOperate("+");
        assertEquals("3", calculator.calculateResultWithTowNumber("1", "2"));
        ILog.iLogDebug(MainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
    }

    public void subTest() {
        calculator.setOperate("-");
        assertEquals("-1", calculator.calculateResultWithTowNumber("1", "2"));
        ILog.iLogDebug(MainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
    }

    public void multiTest() {
        calculator.setOperate("*");
        assertEquals("2", calculator.calculateResultWithTowNumber("1", "2"));
        ILog.iLogDebug(MainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
    }

    public void divTest() {
        calculator.setOperate("/");
        assertEquals("0.5", calculator.calculateResultWithTowNumber("1", "2"));
        ILog.iLogDebug(MainActivity.class.getSimpleName(), calculator.calculateResultWithTowNumber("1", "2"));
    }

}