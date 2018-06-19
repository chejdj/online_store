package com.chejdj.online_store.tools;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class ImageTools {
	public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
		String endingpath = path + "/com.Bmob";
		File dir = new File(endingpath);
		if (!dir.exists()) {
			dir.mkdirs();//创建文件夹！！
		}
		File photoFile = new File(endingpath, photoName + ".png");//在parent的文件夹下创立文件！
		if(photoFile.exists())
			photoFile.delete();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(photoFile);
			if (photoBitmap != null) {
				if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
					fileOutputStream.flush();
					Log.e("savePhoto", "执行dir.mkdirs");
					dir.mkdirs();//创建文件夹！！
				}
				Log.e("savePhoto", "开始执行new File");
					fileOutputStream = new FileOutputStream(photoFile);
					if (photoBitmap!=null) {
						Log.e("savePhoto", "开始创建文件");
						if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
							fileOutputStream.flush();
							Log.e("savePhoto", "第一次关闭");
							fileOutputStream.close();
						}
					}
				}
		} catch (FileNotFoundException e) {
					photoFile.delete();
					e.printStackTrace();
				} catch (IOException e) {
					photoFile.delete();
					e.printStackTrace();
				} finally {
					try {
						Log.e("ImageTools", "第二次关闭");
						if(fileOutputStream!=null)
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
	}
