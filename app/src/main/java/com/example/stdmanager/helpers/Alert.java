package com.example.stdmanager.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stdmanager.R;

public class Alert {
    private Dialog dialog;
    private Context context;
    private TextView msgText;
    private ImageView iconAlert;
    public Button btnOK;
    public Button btnCancel;

    public Alert(Context context) {
        this.context = context;
    }

    public void normal(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.normal_alert);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        setControl();
    }

    public void confirm(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm_alert);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        setControl();
    }

    private void setControl(){
        msgText = dialog.findViewById(R.id.msgText);
        iconAlert = dialog.findViewById(R.id.iconAlert);
        btnOK = dialog.findViewById(R.id.btnOK);
        btnCancel = dialog.findViewById(R.id.btnCancel);
    }

    public void showAlert(String title, Integer ico){
        msgText.setText(title);
        iconAlert.setBackgroundResource(ico);
        dialog.show();
    }

    public void showAlert(Integer resid, Integer ico){
        String title = context.getResources().getString(resid);
        msgText.setText(title);
        iconAlert.setBackgroundResource(ico);
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }
}
