package com.edu.duongdua.core.bandoan.Controllers;

import com.edu.duongdua.core.bandoan.Models.Connector;
import com.edu.duongdua.core.bandoan.Models.Discount;
import com.edu.duongdua.core.bandoan.Models.Dish;
import com.edu.duongdua.core.bandoan.Models.Dishlist;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

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

    @FXML private TextField searchBox;
    @FXML private ToolBar tbDishlist;

    public MainController()
    {
        conn = Connector.getConnection();
    }

    // Query
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

    public ResultSet SearchDishes() {
        String searchText = searchBox.getText();
        String sql = "SELECT * FROM dishes WHERE name LIKE '%" + searchText + "%'";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet FindDishes(int dishlistId) {
        String sql = "SELECT * FROM dishes WHERE dishlist_id = " + dishlistId;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet GetDiscounts() {
        String sql = "SELECT * FROM discounts";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet GetDishlist() {
        String sql = "SELECT * FROM dishlist";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dishlist FindDishlistByName(String name) {
        String sql = "SELECT * FROM dishlist WHERE name = '" + name + "'";
        PreparedStatement ps;
        Dishlist dishlist = new Dishlist();
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dishlist = new Dishlist(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishlist;
    }

    // Init
    public Button CreateButton(String category) {
        Button button = new Button();
        button.setText(category);
        button.setFont(new Font("System", 20));
        button.setMinSize(97, 42);
        button.addEventHandler(ActionEvent.ACTION, this::OnActionGetDishesByCategory);
        return button;
    }

    public void InitToolBar(ResultSet data) {
        ResultSet rs = data;
        try {
            while (rs.next()) {
                String category = rs.getString("Name");
                tbDishlist.getItems().add(CreateButton(category));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Event
    @FXML
    public void RefreshDishTable(ResultSet data) {
        final ObservableList<Dish> dishList = FXCollections.observableArrayList();
        try {
            ResultSet rs = data;
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

        tblDishes.setItems(dishList);
    }

    @FXML
    public void RefreshDiscountTable(ResultSet data) {
        final ObservableList<Discount> discountList = FXCollections.observableArrayList();
        try {
            ResultSet rs = data;
            while (rs.next()) {
                discountList.add(new Discount(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("dish_id"),
                    rs.getInt("discount_price"),
                    rs.getTimestamp("time"),
                    rs.getTimestamp("created_at"),
                    rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        discountId.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountDishId.setCellValueFactory(new PropertyValueFactory<>("dishId"));
        discountPrice.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        discountTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        discountCA.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        discountStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblDiscounts.setItems(discountList);
    }

    @FXML
    public void OnActionSearchDishes() {
        try {
            ResultSet rs = SearchDishes();
            RefreshDishTable(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void OnActionGetDishesByCategory(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        Dishlist dishlist =  FindDishlistByName(button.getText());
        ResultSet data = FindDishes(dishlist.getId());
        RefreshDishTable(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ResultSet dishes = GetDishes();
            ResultSet discounts = GetDiscounts();
            ResultSet dishlist = GetDishlist();

            InitToolBar(dishlist);
            RefreshDishTable(dishes);
            RefreshDiscountTable(discounts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
