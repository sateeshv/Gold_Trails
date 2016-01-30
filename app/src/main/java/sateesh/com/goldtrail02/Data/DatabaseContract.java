package sateesh.com.goldtrail02.Data;

import android.net.Uri;

/**
 * Created by Sateesh on 1/18/2016.
 */
public class DatabaseContract {

    public static final String AUTHORITY = "sateesh.com.goldtrail02";
    public static final Uri BASIC_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_CLASSINFO = "ClassInfo";
    public static final String PATH_CITYINFO = "CityInfo";


    public static class ClassInfo{
        public static final int LIMIT = 7;
        public static final Uri CONTENT_URI = BASIC_URI.buildUpon().appendPath(PATH_CLASSINFO).build();
        public static final String TABLE_NAME = "ClassInfo";

        public static final String _ID = "_id";
        public static final String COLUMN_CREATED_DATE = "CreatedDate";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_SNO = "SNo";
        public static final String COLUMN_ROLLNO = "RollNo";
        public static final String COLUMN_STUDENTNAME = "StudentName";
        public static final String COLUMN_AGE = "Age";
        public static final String COLUMN_GENDER = "Gender";
        public static final String COLUMN_CITY = "City";
        public static final String COLUMN_STATE = "State";

    }

    public static class CityInfo{
        public static final Uri CONTENT_URI = BASIC_URI.buildUpon().appendPath(PATH_CITYINFO).build();
        public static final String TABLE_NAME = "CityInfo";

        public static final String _ID = "_id";
        public static final String COLUMN_CITYNAME = "CityName";

    }
}
