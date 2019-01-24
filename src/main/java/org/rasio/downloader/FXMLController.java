package org.rasio.downloader;

import de.jensd.fx.glyphs.octicons.OctIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    
    @FXML
    private Label title_name;
    @FXML
    private OctIconView btn_close;
    @FXML
    private VBox download_item;
    @FXML
    private AnchorPane title_bar;
    @FXML
    private Label btn_cancel;
    @FXML
    private Label btn_start;
    @FXML
    private TextField field_url;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        movableWindow();
        
        btn_close.setOnMouseClicked(event -> {
            Stage stage = (Stage) btn_close.getScene().getWindow();
            stage.close();
        });
        
        btn_start.setOnMouseClicked((MouseEvent event) -> {
            new DownloadItemController(field_url.getText());
        });

    }

    private void movableWindow() {
        title_bar.setOnMousePressed((MouseEvent event) -> {
            Stage stage = (Stage) title_bar.getScene().getWindow();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //move around here
        title_bar.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) title_bar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }


    
}
