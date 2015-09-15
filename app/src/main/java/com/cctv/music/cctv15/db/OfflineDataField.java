package com.cctv.music.cctv15.db;

import java.sql.SQLException;
import android.content.Context;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="offline_data_field")
public class OfflineDataField {
    @DatabaseField(id=true)
    private String hash;
    @DatabaseField
    private String data;
    public OfflineDataField(String hash, String data) {
        super();
        this.hash = hash;
        this.data = data;
    }


    public OfflineDataField() {
        // TODO Auto-generated constructor stub
    }
    public String getData() {
        return data;
    }
    public static OfflineDataField getOffline(Context context, String hash){
        OfflineDataField dataField = null;
        final DataBaseHelper helper = new DataBaseHelper(context);
        try {
            dataField = helper.getOfflineDao().queryForId(hash);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataField;
    }

    public static void create(Context context, OfflineDataField field){
        final DataBaseHelper helper = new DataBaseHelper(context);
        try {
            helper.getOfflineDao().create(field);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }





}