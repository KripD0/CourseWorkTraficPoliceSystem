package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuController {

    @FXML
    private Label labelToDirectory;

    @FXML
    private Label labelToForms;

    @FXML
    private Label labelToReport;

    @FXML
    private void clickOnLabelForm(){
        SceneChanger sceneChanger = new SceneChanger(labelToForms.getScene());
        sceneChanger.changeScene("scenes/Forms.fxml");
    }

    @FXML
    private void clickOnLabelDirectory(){
        SceneChanger sceneChanger = new SceneChanger(labelToDirectory.getScene());
        sceneChanger.changeScene("scenes/Directory.fxml");
    }

    @FXML
    private void clickOnLabelRepot(){
        SceneChanger sceneChanger = new SceneChanger(labelToReport.getScene());
        sceneChanger.changeScene("scenes/Reports.fxml");
    }
}