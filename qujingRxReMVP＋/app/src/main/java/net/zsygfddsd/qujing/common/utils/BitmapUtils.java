package net.zsygfddsd.qujing.common.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    private static final String TAG = BitmapUtils.class.getSimpleName();

    /**
     * convert Bitmap to byte array
     */
    public static byte[] bitmapToByte(Bitmap b) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * 把bitmap转换成Base64编码String
     */
    public static String bitmapToString(Bitmap bitmap) {
        return Base64.encodeToString(bitmapToByte(bitmap), Base64.DEFAULT);
    }

    /**
     * convert Drawable to Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(bitmap);
    }

    /**
     * scale image
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //paint.setColor(0xff424242);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle, int newHeight, int newWidth) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
        if (needRecycle)
            bitMap.recycle();
        return newBitMap;
    }

    public static boolean saveBitmap(Bitmap bitmap, File file) {
        if (bitmap == null)
            return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean saveBitmap(Bitmap bitmap, String absPath) {
        return saveBitmap(bitmap, new File(absPath));
    }

    public static Intent buildImageGetIntent(Uri saveTo, int outputX, int outputY, boolean returnData) {
        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
    }

    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
                                             int outputX, int outputY, boolean returnData) {
        Log.i(TAG, "Build.VERSION.SDK_INT : " + Build.VERSION.SDK_INT);
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        intent.putExtra("output", saveTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int outputX, int outputY, boolean returnData) {
        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
    }

    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
                                              int outputX, int outputY, boolean returnData) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriFrom, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uriTo);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", returnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildImageCaptureIntent(Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * reqWidth或reqHeight若为-1则不做为压缩限制
     * <p/>
     * 如: reqWidth=480,reqHeight=-1,则只要求宽必须小于480
     * reqWidth=480,reqHeight=800,则要求压缩后图片宽高必须都小于要求的宽高,即宽<480,高<800;
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int h = options.outHeight;
        int w = options.outWidth;
        int inSampleSize = 1;

        if (reqWidth != -1 && reqHeight != -1) {
            if (h > reqHeight || w > reqWidth) {
                inSampleSize = 2;
                while (w / inSampleSize >= reqWidth || h / inSampleSize >= reqHeight)//压缩后的照片必须宽高都小于要求宽高
                    inSampleSize *= 2;
            }
            return inSampleSize;

        } else if (reqWidth != -1 && reqHeight == -1) {
            if (w > reqWidth) {
                inSampleSize = 2;
                while (w / inSampleSize >= reqWidth)//压缩后的照片必须宽小于要求宽
                    inSampleSize *= 2;
            }
            return inSampleSize;

        } else if (reqWidth == -1 && reqHeight != -1) {
            if (h > reqHeight) {
                inSampleSize = 2;
                while (h / inSampleSize >= reqHeight)//压缩后的照片必须高小于要求高
                    inSampleSize *= 2;
            }
            return inSampleSize;

        } else {
            return inSampleSize;
        }

    }


    //大小压缩 px
    public static Bitmap compressBitmapWidthAndHeight(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //大小压缩 px
    public static Bitmap compressBitmapWidthAndHeight(Bitmap bitmap, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] bytes = bitmapToByte(bitmap);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 质量压缩,二分法压缩,优点是快,缺点是不能保证限制条件下的最高质量
     *
     * @param bitmap
     * @param maxQualityKB 压缩后在maxQualityKB之内
     * @return
     */
    public static byte[] compressBitmapQualityDivision(Bitmap bitmap, int maxQualityKB) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 80;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        //最大压缩到maxQualityKB或者原来质量的百分之14
        while (bytes.length / 1024 > maxQualityKB && quality > 14) {
            quality = quality / 2;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        //        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        //        Bitmap bitmap1 = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        Log.i(TAG, "After Bitmap quality compressed, size: " + baos.toByteArray().length / 1024 + "kb");
        return baos.toByteArray();
    }


    /**
     * 质量压缩,减法压缩,缺点是慢,优点是逐步靠近目标质量
     *
     * @param bitmap
     * @param maxQualityKB 压缩后在maxQualityKB之内
     * @return
     */
    public static byte[] compressBitmapQualityReduce(Bitmap bitmap, int maxQualityKB) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 80;
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        byte[] bytes = baos.toByteArray();
        //最大压缩到maxQualityKB或者原来质量的百分之20
        while (bytes.length / 1024 > maxQualityKB && quality > 20) {
            quality = quality - 10;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        }
        //        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        //        Bitmap bitmap1 = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        Log.i(TAG, "After Bitmap quality compressed, size: " + baos.toByteArray().length / 1024 + "kb");
        return baos.toByteArray();
    }

    //    public static byte[] compressBitmapQuality(String filePath,int maxQualityKB){
    //        BitmapFactory.Options options = new BitmapFactory.Options();
    //        options.inJustDecodeBounds = false;
    //        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
    //        return compressBitmapQuality(bitmap,maxQualityKB);
    //    }
    //从byte[]转file
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    //从file转为byte[]
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }


    public byte[] compressBitmapToBytes(String filePath, int reqWidth, int reqHeight, int quality) {
        Bitmap bitmap = compressBitmapWidthAndHeight(filePath, reqWidth, reqHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        bitmap.recycle();
        Log.i(TAG, "Bitmap compressed success, size: " + bytes.length);
        return bytes;
    }

    public byte[] compressBitmapSmallTo(String filePath, int reqWidth, int reqHeight, int maxLenth) {
        int quality = 100;
        byte[] bytes = compressBitmapToBytes(filePath, reqWidth, reqHeight, quality);
        while (bytes.length > maxLenth && quality > 0) {
            quality = quality / 2;
            bytes = compressBitmapToBytes(filePath, reqWidth, reqHeight, quality);
        }
        return bytes;
    }

    public byte[] compressBitmapQuikly(String filePath) {
        return compressBitmapToBytes(filePath, 480, 800, 50);
    }

    public byte[] compressBitmapQuiklySmallTo(String filePath, int maxLenth) {
        return compressBitmapSmallTo(filePath, 480, 800, maxLenth);
    }
}
