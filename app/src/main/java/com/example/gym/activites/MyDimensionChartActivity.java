package com.example.gym.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
/*
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
import com.anychart.anychart.ValueDataEntry;*/
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.ui.ChartScroller;
import com.anychart.enums.HoverMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.gym.Constants;
import com.example.gym.Dialogs;
import com.example.gym.PerformNetworkRequest;
import com.example.gym.R;
import com.example.gym.dataClasses.Dimensions;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.skydoves.powerspinner.SpinnerAnimation;


import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;


public class MyDimensionChartActivity extends AppCompatActivity {

    private ArrayList<Date> dateList = new ArrayList<>();
    private ArrayList<Double> weightList = new ArrayList<>();
 //   private ArrayList<Integer> adiposeTissueList = new ArrayList<>();

    List<DataEntry> weightDataList = new ArrayList<>();
    List<DataEntry> heightDataList = new ArrayList<>();
    List<DataEntry> adiposeTissueList = new ArrayList<>();
    List<DataEntry> muscleTissueList= new ArrayList<>();
    List<DataEntry> bodyWaterPercentageList = new ArrayList<>();


    IntentFilter filter;
    private ArrayList<Dimensions> dimensionsArrayList = new ArrayList<>();
    private static final String GET_USER_ALL_DIMENSIONS="getUserAllDimensionsForChart";
    private SpotsDialog progressDialog;

    Cartesian cartesian;
   AnyChartView anyChartView;

