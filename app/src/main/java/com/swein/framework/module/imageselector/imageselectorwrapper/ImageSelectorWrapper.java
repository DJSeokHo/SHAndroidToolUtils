package com.swein.framework.module.imageselector.imageselectorwrapper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.swein.framework.module.imageselector.selector.adapter.item.ImageFolderItemBean;
import com.swein.framework.module.imageselector.selector.adapter.item.ImageSelectorItemBean;
import com.swein.framework.tools.util.thread.ThreadUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageSelectorWrapper {

    private static ImageSelectorWrapper instance = new ImageSelectorWrapper();
    public static ImageSelectorWrapper getInstance() {
        return instance;
    }

    private ImageSelectorWrapper() {}

    public interface ImageSelectorWrapperDelegate {
        void onSuccess(List<ImageFolderItemBean> imageFolderItemBeans, List<ImageSelectorItemBean> imageSelectorItemBeanList);
        void onError();
    }

    public void scanImageFile(Context context, ImageSelectorWrapperDelegate imageSelectorWrapperDelegate) {

        List<ImageFolderItemBean> imageFolderItemBeanList = new ArrayList<>();
        List<ImageSelectorItemBean> imageSelectorItemBeanList = new ArrayList<>();

        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // storage error
            imageSelectorWrapperDelegate.onError();
            return;
        }

        ThreadUtil.startThread(() -> {

            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = context.getContentResolver();

            Cursor cursor = contentResolver.query(uri, null,
                    MediaStore.Images.Media.MIME_TYPE + " = ? or " +
                            MediaStore.Images.Media.MIME_TYPE + " = ? or " +
                            MediaStore.Images.Media.MIME_TYPE + " = ? ",
                    new String[]{"image/jpeg", "image/jpg", "image/png"},
                    MediaStore.Images.Media.DATE_MODIFIED);

            if(cursor == null) {
                return;
            }

            Set<String> dirPaths = new HashSet<>();
            int count = cursor.getCount();

            File parentFile;
            for(int i = count - 1; i >= 0; i--) {

                cursor.moveToPosition(i);
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                String dirPath;
                String dirName;
                parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    // if path is top, than have no parent folder
                    dirPath = "";
                    dirName = "";
                }
                else {
                    dirPath = parentFile.getAbsolutePath();
                    dirName = parentFile.getName();
                }

                if(i == count - 1) {
                    ImageFolderItemBean imageFolderItemBean = new ImageFolderItemBean();
                    imageFolderItemBean.dirName = "";
                    imageFolderItemBean.dirPath = "";
                    imageFolderItemBean.isAll = true;
                    imageFolderItemBean.isSelected = true;
                    imageFolderItemBeanList.add(imageFolderItemBean);
                }

                ImageSelectorItemBean imageSelectorItemBean = new ImageSelectorItemBean();
                imageSelectorItemBean.dirPath = dirPath;
                imageSelectorItemBean.filePath = path;
                imageSelectorItemBean.isSelected = false;

                imageSelectorItemBeanList.add(imageSelectorItemBean);

                if(dirPath.equals("")) {
                    continue;
                }

                ImageFolderItemBean imageFolderItemBean;
                if (dirPaths.contains(dirPath)) {
                    continue;
                }
                else {
                    dirPaths.add(dirPath);
                    imageFolderItemBean = new ImageFolderItemBean();

                    imageFolderItemBean.dirPath = dirPath;
                    imageFolderItemBean.dirName = dirName;
                    imageFolderItemBean.isAll = false;
                    imageFolderItemBean.isSelected = false;
                    imageFolderItemBeanList.add(imageFolderItemBean);
                }

                if (parentFile.list() == null)
                    continue;

                imageFolderItemBean.size = parentFile.list((file, s) -> {
                    // FilenameFilter
                    return s.endsWith(".jpg") || s.endsWith(".jpeg") || s.endsWith(".png");
                }).length;
            }

            cursor.close();

            ThreadUtil.startUIThread(0, () -> imageSelectorWrapperDelegate.onSuccess(imageFolderItemBeanList, imageSelectorItemBeanList));

        });
    }

}
