package com.project.Auxiliary;

import com.project.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SceneChanger {

    private static List<Scene> scenesList = new ArrayList<>();

    public SceneChanger(Scene scene){
        scenesList.add(scene);
        while(scenesList.size() > 2){
            scenesList.remove(0);
        }
    }

    public void changeScene(String nameScene){
        Image ico = new Image("D:/Java/CourseWorkTraficPoliceSystem/src/main/resources/com/project/Images/ico.png");
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(nameScene));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(ico);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(){
        Image ico = new Image("D:/Java/CourseWorkTraficPoliceSystem/src/main/resources/com/project/Images/ico.png");
        Stage stage = new Stage();
        stage.setScene(scenesList.get(scenesList.size()-2));
        stage.getIcons().add(ico);
        stage.show();
    }
}
