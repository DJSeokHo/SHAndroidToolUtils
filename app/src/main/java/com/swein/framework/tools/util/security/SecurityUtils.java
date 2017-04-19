package com.swein.framework.tools.util.security;

import com.swein.framework.tools.util.debug.log.ILog;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by seokho on 27/02/2017.
 */

public class SecurityUtils {

    /**
     * md5 encode, not good for big file
     *
     * @param str
     * @return
     */
    public static String getMD5( String str ) {
        try {

            MessageDigest md = MessageDigest.getInstance( "MD5" );

            md.update( str.getBytes() );

            //16 byte hex value
            return new BigInteger( 1, md.digest() ).toString( 16 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
            ILog.iLogError( SecurityUtils.class.getSimpleName(), "MD5 error" );
            return null;
        }
    }


}
