package com.grability.cloudstore.dao;

import android.content.Context;

import com.grability.cloudstore.entities.App;
import com.grability.cloudstore.entities.Category;
import com.grability.cloudstore.managers.DatabaseManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by AndresDev on 22/02/16.
 */
public class CategoryDao extends DatabaseManager {

    public CategoryDao(Context context) {
        super(context);
    }

    public void addCategory(Category category) throws SQLException {

        this.getHelper().getCategoryDao().create(category);
    }

    public List<Category> getAllCategories() throws SQLException {

        return this.getHelper().getCategoryDao().queryForAll();
    }

    public void deleteCategories(){

        try {
            TableUtils.clearTable(this.getHelper().getConnectionSource(), Category.class);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


}
