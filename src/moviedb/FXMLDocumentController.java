package moviedb;

import com.mmdb.beans.MovieBean;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author maxangman
 */
public class FXMLDocumentController implements Initializable {
   
   @FXML
   private VBox movie_container;

   //New movie window elements
   @FXML
   private TextField add_movie_url;
   @FXML
   private TextField add_movie_title;
   @FXML
   private TextField add_movie_description;
   @FXML
   private TextField add_movie_genres;
   @FXML
   private TextField add_movie_length;
   @FXML
   private TextField add_movie_rating;
   @FXML
   private DatePicker add_movie_date;

   private MovieBean bean = new MovieBean();

   private static boolean isLoggedIn = false;
   private List<Movie> movies = new ArrayList<>();
   private Movie movie;
   private Node selectedMovie;

   @FXML
   private void doUpdate(ActionEvent event) {
      loadMovies();
   }

   @FXML
   private void openAddNew(ActionEvent event) {
      Parent root;
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("moviedb/AddMovie.fxml"));
         FXMLDocumentController controller = new FXMLDocumentController();
         loader.setController(controller);
         Stage stage = new Stage();
         stage.setTitle("MyMovieDb");
         stage.setScene(new Scene(loader.load(), 450, 450));
         stage.show();

         ((Node) (event.getSource())).getScene().getWindow().hide();
      } catch (Exception e) {
         System.out.println("New Movie Error: " + e.getMessage());
      }
   }

   @FXML
   private void closeAddNew(ActionEvent event) {
      Parent root;
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("moviedb/FXMLDocument.fxml"));
         FXMLDocumentController controller = new FXMLDocumentController();
         loader.setController(controller);
         Stage stage = new Stage();
         stage.setTitle("MyMovieDb");
         Scene scene = new Scene(loader.load(), 450, 450);
         stage.setScene(scene);
         stage.show();
         if (isLoggedIn == true) {
            scene.lookup("#signInPane").setVisible(false);
         }
         ((Node) (event.getSource())).getScene().getWindow().hide();
      } catch (Exception e) {
         System.out.println("Close Movie Error: " + e.getMessage());
      }
   }

   @FXML
   private void signIn(ActionEvent event) {
      loadMovies();
      ((Node) event.getTarget()).getScene().lookup("#signInPane").setVisible(false);
      isLoggedIn = true;
   }

   @FXML
   private void addMovie(ActionEvent event) throws SQLException, ClassNotFoundException {
      String title = add_movie_title.getText();
      String summery = add_movie_description.getText();
      String genre = add_movie_genres.getText();
      String length = add_movie_length.getText();
      String release = add_movie_date.getValue().toString();
      String rating = add_movie_rating.getText();
      movie = new Movie(title, summery, genre, length,
              release, Double.parseDouble(rating));
      bean.putMovie(movie);
      closeAddNew(event);
   }

   @FXML
   private void selectMovie(ActionEvent event) {
      selectedMovie = ((Node) event.getTarget()).getParent();
   }

   @FXML
   private void gatherMovie() {
      try {
         String url = add_movie_url.getText();
         Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
         Elements info = doc.select("div#title-overview-widget");

         String imdbTitle = info.select("div.title_wrapper h1").html();
         imdbTitle = imdbTitle.substring(0, imdbTitle.indexOf("&"));

         String imdbSummery = info.select("div.plot_summary div.summary_text").html();

         String rating = info.select("span[itemprop=ratingValue]").html();
         double ratingNum;
         if (!rating.equals("")) {
            ratingNum = Double.parseDouble(rating);
         } else {
            ratingNum = 0.0;
         }

         String release = info.select("div.title_wrapper div.subtext meta[itemprop=datePublished]").attr("content");

         String length = info.select("div.title_wrapper div.subtext time").html();
         if (!length.equals("")) {
            String lH = length.substring(0, length.indexOf("h"));
            String lM = length.substring(length.indexOf("h") + 2, length.indexOf("m"));
            length = lH + ":" + lM + ":00";
         } else {
            length = "00:00:00";
         }

         String genre = info.select("div.title_wrapper div.subtext a[href] span").html();
         if (!genre.equals("")) {
            genre = genre.replace("\n", ", ");
         }

         LocalDate date = LocalDate.parse(release);

         add_movie_title.setText(imdbTitle);
         add_movie_description.setText(imdbSummery);
         add_movie_genres.setText(genre);
         add_movie_rating.setText(String.valueOf(ratingNum));
         add_movie_length.setText(length);
         add_movie_date.setValue(date);
         add_movie_url.clear();

      } catch (Exception ex) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Gather Error");
         alert.setHeaderText("There seems to have been an error...");
         alert.setContentText("An error has occurred while trying to get \n"
                 + "information from provided site. \n" + "\nError\n" + ex.getMessage());
         add_movie_url.clear();
         alert.showAndWait();
      }
   }

   private void loadMovies() {
      List<Movie> movies = bean.getMovies();
      movie_container.getChildren().clear();
      for (int i = 0; i < movies.size(); i++) {
         GridPane grid = new GridPane();
         ImageView img = new ImageView();
         Label labelTitle = new Label(movies.get(i).getTitle());
         Label labelSummery = new Label(movies.get(i).getDescription());
         Label labelGenre = new Label(movies.get(i).getGenre());
         Label labelRating = new Label(String.valueOf(movies.get(i).getRating()));
         Label labelLength = new Label(movies.get(i).getLength());
         
         img.setFitHeight(98);
         grid.add(img, 0, 0);
         
         labelTitle.setAlignment(Pos.CENTER);
         labelTitle.setPadding(new Insets(0, 0, 0, 5));
         labelTitle.setTextFill(Paint.valueOf("#eeeeee"));
         labelTitle.setFont(Font.font("System", 18));
         grid.add(labelTitle, 1, 0);
         grid.setValignment(labelTitle, VPos.TOP);
         
         labelSummery.setAlignment(Pos.CENTER_LEFT);
         labelSummery.setPadding(new Insets(25, 5, 0, 5));
         labelSummery.setWrapText(true);
         labelSummery.setTextFill(Paint.valueOf("#eeeeee"));
         grid.add(labelSummery, 1, 0);
         grid.setValignment(labelSummery, VPos.TOP);
         
         labelGenre.setAlignment(Pos.TOP_CENTER);
         labelGenre.setPadding(new Insets(10, 5, 0, 0));
         labelGenre.setTextFill(Paint.valueOf("#eeeeee"));
         labelGenre.setTextAlignment(TextAlignment.CENTER);
         labelGenre.setWrapText(true);
         grid.add(labelGenre, 2, 0);
         grid.setHalignment(labelGenre, HPos.CENTER);
         grid.setValignment(labelGenre, VPos.TOP);
         
         labelRating.setAlignment(Pos.TOP_CENTER);
         labelRating.setPadding(new Insets(5, 5, 0, 0));
         labelRating.setTextFill(Paint.valueOf("#eeeeee"));
         grid.add(labelRating, 2, 0);
         grid.setHalignment(labelRating, HPos.CENTER);
         grid.setValignment(labelRating, VPos.CENTER);
         
         labelLength.setAlignment(Pos.BOTTOM_CENTER);
         labelLength.setPadding(new Insets(0, 5, 10, 0));
         labelLength.setTextFill(Paint.valueOf("#eeeeee"));
         grid.add(labelLength, 2, 0);
         grid.setHalignment(labelLength, HPos.CENTER);
         grid.setValignment(labelLength, VPos.BOTTOM);
         
         grid.setMinHeight(100);
         grid.setStyle("-fx-border-color:#fff;-fx-border-width:0 0 1 0;");
         
         ColumnConstraints col1 = new ColumnConstraints();
         col1.setMinWidth(70);
         col1.setMaxWidth(70);
         ColumnConstraints col2 = new ColumnConstraints();
         col2.setMinWidth(100);
         col2.setPrefWidth(427);
         col2.setHgrow(Priority.ALWAYS);
         ColumnConstraints col3 = new ColumnConstraints();
         col3.setMinWidth(100);
         col3.setMaxWidth(150);
         
         grid.getColumnConstraints().addAll(col1,col2,col3);
         
         movie_container.getChildren().add(grid);
      }
      movies.clear();
   }

   @Override
   public void initialize(URL url, ResourceBundle rb) {
   }

}
