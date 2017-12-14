package com.example.owen.sleep_lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import com.jjoe64.graphview.GraphView;

public class StatsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //display list of values that represent files

       /* GraphView graphView = (GraphView)findViewById(R.id.graph);
        LineGraphSeries<DataPoint> setries =new LineGraphSeries<>(new DataPoint[]{
           new DataPoint(0,1),
           new DataPoint(1,5),
           new DataPoint(2,3),
           new DataPoint(2,5)
        });
        graphView.addSeries(series);*/
    }
}
