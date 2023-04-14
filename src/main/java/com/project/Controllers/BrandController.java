package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import com.project.ClassesForTables.Brand;
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
import java.sql.*;

public class BrandController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    @FXML
    private TextField deleteBrandField;
    @FXML
    private TextField afterUpdateField;

    @FXML
    private TextField beforeUpdateField;

    @FXML
    private TableView<Brand> brandTable;

    @FXML
    private TableColumn<Brand, String> brandColumn;

    @FXML
    private TableColumn<Brand, Integer> idColumn;

    @FXML
    private TextField brandField;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView imageBack;

    public BrandController() throws SQLException {
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

    @FXML
    private void addBrand() throws SQLException, IOException {
        if(brandField.getText().replace(" ", "").isEmpty()){
            exeptionScene.createExeptionScene("Не было заполнено поле для добавления.");
        }
        else{
            String addBrand = "INSERT INTO brand(name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addBrand);
            preparedStatement.setString(1, brandField.getText());
            preparedStatement.executeUpdate();
            loadTable(connection);
        }
    }

    @FXML
    private int deleteBrand() throws SQLException, IOException {
        if(deleteBrandField.getText().replace(" ", "").isEmpty()){
            exeptionScene.createExeptionScene("Не было заполнено поле для удаления.");
            return 1;
        }
        String checkForNull = "SELECT name FROM brand WHERE name = ?";
        PreparedStatement preparedCheck = connection.prepareStatement(checkForNull);
        preparedCheck.setString(1, deleteBrandField.getText());
        ResultSet resultSet = preparedCheck.executeQuery();
        if(resultSet.next()){
            String deleteBrand = "DELETE FROM brand WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setString(1, deleteBrandField.getText());
            preparedStatement.executeUpdate();
            loadTable(connection);
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
        String checkForNull = "SELECT name FROM brand WHERE name = ?";
        PreparedStatement preparedCheck = connection.prepareStatement(checkForNull);
        preparedCheck.setString(1, beforeUpdateField.getText());
        ResultSet resultSet = preparedCheck.executeQuery();
        if(resultSet.next()){
            String updateBrand = "UPDATE brand SET name = ? WHERE name = ?";
            PreparedStatement preparedUpdate = connection.prepareStatement(updateBrand);
            preparedUpdate.setString(1, afterUpdateField.getText());
            preparedUpdate.setString(2, beforeUpdateField.getText());
            preparedUpdate.executeUpdate();
            loadTable(connection);
        }
        else {
            exeptionScene.createExeptionScene("Элемента с данным именем не было найдено.");
        }
        return 1;
    }

    private void loadTable(Connection connection) throws SQLException {
        ObservableList<Brand> listBrand = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM brand";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()){
            listBrand.add(new Brand(resultSet.getInt(1), resultSet.getString(2)));
        }
        brandTable.setItems(listBrand);
    }
}
