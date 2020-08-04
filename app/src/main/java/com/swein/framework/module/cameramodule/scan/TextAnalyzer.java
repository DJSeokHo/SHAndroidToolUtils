package com.swein.framework.module.cameramodule.scan;

import android.annotation.SuppressLint;
import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.ROTATION_0;
import static com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.ROTATION_180;
import static com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.ROTATION_270;
import static com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata.ROTATION_90;

public class TextAnalyzer implements ImageAnalysis.Analyzer {

    public interface TextAnalyzerDelegate {
        void onTextDetected(String result);
        int getRotation();
    }

    private final static String TAG = "TextAnalyzer";

    private boolean finish = false;

    private TextAnalyzerDelegate textAnalyzerDelegate;

    public TextAnalyzer(TextAnalyzerDelegate textAnalyzerDelegate) {
        this.textAnalyzerDelegate = textAnalyzerDelegate;
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {

        if(textAnalyzerDelegate == null) {
            return;
        }

        if(finish) {
            return;
        }

        @SuppressLint("UnsafeExperimentalUsageError") Image mediaImage = image.getImage();

//        val metadata = FirebaseVisionImageMetadata.Builder()
//                .setWidth((imageProxy.width * 0.5f).toInt()) // 480x360 is typically sufficient for
//                .setHeight((imageProxy.height * 0.5f).toInt()) // image recognition
//                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12)
//                .setRotation(0)
//                .build()



        if (mediaImage != null) {

            FirebaseVisionImage firebaseVisionImage;

            if(textAnalyzerDelegate.getRotation() == 0) {
                firebaseVisionImage = FirebaseVisionImage.fromMediaImage(mediaImage, ROTATION_0);
            }
            else if(textAnalyzerDelegate.getRotation() == 90) {
                firebaseVisionImage = FirebaseVisionImage.fromMediaImage(mediaImage, ROTATION_90);
            }
            else if(textAnalyzerDelegate.getRotation() == 180) {
                firebaseVisionImage = FirebaseVisionImage.fromMediaImage(mediaImage, ROTATION_180);
            }
            else if(textAnalyzerDelegate.getRotation() == 270) {
                firebaseVisionImage = FirebaseVisionImage.fromMediaImage(mediaImage, ROTATION_270);
            }
            else {
                firebaseVisionImage = FirebaseVisionImage.fromMediaImage(mediaImage, ROTATION_0);
            }

            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

            detector.processImage(firebaseVisionImage).addOnSuccessListener(firebaseVisionText -> {
                processResult(firebaseVisionText);
                image.close();
            }).addOnFailureListener(Throwable::printStackTrace);

        }
    }

    private void processResult(FirebaseVisionText result) {

        List<String> stringList = new ArrayList<>();

        List<FirebaseVisionText.TextBlock> textBlockList = result.getTextBlocks();
//        ILog.iLogDebug(TAG, result.getText());
        for(FirebaseVisionText.TextBlock textBlock : textBlockList) {
            stringList.add(textBlock.getText());
//            String blockText = textBlock.getText();

//            if(textBlock.getCornerPoints() != null) {
//            }

//            Float blockConfidence = textBlock.getConfidence();
//            ILog.iLogDebug(TAG, blockText + " blockConfidence " + blockConfidence)
//            if(isNumeric(blockText))

//            gerCardNumber(blockText)

//            val blockLanguages = block.recognizedLanguages
//            val blockCornerPoints = block.cornerPoints
//            val blockFrame = block.boundingBox
//            for (line in block.lines) {
//                val lineText = line.text
//                val lineConfidence = line.confidence
//                val lineLanguages = line.recognizedLanguages
//                val lineCornerPoints = line.cornerPoints
//                val lineFrame = line.boundingBox
//                for (element in line.elements) {
//                    val elementText = element.text
//                    val elementConfidence = element.confidence
//                    val elementLanguages = element.recognizedLanguages
//                    val elementCornerPoints = element.cornerPoints
//                    val elementFrame = element.boundingBox
//                }
//            }
        }

        getCardNumber(stringList);
    }

    private void getCardNumber(List<String> stringList) {

        List<String> twoLine = new ArrayList<>();
        String result;
        for(int i = 0; i < stringList.size(); i++) {

            result = stringList.get(i);

            if(result.contains(" ")) {
                result = result.replace(" ", "");
            }
            else if(result.contains("-")) {
                return;
            }
            else if(result.contains("/")) {
                return;
            }

            if(!isNumeric(result)) {
                // check all number
                continue;
            }

            if(result.length() == 16) {
                // check length
                result = stringList.get(i);

                String[] cardNumbers;
                if(result.contains(" ")) {
                    cardNumbers = result.split(" ");
                }
                else {
                    return;
                }

                if(cardNumbers.length == 4) {
                    finish = true;
                    textAnalyzerDelegate.onTextDetected(cardNumbers[0] + cardNumbers[1] + cardNumbers[2] + cardNumbers[3]);
                    return;
                }
            }
            else if(result.length() == 8) {
                // check length
                result = stringList.get(i);

                String[] cardNumbers;
                if(result.contains(" ")) {
                    cardNumbers = result.split(" ");
                }
                else {
                    return;
                }

                if(cardNumbers.length == 2) {

                    twoLine.add(cardNumbers[0] + " " + cardNumbers[1]);

                    if(twoLine.size() == 1) {
                        continue;
                    }
                    else  if(twoLine.size() == 2) {
                        finish = true;

                        String twoLineResult = twoLine.get(0) + " " + twoLine.get(1);
                        String[] twoLineResults = twoLineResult.split(" ");

                        if(twoLineResults.length == 4) {
                            textAnalyzerDelegate.onTextDetected(twoLineResults[0] + twoLineResults[1] + twoLineResults[2] + twoLineResults[3]);
                        }
                    }

                    return;
                }
            }
        }
    }

    private boolean isNumeric(String str) {

        if(str == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
