package com.grability.cloudstore.managers;

import android.content.Context;

import com.grability.cloudstore.helpers.DatabaseHelper;

/**
 * Created by AndresDev on 21/02/16.
 */
public class DatabaseManager {

    private DatabaseHelper helper;

    public DatabaseManager(Context context){
        helper = new DatabaseHelper(context);
    }

    public DatabaseHelper getHelper() {
        return helper;
    }
}
