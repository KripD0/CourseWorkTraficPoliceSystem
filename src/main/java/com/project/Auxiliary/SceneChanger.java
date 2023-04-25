package com.project.Auxiliary;

import com.project.Controllers.DeleteAndEditController;
import com.project.Controllers.ExeptionController;
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

    private static Stage stage;
    private final static List<Scene> scenesList = new ArrayList<>();

    private final DeleteAndEditController deleteAndEditController = new DeleteAndEditController();
    private final ExeptionController exeptionController = new ExeptionController();

    public SceneChanger(Stage stage, Scene scene){
        this.stage = stage;
        scenesList.add(scene);
    }

    public SceneChanger(Scene scene){
        scenesList.add(scene);
    }
    public SceneChanger(){
    }

    public void changeScene(String nameScene){
        if(nameScene.equals("Scenes/Menu.fxml")){
            while (scenesList.size() > 1){
                scenesList.remove(scenesList.size() - 1);
            }
        }
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(nameScene));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(){
        stage.setScene(scenesList.get(scenesList.size()-1));
        stage.show();
        for (int i = 0; i < 2; i ++){
            if(scenesList.size() > 1){
                scenesList.remove(scenesList.size() - 1);
            }
        }
    }

    public void createExeptionScene(String message) throws IOException {
        exeptionController.setMessage(message);
        Parent root = FXMLLoader.load(Main.class.getResource("Scenes/Exeption.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createDeleteScene(String message) throws IOException {
        deleteAndEditController.setMessage("Вы уверены что хотите удалить\n\"" + message + "\"");
        Parent root = FXMLLoader.load(Main.class.getResource("Scenes/DeleteAndEditForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void createEditScene(String beforeUpdate, String afterUpdate) throws IOException{
        deleteAndEditController.setMessage("Вы уверены что хотите изменить \"" + beforeUpdate + "\" на \"" + afterUpdate + "\"");
        Parent root = FXMLLoader.load(Main.class.getResource("Scenes/DeleteAndEditForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void createEditHardScene(String id) throws IOException {
        deleteAndEditController.setMessage("Вы уверены что хотите изменить элемент с ID: " + id);
        Parent root = FXMLLoader.load(Main.class.getResource("Scenes/DeleteAndEditForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
