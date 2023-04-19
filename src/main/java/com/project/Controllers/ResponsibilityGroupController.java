package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import com.project.ClassesForTables.Color;
import com.project.ClassesForTables.ResponsibilityGroup;
import com.project.DbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsibilityGroupController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private Button addButton;

    @FXML
    private TextField addField;

    @FXML
    private TextField afterUpdateField;

    @FXML
    private TextField beforeUpdateField;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField deleteField;

    @FXML
    private TableColumn<ResponsibilityGroup, String> groupColumn;

    @FXML
    private TableView<ResponsibilityGroup> groupResponsibilityTable;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<ResponsibilityGroup, Integer> idColumn;

    @FXML
    private ImageView imageBack;

    @FXML
    private Button updateButton;

    public ResponsibilityGroupController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable(connection);
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

    private void loadTable(Connection connection) throws SQLException {
        ObservableList<ResponsibilityGroup> listResponsibilityGroup = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM responsibility_group";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()){
            listResponsibilityGroup.add(new ResponsibilityGroup(resultSet.getInt(1), resultSet.getString(2)));
        }
        groupResponsibilityTable.setItems(listResponsibilityGroup);
    }

    @FXML
    private void addBrand() throws SQLException, IOException {
        if(addField.getText().replace(" ", "").isEmpty()){
            exeptionScene.createExeptionScene("Не было заполнено поле для добавления.");
        }
        else{
            String addBrand = "INSERT INTO responsibility_group(name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addBrand);
            preparedStatement.setString(1, addField.getText());
            preparedStatement.executeUpdate();
            loadTable(connection);
            addField.clear();
        }
    }

    @FXML
    private int deleteBrand() throws SQLException, IOException {
        if(deleteField.getText().replace(" ", "").isEmpty()){
            exeptionScene.createExeptionScene("Не было заполнено поле для удаления.");
            return 1;
        }
        String checkForNull = "SELECT name FROM responsibility_group WHERE name = ?";
        PreparedStatement preparedCheck = connection.prepareStatement(checkForNull);
        preparedCheck.setString(1, deleteField.getText());
        ResultSet resultSet = preparedCheck.executeQuery();
        if(resultSet.next()){
            String deleteBrand = "DELETE FROM responsibility_group WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setString(1, deleteField.getText());
            preparedStatement.executeUpdate();
            loadTable(connection);
            deleteField.clear();
        }
        else {
            exeptionScene.createExeptionScene("Элемента с данным именем не было найдено.");
        }
        return 1;
    }

    @FXML
    private int updateBrand() throws SQLException, IOException {
        if(beforeUpdateField.getText().replace(" ", "").isEmpty() || afterUpdateField.getText().replace(" ", "").isEmpty()){
            exeptionScene.createExeptionScene("Одно или несколько из полей не были заполнены.");
            return 1;
        }
        String checkForNull = "SELECT name FROM responsibility_group WHERE name = ?";
        PreparedStatement preparedCheck = connection.prepareStatement(checkForNull);
        preparedCheck.setString(1, beforeUpdateField.getText());
        ResultSet resultSet = preparedCheck.executeQuery();
        if(resultSet.next()){
            String updateBrand = "UPDATE responsibility_group SET name = ? WHERE name = ?";
            PreparedStatement preparedUpdate = connection.prepareStatement(updateBrand);
            preparedUpdate.setString(1, afterUpdateField.getText());
            preparedUpdate.setString(2, beforeUpdateField.getText());
            preparedUpdate.executeUpdate();
            loadTable(connection);
            afterUpdateField.clear();
            beforeUpdateField.clear();
        }
        else {
            exeptionScene.createExeptionScene("Элемента с данным именем не было найдено.");
        }
        return 1;
    }

}

