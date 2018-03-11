package com.example.empower.empower;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

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

        LinearLayout linear = findViewById(R.id.linear);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        ArrayList<Integer> idList = new ArrayList<>();



        // getting spreadsheet details
        try {

            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelsheet));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();
            Button[] btn = new Button[rows];

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            for(int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {

                    double id = 0;
                    Boolean done = false;

                    String raw;
                    List<String> split;
                    List<String> splitTemp;

                    List<Integer> dates = new ArrayList<>();
                    List<Integer> times = new ArrayList<>();

                    List<Integer> datee = new ArrayList<>();
                    List<Integer> timee = new ArrayList<>();

                    String category = "";

                    double value;

                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell((short)c);
                        if(cell != null) {

                            switch(c){

                                case 0:
                                    id = cell.getNumericCellValue();

                                case 1:
                                    raw = cell.getStringCellValue();
                                    split = Arrays.asList(raw.split(" "));

                                    raw = split.get(0);
                                    splitTemp = Arrays.asList(raw.split("-"));
                                    dates = new ArrayList<>();
                                    for(String s : splitTemp) dates.add(Integer.valueOf(s));

                                    raw = split.get(1);
                                    splitTemp = Arrays.asList(raw.split(":"));
                                    times = new ArrayList<>();
                                    for(String s : splitTemp) times.add(Integer.valueOf(s));

                                    break;

                                case 2:
                                    raw = cell.getStringCellValue();
                                    split = Arrays.asList(raw.split(" "));

                                    raw = split.get(0);
                                    splitTemp = Arrays.asList(raw.split("/"));
                                    datee = new ArrayList<>();
                                    for(String s : splitTemp) datee.add(Integer.valueOf(s));

                                    raw = split.get(1);
                                    splitTemp = Arrays.asList(raw.split(":"));
                                    timee = new ArrayList<>();
                                    for(String s : splitTemp) timee.add(Integer.valueOf(s));

                                    break;

                                case 3:
                                    category = cell.getStringCellValue();
                                    break;

                                case 4:
                                    value = cell.getNumericCellValue();
                                    break;

                            }

                        }

                    }

                    for(int did : idList) {
                        if(did == id) {
                            done = true;
                        }
                        else {
                            done = false;
                        }
                    }

                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    int month = Calendar.getInstance().get(Calendar.MONTH);
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
                        btn[(int) id] = new Button(getApplicationContext());
                        btn[(int) id].setText(category);
                        //ADD TO ID
                    }

                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }


    }

    View.OnClickListener handleOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
            }
        };
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
