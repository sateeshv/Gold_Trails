package sateesh.com.goldtrail02;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import sateesh.com.goldtrail02.Data.DatabaseContract;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    customCursorAdapter cursorAdapter;
    SimpleCursorAdapter citySpinnerAdapter;
    String selectedCity ;
    String[] cityFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Spinner citySpinner = (Spinner) findViewById(R.id.city_spinnner);
        String[] fromColumns = {DatabaseContract.CityInfo.COLUMN_CITYNAME};
        int[] toColumns = {R.id.spinner_text_view};

        Uri city_uri = Uri.withAppendedPath(DatabaseContract.CityInfo.CONTENT_URI, "3");
        Cursor cityQuery = getContentResolver().query(city_uri, null, null, null, null);

        citySpinnerAdapter = new SimpleCursorAdapter(this, R.layout.city_spinner, cityQuery, fromColumns, toColumns, 0);
        citySpinner.setAdapter(citySpinnerAdapter);
        int count = citySpinner.getCount();


        ListView listView = (ListView) findViewById(R.id.list_items);
        cursorAdapter = new customCursorAdapter(getApplicationContext(), null, 0);
        listView.setAdapter(cursorAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int location = citySpinner.getSelectedItemPosition();
                String s = citySpinner.getAdapter().getItem(position).toString();

                if(citySpinner != null &&  citySpinner.getChildAt(0) != null){
                    TextView selectedTextView = (TextView) view.findViewById(R.id.spinner_text_view); // You may need to replace android.R.id.text1 whatever your TextView label id is
                    selectedCity = selectedTextView.getText().toString();
                    Log.v("Sateesh: ", "*** Selected option is: " + selectedCity);
                    Log.v("Sateesh: ", "*** Position is: " + position);

                    cityFilter = new String[1];
                    cityFilter[0] = selectedCity;
                    getLoaderManager().initLoader(position, null, DetailActivity.this);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int selectedItemId = (int) citySpinner.getSelectedItemPosition();
        Log.v("Sateesh: ", "*** selectedItem is: " + selectedItemId);



    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(DatabaseContract.ClassInfo.CONTENT_URI, "2");
//        Uri uri = DatabaseContract.ClassInfo.CONTENT_URI;
        Log.v("Sateesh: ", "*** onCreateLoader reached");
        String citySelection = DatabaseContract.ClassInfo.COLUMN_CITY + "=?";
//        String[] filter = {"Gudur", "Nellore", "Hyderabad"};
        String[] filter = cityFilter;
         Log.v("Sateesh: ", "*** Selection Param is: " + citySelection);
        return new CursorLoader(this, uri, null, citySelection, filter, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v("Sateesh: ", "*** onLoadFinished reached");
        Log.v("Sateesh: ", "*** Cursor Data in onLoadFinised: " + DatabaseUtils.dumpCursorToString(data));
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
