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

}
