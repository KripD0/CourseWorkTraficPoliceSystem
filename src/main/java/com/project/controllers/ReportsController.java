package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ReportsController {

    @FXML
    private ImageView homeImage;

    @FXML
    private Label periodReportlabel;

    @FXML
    private void clickOnLabelPeriodReport(){
        SceneChanger sceneChanger = new SceneChanger(periodReportlabel.getScene());
        sceneChanger.changeScene("scenes/PeriodReport.fxml");
    }

    @FXML
    public void clickOnImageBack() {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    public void clickOnImageHome() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Menu.fxml");
    }

}
