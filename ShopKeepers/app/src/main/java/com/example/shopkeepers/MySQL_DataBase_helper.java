package com.example.shopkeepers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
public class MySQL_DataBase_helper extends SQLiteOpenHelper {
    // whole DataBase name
    private static final String DATA_BASE_NAME = "ShopKeepers_Payment_dueList.db";
    // DataBase Table1 Name and Column Name
    private static final String TABLE_NAME1 = "Shopkeeper_UserDetails";
    private static final String ID = "_ID";
    private static final String SHOPKEEPER_NAME = "Shopkeeper_Name";
    private static final String SHOPKEEPER_USER_NAME = "Shopkeeper_User_Name";
    private static final String PASSWORD = "Password";
    private static final String FAQ1 = "FAQ1";
    private static final String FAQ2 = "FAQ2";
    private static final String FAQ3 = "FAQ3";
    private static  final String CREATE_TABLE1 = "CREATE TABLE " +TABLE_NAME1+ "( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +SHOPKEEPER_NAME+ " VARCHAR(255) NOT NULL,"
                          +SHOPKEEPER_USER_NAME+ " TEXT NOT NULL," +PASSWORD+ " TEXT NOT NULL, " +FAQ1+ " TEXT NOT NULL," +FAQ2+ " TEXT NOT NULL," +FAQ3+ " TEXT NOT NULL);";
    private static final String DROP_TABLE1 = "DROP TABLE IF EXISTS "+TABLE_NAME1;

    private static final  int VERSION_NUMBER =4;
    //NEED Context to pass this Context
    private Context context;
    public MySQL_DataBase_helper(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         try {
             sqLiteDatabase.execSQL(CREATE_TABLE1);
             Toast.makeText(context,"DataBase is created",Toast.LENGTH_SHORT).show();
         } catch (Exception e){
             Toast.makeText(context,"Exception "+e,Toast.LENGTH_LONG).show();
         }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            sqLiteDatabase.execSQL(DROP_TABLE1);
            onCreate(sqLiteDatabase);
            Toast.makeText(context,"DataBase is updated",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(context,"Exception "+e,Toast.LENGTH_LONG).show();
        }
    }

    public long ShopkeeperDetails_InsertData(ShopKeeper_UserDetails shopKeeper_userDetails){
        SQLiteDatabase ShopKeeper_database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SHOPKEEPER_NAME,shopKeeper_userDetails.getShopkeeper_Name());
        contentValues.put(SHOPKEEPER_USER_NAME,shopKeeper_userDetails.getShopKeeper_UserName());
        contentValues.put(PASSWORD,shopKeeper_userDetails.getShopKeeper_Password());
        contentValues.put(FAQ1,shopKeeper_userDetails.getFAQ1());
        contentValues.put(FAQ2,shopKeeper_userDetails.getFAQ2());
        contentValues.put(FAQ3,shopKeeper_userDetails.getFAQ3());

        long Row_ID = ShopKeeper_database.insert(TABLE_NAME1,null,contentValues );
        return Row_ID;
    }

    public boolean ShopKeeper_Identity_Check(String ShopKeeper_UserName, String ShopKeeper_Password){
        SQLiteDatabase ShopKeeper_Database = this.getReadableDatabase();
        Cursor cursor = ShopKeeper_Database.rawQuery(" SELECT * FROM " + TABLE_NAME1,null);
        boolean result = false;

        if (cursor.getCount() == 0){
            Toast.makeText(context,"Please Sign Up First",Toast.LENGTH_LONG).show();
        }else {

            while (cursor.moveToNext()){
                String UserName = cursor.getString(2);
                String Password = cursor.getString(3);
                if (UserName.equals(ShopKeeper_UserName) && Password.equals(ShopKeeper_Password)){
                    result = true;
                    break;

                }
            }
        }
        return result;
    }
}
