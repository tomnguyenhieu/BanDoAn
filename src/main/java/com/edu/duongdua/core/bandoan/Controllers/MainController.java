package com.edu.duongdua.core.bandoan.Controllers;

import com.edu.duongdua.core.bandoan.Main;
import com.edu.duongdua.core.bandoan.Models.Connector;
import com.edu.duongdua.core.bandoan.Models.Discount;
import com.edu.duongdua.core.bandoan.Models.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    private Connection conn;

    @FXML private TableView<Dish> tblDishes;
    @FXML private TableColumn<Dish, Integer> dishId;
    @FXML private TableColumn<Dish, String> dishName;
    @FXML private TableColumn<Dish, Double> dishPrice;
    @FXML private TableColumn<Dish, String> dishDesc;
    @FXML private TableColumn<Dish, Integer> dishDishlistId;
    @FXML private TableColumn<Dish, String> dishSize;
    @FXML private TableColumn<Dish, Boolean> dishStatus;
    @FXML private TableColumn<Dish, Boolean> dishMustTry;
    @FXML private TableColumn<Dish, Integer> dishQty;
    @FXML private TableColumn<Dish, Integer> dishTotalSold;

    @FXML private TableView<Discount> tblDiscounts;
    @FXML private TableColumn<Discount, Integer> discountId;
    @FXML private TableColumn<Discount, Integer> discountDishId;
    @FXML private TableColumn<Discount, Double> discountPrice;
    @FXML private TableColumn<Discount, String> discountTime;
    @FXML private TableColumn<Discount, String> discountCA;
    @FXML private TableColumn<Discount, Boolean> discountStatus;

    @FXML private ToolBar tbDishlist;

    public MainController()
    {
        conn = Connector.getConnection();
    }

    public ResultSet GetDishes() {
        String sql = "SELECT * FROM dishes";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void refreshDishTable() {
        final ObservableList<Dish> dishList = FXCollections.observableArrayList();
        ResultSet rs = GetDishes(); // Hàm này lấy dữ liệu từ database

        try {
            while (rs.next()) {
                dishList.add(new Dish(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("description"),
                        rs.getInt("dishlist_id"),
                        rs.getString("size"),
                        rs.getInt("status"),
                        rs.getInt("must_try"),
                        rs.getInt("quantity"),
                        rs.getInt("total_sold")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gán dữ liệu cho từng cột
            dishId.setCellValueFactory(new PropertyValueFactory<>("id"));
            dishName.setCellValueFactory(new PropertyValueFactory<>("name"));
            dishPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            dishDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            dishDishlistId.setCellValueFactory(new PropertyValueFactory<>("dishListId"));
            dishSize.setCellValueFactory(new PropertyValueFactory<>("size"));
            dishStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            dishMustTry.setCellValueFactory(new PropertyValueFactory<>("mustTry"));
            dishQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            dishTotalSold.setCellValueFactory(new PropertyValueFactory<>("totalSold"));

        // Cập nhật dữ liệu vào bảng
        tblDishes.setItems(dishList);
        System.out.println("Dish table refreshed!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshDishTable();
    }
}
