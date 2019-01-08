package edu.amd.spbstu.elevator.AppEntities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class BackgroundObject extends RectF {
    Bitmap bmp;

    BackgroundObject(float left, float top, float right, float bottom, Bitmap bmp) {
        super(left, top, right, bottom);
        this.bmp = bmp;
        this.bmp = Bitmap.createScaledBitmap(bmp,(int)((right - left)), (int)((bottom - top)), true);
    }


    BackgroundObject(Rect r, Bitmap bmp) {
        super(r);
        this.bmp = bmp;
        this.bmp = Bitmap.createScaledBitmap(bmp,(int)((right - left)), (int)((bottom - top)), true);
    }


    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, this.left, this.top, null);
        //Paint p = new Paint();
        ///p.setColor(Color.RED);
        //c.drawRect(this, p); //DEBUG
    }
}

