package com.repelliuss.npuzzle.utils;

import android.content.Context;
import android.util.TypedValue;

public class Screen {

    public static int dpToPixel(float dp, Context context) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()));
    }
}
