package no.woact.bordan16.ticatac.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daniel on 13/04/2018.
 */

/**
 * Class that creates a database.
 */
public class SQDatabaseHelper extends SQLiteOpenHelper {
    //Her opptrettes det database navn, table navn og kolonne navn til senere bruk.
    public static final String DATABASE_NAME = "Player.db";
    public static final String TABLE_NAME = "Player_table";
    public static final String PLAYER_NAME = "NAME";
    public static final String PLAYER_SCORE = "SCORE";
    public static final String PLAYER_SYMBOL = "SYMBOL";
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;

    public SQDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sqLiteDatabase = this.getWritableDatabase();
        contentValues = new ContentValues();

    }

    /**
     * Logic that is executed in onCreate!
     * We create a new table with desired columns.
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creates a new table
        db.execSQL("create table " + TABLE_NAME + "(NAME TEXT PRIMARY KEY, SCORE INTEGER, SYMBOL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * Method that populates our table with desired columns.
     * We use ContentValues to insert our variables into the columns.
     * @param playerName playerName
     * @param score playerScore
     * @param symbol playerSymbol
     * @return Boolean if success
     */
    public boolean insertData(String playerName, int score, int symbol) {
        score += 1;
        sqLiteDatabase = getWritableDatabase();
        contentValues.put(PLAYER_NAME, playerName);
        contentValues.put(PLAYER_SCORE, score);
        contentValues.put(PLAYER_SYMBOL, symbol);
        long result = sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method that collects data from our database, if it exist.
     * @return Cursor data
     */
    public Cursor getAllData() {
        SQLiteDatabase sDb = this.getReadableDatabase();
        Cursor res = sDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    /**
     * Method that insert data to our database.
     * @param playerName playerName
     * @param score playerScore
     * @param symbol playerSymbol
     */
    public void updateDataBase(String playerName, int score, int symbol) {
        insertData(playerName, score, symbol);
    }

    /**
     * Method that clears our table.
     */
    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }
}
