package com.example.stdmanager.Statistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.stdmanager.DB.ScoreDBHelper;
import com.example.stdmanager.LoginActivity;
import com.example.stdmanager.R;
import com.example.stdmanager.helpers.Alert;
import com.example.stdmanager.models.ReportScore;
import com.example.stdmanager.models.ReportTotal;
import com.example.stdmanager.models.Statistic;
import com.example.stdmanager.models.Subject;

import java.util.ArrayList;
import java.util.List;

public class MarkStatsActivity extends AppCompatActivity {

    TextView title;
    Statistic item;
    Subject subject;
    AnyChartView anyChartView;

    Alert alert;
    ScoreDBHelper db = new ScoreDBHelper(this);
    ArrayList<ReportTotal> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_stats);

        item = (Statistic) getIntent().getSerializableExtra("Statistic");
        subject = (Subject) getIntent().getSerializableExtra("Subject");

        alert = new Alert(MarkStatsActivity.this);
        alert.normal();

        setControl();
        setData();
        setEvent();

        setupChart();

    }

    private void setData() {
        title.setText("Thống kê " + item.getTitle() + " môn " + subject.getTenMH());
    }

    private void setEvent() {
        alert.btnOK.setOnClickListener(view -> {
            alert.dismiss();
            finish();
        });
    }

    private void setControl() {
        title = findViewById(R.id.title);

        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

    }


    private void setupChart(){

        listData = db.getReportCountByScore(subject.getMaMH());
        if(listData.size() == 0){
            alert.showAlert("Dữ liệu điểm chưa có. Hãy thử lại!", R.drawable.info_icon);
            return;
        }

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++){
            System.out.println(listData.get(i).getName() + " - " + listData.get(i).getValue());
            data.add(new ValueDataEntry(listData.get(i).getName(), listData.get(i).getValue()));
        }
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("Điểm: {%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("Số lượng: {%Value}");

        cartesian.animation(true);
        cartesian.yScale().minimum(0d);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Điểm");
        cartesian.yAxis(0).title("Số lượng");

        anyChartView.setChart(cartesian);
    }
}