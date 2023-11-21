package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    EditText editTextX1, editTextX2, editTextA;
    Button btnSubmit, btnZoomIn, btnZoomOut;
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextX1 = findViewById(R.id.editTextX1);
        editTextX2 = findViewById(R.id.editTextX2);
        editTextA = findViewById(R.id.editTextA);
//        editTextE = findViewById(R.id.editTextE);
        btnSubmit = findViewById(R.id.btnSubmit);
        graphView = findViewById(R.id.graph);

        btnZoomIn = findViewById(R.id.btnZoomIn);
        btnZoomOut = findViewById(R.id.btnZoomOut);

        btnZoomIn.setOnClickListener(v -> {
            graphView.getViewport().setXAxisBoundsManual(true);
            double minX = graphView.getViewport().getMinX(true);
            double maxX = graphView.getViewport().getMaxX(true);
            graphView.getViewport().setMinX(minX + 1); // Adjust the zoom level
            graphView.getViewport().setMaxX(maxX - 1);
            graphView.onDataChanged(false, false);
        });

        btnZoomOut.setOnClickListener(v -> {
            graphView.getViewport().setXAxisBoundsManual(true);
            double minX = graphView.getViewport().getMinX(true);
            double maxX = graphView.getViewport().getMaxX(true);
            graphView.getViewport().setMinX(minX - 1); // Adjust the zoom level
            graphView.getViewport().setMaxX(maxX + 1);
            graphView.onDataChanged(false, false);
        });

        btnSubmit.setOnClickListener(v -> {
            String x1Txt = editTextX1.getText().toString();
            String x2Txt = editTextX2.getText().toString();
            String aTxt = editTextA.getText().toString();
            if (x1Txt.isEmpty() && x2Txt.isEmpty() && aTxt.isEmpty()){
                Toast.makeText(MainActivity.this, "Vui long nhap gia tri!", Toast.LENGTH_SHORT).show();
            }else{
                double x1 = Double.parseDouble(editTextX1.getText().toString());
                double x2 = Double.parseDouble(editTextX2.getText().toString());
                double a = Double.parseDouble(editTextA.getText().toString());
//                double e = Double.parseDouble(editTextE.getText().toString());

                drawGraph(x1, x2, a);
            }
        });
    }

    private void drawGraph(double x1, double x2, double a) {
        graphView.removeAllSeries();

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

        for (double i = x1; i <= x2; i += 0.1) {
            double y1 = Math.pow(a, i); // y = a^x
            double y2 = Math.exp(i); // y = e^x
            series1.appendData(new DataPoint(i, y1), true, 100);
            series2.appendData(new DataPoint(i, y2), true, 100);
        }
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);
        graphView.addSeries(series1);
        graphView.addSeries(series2);

    }

}