package edu.amd.spbstu.elevator.AppEntities.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import edu.amd.spbstu.elevator.R;

public class Bitmaps {
    public Bitmap bmpElevator;

    public Bitmap bmpFemLeft;
    public Bitmap bmpFemRight;

    public Bitmap bmpMLeft;
    public Bitmap bmpMRight;

    public Bitmap bmpOldFemLeft;
    public Bitmap bmpOldFemRight;

    public Bitmap bmpOldMLeft;
    public Bitmap bmpOldMRight;

    public Bitmap bmpMsg;
    public Bitmap bmpDislike;

    public Bitmap bmpFloor1;
    public Bitmap bmpFloor2;
    public Bitmap bmpFloor3;

    public Bitmap bmpBgObj;

    private BitmapFactory.Options options;

    Bitmaps(Resources res) {
        options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        bmpElevator = loadBitmap(res, R.drawable.elevator);

        Matrix leftMatrix = new Matrix();
        leftMatrix.preScale(-1.0f, 1.0f);

        bmpFemRight = loadBitmap(res, R.drawable.fem);
        bmpFemLeft = Bitmap.createBitmap(bmpFemRight, 0, 0, bmpFemRight.getWidth(), bmpFemRight.getHeight(), leftMatrix, true);

        bmpMRight = loadBitmap(res, R.drawable.m);
        bmpMLeft = Bitmap.createBitmap(bmpMRight, 0, 0, bmpMRight.getWidth(), bmpMRight.getHeight(), leftMatrix, true);

        bmpOldFemLeft = loadBitmap(res, R.drawable.old_fem);
        bmpOldFemRight= Bitmap.createBitmap(bmpOldFemLeft, 0, 0, bmpOldFemLeft.getWidth(), bmpOldFemLeft.getHeight(), leftMatrix, true);

        bmpOldMLeft = loadBitmap(res, R.drawable.old_m);
        bmpOldMRight= Bitmap.createBitmap(bmpOldMLeft, 0, 0, bmpOldMLeft.getWidth(), bmpOldMLeft.getHeight(), leftMatrix, true);


        bmpMsg = loadBitmap(res, R.drawable.speech_bubble);
        bmpDislike = loadBitmap(res, R.drawable.anger_symbol);


        bmpFloor1 = loadBitmap(res, R.drawable.fl_0);
        bmpFloor2 = loadBitmap(res, R.drawable.fl_1);
        bmpFloor3 = loadBitmap(res, R.drawable.fl_2);

        bmpBgObj = loadBitmap(res, R.drawable.brickwall);
    }

    private Bitmap loadBitmap(Resources res, int ind) {
        return BitmapFactory.decodeResource(res, ind);
    }

}
