package net.micode.notes.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserManager {
    private UserDbHelper dbHelper;
    private Context context;
    private static final String PREF_NAME = "user_pref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERID = "user_id";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private static UserManager instance;

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    private UserManager(Context context) {
        this.context = context.getApplicationContext();
        this.dbHelper = UserDbHelper.getInstance(this.context);
    }

    // 注册新用户
    public boolean registerUser(String username, String password, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        
        // 检查用户名是否已存在
        Cursor cursor = db.query(
                UserDbHelper.TABLE_USERS,
                new String[]{UserDbHelper.COLUMN_ID},
                UserDbHelper.COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
                
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        
        if (exists) {
            return false; // 用户名已存在
        }
        
        // 创建新用户
        ContentValues values = new ContentValues();
        values.put(UserDbHelper.COLUMN_USERNAME, username);
        values.put(UserDbHelper.COLUMN_PASSWORD, password); // 简单实现，不加密
        if (email != null) {
            values.put(UserDbHelper.COLUMN_EMAIL, email);
        }
        
        long id = db.insert(UserDbHelper.TABLE_USERS, null, values);
        return id != -1;
    }
    
    // 登录验证
    public boolean login(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        Cursor cursor = db.query(
                UserDbHelper.TABLE_USERS,
                new String[]{UserDbHelper.COLUMN_ID, UserDbHelper.COLUMN_PASSWORD},
                UserDbHelper.COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
                
        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(UserDbHelper.COLUMN_ID);
            int passwordColumnIndex = cursor.getColumnIndex(UserDbHelper.COLUMN_PASSWORD);
            
            long id = cursor.getLong(idColumnIndex);
            String storedPassword = cursor.getString(passwordColumnIndex);
            cursor.close();
            
            if (password.equals(storedPassword)) {
                // 登录成功，保存登录状态
                saveLoginState(id, username, true);
                return true;
            }
        }
        
        if (cursor != null) {
            cursor.close();
        }
        
        return false;
    }
    
    // 保存登录状态
    private void saveLoginState(long id, String username, boolean isLoggedIn) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(KEY_USERID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
    
    // 检查是否已登录
    public boolean isLoggedIn() {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    // 获取当前登录用户名
    public String getCurrentUsername() {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_USERNAME, null);
    }
    
    // 获取当前登录用户ID
    public long getCurrentUserId() {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getLong(KEY_USERID, -1);
    }
    
    // 注销
    public void logout() {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
} 