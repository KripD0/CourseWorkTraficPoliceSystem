package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FormController {

    @FXML
    private ImageView imageBack;

    @FXML
    private ImageView homeImage;

    @FXML
    private Label labelDriverRegistration;

    @FXML
    private Label labelRegistrationCars;

    @FXML
    private Label labelRegistrationsOfDesicions;

    @FXML
    private Label labelVewwu;

    @FXML
    private void clickOnImageBack(){
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    private void clickOnImageHome(){
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("Scenes/Menu.fxml");
    }
}
