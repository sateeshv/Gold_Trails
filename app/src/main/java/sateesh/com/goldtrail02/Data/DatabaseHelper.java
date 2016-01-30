package sateesh.com.goldtrail02.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Sateesh on 1/18/2016.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "ClassDetails.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        final String CREATE_CLASS_INFO_TABLE = "CREATE TABLE " + DatabaseContract.ClassInfo.TABLE_NAME  +
//                "( "  + DatabaseContract.ClassInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                DatabaseContract.ClassInfo.COLUMN_CREATED_DATE + " LONG NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_DATE + " LONG NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_SNO + " TEXT NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_ROLLNO + " INTEGER NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_STUDENTNAME + " TEXT NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_AGE + " TEXT NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_GENDER + " TEXT NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_CITY + " TEXT NOT NULL, " +
//                DatabaseContract.ClassInfo.COLUMN_STATE + " TEXT NOT NULL " + ");";
//
//        final String CREATE_CITY_INFO_TABLE = "CREATE TABLE " + DatabaseContract.CityInfo.TABLE_NAME +
//                "( " + DatabaseContract.CityInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                DatabaseContract.CityInfo.COLUMN_CITYNAME + "  TEXT UNIQUE NOT NULL " + ");";
//
//        db.execSQL(CREATE_CLASS_INFO_TABLE);
//        db.execSQL(CREATE_CITY_INFO_TABLE);
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ClassInfo.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.CityInfo.TABLE_NAME);
        onCreate(db);
    }
}
