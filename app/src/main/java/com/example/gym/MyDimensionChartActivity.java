package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesColumn;
import com.anychart.anychart.ChartScroller;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.OrdinalZoom;
import com.anychart.anychart.Position;
import com.anychart.anychart.Tooltip;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.skydoves.powerspinner.SpinnerAnimation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MyDimensionChartActivity extends AppCompatActivity {

    private ArrayList<Date> dateList = new ArrayList<>();
    private ArrayList<Double> weightList = new ArrayList<>();
    private ArrayList<Integer> adiposeTissueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dimension_chart);

       spinnerDataNamesInit();
        arrayListsInit();

/*
        BarChart barChart = (BarChart) findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(dateList.get(1).getTime(), 0));
        entries.add(new BarEntry(2, 1));
        entries.add(new BarEntry(3, 2));
        entries.add(new BarEntry(4, 3));
        entries.add(new BarEntry(5, 4));
        entries.add(new BarEntry(6, 5));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

        BarData data = new BarData(bardataset);
        barChart.setData(data);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        // BarData data = new BarData(labels, bardataset);
        //barChart.setData(data); // set the data and list of labels into chart
        Description desc = new Description();
        desc.setText("Set Bar Chart Description Here");
        barChart.setDescription(desc);  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);

    }
*/

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        // anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<DataEntry> data = new ArrayList<>();
        for(int i=0; i<dateList.size();i++){
            data.add(new ValueDataEntry(sdf.format(dateList.get(i)), weightList.get(i)));
        }

    /*    data.add(new ValueDataEntry("Rouge", 80540));
        data.add(new ValueDataEntry("Foundation", 94190));
        data.add(new ValueDataEntry("Mascara", 102610));
        data.add(new ValueDataEntry("Lip gloss", 110430));
        data.add(new ValueDataEntry("Lipstick", 128000));
        data.add(new ValueDataEntry("Nail polish", 143760));
        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
        data.add(new ValueDataEntry("Eyeliner", 213210));
        data.add(new ValueDataEntry("Eyeshadows", 249980));*/


       CartesianSeriesColumn column = cartesian.column(data);


       /*Tooltip tp = new Tooltip().setTitle("{%X}")
               .setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
                .setOffsetX(0d)
                .setOffsetY(5d)
                .setFormat("${%Value}{groupsSeparator: }");

        column.setTooltip(tp);


       column.getTooltip()
                .setTitle(true)
                .setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
               .setFormat("{%Value}");



        /*column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");
*/

      cartesian.setAnimation(true);
        //cartesian.animation();

        cartesian.setTitle(("Pomiary wagi"));
       // cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.getYScale().setMinimum(0d);
        //cartesian.yScale().minimum(0d);


       // cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
        cartesian.getYAxis().setLabels("${%Value}{groupsSeparator: }");

        //cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        //cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        //cartesian.getInteractivity().setHoverMode(HoverMode.BY_X);
       // cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.getXAxis().setTitle("Data pomiaru");
        //cartesian.xAxis(0).title("Product");

        cartesian.getYAxis().setTitle("Waga [kg]");
        //cartesian.yAxis(0).title("Revenue");

        cartesian.setXScroller(true);
        ChartScroller chartScroller = cartesian.getXScroller();
        chartScroller.setHeight((double)30);

        OrdinalZoom xZoom = cartesian.getXZoom();
        xZoom.setToPointsCount((double)4, false, null);

        anyChartView.setChart(cartesian);
        Log.e("t","uy");

