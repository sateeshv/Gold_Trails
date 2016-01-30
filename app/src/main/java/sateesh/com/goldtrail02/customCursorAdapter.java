package sateesh.com.goldtrail02;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import sateesh.com.goldtrail02.Data.DatabaseContract;

/**
 * Created by Sateesh on 26-01-2016.
 */
public class customCursorAdapter extends CursorAdapter {
    public customCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        Log.v("Sateesh: ", "*** CursorAdapter Constructor reached");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.v("Sateesh: ", "*** newView reached");
        Log.v("Sateesh: ", "*** Cursor Data in newView: " + DatabaseUtils.dumpCursorToString(cursor));
        View view = LayoutInflater.from(context).inflate(R.layout.database_layout, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        view.setTag(holder);


        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView date = (TextView) view.findViewById(R.id.text0);
        TextView   rollNo = (TextView) view.findViewById(R.id.text1);
        TextView   studentName = (TextView) view.findViewById(R.id.text2);
        TextView    gender = (TextView) view.findViewById(R.id.text3);

        date.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_DATE)));
//        ViewHolder.rollNo.setText("");

//        String rawRollNo = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_ROLLNO)));
//        Log.v("Sateesh: ", "RawDate is: " + rawRollNo);
        rollNo.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_ROLLNO))));

//        ViewHolder.studentName.setText("");
        studentName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_STUDENTNAME)));
//        ViewHolder.gender.setText("");

        gender.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_CITY)));
//        Log.v("Sateesh: ", "*** bindView reached");
//        ViewHolder holder = (ViewHolder) view.getTag();
//        Log.v("Sateesh: ", "*** Cursor Data in bindView: " + DatabaseUtils.dumpCursorToString(cursor));
//        //holder.date.setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_DATE))));
//
//        String rawDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_DATE));
//        Log.v("Sateesh: ", "RawDate is: " + rawDate);
//        ViewHolder.date.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_DATE)));
//        ViewHolder.rollNo.setText("");
//
//        String rawRollNo = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_ROLLNO)));
//        Log.v("Sateesh: ", "RawDate is: " + rawRollNo);
//        ViewHolder.rollNo.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_ROLLNO))));
//
//        ViewHolder.studentName.setText("");
//        ViewHolder.studentName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_STUDENTNAME)));
//        ViewHolder.gender.setText("");
//        ViewHolder.gender.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClassInfo.COLUMN_CITY)));
    }
//    public static class ViewHolder{
//        public static TextView date;
//        public static TextView rollNo;
//        public static TextView studentName;
//        public static TextView gender;
//
//        public ViewHolder(View view) {
//            date = (TextView) view.findViewById(R.id.text0);
//            rollNo = (TextView) view.findViewById(R.id.text1);
//            studentName = (TextView) view.findViewById(R.id.text2);
//            gender = (TextView) view.findViewById(R.id.text3);
//        }
//    }
}
