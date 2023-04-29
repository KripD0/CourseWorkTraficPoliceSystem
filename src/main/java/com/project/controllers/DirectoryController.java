package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class DirectoryController {

    @FXML
    private ImageView homeImage;

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
    private void clickOnlabelMarks() {
        SceneChanger sceneChanger = new SceneChanger(labelMarks.getScene());
        sceneChanger.changeScene("scenes/Brands.fxml");
    }

    @FXML
    private void clickOnlabelViolation() {
        SceneChanger sceneChanger = new SceneChanger(labelViolations.getScene());
        sceneChanger.changeScene("scenes/Violation.fxml");
    }

    @FXML
    private void clickOnlabelColor() {
        SceneChanger sceneChanger = new SceneChanger(labelColors.getScene());
        sceneChanger.changeScene("scenes/Colors.fxml");
    }

    @FXML
    private void clickOnlabelStatus() {
        SceneChanger sceneChanger = new SceneChanger(labelStatus.getScene());
        sceneChanger.changeScene("scenes/Status.fxml");
    }

    @FXML
    private void clickOnlabelResponsibility() {
        SceneChanger sceneChanger = new SceneChanger(labelTypeOfResponsibility.getScene());
        sceneChanger.changeScene("scenes/TypeOfResponsibility.fxml");
    }

    @FXML
    private void clickOnlabelRegions() {
        SceneChanger sceneChanger = new SceneChanger(labelRegions.getScene());
        sceneChanger.changeScene("scenes/Region.fxml");
    }

    @FXML
    private void clickOnlabelGroups() {
        SceneChanger sceneChanger = new SceneChanger(labelGroupOfResponsibility.getScene());
        sceneChanger.changeScene("scenes/ResponsibilityGroup.fxml");
    }

    @FXML
    private void clickOnImageBack() {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    private void clickOnImageHome() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Menu.fxml");
    }
}
