package com.android.project.chefschoice.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.android.project.chefschoice.Activities.MainActivity;
import com.android.project.chefschoice.DTO.ProductModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.android.project.chefschoice.DB.Constants.AVAILABILITY;
import static com.android.project.chefschoice.DB.Constants.DESCRIPTION;
import static com.android.project.chefschoice.DB.Constants.NAME;
import static com.android.project.chefschoice.DB.Constants.PRICE;
import static com.android.project.chefschoice.DB.Constants.TABLE_NAME;
import static com.android.project.chefschoice.DB.Constants.WEIGHT;

public class ProductData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;

    private static String[] FIELDS = {_ID, NAME, WEIGHT, PRICE, DESCRIPTION, AVAILABILITY};
    private static String ORDER_BY = NAME + " ASC";
    private static String[] SELECTION_ARGS_AVAILABILITY = new String[]{"Available"};
    private static String WHERE = _ID + "  = ?";

    public ProductData(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " VARCHAR(25), " +
                WEIGHT + " DECIMAL, " +
                PRICE + " DECIMAL, " +
                DESCRIPTION + " VARCHAR(150), " +
                AVAILABILITY + " VARCHAR(6) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addProduct(String name, double weight, double price, String description) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(WEIGHT, weight);
        values.put(PRICE, price);
        values.put(DESCRIPTION, description);
        values.put(AVAILABILITY, "Not Available");

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ProductModel> getProductList() {
        ArrayList<ProductModel> productModelArrayList = new ArrayList<ProductModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.query(TABLE_NAME, FIELDS, null, null, null,
                null, ORDER_BY);

        if (data.getCount() == 0) {
            Toast.makeText(MainActivity.getContext(), "No Data to Show",
                    Toast.LENGTH_SHORT).show();
        } else if (data.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(data.getInt(data.getColumnIndex(_ID)));
                productModel.setName(data.getString(data.getColumnIndex(NAME)));
                productModel.setWeight(data.getDouble(data.getColumnIndex(WEIGHT)));
                productModel.setPrice(data.getDouble(data.getColumnIndex(PRICE)));
                productModel.setDescription(data.getString(data.getColumnIndex(DESCRIPTION)));
                productModel.setAvailability(data.getString(data.getColumnIndex(AVAILABILITY)));

                productModelArrayList.add(productModel);
            } while (data.moveToNext());
        }
        data.close();
        return productModelArrayList;

    }

    public boolean editProducts(int id, String name, double weight, double price, String description, String availability) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(WEIGHT, weight);
        values.put(PRICE, price);
        values.put(DESCRIPTION, description);
        values.put(AVAILABILITY, availability);

        long result = db.update(TABLE_NAME, values, _ID + " = ?",
                new String[]{String.valueOf(id)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean setSelectedList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(AVAILABILITY, "Available");

        long result = db.update(TABLE_NAME, values, WHERE, new String[]{String.valueOf(id)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean setUnavailableList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(AVAILABILITY, "Not Available");

        long result = db.update(TABLE_NAME, values, WHERE, new String[]{String.valueOf(id)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ProductModel> getSelectedList() {
        ArrayList<ProductModel> productModelSelectedArrayList = new ArrayList<ProductModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECTION_AVAILABILITY = AVAILABILITY + " = ?";
        Cursor data = db.query(TABLE_NAME, FIELDS, SELECTION_AVAILABILITY, SELECTION_ARGS_AVAILABILITY,
                null, null, ORDER_BY);

        if (data.getCount() == 0) {
            Toast.makeText(MainActivity.getContext(), "No Data to Show",
                    Toast.LENGTH_SHORT).show();
        } else if (data.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(data.getInt(data.getColumnIndex(_ID)));
                productModel.setName(data.getString(data.getColumnIndex(NAME)));
                productModel.setWeight(data.getDouble(data.getColumnIndex(WEIGHT)));
                productModel.setPrice(data.getDouble(data.getColumnIndex(PRICE)));
                productModel.setDescription(data.getString(data.getColumnIndex(DESCRIPTION)));
                productModel.setAvailability(data.getString(data.getColumnIndex(AVAILABILITY)));

                productModelSelectedArrayList.add(productModel);
            } while (data.moveToNext());
        }
        data.close();
        return productModelSelectedArrayList;
    }

}
