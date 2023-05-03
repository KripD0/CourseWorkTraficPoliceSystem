package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.classesForTables.Brand;
import com.project.classesForTables.Category;
import com.project.dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CategoryController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private TableView<Category> brandTable;

    @FXML
    private TableColumn<Category, String> brandColumn;

    @FXML
    private TableColumn<Category, Integer> idColumn;

    @FXML
    private TextField brandField;

    @FXML
    private TextField findField;

    @FXML
    private ImageView homeImage;

    public CategoryController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable();
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

    @FXML
    private void addBrand() throws SQLException, IOException {
        if (brandField.getText().replace(" ", "").isEmpty()) {
            exeptionScene.createExeptionScene("Не было заполнено поле для добавления.");
        } else {
            String addBrand = "INSERT INTO category(name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addBrand);
            preparedStatement.setString(1, brandField.getText());
            preparedStatement.executeUpdate();
            loadTable();
            brandField.clear();
        }
    }

    @FXML
    private int deleteBrand() throws SQLException, IOException {
        if (brandTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        String checkSelect = """
                SELECT * FROM drivers_license WHERE category_id = ?""";
        PreparedStatement check = connection.prepareStatement(checkSelect);
        check.setLong(1, Long.parseLong(String.valueOf(brandTable.getSelectionModel().getSelectedItem().getId())));
        ResultSet resultSet = check.executeQuery();
        if(resultSet.next()){
            exeptionScene.createExeptionScene("Удаление данного элемента нарушает целостность базы данных.");
            return 1;
        }
        Category category = brandTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createDeleteScene(category.getName());
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM category WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.executeUpdate();
            loadTable();
        }
        return 1;
    }

    @FXML
    private int updateBrand() throws SQLException, IOException {
        if (brandTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для редактирования.");
            return 1;
        }
        if (brandField.getText().isEmpty() || brandField.getText().replace(" ", "") == "") {
            exeptionScene.createExeptionScene("Поле для изменения не было заполнено.");
            return 1;
        }
        Category category = brandTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createEditScene(category.getName(), brandField.getText());
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            String updateBrand = "UPDATE category SET name = ? WHERE id = ?";
            PreparedStatement preparedUpdate = connection.prepareStatement(updateBrand);
            preparedUpdate.setString(1, brandField.getText());
            preparedUpdate.setInt(2, category.getId());
            preparedUpdate.executeUpdate();
            loadTable();
        }
        brandField.clear();
        return 1;
    }

    @FXML
    public void findByString() throws SQLException{
        ObservableList<Category> listBrand = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM category WHERE name LIKE ? ORDER BY id";
        String finalString;
        finalString = "%" + findField.getText().trim() + "%";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        preparedStatement.setString(1, finalString);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            listBrand.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
        }
        brandTable.setItems(listBrand);
    }

    @FXML
    public void clearButton() throws SQLException {
        findField.clear();
        loadTable();
    }

    public void loadTable() throws SQLException {
        ObservableList<Category> listBrand = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM category ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            listBrand.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
        }
        brandTable.setItems(listBrand);
    }
}

