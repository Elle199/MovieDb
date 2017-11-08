package com.mmdb.beans;

import com.mmdb.utilities.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import moviedb.Movie;

public class MovieBean {

   private List<Movie> movies = new ArrayList<>();

   public List getMovies() {
      String title;
      String description;
      String genre;
      String length;
      int releaseYear;
      int ageRestriction;
      double rating;

      try {
         Connection connection = ConnectionFactory.getConnection();
         Statement stmt = connection.createStatement();
         String query = "SELECT * FROM movies";
         ResultSet res = stmt.executeQuery(query);
         while (res.next()) {
            title = res.getString("title");
            description = res.getString("description");
            genre = res.getString("genre");
            length = res.getString("length");
            releaseYear = res.getInt("release_date");
            ageRestriction = res.getInt("age_restriction");
            rating = res.getDouble("rating");
            movies.add(new Movie(title, description, genre, length, releaseYear, ageRestriction, rating));
         }
         return movies;
      } catch (SQLException ex) {
         System.out.println("SQULException: " + ex.getMessage());
         System.out.println("SQULException: " + ex.getSQLState());
         System.out.println("SQULException: " + ex.getErrorCode());
      } catch (Exception e) {
         System.out.println("Exception: " + e.getMessage());
      }
      return null;
   }
}
