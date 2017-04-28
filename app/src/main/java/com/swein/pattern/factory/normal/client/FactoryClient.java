package com.swein.pattern.factory.normal.client;


import com.swein.pattern.factory.normal.ProductFactory;
import com.swein.pattern.factory.normal.product.TargetProduct;
import com.swein.pattern.factory.normal.product.type.ProductA;
import com.swein.pattern.factory.normal.product.type.ProductB;

/**
 * Created by seokho on 27/04/2017.
 */

public class FactoryClient {

    /**
     * get product by string
     */
    public ProductA createTargetProductA() {

        ProductFactory productFactory = new ProductFactory();
        TargetProduct targetProduct = productFactory.getProduct( "ProductA" );
        targetProduct.productIntroduction();

        return (ProductA)targetProduct;

    }

    /**
     * get product by string
     */
    public ProductB createTargetProductB() {

        ProductFactory productFactory = new ProductFactory();
        TargetProduct targetProduct = productFactory.getProduct( "ProductB" );
        targetProduct.productIntroduction();

        return (ProductB)targetProduct;
    }


    /**
     *
     * @param className: get product by class name, need class name (com.xxxx.xxxx)
     * @return
     */
    public TargetProduct createTargetProduct(String className) {
        try {
            TargetProduct targetProduct = (TargetProduct) Class.forName( className ).newInstance();
            return targetProduct;
        }
        catch ( InstantiationException e ) {
            e.printStackTrace();
        }
        catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }
        catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }

        return null;
    }

}
