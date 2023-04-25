package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import com.project.ClassesForTables.Brand;
import com.project.DbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private TableView<Brand> brandTable;

    @FXML
    private TableColumn<Brand, String> brandColumn;

    @FXML
    private TableColumn<Brand, Integer> idColumn;

    @FXML
    private TextField brandField;

    @FXML
    private TextField findField;

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
    private void clickOnImageBack() {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    private void clickOnImageHome() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("Scenes/Menu.fxml");
    }

    @FXML
    private void addBrand() throws SQLException, IOException {
        if (brandField.getText().replace(" ", "").isEmpty()) {
            exeptionScene.createExeptionScene("Не было заполнено поле для добавления.");
        } else {
            String addBrand = "INSERT INTO brand(name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addBrand);
            preparedStatement.setString(1, brandField.getText());
            preparedStatement.executeUpdate();
            loadTable(connection);
            brandField.clear();
        }
    }

    @FXML
    private int deleteBrand() throws SQLException, IOException {
        if (brandTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        Brand brand = brandTable.getSelectionModel().getSelectedItem();
        exeptionScene.createDeleteScene(brand.getName());
        while (DeleteAndEditController.isWaiting) {
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM brand WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, brand.getId());
            preparedStatement.executeUpdate();
            loadTable(connection);
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
        Brand brand = brandTable.getSelectionModel().getSelectedItem();
        exeptionScene.createEditScene(brand.getName(), brandField.getText());
        while (DeleteAndEditController.isWaiting) {
        }
        if (DeleteAndEditController.needDelete) {
            String updateBrand = "UPDATE brand SET name = ? WHERE id = ?";
            PreparedStatement preparedUpdate = connection.prepareStatement(updateBrand);
            preparedUpdate.setString(1, brandField.getText());
            preparedUpdate.setInt(2, brand.getId());
            preparedUpdate.executeUpdate();
            loadTable(connection);
        }
        brandField.clear();
        return 1;
    }

    @FXML
    private void findByString() throws SQLException{
        ObservableList<Brand> listBrand = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM brand WHERE name LIKE ?";
        String finalString;
        finalString = "%" + findField.getText().trim() + "%";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        preparedStatement.setString(1, finalString);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            listBrand.add(new Brand(resultSet.getInt(1), resultSet.getString(2)));
        }
        brandTable.setItems(listBrand);
    }

    @FXML
    private void clearButton() throws SQLException {
        findField.clear();
        loadTable(connection);
    }

    public void loadTable(Connection connection) throws SQLException {
        ObservableList<Brand> listBrand = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM brand";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            listBrand.add(new Brand(resultSet.getInt(1), resultSet.getString(2)));
        }
        brandTable.setItems(listBrand);
    }
}
