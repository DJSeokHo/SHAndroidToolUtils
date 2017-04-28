package com.swein.pattern.factory.normal.product.type;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.factory.normal.product.TargetProduct;

/**
 * Created by seokho on 27/04/2017.
 */

public class ProductB implements TargetProduct {

    //draw right hair style
    @Override
    public void productIntroduction() {
        ILog.iLogDebug( ProductA.class.getSimpleName(), "ProductB" );
    }
}
