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
      String releaseDate;
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
            releaseDate = res.getString("release_date");
            rating = res.getDouble("rating");
            movies.add(new Movie(title, description, genre, length, releaseDate, rating));
         }
         return movies;
      } catch (SQLException ex) {
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLException: " + ex.getSQLState());
         System.out.println("SQLException: " + ex.getErrorCode());
      } catch (Exception e) {
         System.out.println("Exception: " + e.getMessage());
      }
      return null;
   }

   public void putMovie(Movie movie) throws SQLException, ClassNotFoundException {
      Connection connection = ConnectionFactory.getConnection();
      Statement stmt = connection.createStatement();
      String title = movie.getTitle();
      String description = movie.getDescription();
      String genre = movie.getGenre();
      String length = movie.getLength();
      String releaseDate = movie.getReleaseDate();
      double rating = movie.getRating();
      description = description.replaceAll("\"", "");
      description = description.replaceAll("'", "");
      String query = String.format("INSERT INTO `movies` VALUES ('%s', '%s', '%s', '%s', '%s', '%s');", 
              title, description, genre, length, releaseDate, rating);
      stmt.execute(query);
   }
}
