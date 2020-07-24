package com.example.shopkeepers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.xml.sax.Parser;

public class MySQL_DataBase_helper extends SQLiteOpenHelper {
    // whole DataBase name
    private static final String DATA_BASE_NAME = "ShopKeepers_Payment_dueList.db";
    // DataBase Table1 Name and Column Name
    private static final String TABLE_NAME1 = "Shopkeeper_UserDetails";
    private static final String SHOPKEEPER_ID = "ID";
    private static final String SHOPKEEPER_NAME = "Shopkeeper_Name";
    private static final String SHOPKEEPER_USER_NAME = "Shopkeeper_User_Name";
    private static final String PASSWORD = "Password";
    private static final String FAQ1 = "FAQ1";
    private static final String FAQ2 = "FAQ2";
    private static final String FAQ3 = "FAQ3";
    private static  final String CREATE_TABLE1 = " CREATE TABLE " + TABLE_NAME1 + "( "+ SHOPKEEPER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                 + SHOPKEEPER_NAME + " VARCHAR(255) NOT NULL,"
                                                 + SHOPKEEPER_USER_NAME + " TEXT NOT NULL," + PASSWORD + " TEXT NOT NULL, " +FAQ1+ " TEXT NOT NULL,"
                                                 +FAQ2+ " TEXT NOT NULL," +FAQ3+ " TEXT NOT NULL);";
    private static final String DROP_TABLE1 = " DROP TABLE IF EXISTS "+TABLE_NAME1;

    //Database Table2 Name and Column Name
    private static final String TABLE_NAME2 = "Customer_List";
    private static final String CUSTOMER_LIST_ID = "CustomerList_ID";
    private static final String CUSTOMER_NAME = "Customer_Name";
    private static final String TOTAL_AMOUNT = "Total_Amount";
    private static final String CREATE_TABLE2 = " CREATE TABLE " + TABLE_NAME2 + "( "+ CUSTOMER_LIST_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + CUSTOMER_NAME +" TEXT NOT NULL," + TOTAL_AMOUNT +" TEXT )";
    private static final String DROP_TABLE2 = " DROP TABLE IF EXISTS "+TABLE_NAME2;

    //Database Table3 Name and Column Name
    private static final String TABLE_NAME3 = "Customer_Details";
    private static final String CUSTOMER_DETAILS_ID = "Customer_List_ID";
    private static final String SPECIFIC_CUSTOMER_ID = "Specific_Customer_ID";
    private static final String ITEM = "ITEM";
    private static final String AMOUNT = "Amount";
    private static final String DATE = "Date";
    private static final String CREATE_TABLE3 = " CREATE TABLE " + TABLE_NAME3 + "( "+ CUSTOMER_DETAILS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + ITEM +" TEXT ,"+ AMOUNT +" TEXT ," + DATE +" TEXT ,"
                                                +SPECIFIC_CUSTOMER_ID+" INTEGER  REFERENCES "+ CUSTOMER_LIST_ID +" (CUSTOMER_LIST_ID) ) ";

    private static final String DROP_TABLE3 = " DROP TABLE IF EXISTS "+TABLE_NAME3;


    private static final  int VERSION_NUMBER =13;
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
             sqLiteDatabase.execSQL(CREATE_TABLE2);
             sqLiteDatabase.execSQL(CREATE_TABLE3);
             Toast.makeText(context,"DataBase is created",Toast.LENGTH_SHORT).show();
         } catch (Exception e){
             Toast.makeText(context,"Exception "+e,Toast.LENGTH_LONG).show();
         }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            sqLiteDatabase.execSQL(DROP_TABLE1);
            sqLiteDatabase.execSQL(DROP_TABLE2);
            sqLiteDatabase.execSQL(DROP_TABLE3);
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

    public void Customer_Details_Name_Insertion(String name, String  total_Amount){
        SQLiteDatabase Customer_details_database = this.getWritableDatabase();
        String sql = "INSERT INTO Customer_List VALUES (NULL, ?, ?)";
        SQLiteStatement statement = Customer_details_database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,total_Amount);
        statement.executeInsert();
        Customer_details_database.close();

    }

    public void SpecificCustomer_Details_Insertion(String item, String amount, String date, String CustomerID){
        SQLiteDatabase Customer_details_database = this.getWritableDatabase();
        String sql = "INSERT INTO Customer_Details VALUES (NULL, ?, ?, ?, ?)";
        SQLiteStatement statement = Customer_details_database.compileStatement(sql);

        statement.clearBindings();
        statement.bindString(1,item);
        statement.bindString(2,amount);
        statement.bindString(3,date);
        statement.bindString(4,CustomerID);

        statement.executeInsert();
        Customer_details_database.close();

    }
    public Cursor GetCustomerName_TotalAmount(){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM Customer_List";
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public Cursor GetSpecific_Customer_Details(String ID){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM Customer_Details WHERE Specific_Customer_ID = "+ ID +"";
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public  Cursor GetTotal_Amount_Specific_CustomerDetails(String ID){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT Specific_Customer_ID, SUM(Amount) FROM Customer_Details  WHERE Specific_Customer_ID="+ID+" GROUP BY Specific_Customer_ID ";
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    public void Update_TotalAmount(int Id, String total_Amount){
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "UPDATE Customer_List SET Total_Amount = ? WHERE CustomerList_ID ="+Id+"";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.bindString(1,total_Amount);
        sqLiteStatement.execute();
        database.close();
    }


}
