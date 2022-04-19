package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.stdmanager.DB.StudentOpenHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.ReportTotal;
import com.example.stdmanager.models.Statistic;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenderStatsActivity extends AppCompatActivity {

    TextView title;
    Statistic item;
    AppCompatButton btnExport;

    AnyChartView anyChartView;
    Alert alert;
    StudentOpenHelper studentOpenHelper = new StudentOpenHelper(this);
    ArrayList<ReportTotal> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_stats);

        item = (Statistic) getIntent().getSerializableExtra("detail");

        alert = new Alert(GenderStatsActivity.this);
        alert.normal();

        setControl();
        setData();
        setEvent();

        setupPieChart();
    }

    private void setData() {
        title.setText("Thống kê " + item.getTitle());
    }

    private void setEvent() {
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createExcel(view);
                alert.showAlert("Xuất thành công!", R.drawable.check_icon);

            }
        });

        alert.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
    }

    private void setControl() {
        title = findViewById(R.id.title);
        btnExport = findViewById(R.id.btnExport);
        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

    }

    private void setupPieChart(){
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        listData = studentOpenHelper.countByGender();

        for (int i = 0; i < listData.size(); i++){
            data.add(new ValueDataEntry(listData.get(i).getName(), listData.get(i).getValue()));
        }

        pie.data(data);
        pie.palette(new String[]{"#ffcc80", "#aed581"});
        pie.title(item.getTitle());
        pie.labels().position("outside");

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }


    public void createExcel(View view)
    {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();

        ArrayList<String> headers = new ArrayList<>();
        headers.add("Giới tính");
        headers.add("Tổng công");

        HSSFRow hssfRow = hssfSheet.createRow(0);
        for (int i = 0; i < headers.size(); i++){
            HSSFCell hssfCell = hssfRow.createCell(i);
            hssfCell.setCellValue(headers.get(i));
        }

        for (int i = 0; i < listData.size(); i++){
            HSSFRow hssfRow1 = hssfSheet.createRow(i+1);

            HSSFCell hssfCell = hssfRow1.createCell(0);
            hssfCell.setCellValue(listData.get(i).getName());

            HSSFCell hssfCell1 = hssfRow1.createCell(1);
            hssfCell1.setCellValue(listData.get(i).getValue());
        }

        try {
            @SuppressLint("SdCardPath") String path = "/sdcard/ThongKeGioiTinh.xls";
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream output = new FileOutputStream(file);
            hssfWorkbook.write(output);

            if(output != null){
                output.flush();
                output.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}