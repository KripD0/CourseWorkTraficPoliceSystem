package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FormController {
    @FXML
    private Label bebra;

    @FXML
    private void cliclOnLabelBack(){
        bebra.getScene().getWindow().hide();
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene("Scenes/Menu.fxml");
    }
}
