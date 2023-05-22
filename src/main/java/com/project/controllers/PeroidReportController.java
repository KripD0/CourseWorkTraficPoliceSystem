package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.dbUtil.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.awt.Desktop;

public class PeroidReportController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private DatePicker afterDatepicker;

    @FXML
    private DatePicker beforeDatepicker;


    public PeroidReportController() throws SQLException {
    }

    @FXML
    public void clickOnImageBack() {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    private int createReport() throws IOException {
        if(beforeDatepicker.getValue() == null){
            exeptionScene.createExeptionScene("Выберите дату начала.");
            return 1;
        }
        File file = new File("report.html");
        try (FileWriter fw = new FileWriter(file.getPath(), StandardCharsets.UTF_8)) {
            fw.write("""
                    <!DOCTYPE html>
                    <html>
                      <head>
                        <link rel="icon" href="ico.png"/>
                        <meta charset="utf-8" />
                        <title>Очёт</title>
                        <style>
                                                  body {
                                                    background-color: #FFFFFF; /*цвет фона*/
                                                    font-family: "Comic Sans MS", sans-serif;
                                                    font-size: 18px;
                                                    text-align: center;
                                                  }
                                                  table {
                                                    width: 100%;
                                                    margin: auto;
                                                  }
                                                 \s
                                                  tr, td {
                                                    border: 3px solid #00ADE8;
                                                    border-radius: 1%;
                                                    padding: 2px;
                                                  }
                                                 \s
                                                  td:last-child {
                                                    border-radius: 1% 5% 5% 1%;
                                                  }
                                                 \s
                                                  td:first-child {
                                                    border-radius: 5% 1% 1% 5%;
                                                  }
                                                </style>
                      </head>
                      <body>
                      <table>
                      """);
            fw.write("<tr><td colspan=\"999\" style= \"border: 0px;\"> Отчёт по нарушениям по регионам. " +" </td></tr>");
            fw.write("<tr><td colspan=\"999\" style= \"border: 0px;\"> Отчет от: " + beforeDatepicker.getValue() + " до: " + afterDatepicker.getValue() + " </td></tr>");
            fw.write("<tr><td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Номер Водительского удостоверения</td>");
            fw.write("<td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Нарушение</td>");
            fw.write("<td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Дата</td>");
            fw.write("<td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Номер транспортного средства</td>");
            fw.write("<td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Регион</td>");
            fw.write("<td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Статус</td>");
            fw.write("<td style = \"background-color: #1CF4FF; color: #000000; border: 0px \">Штраф</td></tr>");
            String sqlRegions = "SELECT name FROM decree INNER JOIN region r on r.id = decree.region_id GROUP BY name";
            Statement regionStatement = connection.createStatement();
            ResultSet regionSet = regionStatement.executeQuery(sqlRegions);
            int rowCount = 0;

            while (regionSet.next()) {
                String sqlAll = """
                                SELECT dln_id, v.name, date_violation,  v2.state_number, r.name, s.name, tor.name FROM decree d
                        INNER JOIN violation v on v.id = d.violation_id
                        INNER JOIN vehicle v2 on v2.id = d.vehicle_id
                        INNER JOIN region r on r.id = d.region_id
                        INNER JOIN status s on s.id = d.status_id
                        INNER JOIN type_of_responsibility tor on d.responsibility_id = tor.id
                        WHERE r.name = ?
                        AND date_violation BETWEEN ? AND ?
                                """;
                PreparedStatement preparedStatement1 = connection.prepareStatement(sqlAll);
                preparedStatement1.setString(1, regionSet.getString(1));
                preparedStatement1.setDate(2, Date.valueOf(beforeDatepicker.getValue()));
                if (afterDatepicker.getValue() != null) {
                    preparedStatement1.setDate(3, Date.valueOf(afterDatepicker.getValue()));
                }
                else {
                    preparedStatement1.setDate(3, Date.valueOf(LocalDate.now()));
                }
                ResultSet bebraSet = preparedStatement1.executeQuery();

                if (bebraSet.next()) {
                    fw.write("<tr><td colspan=\"999\" style = \"background-color: #0D80FF; color: #FFFFFF; border: 0px\">" + regionSet.getString(1) + "</td></tr>");
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlAll);
                    preparedStatement.setString(1, regionSet.getString(1));
                    preparedStatement.setDate(2, Date.valueOf(beforeDatepicker.getValue()));
                    if (afterDatepicker.getValue() != null) {
                        preparedStatement.setDate(3, Date.valueOf(afterDatepicker.getValue()));
                    }
                    else {
                        preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
                    }
                    ResultSet resultSet = preparedStatement.executeQuery();

                    int countRows = 0;

                    while (resultSet.next()) {
                        fw.write("\n<tr>");
                        for (int i = 1; i < 8; i++) {
                            fw.write("\n<td>");
                            fw.write(resultSet.getString(i));
                            fw.write("</td>");
                        }
                        fw.write("</tr>");
                        countRows++;
                        rowCount++;
                    }
                    fw.write("<tr><td colspan=\"999\" style = \"background-color: #FFFFFF; color: #FF0000; border: 0px; text-align: right\">");
                    fw.write("Всего нарушений по региону \"" + regionSet.getString(1) + "\" : " + countRows);
                    fw.write("</td></tr>");
                }
            }
            fw.write("<tr><td colspan=\"999\" style = \"background-color: #FFFFFF; color: #FF0000; border: 0px; text-align: center\">");
            fw.write("Итого нарушений " + " : " + rowCount);
            fw.write("</td></tr>");
            fw.write("<tr><td colspan=\"999\" style= \"border: 0px; text-align: left\">" +
                    "<br>Дата создания отчета: " + LocalDate.now()
                    + "<br>Время создания отчета: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + " </td></tr>");

            fw.write("""
                    </table>
                    </body>
                    </html>
                    """);
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}