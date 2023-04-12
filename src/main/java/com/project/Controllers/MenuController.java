package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import com.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Label labelToDirectory;

    @FXML
    private Label labelToForms;

    @FXML
    private Label labelToReport;

    @FXML
    void initialize(){
    }

    @FXML
    private void clickOnLabelForm(){
        labelToForms.getScene().getWindow().hide();
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene("Scenes/Forms.fxml");
    }
}