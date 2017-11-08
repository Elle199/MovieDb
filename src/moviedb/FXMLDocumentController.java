/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author maxangman
 */
public class FXMLDocumentController implements Initializable {

   @FXML
   private Label label;
   @FXML
   private TextField search_box;
   @FXML
   private TextField add_movie_url;

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
         e.printStackTrace();
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
         e.printStackTrace();
      }
   }

   @FXML
   private void signIn(ActionEvent event) {
      System.out.println(bean.getMovies());
      ((Node) event.getTarget()).getScene().lookup("#signInPane").setVisible(false);
      isLoggedIn = true;
   }

   @FXML
   public void gatherMovie() {
      try {
         String url = add_movie_url.getText();
         Document doc = Jsoup.connect(url).get();
         Elements info = doc.select("div#title-overview-widget");
         
      } catch (Exception ex) {
         System.out.println("ScrapException: " + ex.getMessage());
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle rb) {

   }

}
