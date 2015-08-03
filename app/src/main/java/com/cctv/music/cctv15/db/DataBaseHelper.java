package com.cctv.music.cctv15.db;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "com.cctv.music.db";

    private static final int DATABASE_VERSION = 1;

    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, InfoTable.class);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource arg1,
                          int arg2, int arg3) {
        try {
            TableUtils.dropTable(connectionSource, InfoTable.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Dao<InfoTable, Integer> infoDao;

    public Dao<InfoTable, Integer> getInfoDao() throws SQLException {
        if (infoDao == null) {
            infoDao = DaoManager.createDao(getConnectionSource(),
                    InfoTable.class);
        }
        return infoDao;
    }



}