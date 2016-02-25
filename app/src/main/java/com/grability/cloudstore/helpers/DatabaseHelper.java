package com.grability.cloudstore.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.entities.Category;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by AndresDev on 21/02/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "cloudstore.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Category, Integer> categoryDao = null;
    private Dao<App, Integer> appDao = null;

    private RuntimeExceptionDao<Category, Integer> categoryRuntimeDao = null;
    private RuntimeExceptionDao<App, Integer> appRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, App.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, App.class, true);
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Category, Integer> getCategoryDao() throws java.sql.SQLException {
        if (categoryDao == null)
            categoryDao = getDao(Category.class);

        return categoryDao;
    }

    public Dao<App, Integer> getAppDao() throws java.sql.SQLException {
        if(appDao == null)
            appDao = getDao(App.class);

        return appDao;
    }

    public RuntimeExceptionDao<Category, Integer> getCategoryRuntimeDao(){
        if (categoryRuntimeDao == null)
            categoryRuntimeDao = getRuntimeExceptionDao(Category.class);

        return categoryRuntimeDao;
    }

    public RuntimeExceptionDao<App, Integer> getAppRuntimeDao() {
        if (appRuntimeDao == null)
            appRuntimeDao = getRuntimeExceptionDao(App.class);

        return appRuntimeDao;
    }

    @Override
    public void close(){
        super.close();
        categoryDao = null;
        categoryRuntimeDao = null;
        appDao = null;
        appRuntimeDao = null;

    }
}
