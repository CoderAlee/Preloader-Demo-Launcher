package org.alee.component.demo.component.b;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.alee.component.demo.capability.statement.ICalculation;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/12/15
 * @description: xxxx
 *
 *********************************************************/
@Route(path = "/component/b/provider/calculation")
public class CalculationImpl implements ICalculation {
    @Override
    public int add(int number1, int number2) {
        return number1 + number2;
    }

    @Override
    public void init(Context context) {

    }
}
