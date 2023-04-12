package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class DirectoryController {

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView imageBack;

    @FXML
    private Label labelColors;

    @FXML
    private Label labelGroupOfResponsibility;

    @FXML
    private Label labelMarks;

    @FXML
    private Label labelRegions;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelTypeOfResponsibility;

    @FXML
    private Label labelViolations;

    @FXML
    private void clickOnImageBack(){
        SceneChanger sceneChanger = new SceneChanger(imageBack.getScene());
        imageBack.getScene().getWindow().hide();
        sceneChanger.changeScene();
    }

    @FXML
    private void clickOnImageHome(){
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        homeImage.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Menu.fxml");
    }
}
