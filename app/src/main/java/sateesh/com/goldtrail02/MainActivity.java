package sateesh.com.goldtrail02;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import sateesh.com.goldtrail02.Data.DatabaseContract;
import sateesh.com.goldtrail02.Network.ExitNoInternet;
import sateesh.com.goldtrail02.Network.ExitWithInternet;
import sateesh.com.goldtrail02.Network.InternetCheck;
import sateesh.com.goldtrail02.Network.LaunchNoInternet;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InternetCheck networkCheck = new InternetCheck(getApplicationContext());
        boolean connectingToInternet = networkCheck.isConnectingToInternet();

//        TextView internetCheck_tv = (TextView) findViewById(R.id.network_check);
        if(connectingToInternet == true){
            Log.v("Sateesh: ", "*** Internet is ON");
//            internetCheck_tv.setText("Connected");
        }else{
            Log.v("Sateesh: ", "*** Internet is OFF");
            LaunchNoInternet noInternet = new LaunchNoInternet();
            noInternet.show(getFragmentManager(), "LaunchNoInternet");

        }


        Button insert_records_btn = (Button) findViewById(R.id.insert_records);
        insert_records_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchSheetTask task = new FetchSheetTask(getApplicationContext());
                task.execute();
            }
        });

        Button view_records_btn = (Button) findViewById(R.id.view_records);
        view_records_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(getApplication(), DetailActivity.class);
                startActivity(detail);
            }
        });

        Button delete_records_btn = (Button) findViewById(R.id.delete_records);
        delete_records_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri_all_records = Uri.withAppendedPath(DatabaseContract.ClassInfo.CONTENT_URI, "0");
                getContentResolver().delete(uri_all_records, null, null);
                Uri city_uri = Uri.withAppendedPath(DatabaseContract.CityInfo.CONTENT_URI, "3");
                getContentResolver().delete(city_uri, null, null);
            }
        });

        Button export_db = (Button) findViewById(R.id.export_records);
        export_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();
                FileChannel source=null;
                FileChannel destination=null;
                String SAMPLE_DB_NAME = "ClassDetails.db";
                String currentDBPath = "/data/"+ "sateesh.com.goldtrail02" +"/databases/"+SAMPLE_DB_NAME;
                String backupDBPath = SAMPLE_DB_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                try {
                    source = new FileInputStream(currentDB).getChannel();
                    destination = new FileOutputStream(backupDB).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    source.close();
                    destination.close();
                    Toast.makeText(MainActivity.this, "DB Exported!", Toast.LENGTH_LONG).show();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button chart = (Button) findViewById(R.id.draw_chart);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chartActivity = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(chartActivity);
            }
        });

    }

    @Override
    public void onBackPressed() {

        InternetCheck networkCheck = new InternetCheck(getApplicationContext());
        boolean connectingToInternet = networkCheck.isConnectingToInternet();
        if(connectingToInternet == true){
            Log.v("Sateesh: ", "*** Internet is ON");
            ExitWithInternet exitWithInternet = new ExitWithInternet();
            exitWithInternet.show(getFragmentManager(), "ExitWithInternet");

        }else{
            Log.v("Sateesh: ", "*** Internet is OFF");
            ExitNoInternet exitNoInternet = new ExitNoInternet();
            exitNoInternet.show(getFragmentManager(), "ExitNoInternet");
        }
    }
}
