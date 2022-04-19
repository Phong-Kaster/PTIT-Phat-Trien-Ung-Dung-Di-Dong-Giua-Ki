package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.stdmanager.Classroom.ClassroomExportActivity;
import com.example.stdmanager.DB.ScoreDBHelper;
import com.example.stdmanager.R;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.ReportScore;
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

public class RankedStatsActivity extends AppCompatActivity {

    TextView title;
    Statistic item;
    AppCompatButton btnExport;
    AnyChartView anyChartView;

    Alert alert;
    ScoreDBHelper db = new ScoreDBHelper(this);
    ArrayList<ReportTotal> listData = new ArrayList<>();
    String ranked[] = new String[]{ "Giỏi", "Khá", "Trung bình", "Yếu"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_stats);

        item = (Statistic) getIntent().getSerializableExtra("detail");
        alert = new Alert(RankedStatsActivity.this);
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
        ArrayList<ReportScore> list = db.getReportScore();

        for (int i = 0; i < ranked.length; i++){
            int count = 0;
            for (int j = 0; j < list.size(); j++){
                String xeploai = getXepLoai(list.get(j).getDiem());
                if(xeploai.equals(ranked[i])){
                    count++;
                }
            }
            listData.add(new ReportTotal(ranked[i], Double.valueOf(count)));
            data.add(new ValueDataEntry(ranked[i], count));
        }

        pie.data(data);
        pie.palette(new String[]{"#61CDBB", "#E8A838", "#DC143C", "#473F97"});
        pie.title(item.getTitle());
        pie.labels().position("outside");

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

    private String getXepLoai(Double diem){
        if(diem >= 8) {
            return "Giỏi";
        }else if(diem >= 6.5 && diem < 8) {
            return "Khá";
        }else if(diem >= 5 && diem < 6.5) {
            return "Trung bình";
        }else{
            return "Yếu";
        }
    }

    public void createExcel(View view)
    {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();


        ArrayList<String> headers = new ArrayList<>();
        headers.add("Xếp loại");
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
            @SuppressLint("SdCardPath") String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ThongKeXepLoai.xls";
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

            hssfWorkbook.cloneSheet(0);
            hssfWorkbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}