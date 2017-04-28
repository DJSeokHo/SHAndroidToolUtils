package com.swein.pattern.factory.normal;

import com.swein.pattern.factory.normal.product.TargetProduct;
import com.swein.pattern.factory.normal.product.type.ProductA;
import com.swein.pattern.factory.normal.product.type.ProductB;

/**
 * Created by seokho on 27/04/2017.
 */

public class ProductFactory {

    public TargetProduct getProduct(String key) {
        switch ( key ) {

            case "ProductA":
                return new ProductA();

            case "ProductB":
                return new ProductB();

            default:
                return null;

        }
    }

}
