package ru.liner.facerapp.engine.file;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ru.liner.facerapp.engine.model.FileWatchface;
import ru.liner.facerapp.engine.resource.FilesystemManager;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class UnCompress {
    private static final int BUFFER = 2048;
    private String dest;
    private String mDestFolder;
    private Boolean mIsWatchMaker;
    private OnCompleteListener mListener;
    private String mZipFile;

    /* loaded from: classes2.dex */
    public interface OnCompleteListener {
        void onComplete();

        void onError();
    }

    public UnCompress(String dest, String zipFile, OnCompleteListener mListener) {
        this.mIsWatchMaker = false;
        this.mDestFolder = dest;
        this.dest = dest;
        this.mZipFile = zipFile;
        this.mListener = mListener;
        if (zipFile != null) {
            if (zipFile.endsWith(".watch")) {
                this.mIsWatchMaker = true;
                return;
            }
            return;
        }
        mListener.onError();
    }

    public void unzip() {
        if (this.dest != null) {
            File tmp = new File(this.dest);
            if (!tmp.exists()) {
                tmp.mkdirs();
            }
            File mImageDir = new File(tmp, "images");
            mImageDir.mkdirs();
            File mImageDir2 = new File(tmp, FilesystemManager.LEGACY_TYPEFACE_FOLDER);
            mImageDir2.mkdirs();
        }
        Boolean hasDescription = false;
        String resutName = "err.err";
        try {
            if (this.mZipFile == null) {
                return;
            }
            try {
                InputStream is = new FileInputStream(this.mZipFile);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
                byte[] buffer = new byte[2048];
                while (true) {
                    ZipEntry ze = zis.getNextEntry();
                    if (ze == null) {
                        break;
                    }
                    String name = ze.getName();
                    if (!name.contains(FileWatchface.PREVIEW_IMAGE) || name.equals(FilesystemManager.LEGACY_PREVIEW_FILE)) {
                        if (name.endsWith("description.json")) {
                            hasDescription = true;
                        }
                        if (ze.isDirectory()) {
                            File fmd = new File(this.mDestFolder, name);
                            fmd.mkdirs();
                        } else {
                            if (name.endsWith(".json") || name.endsWith(FilesystemManager.LEGACY_PREVIEW_FILE)) {
                                String[] finalName = name.split(File.separator);
                                resutName = finalName[finalName.length - 1];
                            } else if (name.contains(FilesystemManager.LEGACY_TYPEFACE_FOLDER)) {
                                String[] finalName2 = name.split(File.separator);
                                resutName = "fonts/" + finalName2[finalName2.length - 1];
                            } else if (name.contains("images")) {
                                String[] finalName3 = name.split(File.separator);
                                resutName = "images/" + finalName3[finalName3.length - 1];
                            }
                            File temp = new File(this.mDestFolder, resutName);
                            if (temp.isDirectory() && !temp.exists()) {
                                temp.mkdirs();
                            }
                            Log.v("Unzipdebug", "UNCOMPRESS: " + temp.getAbsolutePath());
                            FileOutputStream fout = new FileOutputStream(temp);
                            while (true) {
                                int count = zis.read(buffer);
                                if (count == -1) {
                                    break;
                                }
                                fout.write(buffer, 0, count);
                            }
                            fout.close();
                            zis.closeEntry();
                        }
                    }
                }
                zis.close();
                is.close();
                if (!hasDescription.booleanValue()) {
                    JSONObject mWatchFaceData = new JSONObject();
                    try {
                        mWatchFaceData.put(FileWatchface.TITLE, "Imported WatchFace");
                        WriteFile mWriteFile = new WriteFile();
                        File mWatchFaceInfo = new File(this.mDestFolder, "description.json");
                        mWriteFile.write(mWatchFaceInfo, mWatchFaceData.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (this.mListener == null) {
                    return;
                }
                this.mListener.onComplete();
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (!hasDescription.booleanValue()) {
                    JSONObject mWatchFaceData2 = new JSONObject();
                    try {
                        mWatchFaceData2.put(FileWatchface.TITLE, "Imported WatchFace");
                        WriteFile mWriteFile2 = new WriteFile();
                        File mWatchFaceInfo2 = new File(this.mDestFolder, "description.json");
                        mWriteFile2.write(mWatchFaceInfo2, mWatchFaceData2.toString());
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
                if (this.mListener == null) {
                    return;
                }
                this.mListener.onComplete();
            } catch (IOException e4) {
                e4.printStackTrace();
                if (!hasDescription.booleanValue()) {
                    JSONObject mWatchFaceData3 = new JSONObject();
                    try {
                        mWatchFaceData3.put(FileWatchface.TITLE, "Imported WatchFace");
                        WriteFile mWriteFile3 = new WriteFile();
                        File mWatchFaceInfo3 = new File(this.mDestFolder, "description.json");
                        mWriteFile3.write(mWatchFaceInfo3, mWatchFaceData3.toString());
                    } catch (JSONException e5) {
                        e5.printStackTrace();
                    }
                }
                if (this.mListener == null) {
                    return;
                }
                this.mListener.onComplete();
            }
        } catch (Throwable th) {
            if (!hasDescription.booleanValue()) {
                JSONObject mWatchFaceData4 = new JSONObject();
                try {
                    mWatchFaceData4.put(FileWatchface.TITLE, "Imported WatchFace");
                    WriteFile mWriteFile4 = new WriteFile();
                    File mWatchFaceInfo4 = new File(this.mDestFolder, "description.json");
                    mWriteFile4.write(mWatchFaceInfo4, mWatchFaceData4.toString());
                } catch (JSONException e6) {
                    e6.printStackTrace();
                }
            }
            if (this.mListener != null) {
                this.mListener.onComplete();
            }
            throw th;
        }
    }

    public void unzipNonWatchFace() {
        File destination = new File(this.dest);
        if (!destination.exists()) {
            destination.mkdirs();
        }
        try {
            if (this.mZipFile == null) {
                return;
            }
            try {
                InputStream is = new FileInputStream(this.mZipFile);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
                byte[] buffer = new byte[2048];
                while (true) {
                    ZipEntry ze = zis.getNextEntry();
                    if (ze == null) {
                        break;
                    }
                    String name = ze.getName();
                    if (ze.isDirectory()) {
                        File fmd = new File(this.mDestFolder + name);
                        fmd.mkdirs();
                    } else {
                        FileOutputStream fout = new FileOutputStream(this.mDestFolder + name);
                        while (true) {
                            int count = zis.read(buffer);
                            if (count == -1) {
                                break;
                            }
                            fout.write(buffer, 0, count);
                        }
                        fout.close();
                        zis.closeEntry();
                    }
                }
                zis.close();
                is.close();
                if (this.mListener == null) {
                    return;
                }
                this.mListener.onComplete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                if (this.mListener == null) {
                    return;
                }
                this.mListener.onComplete();
            } catch (IOException e2) {
                e2.printStackTrace();
                if (this.mListener == null) {
                    return;
                }
                this.mListener.onComplete();
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onComplete();
            }
            throw th;
        }
    }

}
