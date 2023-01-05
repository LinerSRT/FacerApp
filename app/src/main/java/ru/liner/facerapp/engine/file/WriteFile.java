package ru.liner.facerapp.engine.file;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class WriteFile {
    public static void deleteDirectory(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                File child = new File(dir, aChildren);
                if (child.isDirectory()) {
                    deleteDirectory(child);
                    child.delete();
                } else {
                    child.delete();
                }
            }
            dir.delete();
        }
    }

    public void write(File mFile, Bitmap mBitmap) {
        if (mFile != null && mBitmap != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(mFile);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void write(File mFile, File mOldFile) {
        if (mFile != null && mOldFile != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(mFile);
                FileInputStream inputStream = new FileInputStream(mOldFile);
                byte[] buf = new byte[(int) mOldFile.length()];
                while (true) {
                    int c = inputStream.read(buf, 0, buf.length);
                    if (c > 0) {
                        outputStream.write(buf, 0, c);
                        outputStream.flush();
                    } else {
                        outputStream.close();
                        inputStream.close();
                        return;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void write(File mFile, byte[] data) {
        if (mFile != null && data != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(mFile);
                outputStream.write(data, 0, data.length);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
            }
            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
            }
            return;
        }
        File directory = targetLocation.isDirectory() ? targetLocation : targetLocation.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            throw new IOException("Cannot create dir " + directory.getAbsolutePath());
        }
        InputStream in = new FileInputStream(sourceLocation);
        OutputStream out = new FileOutputStream(targetLocation);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    public void write(File mFile, String mText) {
        if (mFile != null && mText != null) {
            try {
                OutputStream mOutPutStream = new FileOutputStream(mFile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mOutPutStream);
                outputStreamWriter.write(mText);
                outputStreamWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

}
