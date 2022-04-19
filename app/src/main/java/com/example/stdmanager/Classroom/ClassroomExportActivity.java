package com.example.stdmanager.Classroom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.models.Student;


import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.*;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;



public class ClassroomExportActivity extends AppCompatActivity {

    ArrayList<Student> objects = new ArrayList<>();
    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);
    TableLayout table;

    AppCompatButton buttonPhoto, buttonGoBack, buttonPDF;
    LinearLayout linearLayout;


    /**
     * @author Phong-Kaster
     * @path is the name of PDF file we need to open
     *
     * Open PDF file
     * */
    private void openPDFfile(String path)
    {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/" + path);
        if(file.exists()){
            Intent target = new Intent(Intent.ACTION_VIEW);

            target.setDataAndType(Uri.fromFile(file),"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(ClassroomExportActivity.this,
                    "Hãy thử lại!",
                    Toast.LENGTH_LONG)
                    .show();
        }


    }

    /**
     * @author Phong-Kaster
     * @path is the name of JPEG file we need to open
     *
     * Open JPEG file
     * */
    private void openJPEGfile()
    {
       Intent intent = new Intent(ClassroomExportActivity.this, ClassroomExportPreviewActivity.class);
       startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_export);

        /*Pop up a notice that user must accept request READ | WRITE EXTERNAL STORAGE*/
        ActivityCompat.requestPermissions(ClassroomExportActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        setControl();
        setScreen();
        setEvent();


    }

    private void setControl()
    {
        table = findViewById(R.id.classroomTable);
        linearLayout = findViewById(R.id.classroomExportLayout);
        buttonPhoto = findViewById(R.id.classroomExportButtonPhoto);
        buttonGoBack = findViewById(R.id.classroomExportButtonGoBack);
        buttonPDF = findViewById(R.id.classroomExportButtonPDF);
    }

    private void setScreen()
    {
        objects = studentOpenHelper.retrieveAllStudents();

        for( int i = 0 ; i < objects.size() ; i++)
        {
            TableRow row = new TableRow(this);


            TextView tv1 = new TextView(this);
            tv1.setText(objects.get(i).getFamilyName());
            tv1.setPadding(100, 5,5,5);
            tv1.setTextColor(Color.BLACK);
            tv1.setWidth(250);

            TextView tv2 = new TextView(this);
            tv2.setText(objects.get(i).getFirstName());
            tv2.setPadding(100, 5,5,5);
            tv2.setTextColor(Color.BLACK);

            TextView tv3 = new TextView(this);
            String gender = objects.get(i).getGender() == 0 ? "Nam" : "Nữ";
            tv3.setText(gender);
            tv3.setPadding(100, 5,5,5);
            tv3.setTextColor(Color.BLACK);

            TextView tv4 = new TextView(this);
            tv4.setText(objects.get(i).getBirthday());
            tv4.setPadding(150, 5,5,5);
            tv4.setTextColor(Color.BLACK);

            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            row.addView(tv4);

            table.addView(row);
        }
    }

    /**
     * @author Phong-Kaster
     * set button and listen event click on button
     * */
    private void setEvent()
    {
        buttonPhoto.setOnClickListener(view -> {
            ViewGroup viewGroup = table;
            screenshotToPhoto(viewGroup, "result");

            Toast.makeText(ClassroomExportActivity.this,
                            "Xuất hình ảnh thành công !",
                            Toast.LENGTH_LONG)
                .show();
            openJPEGfile();
        });

        buttonPDF.setOnClickListener(view -> {
            createPdfWithItext7(view);
            Toast.makeText(ClassroomExportActivity.this,
                        "Xuất tệp tin PDF thành công !",
                        Toast.LENGTH_LONG)
                .show();


//        openPDFfile("DanhSachSinhVien.pdf");

        });

        buttonGoBack.setOnClickListener(view -> finish());
    }

    /**
     * @author Phong-Kaster
     * This funtion uses bitmap library - a pre-installed Android libray so as to
     * capture screen and store the photo into root directory
     *
     * To check, open "View -> Tool Window -> Device File Explorer -> sdCard". We will see the stored photo
     * Remember choose "Synchronize" to refresh sdCard
     *
     * File name: DanhSachSinhVien.jpeg
     * */
    private static void screenshotToPhoto(View view, String filename) {
        /*Step 1*/
        Date date = new Date();


        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            /*Step 2*/
            String dirpath = Environment.getExternalStorageDirectory() + "";

            // File name
            String path = dirpath + "/DanhSachSinhVien.jpeg";

            /*Step 3*/
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);


            /*Step 4*/
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);


            /*Step 5*/
            outputStream.flush();
            outputStream.close();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    /**
     * @author Phong-Kaster
     * Create Pdf with iText 7 - a third-party library to instanciate and handle PDF file
     *
     * To check, open "View -> Tool Window -> Device File Explorer -> sdCard". We will see the stored photo
     * Remember choose "Synchronize" to refresh sdCard
     *
     * File name: DanhSachSinhVien.pdf
     *
     * Step 1: instanciate output PDF File
     * Step 2: declare PdfWriter, PdfDocument and Document
     * Step 3: declare number of column and dimension
     *          #   Ho   Ten    Gioi Tinh      Ngay Sinh
     * Step 4: declare header
     * Step 5: declare columns name
     * Step 6: insert student information from database
     * */
    public void createPdfWithItext7(View v)
    {

        try {
            /*Step 1*/
            @SuppressLint("SdCardPath") String path = "/sdcard/DanhSachSinhVien.pdf";
            File file = new File(path);
            OutputStream output = new FileOutputStream(file);


            /*Step 2*/
            PdfWriter writer = new PdfWriter(output);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);

            Document document = new Document(pdfDocument);


            /*Step 3*/
            float[] dimension = {1, 2, 2, 2, 2};
            Table table = new Table(UnitValue.createPercentArray(dimension));


            /*Step 4*/
            ArrayList<Student> objects = studentOpenHelper.retrieveAllStudents();
            String gradeName = objects.get(1).getGradeName();

            Paragraph header = new Paragraph("Danh Sách Sinh Viên " + gradeName);
            Cell cell = new Cell(1, 5)
                    .add( header )
                    .setFontSize(13)
                    .setFontColor(DeviceGray.WHITE)
                    .setBackgroundColor(DeviceGray.GRAY)
                    .setTextAlignment(TextAlignment.CENTER);
            table.addHeaderCell(cell);


            /*Step 5*/
            DeviceGray color = new DeviceGray(0.75f);

            Paragraph column1 = new Paragraph("#");
            Paragraph column2 = new Paragraph("Ho");
            Paragraph column3 = new Paragraph("Ten");
            Paragraph column4 = new Paragraph("Gioi tinh");
            Paragraph column5 = new Paragraph("Ngay sinh");

            for (int i = 0; i < 2; i++)
            {
                Cell[] headerFooter = new Cell[]{
                        new Cell().setBackgroundColor(color).add(column1),
                        new Cell().setBackgroundColor(color).add(column2),
                        new Cell().setBackgroundColor(color).add(column3),
                        new Cell().setBackgroundColor(color).add(column4),
                        new Cell().setBackgroundColor(color).add(column5)
                };

                for (Cell hfCell : headerFooter) {
                    if (i == 0) {
                        table.addHeaderCell(hfCell);
                    } else {
                        table.addFooterCell(hfCell);
                    }
                }
            }


            /*Step 6*/
            for (int i = 0; i < objects.size(); i++) {
                Student student = objects.get(i);
                String family = student.getFamilyName();
                String first = student.getFirstName();
                String gender = student.getGender() == 0 ? "Nam" : "Nu";
                String birthday = student.getBirthday();

                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(i + 1))));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  family  )));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  first   )));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  gender  )));
                table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(  birthday )));
            }

            document.add(table);
            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}