package com.xxx.skynet.saveData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/10/20.
 * Android 操作SQLite数据库（初步）-在程序中删除数据库 http://blog.csdn.net/qiuzhping/article/details/17660111
 */

public class SqliteHelper extends SQLiteOpenHelper{
    private static final String TAG = "SqliteHelper";
    private static final String types[] = {getClassShortName(byte.class),getClassShortName(short.class),getClassShortName(int.class),getClassShortName(long.class),
            getClassShortName(Byte.class),getClassShortName(Short.class),getClassShortName(Integer.class),getClassShortName(Long.class),
            getClassShortName(float.class),getClassShortName(long.class), getClassShortName(Float.class),getClassShortName(Double.class),
            getClassShortName(String.class)};
    private static final int VERSION = 1;
    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        /*try {
            context.deleteDatabase(getClassShortName(Class.forName(name)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder("create table ");
        try {
            Class cls = Class.forName(getDatabaseName());
            sql.append(getClassShortName(cls)).append("(id int primary key autoincrement,").append(getMemberNames(cls));//append("(id int primary key autoincrement,name varchar(32),phone varchar(11),account varchar(32),password varchar(32))");
            Log.i(TAG,"onCreate创建sqlite->"+sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"onUpgrade更新sqlite->");
    }
    private String getMemberNames(Class<?> cls){
        StringBuilder sb = new StringBuilder();
        Field[] fields = cls.getDeclaredFields();
        if(null != fields)
            for(Field field :fields)
                sb.append(field.getName()).append(" ").append(getType(field.getType())).append(",");
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    public static String getClassShortName(Class<?> cls){
        String classFullName = cls.getName();
        int start = classFullName.lastIndexOf('.');
        return classFullName.substring(start+1);
    }
    //SQL 数据类型 http://www.w3school.com.cn/sql/sql_datatypes.asp
    private static String getType(Class<?> cls){
        String type = getClassShortName(cls);
        if(type.endsWith(types[0])||type.endsWith(types[4]))
            return "byte";
        else if(type.endsWith(types[1])||type.endsWith(types[5]))
            return "short";
        else if (type.equals(types[2])||type.endsWith(types[6]))
            return "int(16)";
        else if(type.equals(types[3])||type.endsWith(types[7]))
            return "long(32)";
        else if(type.equals(types[8])||type.endsWith(types[10]))
            return "single(32)";
        else if (type.equals(types[9])||type.endsWith(types[11]))
            return"double(64)";
        else if (type.equals(types[12]))
            return"varchar(64)";
        return  null;
    }
}
