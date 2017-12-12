package moviedb;

public class Movie {
   private String title;
   private String description;
   private String genre;
   private String length;
   private String releaseDate;
   private double rating;

   public Movie(String title, String description, String genre, String length, String releaseDate, double rating) {
      this.title = title;
      this.description = description;
      this.genre = genre;
      this.length = length;
      this.releaseDate = releaseDate;
      this.rating = rating;
   }

   public String getTitle() {
      return title;
   }

   public String getDescription() {
      return description;
   }

   public String getGenre() {
      return genre;
   }

   public String getLength() {
      return length;
   }

   public String getReleaseDate() {
      return releaseDate;
   }

   public double getRating() {
      return rating;
   }
}
