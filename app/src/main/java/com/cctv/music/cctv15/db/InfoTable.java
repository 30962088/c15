package com.cctv.music.cctv15.db;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

@DatabaseTable(tableName = "info")
public class InfoTable {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private boolean read = false;

    public InfoTable() {
    }

    public InfoTable(int id, boolean read) {
        this.id = id;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public boolean isRead() {
        return read;
    }

    public static boolean isRead(Context context, int id){
        DataBaseHelper helper = new DataBaseHelper(context);
        InfoTable infoTable = null;
        boolean isRead = false;
        try {
            infoTable = helper.getInfoDao().queryForId(id);
            if(infoTable != null){
                isRead = infoTable.isRead();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isRead;

    }

    public static void setRead(Context context,int id,boolean isRead){
        DataBaseHelper helper = new DataBaseHelper(context);
        try {
            helper.getInfoDao().createOrUpdate(new InfoTable(id,isRead));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
