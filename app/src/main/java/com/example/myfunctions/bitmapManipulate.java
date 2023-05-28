package com.example.myfunctions;

import android.graphics.Bitmap;
import android.graphics.Color;

public class bitmapManipulate {

    public static Bitmap chopInvisible(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        int top = height;
        int bottom = 0;
        int left = width;
        int right = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = source.getPixel(x, y);
                if (Color.alpha(pixel) > 50) {
                    if (x < left) { left = x; }
                    if (x > right) { right = x; }
                    if (y < top) { top = y; }
                    if (y > bottom) { bottom = y; }
                }
            }
        }
        Bitmap crop = Bitmap.createBitmap(source, left, top, right - left + 1, bottom - top + 1);
        return crop;
    }

    public static void findCropSize(Bitmap source){
        int width = source.getWidth();
        int height = source.getHeight();
        int top = height;
        int bottom = 0;
        int left = width;
        int right = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = source.getPixel(x, y);
                if (Color.alpha(pixel) > 50) {
                    if (x < left) { left = x; }
                    if (x > right) { right = x; }
                    if (y < top) { top = y; }
                    if (y > bottom) { bottom = y; }
                }
            }
        }
        String s = String.format("%d, %d, %d, %d", left, top, right, bottom);
        System.out.println(s);
    }

    public static Bitmap cropBitmap(Bitmap source, int left, int top, int right, int bottom){
        String s = String.format("%d, %d, %d, %d", left, top, right, bottom);
        System.out.println(s);
        Bitmap crop = Bitmap.createBitmap(source, left, top, right - left + 1, bottom - top + 1);
        return crop;
    }

}
