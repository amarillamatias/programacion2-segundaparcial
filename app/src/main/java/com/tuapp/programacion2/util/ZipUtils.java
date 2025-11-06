package com.tuapp.programacion2.util;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    public static File zipUris(Context ctx, List<Uri> uris) throws Exception {
        File zip = File.createTempFile("archivos_", ".zip", ctx.getCacheDir());
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            byte[] buf = new byte[8192];
            int idx=0;
            for (Uri uri : uris) {
                File f = com.tuapp.programacion2.util.FileUtils.copyFromUri(ctx, uri);
                try (FileInputStream fis = new FileInputStream(f)) {
                    zos.putNextEntry(new ZipEntry("file_"+ (idx++)));
                    int n; while((n=fis.read(buf))>0) zos.write(buf,0,n);
                    zos.closeEntry();
                }
            }
        }
        return zip;
    }
}
