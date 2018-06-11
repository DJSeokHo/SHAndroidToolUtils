package com.swein.framework.module.devicestoragescanner.data;

import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * read usb, sd card storage information file
 * path is /dev/block/vold/
 */
public class MountInfo {

    private static final String EXTERNAL_STORAGE_PREFIX = "/dev/block/vold/";

    public String[] values;
    String lastPath     = "";
    String redirectPath = "";
    String lastPathComponent;
    MountType type;

    public enum MountType {
        UNKNOWN( "Unknown" ), USB( "Usb" ), SDCARD( "SdCard" );
        String value;

        MountType( String value ) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static MountInfo createMountInfoFromReadLine( String line ) {
        MountInfo mountInfo = new MountInfo();
        mountInfo.values = line.split( " " );
        return mountInfo;
    }

    public void setLastPathWithComponent( String lastPath ) {
        this.lastPath = lastPath;
        String[] comps = lastPath.split( "\\/" );
        if ( comps.length > 0 ) {
            lastPathComponent = comps[comps.length - 1];
        }
        if ( !new File( lastPath ).exists() ) {
            this.lastPath = "";
            //            redirectPath = "/storage/"+lastPathComponent;
        }
    }

    public void setType( MountType type ) {
        this.type = type;
    }

    public boolean isTypeNull() {
        return this.type == null;
    }

    public String getLastPath() {
        return lastPath;
    }

    public MountType getType() {
        return type;
    }

    public String getLastPathComponent() {
        return lastPathComponent;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public boolean isExternalStorage() {
        if ( values[0].contains( EXTERNAL_STORAGE_PREFIX ) ) {

            if ( !lastPath.contains( "/mnt/secure" )
                    && !lastPath.contains( "/mnt/asec" )
                    && !lastPath.contains( "/mnt/obb" )
                    && !lastPath.contains( "/dev/mapper" )
                    && !lastPath.contains( "tmpfs" )
                    ) {
                if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ) {
                    if ( !lastPath.equals( "/mnt/sdcard" ) ) {
                        return true;
                    }
                }
                else {
                    if ( !lastPath.contains( "/mnt/sdcard" ) ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getRealPath() {
        return lastPath;
    }

    public void setLastPath( String lastPath ) {
        this.lastPath = lastPath;
    }

    public boolean isUsb() {
        return type == MountType.USB;
    }

    public boolean isSdCard() {
        return type == MountType.SDCARD;
    }

    public String[] getMajorAndMinor() {
        String majorMinor      = values[0].substring( values[0].indexOf( EXTERNAL_STORAGE_PREFIX ) + EXTERNAL_STORAGE_PREFIX.length(), values[0].length() );
        String[] majorMinorSplit = majorMinor.split( ":" );
        return majorMinorSplit;
    }

    public String[] getMajorNumAndMinorNum() {
        String fileName = new File( values[0] ).getName();
        Log.d( "fileName", fileName );

        ArrayList< ArrayList< Integer > > digitIndexes = findDigitIndex( fileName );
        String major        = "";
        String minor        = "";
        if ( digitIndexes != null && digitIndexes.size() == 2 ) {
            major = mergeIntegerArrayToString( fileName, digitIndexes.get( 0 ) );
            minor = mergeIntegerArrayToString( fileName, digitIndexes.get( 1 ) );
        }

        if ( major.equals( "" ) || minor.equals( "" ) ) {
            return null;
        }
        return new String[]{ major, minor };
    }

    private String mergeIntegerArrayToString(String str, ArrayList< Integer > integers ) {
        String result = "";
        char[] chars  = str.toCharArray();
        for ( int num : integers ) {
            result += chars[num];
        }
        return result;
    }

    private ArrayList< ArrayList< Integer > > findDigitIndex(String str ) {
        ArrayList< ArrayList< Integer > > integers = new ArrayList<>();
        char[]                            chars    = str.toCharArray();
        for ( int i = 0; i < chars.length; ++i ) {
            if ( Character.isDigit( chars[i] ) ) {
                if ( integers.size() == 0 ) {
                    integers.add( new ArrayList< Integer >() );
                    integers.get( integers.size() - 1 ).add( i );
                }
                else {
                    ArrayList< Integer > intGroup = integers.get( integers.size() - 1 );
                    if ( intGroup.get( intGroup.size() - 1 ) == i - 1 ) {
                        intGroup.add( i );
                    }
                    else {
                        integers.add( new ArrayList< Integer >() );
                        integers.get( integers.size() - 1 ).add( i );
                    }
                }
            }
        }
        return integers;
    }

    @Override
    public String toString() {
        return "MountInfoBackup{" +
                "values=" + Arrays.toString( values ) +
                ", lastPath='" + lastPath + '\'' +
                ", type=" + type +
                '}';
    }
}
