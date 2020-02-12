package com.repelliuss.npuzzle.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class Screen {

    public static int dpToPixel(float dp, Resources resources) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, resources.getDisplayMetrics()));
    }
}
