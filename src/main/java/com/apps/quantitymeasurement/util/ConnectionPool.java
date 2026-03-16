package com.apps.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static ConnectionPool instance;

    private final List<Connection> pool = new ArrayList<>();

    private final int POOL_SIZE;

    private ConnectionPool(){

        try{

            ApplicationConfig config = ApplicationConfig.getInstance();

            Class.forName(config.get(ApplicationConfig.ConfigKey.DB_DRIVER));

            String url = config.get(ApplicationConfig.ConfigKey.DB_URL);
            String user = config.get(ApplicationConfig.ConfigKey.DB_USERNAME);
            String password = config.get(ApplicationConfig.ConfigKey.DB_PASSWORD);

            POOL_SIZE = config.getInt(
                    ApplicationConfig.ConfigKey.DB_POOL_SIZE,5);

            for(int i=0;i<POOL_SIZE;i++){

                Connection conn =
                        DriverManager.getConnection(url,user,password);

                pool.add(conn);
            }

        }
        catch(Exception e){
            throw new RuntimeException("Error creating connection pool",e);
        }
    }

    public static synchronized ConnectionPool getInstance(){

        if(instance == null){
            instance = new ConnectionPool();
        }

        return instance;
    }

    public synchronized Connection getConnection(){

        if(pool.isEmpty()){
            throw new RuntimeException("No DB connections available");
        }

        return pool.remove(pool.size()-1);
    }

    public synchronized void releaseConnection(Connection conn){
        pool.add(conn);
    }
}