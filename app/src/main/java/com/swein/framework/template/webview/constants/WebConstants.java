package com.swein.framework.template.webview.constants;

/**
 * Created by seokho on 24/01/2018.
 */
public class WebConstants {

    public final static String COLON_SEPARATOR = ":";
    public final static String QUESTION_MARK = "?";

    /**
     * 이미지 파일 요청
     *
     * native web view has a url length limit (2MB)
     * so you need compress your file before you get base64 string from it
     *
     * @param base64File
     * @return
     */
    public static String requestSetImage(String base64File) {
        return String.format("javascript:nativeCamera.sendImage( '%s' )", base64File);
    }


    public static String getNativeFuncNotExistExceptionUrl(String errorCode, String errorMsg, String nativeFuncName) {
        return String.format("javascript:nativeFuncNotExistException" + "( '%s', '%s', '%s' )", errorCode, errorMsg, nativeFuncName);
    }
}
