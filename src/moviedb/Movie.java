package moviedb;

public class Movie {
   private String title;
   private String description;
   private String genre;
   private String length;
   private int releaseYear;
   private int ageRestriction;
   private double rating;

   public Movie(String title, String description, String genre, String length, int releaseYear, int ageRestriction, double rating) {
      this.title = title;
      this.description = description;
      this.genre = genre;
      this.length = length;
      this.releaseYear = releaseYear;
      this.ageRestriction = ageRestriction;
      this.rating = rating;
   }
}
