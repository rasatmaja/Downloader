/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rasio.downloader;

import com.techweblearn.DownloadListener;
import com.techweblearn.Downloader;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author rasio
 */
public class DownloadItemController {

    @FXML
    private VBox download_item;
    @FXML
    private AnchorPane title_bar;
    @FXML
    private Label title_name;
    @FXML
    private OctIconView btn_close;
    @FXML
    private Label btn_stop;
    @FXML
    private Label btn_open_folder;
    @FXML
    private Label btn_pause;
    @FXML
    private Label btn_open_file;
    @FXML
    private Label status_download;
    @FXML
    private ProgressBar progress_bar;

    private double xOffset = 0;
    private double yOffset = 0;
    private ExecutorService executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(16));
    NumberFormat formatter = new DecimalFormat("0");

    public DownloadItemController(String url) {
        System.out.println(url);
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DownloadItem.fxml"));
            loader.setController(this);
            root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
        initialize();
        startDownload(url);
    }

    private void initialize() {
        movableWindow();
        btn_close.setOnMouseClicked(event -> {
            Stage stage = (Stage) btn_close.getScene().getWindow();
            stage.close();
            executor.shutdownNow();
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

    private void startDownload(String link) {
        try {
            URL url = new URL(link);
            Downloader downloader = new Downloader(url.toString(),16);


            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    downloader.setDownloadStatusListener(new DownloadListener() {
                        @Override
                        public void update(long downloaded, int speed) {
                            //System.out.println((1.0f * downloaded/downloader.getContent_length())*100 +"%");
                            //progress_bar.setProgress((1.0f * downloaded/downloader.getContent_length()));
                            //status_download.setText((1.0f * downloaded/downloader.getContent_length())*100+"%");
                            if (downloaded <= downloader.getContent_length()){
                                updateMessage(formatter.format((1.0f * downloaded/downloader.getContent_length())*100) +"%");
                            }
                            updateProgress(downloaded, downloader.getContent_length());
                        }

                        @Override
                        public void onCompleted() {
                            updateMessage("100%");
                        }

                        @Override
                        public void onPause(ArrayList<Long> downloaded) {
                            System.out.println(downloaded);
                        }

                        @Override
                        public void onPartError(int code, String message, int partNo) { }

                        @Override
                        public void onError(String message) {
                            System.out.println(message);
                        }

                        @Override
                        public void onPartStatus(long downloaded, int partNo) { }

                        @Override
                        public void onPartCompleted(int partNo) { }
                    });
                    return null ;
                }
            };
            new Thread(task).run();
            status_download.textProperty().bind(task.messageProperty());
            progress_bar.progressProperty().bind(task.progressProperty());
            downloader.startDownload();


        } catch (MalformedURLException ex) {
            Logger.getLogger(DownloadItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
