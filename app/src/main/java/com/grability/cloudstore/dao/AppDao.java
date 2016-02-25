package com.grability.cloudstore.dao;

import android.content.Context;

import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.managers.DatabaseManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by AndresDev on 22/02/16.
 */
public class AppDao extends DatabaseManager {

    public AppDao(Context context) {
        super(context);
    }

    public void addApp(App app) throws SQLException {

        this.getHelper().getAppDao().create(app);
    }

    public List<App> getAllApps() throws SQLException {

        return this.getHelper().getAppDao().queryForAll();
    }

    public List<App> getAppsByCategory(String category) throws SQLException {

        return this.getHelper().getAppDao().queryBuilder().where().eq("CATEGORY", category).query();
    }

    public List<App> getAppById(int id) throws SQLException {

        return this.getHelper().getAppDao().queryBuilder().where().eq("ID", id).query();
    }

    public void deleteApps(){

        try {
            TableUtils.clearTable(this.getHelper().getConnectionSource(), App.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
