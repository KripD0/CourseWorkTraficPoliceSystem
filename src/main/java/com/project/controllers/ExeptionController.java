package com.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ExeptionController {

    private static String message;

    public ExeptionController(String message){
        this.message = message;
    }

    public ExeptionController(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @FXML
    private Label okLabel;

    @FXML
    private Label outputLabel;

    @FXML
    private void initialize(){
        outputLabel.setWrapText(true);
        outputLabel.setText(message);
    }

    @FXML
    private void okClick(){
        Stage stage = (Stage) okLabel.getScene().getWindow();
        stage.close();
    }

}
