package com.aghajari.simplechart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LineChart lineChart = findViewById(R.id.line_chart);
        lineChart.setLineColor(ResourcesCompat.getColor(getResources(), R.color.second, null));
        lineChart.setPointColor(ResourcesCompat.getColor(getResources(), R.color.teal_700, null));
        lineChart.addData(8f, 10f, 5f, 7f, 4f, 6f);

        LineChart2 lineChart2 = findViewById(R.id.line_chart2);
        lineChart2.setLineColor(ResourcesCompat.getColor(getResources(), R.color.second, null));
        lineChart2.setPointColor(ResourcesCompat.getColor(getResources(), R.color.teal_700, null));
        lineChart2.addData(8f, 10f, 5f, 7f, 4f, 6f);

        CircleChart circleChart = findViewById(R.id.circle_chart);
        circleChart.addData(0xFF7AA6E8, Color.WHITE, 170, 40);
        circleChart.addData(0xFFD273A2, Color.WHITE, 150, 35);
        circleChart.addData(0xFFE2B263, Color.WHITE, 130, 25);

        BarChart barChart = findViewById(R.id.bar_chart);
        barChart.setMaxValue(100);
        barChart.addData(60, "Say");
        barChart.addData(40, "Hello");
        barChart.addData(80, "World");
        barChart.addData(30, ":)");
    }

}