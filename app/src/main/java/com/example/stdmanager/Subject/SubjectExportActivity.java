package com.example.stdmanager.Subject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stdmanager.Classroom.ClassroomExportActivity;
import com.example.stdmanager.DB.SubjectDBHelper;
import com.example.stdmanager.LoginActivity;
import com.example.stdmanager.R;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.Student;
import com.example.stdmanager.models.Subject;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SubjectExportActivity extends AppCompatActivity {

    AppCompatButton btn_Export,btn_Cancel;
    ArrayList<Subject> objects;
    TableLayout table;
    SubjectDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_export);
        ActivityCompat.requestPermissions(SubjectExportActivity.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);
        setControl();
        setEvent();

    }

    private void setControl()
    {
        table = findViewById(R.id.subjectTable);
        btn_Export = findViewById(R.id.btnExport);
        btn_Cancel = findViewById(R.id.Btn_ExportCancel);

        objects = new ArrayList<Subject>();
        dbHelper = new SubjectDBHelper(this);
        objects = dbHelper.getAllSubjects();

        for (Subject item:objects) {
            TableRow row = new TableRow(this);


            TextView tv1 = new TextView(this);
            tv1.setText(item.getTenMH());

            tv1.setPadding(140, 10,5,5);
            tv1.setTextColor(Color.BLACK);


            TextView tv2 = new TextView(this);
            tv2.setText(String.valueOf(item.getHocKy()));
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv2.setPadding(120, 10,5,5);
            tv2.setTextColor(Color.BLACK);

            TextView tv3 = new TextView(this);

            tv3.setText(item.getNamHoc());
            tv3.setPadding(175, 10,5,5);
            tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv3.setTextColor(Color.BLACK);

            TextView tv4 = new TextView(this);
            tv4.setText(String.valueOf(item.getHeSo()));
            tv4.setPadding(260, 10,5,5);
            tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv4.setTextColor(Color.BLACK);

            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            row.addView(tv4);

            table.addView(row);
        }
    }

    private void setEvent()
    {

        btn_Export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdfWithItext7(view);
                Alert alert = new Alert(SubjectExportActivity.this);
                alert.normal();
                alert.showAlert("Xuất danh sách thành file PDF thành công", R.drawable.check_icon);
                alert.btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });

//                openPDFfile("DanhSachSinhVien.pdf");
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void createPdfWithItext7(View v)
    {

        try {
            /*Step 1*/
            @SuppressLint("SdCardPath") String path = "/sdcard/DanhSachMonHoc.pdf";
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
            ArrayList<Subject> objects = dbHelper.getAllSubjects();

            Paragraph header = new Paragraph("DANH SACH MON HOC  " );
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
            Paragraph column2 = new Paragraph("Ten");
            Paragraph column3 = new Paragraph("Hoc ki");
            Paragraph column4 = new Paragraph("Nam hoc");
            Paragraph column5 = new Paragraph("He so");

            for (int i = 0; i < 2; i++)
            {
                Cell[] headerFooter = new Cell[]{
                        new Cell().setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).add(column1),
                        new Cell().setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).add(column2),
                        new Cell().setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).add(column3),
                        new Cell().setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).add(column4),
                        new Cell().setBackgroundColor(color).setTextAlignment(TextAlignment.CENTER).add(column5)
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

                for (Subject item:objects) {
                    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(item.getMaMH()))));
                    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(item.getTenMH())));
                    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(item.getHocKy()))));
                    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(item.getNamHoc())));
                    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(item.getHeSo()))));
                }


            document.add(table);
            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}