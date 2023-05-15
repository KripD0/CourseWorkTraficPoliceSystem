package com.project;

import com.project.auxiliary.SceneChanger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Scenes/Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Информационная система ГИБДД");
        Image ico = new Image(getClass().getResourceAsStream("images/ico.png"));
        stage.getIcons().add(ico);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        SceneChanger sceneChanger = new SceneChanger(stage, stage.getScene());
    }

    public static void main(String[] args) {
        launch();
    }
}