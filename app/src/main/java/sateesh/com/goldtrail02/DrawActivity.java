package sateesh.com.goldtrail02;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import sateesh.com.goldtrail02.Data.DatabaseContract;

public class DrawActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

//        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
//        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);
////
//        mSeekBarX.setProgress(45);
//        mSeekBarY.setProgress(100);

//        mSeekBarY.setOnSeekBarChangeListener(this);
//        mSeekBarX.setOnSeekBarChangeListener(this);

        mChart = (LineChart) findViewById(R.id.chart1);
//        mChart.setOnChartGestureListener(this);
//        mChart.setOnChartValueSelectedListener(this);
//        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(130f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
//        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
//        ll2.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-50f);
        leftAxis.setStartAtZero(false);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(45, 100);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.line, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.actionToggleValues: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    set.setDrawValues(!set.isDrawValuesEnabled());
//                }
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleHighlight: {
//                if(mChart.getData() != null) {
//                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
//                    mChart.invalidate();
//                }
//                break;
//            }
//            case R.id.actionToggleFilled: {
//
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    if (set.isDrawFilledEnabled())
//                        set.setDrawFilled(false);
//                    else
//                        set.setDrawFilled(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleCircles: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    if (set.isDrawCirclesEnabled())
//                        set.setDrawCircles(false);
//                    else
//                        set.setDrawCircles(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleCubic: {
//                List<ILineDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (ILineDataSet iSet : sets) {
//
//                    LineDataSet set = (LineDataSet) iSet;
//                    if (set.isDrawCubicEnabled())
//                        set.setDrawCubic(false);
//                    else
//                        set.setDrawCubic(true);
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleStartzero: {
//                mChart.getAxisLeft().setStartAtZero(!mChart.getAxisLeft().isStartAtZeroEnabled());
//                mChart.getAxisRight().setStartAtZero(!mChart.getAxisRight().isStartAtZeroEnabled());
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionTogglePinch: {
//                if (mChart.isPinchZoomEnabled())
//                    mChart.setPinchZoom(false);
//                else
//                    mChart.setPinchZoom(true);
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleAutoScaleMinMax: {
//                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
//                mChart.notifyDataSetChanged();
//                break;
//            }
//            case R.id.animateX: {
//                mChart.animateX(3000);
//                break;
//            }
//            case R.id.animateY: {
//                mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
//                break;
//            }
//            case R.id.animateXY: {
//                mChart.animateXY(3000, 3000);
//                break;
//            }
//            case R.id.actionToggleFilter: {
//
//                // the angle of filtering is 35°
//                Approximator a = new Approximator(Approximator.ApproximatorType.DOUGLAS_PEUCKER, 35);
//
//                if (!mChart.isFilteringEnabled()) {
//                    mChart.enableFiltering(a);
//                } else {
//                    mChart.disableFiltering();
//                }
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionSave: {
//                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
//                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
//                            Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
//                            .show();
//
//                // mChart.saveToGallery("title"+System.currentTimeMillis())
//                break;
//            }
//        }
//        return true;
//    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());

        // redraw
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {


        Uri uri = Uri.withAppendedPath(DatabaseContract.ClassInfo.CONTENT_URI, "0");
        String[] Dateprojection = {DatabaseContract.ClassInfo.COLUMN_DATE};
        List<String> xVals = new ArrayList<>();

        Cursor cursorData = getContentResolver().query(uri, Dateprojection, null, null, null);
//        Log.v("Sateesh: ", "*** Chart - Date cursor Data: " + DatabaseUtils.dumpCursorToString(cursorData));

//        cursorData.moveToFirst();

        if(cursorData != null) {
//        while (!cursorData.isAfterLast()) {
            for (cursorData.moveToFirst(); !cursorData.isAfterLast(); cursorData.moveToNext()) {
                int columnIndexOrThrow = cursorData.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_DATE);
                String rawDate = cursorData.getString(columnIndexOrThrow);
                Log.v("Sateesh: ", "*** raw Date each Value: " + rawDate);
                String finalDateValue = rawDate.split("-")[0];
                Log.v("Sateesh: ", "*** final Date each Value: " + finalDateValue);
                xVals.add(finalDateValue);
            }
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>(4);

        Uri rollNouri = Uri.withAppendedPath(DatabaseContract.ClassInfo.CONTENT_URI, "0");
        String[] rollNoprojection = {DatabaseContract.ClassInfo.COLUMN_ROLLNO};

        Cursor rollNocursorData = getContentResolver().query(rollNouri, rollNoprojection, null, null, null);
//        cursorData.moveToFirst();

        int cursorDataValue = cursorData.getCount();
        int rollNocursorDataValue = rollNocursorData.getCount();
        int i = 0;
        Log.v("Sateesh: " ,  "*** X and Y Column Sizes are : " + cursorDataValue + " *** " + rollNocursorDataValue);
//        List<Entry> yVals = new ArrayList<Entry>();
        for(rollNocursorData.moveToFirst(); !rollNocursorData.isAfterLast(); rollNocursorData.moveToNext()) {
            int columnIndexOrThrow = rollNocursorData.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_ROLLNO);
            String rollNo = rollNocursorData.getString(columnIndexOrThrow);
            Log.v("Sateesh: " , "*** Each Roll No is: " + rollNo);
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(Float.valueOf(rollNo), i));
            i++;
            Log.v("Sateesh: " , "*** Value of I is :" + i);
        }

        Log.v("Sateesh: " , "*** Y Values are: " + yVals );
        Log.v("Sateesh: " , "*** X Values are: " + xVals );

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillColor(R.color.colorAccent);
//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//        set1.setFillDrawable(drawable);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets


        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
