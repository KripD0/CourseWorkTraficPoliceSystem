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
    private void clickOnlabelMarks(){
        SceneChanger sceneChanger = new SceneChanger(labelMarks.getScene());
        labelMarks.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Brands.fxml");
    }

    @FXML
    private void clickOnlabelViolation(){
        SceneChanger sceneChanger = new SceneChanger(labelViolations.getScene());
        labelViolations.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Violation.fxml");
    }

    @FXML
    private void clickOnlabelColor(){
        SceneChanger sceneChanger = new SceneChanger(labelColors.getScene());
        labelColors.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Colors.fxml");
    }

    @FXML
    private void clickOnlabelStatus(){
        SceneChanger sceneChanger = new SceneChanger(labelStatus.getScene());
        labelStatus.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Status.fxml");
    }

    @FXML
    private void clickOnlabelResponsibility(){

    }

    @FXML
    private void clickOnlabelRegions(){
        SceneChanger sceneChanger = new SceneChanger(labelRegions.getScene());
        labelRegions.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Region.fxml");
    }

    @FXML
    private void clickOnlabelGroups(){
        SceneChanger sceneChanger = new SceneChanger(labelGroupOfResponsibility.getScene());
        labelGroupOfResponsibility.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/ResponsibilityGroup.fxml");
    }

    @FXML
    private void clickOnImageBack(){
        SceneChanger sceneChanger = new SceneChanger();
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