/*
        //Dobry graf liniowy
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(dateList.get(0).getTime(), weightList.get(0)),
                new DataPoint(dateList.get(1).getTime(), weightList.get(1)),
                new DataPoint(dateList.get(2).getTime(), weightList.get(2)),
                new DataPoint(dateList.get(3).getTime(), weightList.get(3)),
                new DataPoint(dateList.get(4).getTime(), weightList.get(4))
        });
        graph.addSeries(series);
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().set
       // graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
       // graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Data");
        graph.setScaleX(1);

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            public String formatLabel(double value, boolean isValueX) {
                if (isValueX)
                    return sdf.format(new Date((long) value));
                else
                    return super.formatLabel(value, isValueX);
            }
        });
*/
        /*
        BarChart barChart = findViewById(R.id.barChart);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Date date = new Date((long)value);
                //Specify the format you'd like
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                return sdf.format(date);
            }
        };
       // IAxisValueFormatter theFormatYouWant= new TheFormatYouWant();

        //xAxis.setValueFormatter(theFormatYouWant);

        ArrayList<BarEntry> weight = new ArrayList<>();
        for(int i=0;i<dateList.size();i++)
            weight.add(new BarEntry((float)dateList.get(i).getTime(),weightList.get(i)));


        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2014,420));
        visitors.add(new BarEntry(2015,475));
        visitors.add(new BarEntry(2016,520));
        visitors.add(new BarEntry(2017,570));
        visitors.add(new BarEntry(2018,510));
        visitors.add(new BarEntry(2019,619));
        visitors.add(new BarEntry(2020,123));

        BarDataSet barDataSet = new BarDataSet(weight, "Waga");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        //barChart.getDescription().setText("Dobry wykres");
        barChart.animateY(1000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(formatter);
*/
    /*    LineChart volumeReportChart = findViewById(R.id.barChart);
        volumeReportChart.setTouchEnabled(true);
        volumeReportChart.setPinchZoom(true);

        LimitLine ll1 = new LimitLine(30f,"Title");
        ll1.setLineColor(getResources().getColor(Color.blue(6)));
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(35f, "");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);

        XAxis xAxis = volumeReportChart.getXAxis();
        YAxis leftAxis = volumeReportChart.getAxisLeft();
        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);
        volumeReportChart.getDescription().setEnabled(true);
        Description description = new Description();

        description.setText("Week");
        description.setTextSize(15f);

       // xAxis.setValueFormatter(new ClaimsXAxisValueFormatter(dateList));
     //   leftAxis.setValueFormatter(new ClaimsYAxisValueFormatter());
*/
    }

        private void spinnerDataNamesInit(){
            PowerSpinnerView powerSpinnerView = findViewById(R.id.powerSpinnerDataNames);
            List<String> dataTypesList= new ArrayList<>();
            dataTypesList.add("Waga");
            dataTypesList.add("Wzrost");

            List<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
            iconSpinnerItems.add(new IconSpinnerItem(null, "item1"));
            iconSpinnerItems.add(new IconSpinnerItem(null, "item2"));

            IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(powerSpinnerView);
            powerSpinnerView.setSpinnerAdapter(iconSpinnerAdapter);
            powerSpinnerView.setItems(iconSpinnerItems);

            //powerSpinnerView.setItems(dataTypesList);
            powerSpinnerView.setSpinnerPopupAnimation(SpinnerAnimation.FADE);
            powerSpinnerView.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
                @Override public void onItemSelected(int position, String item) {
                    }
                });
            }

        private void arrayListsInit(){

            dateList.add(new Date(2020-1900,5,1));
            dateList.add(new Date(2020-1900,5,3));
            dateList.add(new Date(2020-1900,5,6));
            dateList.add(new Date(2020-1900,5,12));
            dateList.add(new Date(2020-1900,5,23));
            dateList.add(new Date(2020-1900,6,15));
            dateList.add(new Date(2020-1900,4,1));
            dateList.add(new Date(2020-1900,6,24));

            weightList.add(70.0);
            weightList.add(73.0);
            weightList.add(74.0);
            weightList.add(79.0);
            weightList.add(71.0);
            weightList.add(89.0);
            weightList.add(60.0);
            weightList.add(90.0);

            adiposeTissueList.add(10);
            adiposeTissueList.add(15);
            adiposeTissueList.add(17);
            adiposeTissueList.add(14);
            adiposeTissueList.add(13);
            adiposeTissueList.add(11);
            adiposeTissueList.add(10);

            /*
            weightList.add((float)70.0);
            weightList.add((float)73.0);
            weightList.add((float)74.0);
            weightList.add((float)79.0);
            weightList.add((float)71.0);
            weightList.add((float)89.0);
            weightList.add((float)60.0);
            weightList.add((float)90.0);
             */

            }

}
/*
class TheFormatYouWant implements IAxisValueFormatter {

    public String getFormattedValue(float value, AxisBase axis) {

        Date date = new Date((long)value);
        //Specify the format you'd like
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return sdf.format(date);

    }

}


class ClaimsXAxisValueFormatter extends ValueFormatter {

    List<String> datesList;

    public ClaimsXAxisValueFormatter(List<String> arrayOfDates) {
        this.datesList = arrayOfDates;
    }


    @Override
    public String getAxisLabel(float value, AxisBase axis) {/*
Depends on the position number on the X axis, we need to display the label, Here, this is the logic to convert the float value to integer so that I can get the value from array based on that integer and can convert it to the required value here, month and date as value. This is required for my data to show properly, you can customize according to your needs.
*/
   /*     Integer position = Math.round(value);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");

        if (value > 1 && value < 2) {
            position = 0;
        } else if (value > 2 && value < 3) {
            position = 1;
        } else if (value > 3 && value < 4) {
            position = 2;
        } else if (value > 4 && value <= 5) {
            position = 3;
        }
        if (position < datesList.size())
            return sdf.format(new Date((getDateInMilliSeconds(datesList.get(position), "yyyy-MM-dd"))));
        return "";
    }


    public static long getDateInMilliSeconds(String givenDateString, String format) {
        String DATE_TIME_FORMAT = format;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.US);
        long timeInMilliseconds = 1;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

}

class ClaimsYAxisValueFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return value + "k";
    }
}



/*
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
       // anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Rouge", 80540));
        data.add(new ValueDataEntry("Foundation", 94190));
        data.add(new ValueDataEntry("Mascara", 102610));
        data.add(new ValueDataEntry("Lip gloss", 110430));
        data.add(new ValueDataEntry("Lipstick", 128000));
        data.add(new ValueDataEntry("Nail polish", 143760));
        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
        data.add(new ValueDataEntry("Eyeliner", 213210));
        data.add(new ValueDataEntry("Eyeshadows", 249980));

        CartesianSeriesColumn column = cartesian.column(data);


       /* Tooltip tp = new Tooltip().setTitle("{%X}").setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
                .setOffsetX(0d)
                .setOffsetY(5d)
                .setFormat("${%Value}{groupsSeparator: }");
        //column.setTooltip(tp);
        column.getTooltip()
                .setTitle("{%X}")
                .setPosition(Position.CENTER_BOTTOM)
                .setAnchor(EnumsAnchor.CENTER_BOTTOM)
                .setOffsetX(0d)
                .setOffsetY(5d)
                .setFormat("${%Value}{groupsSeparator: }");



        /*column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.setAnimation(true);
        //cartesian.animation();

        cartesian.setTitle(("Top 10 Cosmetic Products by Revenue"));
       // cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.getYScale().setMinimum(0d);
        //cartesian.yScale().minimum(0d);


       // cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
        cartesian.getYAxis().setLabels("${%Value}{groupsSeparator: }");

        cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        //cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.getInteractivity().setHoverMode(HoverMode.BY_X);
       // cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.getXAxis().setTitle("Product");
        //cartesian.xAxis(0).title("Product");

        cartesian.getYAxis().setTitle("Revenue");
        //cartesian.yAxis(0).title("Revenue");

        anyChartView.setChart(cartesian);

    }

    */