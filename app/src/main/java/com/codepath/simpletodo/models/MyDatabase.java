package com.codepath.simpletodo.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by yingbwan on 8/10/2017.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 1;
}