package com.project.DbUtil;

import org.postgresql.ds.PGSimpleDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {

    public static Connection buildConnection() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("traffic_police_information_system");
        dataSource.setPortNumber(5432);
        return dataSource.getConnection();
    }
}
