package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class FormController {

    @FXML
    private ImageView homeImage;

    @FXML
    private Label labelDriverRegistration;

    @FXML
    private Label labelRegistrationCars;

    @FXML
    private Label labelRegistrationsOfDesicions;

    @FXML
    public void clickOnImageBack(){
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    public void clickOnImageHome(){
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Menu.fxml");
    }

    @FXML
    private void clickOnLabelDecree(){
        SceneChanger sceneChanger = new SceneChanger(labelRegistrationsOfDesicions.getScene());
        sceneChanger.changeScene("scenes/Decree.fxml");
    }

    @FXML
    private void clickOnLabelVehicle(){
        SceneChanger sceneChanger = new SceneChanger(labelRegistrationCars.getScene());
        sceneChanger.changeScene("scenes/Vehicle.fxml");
    }

    @FXML
    private void clickOnlabelDrivers(){
        SceneChanger sceneChanger = new SceneChanger(labelDriverRegistration.getScene());
        sceneChanger.changeScene("scenes/Drivers.fxml");
    }
}
