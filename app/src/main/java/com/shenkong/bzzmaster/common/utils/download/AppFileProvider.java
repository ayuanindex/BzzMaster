package com.shenkong.bzzmaster.common.utils.download;

import android.content.Context;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.shenkong.bzzmaster.BuildConfig;

import java.io.File;

public class AppFileProvider extends FileProvider {

    //public static final String AUTHORITY = "com.shenkong.bzzmaster.fileprovider";
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";

    public static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, AUTHORITY, file);
    }
}
