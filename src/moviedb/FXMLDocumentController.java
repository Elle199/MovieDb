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
   private void handleButtonAction(ActionEvent event) {
      System.out.println("You clicked me!");
   }

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
      System.out.println(bean.getMovies());
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
         System.out.println("Got Info");
         String title = info.select("div.title_wrapper h1").html();
         title = title.substring(0, title.indexOf("&"));
         System.out.println(title);
         
         

      } catch (Exception ex) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Gather Error");
         alert.setHeaderText("There seems to have been an error...");
         alert.setContentText("An error has occurred while trying to get \n"
                 + "information from provided site. \n" + "\nError\n" + ex.getMessage());
         alert.showAndWait();
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle rb) {

   }

}
