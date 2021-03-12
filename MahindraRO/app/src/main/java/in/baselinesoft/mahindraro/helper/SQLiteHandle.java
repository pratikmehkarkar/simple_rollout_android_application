package in.baselinesoft.mahindraro.helper;

/**
 * Created by Pratik on 16/03/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandle extends SQLiteOpenHelper
{

    private static final String TAG = SQLiteHandle.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "baseline";

    // Login table name
    private static final String TABLE_ADMIN = "admin";

    // Login Table Columns names
    String TXTA = "admin";
    String TXTP = "admin";
    private static final String KEY_AID = "aid";
    private static final String KEY_PASS = "pass";

    public SQLiteHandle(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "("
                + KEY_AID + " TEXT,"
                + KEY_PASS + " TEXT"+")";
        db.execSQL(CREATE_LOGIN_TABLE);

        //String INSERT_VAL = "INSERT INTO" + TABLE_ADMIN + "("+KEY_AID +","+KEY_PASS+")" + "VALUES" + "(" +TXTA+ ","+TXTP+")";
        String INSERT_VAL = "INSERT INTO admin (aid,pass) VALUES('mroadmin','myadmin111')" ; ;
        db.execSQL(INSERT_VAL);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database

    public void addadmin(String aid, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AID, aid); // Name
        values.put(KEY_PASS, pass); // Email
        // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }* */

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getadminDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_ADMIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("aid", cursor.getString(0));
            user.put("pass", cursor.getString(1));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


}