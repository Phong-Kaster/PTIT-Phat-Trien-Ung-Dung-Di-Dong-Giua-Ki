package com.example.stdmanager.Settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.stdmanager.DB.TeacherDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.TopBarMenuIconFragment;
import com.example.stdmanager.helpers.InterfaceHelper;
import com.example.stdmanager.models.Session;
import com.example.stdmanager.models.Teacher;

public class SettingsAccountActivity extends AppCompatActivity {

    LinearLayout name, password;
    ImageView avatar;
    Session session;
    TeacherDBHelper teacherOpenHelper;
    Teacher teacher;
    AppCompatButton buttonAvatar;


    private final static int RESULT_LOAD_IMAGE = 1;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);

        session = new Session(SettingsAccountActivity.this);
        teacherOpenHelper = new TeacherDBHelper(this);
        int id = Integer.parseInt( session.get("teacherId"));
        teacher = teacherOpenHelper.getTeacher(id);

        setControl();

        setEvent();
    }

    /**
     * @author Phong-Kaster
     * */
    private void setControl()
    {
        name = findViewById(R.id.linearLayoutName);
        password = findViewById(R.id.linearLayoutPassword);
        avatar = findViewById(R.id.settingsAccountImageViewAvatar);
        buttonAvatar = findViewById(R.id.settingsAccountButtonChangeAvatar);
    }

    /**
     * @author Phong-Kaster
     * */
    private void setEvent()
    {
//        teacherName.setText( teacher.getName() );


        name.setOnClickListener(view -> {
            View popup = inflatePopupWindow(view, R.layout.activity_settings_account_edit_name);
            handleEventNameLayout(popup);
        });

        password.setOnClickListener(view -> {
            View popup = inflatePopupWindow(view, R.layout.activity_settings_account_edit_password);
            handleEventPasswordLayout(popup);
        });

        avatar.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        });

        buttonAvatar.setOnClickListener(view->{
            Intent intent = new Intent(
                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        });

    }

    /**
     * @author Phong-Kaster
     * Step 1: retrieve Uri of selected image and store it into an array
     * Step 2: utilize a cursor to take a query selected image. A cursor, in essence, works as array
     * Step 3: the absolute path of selected image lies on column[0]
     * Step 4: insert new avatar with drawable class
     * Step 5: save selected image into datasbase
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            /*Step 1*/
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            /*Step 2*/
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();


            /*Step 3*/
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);//picturePath contains the path of selected Image
            cursor.close();

            /*Step 4*/
            avatar.setImageURI(selectedImage);
            Drawable drawable =  avatar.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

            /*Step 5*/
            /* Here, i am able to write a block of code to save selected image into res/drawable folder but
             * It is not possible to save anything into res/drawable: https://stackoverflow.com/a/5469993
             * If we want to save images, we have to a remote database so as to store it
             * */

        }
        else
        {
            Toast.makeText(this, "You have not selected and image", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * @author Phong-Kaster
     * this functions is used to instanciate a pop-up window
     * */
    @SuppressLint("ClickableViewAccessibility")
    private View inflatePopupWindow(View view, int layout)
    {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layout, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        InterfaceHelper.blurCurrentScreen(popupWindow);

        // dismiss the popup window when touched
        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });

        return popupView;
    }

    /**
     * @author Phong-Kaster
     * this functions instanciate and listen event when we want to custom account's name
     * */
    private void handleEventNameLayout(View view)
    {
        /*Step 1*/
        Button saveButton = view.findViewById(R.id.settingsAccountButtonSaveName);
        EditText teacherNameEdit = view.findViewById(R.id.settingsAccountEditTextName);
        teacherNameEdit.setText( teacher.getName());

        /*Step 2*/
        saveButton.setOnClickListener(view1 -> {
            String fullName = teacherNameEdit.getText().toString();

            teacher.setName(fullName);
//                teacherName.setText(fullName);
            teacherOpenHelper.updateTeacher(teacher);

            Toast.makeText(SettingsAccountActivity.this, "Đổi tên thành công !", Toast.LENGTH_SHORT).show();
            TopBarMenuIconFragment.getmInstanceActivity().setData(teacher);
        });
    }

    /**
     * @author Phong-Kaster
     * this functions instanciate and listen event when we want to custom account's name
     * */
    private void handleEventPasswordLayout(View view)
    {
        /*Step 1*/
        Button savePassword = view.findViewById(R.id.settingsAccountButtonSavePassword);
        EditText password = view.findViewById(R.id.settingsAccountPassword);
        EditText newPassword = view.findViewById(R.id.settingsAccountNewPassword);
        EditText confirmationPassword = view.findViewById(R.id.settingsAccountConfirmationPassword);


        /*Step 2*/
        savePassword.setOnClickListener(view1 -> {

            String pass = password.getText().toString();
            String newPass = newPassword.getText().toString();
            String confirmationPass = confirmationPassword.getText().toString();


            boolean flag = checkInputData(pass, newPass, confirmationPass);
            if( !flag)
                return;

            teacher.setPassword(newPass);
            teacherOpenHelper.updateTeacher(teacher);
            Toast.makeText(SettingsAccountActivity.this, "Đổi tên mật khẩu thành công !", Toast.LENGTH_SHORT).show();


        });
    }

    /**
     * @author Phong-Kaster
     * this functions verify password which is typed, it checkes if password is valid so as to change or not  ?
     * */
    private boolean checkInputData(String pass, String newPass, String confirmationPass)
    {
        if(pass.equals("") || newPass.equals("") || confirmationPass.equals(""))
        {
            Toast.makeText(SettingsAccountActivity.this, "Điền đầy đủ các trường dữ liệu !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if( !pass.equals(teacher.getPassword()) )
        {
            Toast.makeText(SettingsAccountActivity.this, "Mật khẩu nhập vào chưa chính xác !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if( newPass.equals(pass))
        {
            Toast.makeText(SettingsAccountActivity.this, "Mật khẩu nhập mới giống mật khẩu hiện tại  !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if( !newPass.equals( confirmationPass) )
        {
            Toast.makeText(SettingsAccountActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}