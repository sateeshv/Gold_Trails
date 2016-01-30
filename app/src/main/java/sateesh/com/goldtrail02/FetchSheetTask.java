package sateesh.com.goldtrail02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sateesh.com.goldtrail02.Data.DatabaseContract;

/**
 * Created by Sateesh on 26-01-2016.
 */
public class FetchSheetTask extends AsyncTask<Void, Void, Void> {
    Context context;
    long startTime;
    public FetchSheetTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        startTime= System.currentTimeMillis();

        Uri uri = Uri.withAppendedPath(DatabaseContract.ClassInfo.CONTENT_URI, "1");
        Log.v("Sateesh: ", "*** URI link is: " + uri);
        Cursor cursorLastRecord = context.getContentResolver().query(uri, null, null, null, null);
        cursorLastRecord.moveToFirst();
        String lastInsertedDate = null;

        String KEY = "1GcFK97hJwja9GYAAWv24JeKi6PJ-e_SobMrGYquxWV4";
        String sheetURL = null;

        try {
            if (cursorLastRecord.getCount() != 0) {
                lastInsertedDate = cursorLastRecord.getString(cursorLastRecord.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_DATE));
                Log.v("Sateesh: ", "*** Last Inserted Date is: " + lastInsertedDate);
                sheetURL = "https://spreadsheets.google.com/feeds/list/" + KEY + "/od6/public/values?alt=json" + "&sq=date>" + lastInsertedDate;
            } else {
                Log.v("Sateesh: ", "*** No insertions till Now");
                sheetURL = "https://spreadsheets.google.com/feeds/list/" + KEY + "/od6/public/values?alt=json";
            }
        } catch (CursorIndexOutOfBoundsException e) {
            Log.v("Sateesh: ", "*** cursor Index Out of Bound");
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String sheetString = null;


        try {
            URL url = new URL(sheetURL.toString());
            /**
             * Create request to Google Spreadsheet
             */
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /**
             * Read inputStream to a String
             */
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            sheetString = buffer.toString();
            Log.v("Sateesh", "*** " + sheetString);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            getSheetDataFromJSON(sheetString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getSheetDataFromJSON(String sheetString) throws JSONException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();

        List<ContentValues> data;
        List<ContentValues> cityData;
        int numberOfRows;

        JSONObject mainloop = new JSONObject(sheetString);
        JSONObject feed = mainloop.getJSONObject("feed");

        JSONObject searchResultsObject = feed.getJSONObject("openSearch$totalResults");
        String searchResultsValue = searchResultsObject.getString("$t");
        int value = Integer.parseInt(searchResultsValue);
        if (value > 0) {
            Log.v("Sateesh: ", "New Data Available");
            JSONArray entry = feed.getJSONArray("entry");

            JSONObject searchTotalCount = feed.getJSONObject("openSearch$totalResults");
            numberOfRows = searchTotalCount.getInt("$t");

            data = new ArrayList<ContentValues>();
            cityData = new ArrayList<ContentValues>();

            for (int i = 0; i < numberOfRows; i++) {
                String[] rowData = new String[numberOfRows - 1];

                String SNo, date, rollNo, studentName, age, gender, city, state;

                JSONObject eachRow = entry.getJSONObject(i);

                JSONObject sNoObject = eachRow.getJSONObject("gsx$sno");
                SNo = sNoObject.getString("$t");

                JSONObject dateObect = eachRow.getJSONObject("gsx$date");
                date = dateObect.getString("$t");

                JSONObject rollnoObject = eachRow.getJSONObject("gsx$rollno");
                rollNo = rollnoObject.getString("$t");

                JSONObject studentNameObject = eachRow.getJSONObject("gsx$studentname");
                studentName = studentNameObject.getString("$t");

                JSONObject ageObject = eachRow.getJSONObject("gsx$age");
                age = ageObject.getString("$t");

                JSONObject genderObject = eachRow.getJSONObject("gsx$gender");
                gender = genderObject.getString("$t");

                JSONObject cityObject = eachRow.getJSONObject("gsx$city");
                city = cityObject.getString("$t");

                JSONObject stateObject = eachRow.getJSONObject("gsx$state");
                state = stateObject.getString("$t");

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.ClassInfo.COLUMN_SNO, SNo);
                values.put(DatabaseContract.ClassInfo.COLUMN_CREATED_DATE, dateFormat.format(todayDate));
                values.put(DatabaseContract.ClassInfo.COLUMN_DATE, date);
                values.put(DatabaseContract.ClassInfo.COLUMN_ROLLNO, rollNo);
                values.put(DatabaseContract.ClassInfo.COLUMN_STUDENTNAME, studentName);
                values.put(DatabaseContract.ClassInfo.COLUMN_AGE, age);
                values.put(DatabaseContract.ClassInfo.COLUMN_GENDER, gender);
                values.put(DatabaseContract.ClassInfo.COLUMN_CITY, city);
                values.put(DatabaseContract.ClassInfo.COLUMN_STATE, state);

                data.add(values);

                ContentValues cityValues = new ContentValues();
                cityValues.put(DatabaseContract.CityInfo.COLUMN_CITYNAME, city);
                cityData.add(cityValues);
                Log.v("Sateesh: ", "*** City Values are : " +cityData);

            }
            if (data.size() > 0) {
                ContentValues[] dataArray = new ContentValues[data.size()];
                ContentValues[] values = data.toArray(dataArray);
                Log.v("Sateesh: ", "**** content Values data " + values);

                Uri data_uri = Uri.withAppendedPath(DatabaseContract.ClassInfo.CONTENT_URI, "0");
                int insertedRecords = context.getContentResolver().bulkInsert(data_uri, dataArray);
                Log.v("Sateesh: ", "*** FetchSheetTask + Data Inserted Records: " + insertedRecords);
            }
            if (cityData.size() > 0) {
                ContentValues[] cityDataArray = new ContentValues[cityData.size()];
                ContentValues[] cityValues = cityData.toArray(cityDataArray);
                Log.v("Sateesh: ", "**** City Values data " + cityValues);

                Uri city_uri = Uri.withAppendedPath(DatabaseContract.CityInfo.CONTENT_URI, "3");
                int insertedRecords = context.getContentResolver().bulkInsert(city_uri, cityDataArray);
                Log.v("Sateesh: ", "*** FetchSheetTask + City Inserted Records: " + insertedRecords);
            }
            long endTime= System.currentTimeMillis();
            Log.v("Sateesh: " , "*** Time taken to insert " + (endTime - startTime));
        } else {
            Log.v("Sateesh: ", "NO New Data Available");
            long endTime= System.currentTimeMillis();
            Log.v("Sateesh: " , "*** Time taken to Check new Data Available " + (endTime - startTime));
        }



    }
}
