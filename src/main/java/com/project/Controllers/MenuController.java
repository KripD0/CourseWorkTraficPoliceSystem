package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
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
    private void initialize(){
    }

    @FXML
    private void clickOnLabelForm(){
        SceneChanger sceneChanger = new SceneChanger(labelToForms.getScene());
        labelToForms.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Forms.fxml");
    }

    @FXML
    private void clickOnLabelDirectory(){
        SceneChanger sceneChanger = new SceneChanger(labelToDirectory.getScene());
        labelToDirectory.getScene().getWindow().hide();
        sceneChanger.changeScene("Scenes/Directory.fxml");
    }
}