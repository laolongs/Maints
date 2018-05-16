package cn.tties.maint.util;

import org.xutils.x;

public class Dp2Utils {
    public static int Dp2Px(float dp) {
        final float scale = x.app().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}