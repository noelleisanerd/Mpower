package com.example.empower.empower;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final LinearLayout linear = findViewById(R.id.linear);

        final LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        ArrayList<Double> idList = new ArrayList<>();

        // getting spreadsheet details
        try {

            ArrayList<Button> btn = new ArrayList<>();

            InputStream is = this.getResources().openRawResource(R.raw.sample_list_filled);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String line;

            line = br.readLine();

            while((line = br.readLine()) != null) {

                double id = 0;
                Boolean done = false;

                String raw;
                List<String> split;
                List<String> splitTemp;
                List<String> splitDate;

                List<Integer> dates = new ArrayList<>();
                String times;
                List<Integer> datee = new ArrayList<>();
                String timee;

                String category = "";

                double value;



                split = Arrays.asList(line.split(","));



                id = Integer.valueOf(split.get(0));



                raw = split.get(1);
                splitTemp = Arrays.asList(raw.split(" "));
                splitDate = Arrays.asList(splitTemp.get(0).split("-"));



                for(String s : splitDate) {
                    dates.add(Integer.valueOf(s));
                }

                times = splitTemp.get(1);

                category = split.get(3);
                value = Double.valueOf(split.get(4));



                for(double did : idList) {
                    if(did == id) {
                        done = true;
                    }
                    else {
                        done = false;
                    }
                }



                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                int year = Calendar.getInstance().get(Calendar.YEAR);



                if(day == 31) {
                    day = 1;
                    if(month == 12) {
                        month = 1;
                        year = year + 1;
                    }
                    else {
                        month = month + 1;
                    }
                }
                else if(day == 30) {
                    if(month == 4 || month == 6 || month == 9 || month == 11) {
                        day = 1;
                        month = month + 1;
                    }
                    else {
                        day = 31;
                    }
                }
                else {
                    day = day + 1;
                }


                if(dates.get(0) <= year && dates.get(1) <= month && dates.get(2) <= day && !done) {
                    //MAKE A BUTTON
                    int index = 0;
                        ++index;







                        switch(category){

                            case "sleep":

                                final TextView sleep = new TextView(getApplicationContext());
                                sleep.setLayoutParams(param);
                                sleep.setTextSize(32);
                                sleep.setText("Have you taken your medication?");
                                sleep.setTextColor(Color.BLACK);
                                linear.addView(sleep, param);

                                Button temp = new Button(getApplicationContext());
                                temp.setText("You slept well!");
                                temp.setTextSize(28);
                                temp.setLayoutParams(param);
                                btn.add(temp);
                                linear.addView(temp, param);
                                temp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Button b = (Button) view;
                                        b.setVisibility(View.GONE);
                                        System.out.println("You slept well!");
                                        sleep.setVisibility(View.GONE);
                                    }


                                });

                                break;

                            case "take_med":

                                final TextView take_med = new TextView(getApplicationContext());
                                take_med.setLayoutParams(param);
                                take_med.setTextSize(32);
                                take_med.setText("Have you taken your medication?");
                                take_med.setTextColor(Color.BLACK);
                                linear.addView(take_med, param);

                                Button tempa = new Button(getApplicationContext());
                                tempa.setText(category);
                                tempa.setLayoutParams(param);
                                btn.add(tempa);
                                linear.addView(tempa, param);
                                tempa.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Button b = (Button) view;
                                        b.setVisibility(View.GONE);
                                        System.out.println("MEDS");
                                        take_med.setVisibility(View.GONE);
                                    }


                                });

                                break;

                            case "exercise":

                                TextView exercise = new TextView(getApplicationContext());
                                exercise.setLayoutParams(param);
                                exercise.setTextSize(32);
                                exercise.setText("Have you completed exercise?");
                                exercise.setTextColor(Color.BLACK);
                                linear.addView(exercise, param);

                                Button tempb = new Button(getApplicationContext());
                                tempb.setText(category);
                                tempb.setLayoutParams(param);
                                btn.add(tempb);
                                linear.addView(tempb, param);

                                break;

                            case "report_pain":

                                TextView report_pain = new TextView(getApplicationContext());
                                report_pain.setLayoutParams(param);
                                report_pain.setTextSize(32);
                                report_pain.setText("Do you have any pain?");
                                report_pain.setTextColor(Color.BLACK);
                                linear.addView(report_pain, param);

                                Button tempc = new Button(getApplicationContext());
                                tempc.setText(category);
                                tempc.setLayoutParams(param);
                                btn.add(tempc);
                                linear.addView(tempc, param);

                                break;

                            case "report_wellbeing":

                                TextView report_wellbeing = new TextView(getApplicationContext());
                                report_wellbeing.setLayoutParams(param);
                                report_wellbeing.setTextSize(32);
                                report_wellbeing.setText("How is your overall wellbeing?");
                                report_wellbeing.setTextColor(Color.BLACK);
                                linear.addView(report_wellbeing, param);

                                Button tempd = new Button(getApplicationContext());
                                tempd.setText(category);
                                tempd.setLayoutParams(param);

                                btn.add(tempd);
                                linear.addView(tempd, param);

                                break;



                        }


/*                    Button temp = new Button(getApplicationContext());
                    temp.setText(category);
                    temp.setLayoutParams(param);
                    btn.add(temp);
                    linear.addView(temp, param);*/
                    //ADD TO ID
                    idList.add(id);
                }



            }
        } catch(Exception err) {
            err.printStackTrace();
        }


    }

/*    View.OnClickListener handleOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                        TextView sleep = new TextView(getApplicationContext());
                        sleep.setTextSize(22);
                        sleep.setText("You've slept well today!");
                        sleep.setTextColor(Color.BLACK);
            }

        };
    }*/

    void handleOnClick(final Button button) {
       button.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v) {
                TextView sleep = new TextView(getApplicationContext());
                sleep.setTextSize(22);
                sleep.setText("You've slept well today!");
                sleep.setTextColor(Color.BLACK);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
