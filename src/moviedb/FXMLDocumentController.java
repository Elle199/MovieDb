package moviedb;

import com.mmdb.beans.MovieBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author maxangman
 */
public class FXMLDocumentController implements Initializable {

   @FXML
   private Label label;
   @FXML
   private TextField search_box;
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
   private List<String> movies = new ArrayList<>();

   @FXML
   private void doSearch(ActionEvent event) {
      System.out.println(search_box.getText());
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

         ((Node)(event.getSource())).getScene().getWindow().hide();
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
      List<Movie> movies = bean.getMovies();
      for(int i = 0; i < movies.size(); i++){
         String title = movies.get(i).getTitle();
         
      }
      ((Node) event.getTarget()).getScene().lookup("#signInPane").setVisible(false);
      isLoggedIn = true;
   }

   @FXML
   private void gatherMovie() {
      try {
         String url = add_movie_url.getText();
         System.out.println(url);
         Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
         Elements info = doc.select("div#title-overview-widget");

         String imdbTitle = info.select("div.title_wrapper h1").html();
         imdbTitle = imdbTitle.substring(0, imdbTitle.indexOf("&"));

         String imdbSummery = info.select("div.plot_summary div.summary_text").html();

         String rating = info.select("div.metacriticScore.score_mixed.titleReviewBarSubItem span").html();
         double ratingNum;
         if (!rating.equals("")) {
            System.out.println("err");
            ratingNum = Double.parseDouble(rating) / 10;
            System.out.println(ratingNum);
         } else {
            ratingNum = 0.0;
         }

         String release = info.select("div.title_wrapper div.subtext a[title='See more release dates']").html();
         release = release.substring(0, release.indexOf("("));
         System.out.println(release);

         String length = info.select("div.title_wrapper div.subtext time").html();
         if (!length.equals("")) {
            String lH = length.substring(0, length.indexOf("h"));
            String lM = length.substring(length.indexOf("h") + 2, length.indexOf("m"));
            length = lH + ":" + lM + ":00";
            System.out.println(length);
         }else{
            length = "00:00:00";
         }

         String genre = info.select("div.title_wrapper div.subtext a[href] span").html();
         if (!genre.equals("")) {
            genre = genre.replace("\n", ", ");
         }
         System.out.println(genre);

         System.out.println(imdbTitle);
         add_movie_title.setText(imdbTitle);
         add_movie_description.setText(imdbSummery);
         add_movie_genres.setText(genre);
         add_movie_rating.setText(String.valueOf(ratingNum));
         add_movie_length.setText(length);
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

   @Override
   public void initialize(URL url, ResourceBundle rb) {

   }

}
