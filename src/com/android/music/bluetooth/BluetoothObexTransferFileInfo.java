package com.android.music.bluetooth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.os.Environment;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

public class BluetoothObexTransferFileInfo {
   private static final String TAG = "BluetoothObexTransferFileInfo";
   private String mName = "";
   private String mDisplayName = "";
   private long mDoneBytes = 0;
   private long mSizeBytes = 0;

   public BluetoothObexTransferFileInfo(String name) {
      mName = name;
      mDoneBytes = 0 ;
      File file = new File(name);
      mDisplayName = file.getName();
      mSizeBytes = file.length();
   }

   public String getName() {
      return mName;
   }
   public void setName(String name) {
      mName = name;
   }

   public String getDisplayName() {
      return mDisplayName;
   }
   public void setDisplayName(String name) {
      mDisplayName = name;
   }

   public String toString() {
      return getName();
   }
   public void setTotal(long total) {
      mSizeBytes = total;
   }
   public long getTotal() {
      return mSizeBytes;
   }
   public long getDone() {
      return mDoneBytes;
   }
   public void setDone(long done) {
      mDoneBytes = done;
   }

   public void deleteIfVCard(Context context) {
      /** If vcard file, delete it */
      File file = new File(mName);
      if(getExtension().equalsIgnoreCase(".vcf")) {
         try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
               if (file.exists()) {
                  file.delete();
               } else {
                  context.deleteFile(mName);
               }
            }
         } catch (SecurityException e) {
            Log.e(TAG, e.getMessage(), e);
         }
      }
   }

    public String getExtension() {
        if (mName == null) {
            return null;
        }

        int dot = mName.lastIndexOf(".");
        if (dot >= 0) {
            return mName.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

   public int getCompletePercent() {
      int percentDone = 100;
      if (mSizeBytes > 0)
      {
         percentDone =  (int)(mDoneBytes / mSizeBytes);
      }
      return(percentDone);
   }
}