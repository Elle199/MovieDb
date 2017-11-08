/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmdb.utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
   public static Connection getConnection() throws SQLException, ClassNotFoundException{
      String url = "jdbc:mysql://localhost/moviedblist";
      String user = "root";
      String password = "";
      Connection connection = (com.mysql.jdbc.Connection)DriverManager.getConnection(url, user, password);
      return connection;
   }
}
