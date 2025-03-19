package com.edu.duongdua.core.bandoan.Models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector
{
    public static Connection getConnection()
    {
        Connection connect = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quanannhat",
                    "root",
                    "");
            return connect;

        } catch (Exception e) {
            return null;
        }
    }
}
