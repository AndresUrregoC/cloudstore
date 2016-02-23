package com.grability.cloudstore.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by AndresDev on 21/02/16.
 */
@DatabaseTable(tableName = "app")
public class App {

    @DatabaseField(canBeNull = false)
    private int ID;

    @DatabaseField(canBeNull = false)
    private String NAME;

    @DatabaseField(canBeNull = false)
    private String COMPANY;

    @DatabaseField(canBeNull = false)
    private String IMAGE;

    @DatabaseField(canBeNull = false)
    private String SUMMARY;

    @DatabaseField(canBeNull = false)
    private String RIGHTS;

    @DatabaseField(canBeNull = false)
    private String CATEGORY;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public String getRIGHTS() {
        return RIGHTS;
    }

    public void setRIGHTS(String RIGHTS) {
        this.RIGHTS = RIGHTS;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }
}