    final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dimension_chart);
        spinnerDataNamesInit();


       // arrayListsInit();

        filter = new IntentFilter(); //utworzenie filtru zamiaru
        filter.addAction(GET_USER_ALL_DIMENSIONS); //dodanie akcji od pobierania informacji o użytkownikach

        //AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        getUserAllDimensions();


    }

    private void chartInit(){
        anyChartView = findViewById(R.id.any_chart_view);
        // anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        //Cartesian cartesian = AnyChart.column();

        //cartesian = AnyChart.line();
        cartesian = AnyChart.column();

        //final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

      /*  List<DataEntry> data = new ArrayList<>();
        for(int i=0; i<dateList.size();i++){
            data.add(new ValueDataEntry(sdf.format(dateList.get(i)), weightList.get(i)));
        }*/

        //final CartesianSeriesColumn column = cartesian.column(data);
        //Column column = cartesian.column(data);
        cartesian.data(weightDataList);

        // cartesian.setAnimation(true);
        cartesian.animation(true);

        // cartesian.setTitle(("Pomiary wagi"));
        cartesian.title(getString(R.string.MyDimensionsChartWeightDimensions));

        //cartesian.getYScale().setMinimum(0d);
        cartesian.yScale().minimum(0d);


        //cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
        cartesian.yAxis(0).labels().format("{%Value}");
        //cartesian.getYAxis().setLabels("${%Value}{groupsSeparator: }");

        //cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.tooltip().format("{%Value}kg");

        //cartesian.getInteractivity().setHoverMode(HoverMode.BY_X);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        //cartesian.getXAxis().setTitle("Data pomiaru");
        cartesian.xAxis(0).title(getString(R.string.MyDimensionsChartDimensionsDate));

        //cartesian.getYAxis().setTitle("Waga [kg]");
        cartesian.yAxis(0).title(getString(R.string.MyDimensionsChartWeightKg));

        //cartesian.setXScroller(true);
        cartesian.xScroller(true);
        ChartScroller chartScroller = cartesian.xScroller();
 //       chartScroller.autoHide(true);
        chartScroller.height((double)30);

        //  OrdinalZoom xZoom = cartesian.xZoom();
        // xZoom.setToPointsCount((double)4, false, null);

        anyChartView.setChart(cartesian);
        Log.e("t","uy");

        anyChartView.setZoomEnabled(true);
        anyChartView.canScrollHorizontally(1);
    }

        private void spinnerDataNamesInit(){
            PowerSpinnerView powerSpinnerView = findViewById(R.id.powerSpinnerDataNames);


            List<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
            iconSpinnerItems.add(new IconSpinnerItem(getString(R.string.MyDimensionsChartWeightName),null));
            iconSpinnerItems.add(new IconSpinnerItem( getString(R.string.MyDimensionsChartHeightName),null));
            iconSpinnerItems.add(new IconSpinnerItem( getString(R.string.MyDimensionsChartAdiposeTissueName), null));
            iconSpinnerItems.add(new IconSpinnerItem(getString(R.string.MyDimensionsChartMuscleTissueName),null));
            iconSpinnerItems.add(new IconSpinnerItem( getString(R.string.MyDimensionsChartBodyWaterPercentageName),null));

            IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(powerSpinnerView);
            if(powerSpinnerView!=null)
            powerSpinnerView.setSpinnerAdapter(iconSpinnerAdapter); //to powoduje błąd, ale nie wiem dlaczego
            powerSpinnerView.setItems(iconSpinnerItems);

            powerSpinnerView.setSpinnerPopupAnimation(SpinnerAnimation.FADE);
            powerSpinnerView.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<IconSpinnerItem>() {
                @Override
                public void onItemSelected(int i, @Nullable IconSpinnerItem iconSpinnerItem, int i1, IconSpinnerItem t1) {
                    switch (i1){
                        case 0:
                            cartesian.title((getString(R.string.MyDimensionsChartWeightDimensions)));
                            cartesian.yAxis(0).title(getString(R.string.MyDimensionsChartWeightKg));
                            cartesian.xAxis(0).title(getString(R.string.MyDimensionsChartDimensionsDate));
                            cartesian.tooltip().format("{%Value}kg");
                            cartesian.data(weightDataList);
                            break;

                        case 1:
                            cartesian.title((getString(R.string.MyDimensionsChartHeightDimensions)));
                            cartesian.yAxis(0).title(getString(R.string.MyDimensionsChartHeightCm));
                            cartesian.xAxis(0).title(getString(R.string.MyDimensionsChartDimensionsDate));
                            cartesian.tooltip().format("{%Value}cm");
                            cartesian.data(heightDataList);
                            break;

                        case 2:
                            cartesian.title((getString(R.string.MyDimensionsChartAdiposeTissueDimensions)));
                            cartesian.yAxis(0).title(getString(R.string.MyDimensionsChartAdiposeTissuePercentage));
                            cartesian.xAxis(0).title(getString(R.string.MyDimensionsChartDimensionsDate));
                            cartesian.tooltip().format("{%Value}%");
                            cartesian.data(adiposeTissueList);
                            break;

                        case 3:
                            cartesian.title((getString(R.string.MyDimensionsChartMuscleTissueDimensions)));
                            cartesian.yAxis(0).title(getString(R.string.MyDimensionsChartMuscleTissuePercentage));
                            cartesian.xAxis(0).title(getString(R.string.MyDimensionsChartDimensionsDate));
                            cartesian.tooltip().format("{%Value}%");
                            cartesian.data(muscleTissueList);
                            break;

                        case 4:
                            cartesian.title((getString(R.string.MyDimensionsChartBodyWaterDimensions)));
                            cartesian.yAxis(0).title(getString(R.string.MyDimensionsChartBodyWaterPercentage));
                            cartesian.xAxis(0).title(getString(R.string.MyDimensionsChartDimensionsDate));
                            cartesian.tooltip().format("{%Value}%");
                            cartesian.data(bodyWaterPercentageList);
                            break;
                    }
                }

               /* @Override
                public void onItemSelected(int i, IconSpinnerItem iconSpinnerItem) {

                    switch (i){
                        case 0:
                            cartesian.title(("Pomiary wagi"));
                            cartesian.yAxis(0).title("Waga [kg]");
                            cartesian.xAxis(0).title("Data pomiaru");
                            cartesian.tooltip().format("{%Value}kg");
                            cartesian.data(weightDataList);
                            break;

                        case 1:
                            cartesian.title(("Pomiary wzrostu"));
                            cartesian.yAxis(0).title("Wzrost [cm]");
                            cartesian.xAxis(0).title("Data pomiaru");
                            cartesian.tooltip().format("{%Value}cm");
                            cartesian.data(heightDataList);
                            break;

                        case 2:
                            cartesian.title(("Pomiary poziomu tkanki tłuszczowej"));
                            cartesian.yAxis(0).title("Poziom tkanki tłuszczowej [%]");
                            cartesian.xAxis(0).title("Data pomiaru");
                            cartesian.tooltip().format("{%Value}%");
                            cartesian.data(adiposeTissueList);
                            break;

                        case 3:
                            cartesian.title(("Pomiary poziomu tkanki mięśniowej"));
                            cartesian.yAxis(0).title("Poziomu tkanki mięśniowej [%]");
                            cartesian.xAxis(0).title("Data pomiaru");
                            cartesian.tooltip().format("{%Value}%");
                            cartesian.data(muscleTissueList);
                            break;

                        case 4:
                            cartesian.title(("Poziom wody w ciele"));
                            cartesian.yAxis(0).title("Poziom wody w ciele [%]");
                            cartesian.xAxis(0).title("Data pomiaru");
                            cartesian.tooltip().format("{%Value}%");
                            cartesian.data(bodyWaterPercentageList);
                            break;
                    }
                }*/

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
            dateList.add(new Date(2021-1900,6,24));

            weightList.add(70.0);
            weightList.add(73.0);
            weightList.add(74.0);
            weightList.add(79.0);
            weightList.add(71.0);
            weightList.add(89.0);
            weightList.add(60.0);
            weightList.add(90.0);

     /*       adiposeTissueList.add(10);
            adiposeTissueList.add(15);
            adiposeTissueList.add(17);
            adiposeTissueList.add(14);
            adiposeTissueList.add(13);
            adiposeTissueList.add(11);
            adiposeTissueList.add(10);
*/
            }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras(); //pobranie pakunku danych z zamiaru
            if (Objects.equals(intent.getAction(), GET_USER_ALL_DIMENSIONS)) {
                try {
                    String jsonstr =bundle.getString("JSON");
                    JSONObject json = new JSONObject(jsonstr);
                    if(json.isNull(Constants.NETWORK_ERROR_TAG)){
                    JSONArray jsonArray = json.getJSONArray("dimensionsData");
                    //JSONObject userJson = json.getJSONObject("dimensionsData");
                    jsonArray.getJSONObject(1);
                    for(int i=0;i<jsonArray.length();i++){
                        Dimensions dimensions = new Dimensions(jsonArray.getJSONObject(i));
                        dimensionsArrayList.add(dimensions);
                    }

                    for(int i=dimensionsArrayList.size()-1; i>=0;i--){
                        if(dimensionsArrayList.get(i).getWeight()!=null) {
                            int weight = Integer.parseInt(dimensionsArrayList.get(i).getWeight());
                            weightDataList.add(new ValueDataEntry(sdf.format(dimensionsArrayList.get(i).getDate()), weight));
                        }
                        if(dimensionsArrayList.get(i).getHeight()!=null){
                            int height = Integer.parseInt(dimensionsArrayList.get(i).getHeight());
                            heightDataList.add(new ValueDataEntry(sdf.format(dimensionsArrayList.get(i).getDate()),height));
                        }
                        if(dimensionsArrayList.get(i).getAdiposeTissue()!=null){
                            int adiposeTissue = Integer.parseInt(dimensionsArrayList.get(i).getAdiposeTissue());
                            adiposeTissueList.add(new ValueDataEntry(sdf.format(dimensionsArrayList.get(i).getDate()),adiposeTissue));
                        }
                        if(dimensionsArrayList.get(i).getMuscleTissue()!=null){
                            int muscleTissue = Integer.parseInt(dimensionsArrayList.get(i).getMuscleTissue());
                            muscleTissueList.add(new ValueDataEntry(sdf.format(dimensionsArrayList.get(i).getDate()),muscleTissue));
                        }
                        if(dimensionsArrayList.get(i).getBodyWaterPercentage()!=null){
                            int bodyWaterPercentage = Integer.parseInt(dimensionsArrayList.get(i).getBodyWaterPercentage());
                            bodyWaterPercentageList.add(new ValueDataEntry(sdf.format(dimensionsArrayList.get(i).getDate()),bodyWaterPercentage));
                        }

                    }

                    //Log.e("object1:",jsonArray.getJSONObject(1).toString() );
                    Dimensions dimensions = new Dimensions(jsonArray.getJSONObject(1));
                    Log.e("dimensions: ",dimensions.getStringDimension());
                    Log.e("js",jsonstr);
                    //Log.e("userjs: ",userJson.toString());
                    Log.e("jsonArray: ",jsonArray.toString());

                    chartInit();
                    } else {
                        Dialogs.noNetworkFinishDialog(context, MyDimensionChartActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unregisterReceiver(broadcastReceiver);
                //if (dialog.isShowing()) {
                //     dialog.dismiss();
                //}
                progressDialog.dismiss();
            }
        }
    };


    private void getUserAllDimensions(){
        registerReceiver(broadcastReceiver, filter);
        if(getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE)!=null && getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_USER_ID,-1)!=-1){
            int userId=getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE).getInt(Constants.SP_USER_ID,-1);
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", String.valueOf(userId));
            PerformNetworkRequest request = new PerformNetworkRequest(Constants.URL_GET_USER_ALL_DIMENSIONS, params, Constants.CODE_POST_REQUEST, this, GET_USER_ALL_DIMENSIONS);
            request.execute();
            progressDialog = new SpotsDialog(this, R.style.Custom);
            progressDialog.show();
        } else {
            Log.e("getUserAllDimensions()","nullException");
        }

    }
}

 /*   final int delayMillis = 500;
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            List<DataEntry> data = new ArrayList<>();
            data.add(new ValueDataEntry(sdf.format(dateList.get(0)), weightList.get(0)));
            data.add(new ValueDataEntry(sdf.format(dateList.get(1)), weightList.get(1)));

            cartesian.data(data);

            handler.postDelayed(this, delayMillis);
        }
    };
                    handler.postDelayed(runnable, delayMillis);
*/