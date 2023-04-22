package com.nhom13.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {
    public static Connection openConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://localhost;databaseName=QLBUONBANHAISAN;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2";
        String user = "sa";
        String password = "123456";
        Connection connect = DriverManager.getConnection(connectionURL, user, password);
        return connect;
    }
}
