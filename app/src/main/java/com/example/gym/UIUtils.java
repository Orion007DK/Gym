package com.example.gym;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UIUtils {

    public static boolean setListViewHeightBasedOnItems(ListView listView, int textViewId) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            listView.measure(View.MeasureSpec.UNSPECIFIED, 0);
            int width = listView.getMeasuredWidth();
            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);

                TextView textViewMealName = item.findViewById(textViewId);
                countLines(textViewMealName, width-(item.getPaddingEnd()+item.getPaddingStart())-20);
                //20 dla niewielkich rozbieżności w szerokości, które nie wynikają ani z margin, padding

                int height =  View.MeasureSpec.getSize(View.MeasureSpec.UNSPECIFIED);

                item.measure(0, height);



                //
                totalItemsHeight += item.getMeasuredHeight();

                // totalItemsHeight += item.getMeasuredHeight()+item.getPaddingTop()+item.getPaddingBottom();
                //totalItemsHeight += item.getPaddingBottom()+item.getPaddingTop();
                //totalItemsHeight +=20;

            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + (int)(totalItemsHeight*0.05);

            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }

    static private void countLines(TextView textView, int textSpaceWidth){

        String fullString = textView.getText().toString();
      /* WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
       Display display = wm.getDefaultDisplay();
       DisplayMetrics displayMetrics = new DisplayMetrics();
       display.getMetrics(displayMetrics);
       Log.e("listViewWidth", String.valueOf(paddings));
       Log.e("screenWidth", String.valueOf(displayMetrics.widthPixels));
       Log.e("textWidth", String.valueOf((displayMetrics.widthPixels-paddings)/2));
       //int screenWidth = (displayMetrics.widthPixels)/4; // get 1/4 screen width
       // int screenWidth = 300; // screen width*/
        //int screenWidth = paddings;
        float textWidth = textView.getPaint().measureText(fullString);
// this method will give you the total width required to display total String

        int numberOfLines = ((int) textWidth/textSpaceWidth) + 1;
// calculate number of lines it might take

        textView.setLines(numberOfLines);
    }

    public static int getHeight(Context context, CharSequence text, int textSize, int deviceWidth, Typeface typeface, int padding) {
        TextView textView = new TextView(context);
        textView.setPadding(padding,0,padding,padding);
        textView.setTypeface(typeface);
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView, int textViewId, Context context) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            listView.measure(View.MeasureSpec.UNSPECIFIED, 0);
            int width = listView.getMeasuredWidth();
            // Get total height of all items.
            int totalItemsHeight = 0;

            int deviceWidth;
            WindowManager wm = (WindowManager) listView.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
                Point size = new Point();
                display.getSize(size);
                deviceWidth = size.x;
            } else {
                deviceWidth = display.getWidth();
            }

            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);

                TextView textViewMealName = item.findViewById(textViewId);
                totalItemsHeight += getHeight(context, textViewMealName.getText(),(int)textViewMealName.getTextSize(), deviceWidth, Typeface.DEFAULT, textViewMealName.getCompoundPaddingRight());

                // totalItemsHeight += item.getMeasuredHeight()+item.getPaddingTop()+item.getPaddingBottom();
                //totalItemsHeight += item.getPaddingBottom()+item.getPaddingTop();
                //totalItemsHeight +=20;

            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = (totalItemsHeight + totalDividersHeight)/2;

            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }
    }

}
