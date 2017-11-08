/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviedb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author maxangman
 */
public class MovieDb extends Application {
   
   @Override
   public void start(Stage stage) throws Exception {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
      FXMLDocumentController controller = new FXMLDocumentController();
      loader.setController(controller);
      
      Parent root = loader.load();
      
      Scene scene = new Scene(root);
      
      stage.setScene(scene);
      stage.setTitle("MMDb");
      stage.show();
   }
   
   public static void main(String[] args) {
      launch(args);
   }
   
}
