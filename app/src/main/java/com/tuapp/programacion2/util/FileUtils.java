package com.tuapp.programacion2.util;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {
    public static File createTempImage(Context ctx){
        File dir = new File(ctx.getCacheDir(), "images");
        if(!dir.exists()) dir.mkdirs();
        return new File(dir, "img_"+ UUID.randomUUID()+".jpg");
    }
    public static MultipartBody.Part toPart(File file, String name){
        RequestBody rb = RequestBody.create(file, MediaType.parse("image/jpeg"));
        return MultipartBody.Part.createFormData(name, file.getName(), rb);
    }
    public static MultipartBody.Part toZipPart(File file, String name){
        RequestBody rb = RequestBody.create(file, MediaType.parse("application/zip"));
        return MultipartBody.Part.createFormData(name, file.getName(), rb);
    }
    public static Uri getUriForFile(Context ctx, File f, String authority){
        return FileProvider.getUriForFile(ctx, authority, f);
    }
    public static File copyFromUri(Context ctx, Uri uri) throws Exception {
        File out = File.createTempFile("pick_", ".bin", ctx.getCacheDir());
        try (InputStream is = ctx.getContentResolver().openInputStream(uri);
             FileOutputStream os = new FileOutputStream(out)) {
            byte[] buf = new byte[8192]; int n;
            while((n=is.read(buf))>0) os.write(buf,0,n);
        }
        return out;
    }
}
