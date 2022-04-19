package com.example.stdmanager.helpers;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class InterfaceHelper {

    /**
     * @author Phong-Kaster
     * this function helps us to blur current screen whenever popup window is activated
     *
     * Path - https://stackoverflow.com/a/46711174
     * */
    public static void blurCurrentScreen(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
}
