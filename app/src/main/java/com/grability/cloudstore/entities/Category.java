package com.grability.cloudstore.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by AndresDev on 21/02/16.
 */
@DatabaseTable(tableName = "category")
public class Category {

    @DatabaseField(canBeNull = false)
    private String ID;

    @DatabaseField(canBeNull = false)
    private String NAME;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
