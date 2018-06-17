package com.huang.samplematerialdesign;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by huang on 2018/6/14.
 */

public class FileInfo {
    private Context mContext;
    private File mFile;
    private Drawable mFileTypeImage;
    private boolean mIsFolder;
    private String mFileName;
    private String mFileSize;
    private String mFileLastModifiedDate;
    private String mFileLastModifiedTime;
    private String mFileAbsolutePath;
    private String mParentFileAbsolutePath;

    public FileInfo(Context context, String path){
        if(path != null){
            mContext = context;
            mFile = new File(path);
            mFileAbsolutePath = path;
            mFileName = mFile.getName();
            mIsFolder = mFile.isDirectory();
            mFileLastModifiedDate = getLastModifiedDate();
            mFileLastModifiedTime = getLastModifiedTime();
            if(mIsFolder) {
//                mFileSize = getChildFilesCount(context) + "";
                mFileTypeImage = mContext.getResources().getDrawable(R.drawable.file_type_folder, null);
            }else{
                mFileTypeImage = mContext.getResources().getDrawable(R.drawable.file_type_unknow, null);
//                mFileSize = OtherUtils.sizeToHumanString(mFile.length());
//                String mimeType = getMimeType();
//
//                if(OtherUtils.getRegisterMimeTypeMap().containsKey(mimeType)){
//                    mFileTypeImage = mContext.getResources().getDrawable(OtherUtils.getRegisterMimeTypeMap().get(mimeType), null);
//                }else if("application/vnd.android.package-archive".equals(mimeType)){
//                    // 解析APK，获得APK图标
//                    mFileTypeImage = getAPKIcon(mFileAbsolutePath);
//                }else {
//                    mFileTypeImage = mContext.getResources().getDrawable(R.drawable.file_type_unknow, null);
//                }
            }

            mParentFileAbsolutePath = getParentPath();
            //mIsChecked = false;
        }
    }

    private Drawable getAPKIcon(String apkPath){
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);

        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                //Log.e(TAG, "OutOfMemory when getAPKIcon:" + e.toString());
            }
        }

        return null;
    }


    // 是否为隐藏文件
    public boolean isHideFileType(){
        return mFileName.startsWith(".");
    }

    public Drawable getFileTypeImage() {
        return mFileTypeImage;
    }

    public File getFile(){
        return mFile;
    }

    public String getFileAbsolutePath() {
        return mFileAbsolutePath;
    }

    public String getParentFileAbsolutePath() {
        return mParentFileAbsolutePath;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getSuffix(){
        return mFileName.substring(mFileName.lastIndexOf("."), mFileName.length());
    }

//    public String getHumanSize(){
//        if(isFolder()){
//            return getFolderSizeHuman();
//        }else{
//            return getFileSize();
//        }
//    }
//
//    public long getMachineSize(){
//        if(isFolder()){
//            return getFolderSize(mFile);
//        }else{
//            return mFile.length();
//        }
//    }

//    private String getFileSize() {
//        return mFileSize;
//    }
//
//    private long getFolderSize(File file){
//        if (file.exists()) {
//            //如果是目录则递归计算其内容的总大小
//            if (file.isDirectory()) {
//                File[] children = file.listFiles();
//                long size = 0;
//                for (File f : children) {
//                    if(f.getName().startsWith(".") && !PreferenceUtils.getShowHideFileValue(mContext))
//                        break;
//                    size += getFolderSize(f);
//                }
//                return size;
//            } else {
//                if(file.getName().startsWith(".") && !PreferenceUtils.getShowHideFileValue(mContext)){
//                    return 0;
//                }else {
//                    return file.length();
//                }
//            }
//        }else{
//            return -1;
//        }
//    }

//    private String getFolderSizeHuman(){
//        return OtherUtils.sizeToHumanString(getFolderSize(mFile));
//    }
//
//    public boolean isFolder() {
//        return mIsFolder;
//    }

    public String getFileLastModifiedDate() {
        return mFileLastModifiedDate;
    }

    public String getFileLastModifiedTime() {
        return mFileLastModifiedTime;
    }

//    public void setChecked(boolean isChecked){
//        mIsChecked = isChecked;
//    }
//
//    public boolean isChecked(){
//        return mIsChecked;
//    }

    /**
     * 获取文件夹中包含多少子文件
     */
//    public int getChildFilesCount(Context context){
//        boolean canShowHideFile = PreferenceUtils.getShowHideFileValue(context);
//
//        if(canShowHideFile){
//            return mFile.list().length;
//        }else {
//            int length = 0;
//            for (String file : mFile.list()) {
//                if (file.startsWith(".")) {
//                    continue;
//                }
//                length++;
//            }
//            return length;
//        }
//    }

    /**
     * 获取指定路径的父目录
     */
    private String getParentPath() {
        int sepIndex = mFileAbsolutePath.lastIndexOf("/");
        if (sepIndex >= 0) {
            return mFileAbsolutePath.substring(0, sepIndex);
        }
        return "";
    }

    /**
     * 获取文件/文件夹的最后修改日期
     */
    private String getLastModifiedDate(){
        long lastChangeTime = mFile.lastModified();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return dateFormat.format(lastChangeTime);
    }

    /**
     * 获取文件/文件夹的最后修改时间
     */
    private String getLastModifiedTime(){
        long lastChangeTime = mFile.lastModified();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return dateFormat.format(lastChangeTime);
    }

    /*
     * 根据文件名（包含路径）获取文件的MIME类型
     */
    public String getMimeType(){
        if((null == mFileAbsolutePath) || (mFileAbsolutePath.endsWith(".")) || (!mFileAbsolutePath.contains("."))){
            return null;
        }

        int index = mFileAbsolutePath.lastIndexOf(".");
        if (index != -1) {
            String extension = mFileAbsolutePath.substring(index + 1).toLowerCase(Locale.US);
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        return null;
    }
}
