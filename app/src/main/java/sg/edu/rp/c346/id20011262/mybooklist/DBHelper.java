package sg.edu.rp.c346.id20011262.mybooklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BOOK = "Book";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";

    public DBHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_BOOK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_RATING + " INTEGER )";
        db.execSQL(createSongTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(db);
    }

    public long insertBook(String title, String category, String author, int year, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        long result = db.insert(TABLE_BOOK, null, values);
        db.close();
        return result;
    }

    public ArrayList<Book> getAllBook() {
        ArrayList<Book> songslist = new ArrayList<Book>();
        String selectQuery = "SELECT " + COLUMN_ID + "," + COLUMN_TITLE + "," + COLUMN_CATEGORY + "," + COLUMN_AUTHOR + "," + COLUMN_YEAR + "," + COLUMN_RATING
                + " FROM " + TABLE_BOOK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String category = cursor.getString(2);
                String author = cursor.getString(3);
                int year = cursor.getInt(4);
                int rating = cursor.getInt(5);

                Book newBook = new Book(id, title, category, author, year, rating);
                songslist.add(newBook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songslist;
    }

    public ArrayList<Book> getAllSongsByStars(int RatingFilter) {
        ArrayList<Book> booklist = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_CATEGORY, COLUMN_AUTHOR, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_RATING + ">= ?";
        String[] args = {String.valueOf(RatingFilter)};
        Cursor cursor;
        cursor = db.query(TABLE_BOOK, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String category = cursor.getString(2);
                String author = cursor.getString(3);
                int year = cursor.getInt(4);
                int rating = cursor.getInt(5);

                Book newBook = new Book(id, title, category, author, year, rating);
                booklist.add(newBook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return booklist;
    }

    public int updateBook(Book data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_CATEGORY, data.getCategory());
        values.put(COLUMN_AUTHOR, data.getAuthor());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_RATING, data.getRatings());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_BOOK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_BOOK, condition, args);
        db.close();
        return result;
    }

    public ArrayList<String> getYears() {
        ArrayList<String> codes = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_YEAR};
        Cursor cursor;
        cursor = db.query(true, TABLE_BOOK, columns, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                codes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return codes;
    }

    public ArrayList<Book> getAllBooksByYear(int yearFilter) {
        ArrayList<Book> booklist = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_CATEGORY, COLUMN_AUTHOR, COLUMN_YEAR, COLUMN_RATING};
        String condition = COLUMN_YEAR + "= ?";
        String[] args = {String.valueOf(yearFilter)};
        Cursor cursor;
        cursor = db.query(TABLE_BOOK, columns, condition, args, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String category = cursor.getString(2);
                String author = cursor.getString(3);
                int year = cursor.getInt(4);
                int rating = cursor.getInt(5);
                Book newBook = new Book(id, title, category, author, year, rating);
                booklist.add(newBook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return booklist;
    }
}
