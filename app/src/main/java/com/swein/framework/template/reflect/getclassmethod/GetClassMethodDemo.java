package com.swein.framework.template.reflect.getclassmethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GetClassMethodDemo {

    public GetClassMethodDemo(Object object) {

        /* get class */
        Class c = object.getClass();

        /* get name */
        System.out.println(c.getName());

        /* get public methods */
        Method[] publicMethods = c.getMethods();
        for (Method publicMethod : publicMethods) {

            /* get return type of method */
            Class returnTypeOfPublicMethod = publicMethod.getReturnType();
            System.out.print(returnTypeOfPublicMethod.getName() + " ");

            /* get method name */
            System.out.print(publicMethod.getName() + "(");

            /* get parameters type of method */
            Class[] paramTypes = publicMethod.getParameterTypes();
            for(Class cls : paramTypes) {
                System.out.print(cls.getName() + ",");
            }
            System.out.println(")");
        }

        /* get all(public & private) methods */
        Method[] allMethods = c.getDeclaredMethods();
        for (Method allMethod : allMethods) {

            /* get return type of method */
            Class returnTypeOfAllMethod = allMethod.getReturnType();
            System.out.print(returnTypeOfAllMethod.getName() + " ");

            /* get method name */
            System.out.print(allMethod.getName() + "(");

            /* get parameters type of method */
            Class[] paramTypes = allMethod.getParameterTypes();
            for(Class cls : paramTypes) {
                System.out.print(cls.getName() + ",");
            }
            System.out.println(")");
        }

        /* get constructor methods */
        Constructor[] constructors = c.getConstructors();
        for(Constructor constructor : constructors) {
            System.out.println(constructor.getName());
            Class[] paramTypes = constructor.getParameterTypes();
            for(Class cls : paramTypes) {
                System.out.println(cls.getName());
            }
        }
    }
}
