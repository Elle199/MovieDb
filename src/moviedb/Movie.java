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

   public int getReleaseYear() {
      return releaseYear;
   }

   public int getAgeRestriction() {
      return ageRestriction;
   }

   public double getRating() {
      return rating;
   }
}
