package com.edu.duongdua.core.bandoan.Controllers;

import com.edu.duongdua.core.bandoan.Models.Connector;
import com.edu.duongdua.core.bandoan.Models.Dish;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModalController implements Initializable
{
    private Connection conn;

    @FXML private Label dishId;
    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextField tfDesc;
    @FXML private TextField tfQty;
    @FXML private TextField tfTS;
    @FXML private ComboBox<Integer> cbDishlistId;
    @FXML private ComboBox<String> cbStatus;
    @FXML private ComboBox<String> cbSize;
    @FXML private ComboBox<String> cbMT;

    private boolean IsEdit = false;

    public boolean isEdit() {
        return this.IsEdit;
    }

    public void setEdit(boolean edit) {
        this.IsEdit = edit;
    }

    public ModalController() {
        conn = Connector.getConnection();
    }

    // Query
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

    public void InsertDish(Dish dish) {
        String sql = "INSERT INTO dishes (name, price, description, dishlist_id, size, status, must_try, quantity, total_sold) "
                + "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, dish.getName());
            ps.setInt(2, dish.getPrice());
            ps.setString(3, dish.getDescription());
            ps.setInt(4, dish.getDishListId());
            ps.setString(5, dish.getSize());
            ps.setInt(6, dish.getStatus());
            ps.setInt(7, dish.getMustTry());
            ps.setInt(8, dish.getQuantity());
            ps.setInt(9, dish.getTotalSold());
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateDish(Dish dish) {
        String sql = "UPDATE dishes " +
                "SET name = ?, price = ?, description = ?, dishlist_id = ?, size = ?, status = ?, must_try = ?, quantity = ?, total_sold = ? " +
                "WHERE id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, dish.getName());
            ps.setInt(2, dish.getPrice());
            ps.setString(3, dish.getDescription());
            ps.setInt(4, dish.getDishListId());
            ps.setString(5, dish.getSize());
            ps.setInt(6, dish.getStatus());
            ps.setInt(7, dish.getMustTry());
            ps.setInt(8, dish.getQuantity());
            ps.setInt(9, dish.getTotalSold());
            ps.setInt(10, dish.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Init components
    public void InitComboBoxs() {
        ResultSet rs = GetDishlist();
        List<Integer> dishListId = new ArrayList<>();
        try {
            while (rs.next()) {
                dishListId.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Integer id : dishListId) {
            cbDishlistId.getItems().add(id);
        }
        cbStatus.getItems().add("Hết món");
        cbStatus.getItems().add("Còn món");
        cbSize.getItems().add("S");
        cbSize.getItems().add("M");
        cbSize.getItems().add("L");
        cbMT.getItems().add("Không must try");
        cbMT.getItems().add("Must try");
    }

    public void InitModal(Dish dish) {
        dishId.setText("Id: " + dish.getId());
        tfName.setText(dish.getName());
        tfPrice.setText(Integer.toString(dish.getPrice()));
        tfDesc.setText(dish.getDescription());
        tfQty.setText(Integer.toString(dish.getQuantity()));
        tfTS.setText(Integer.toString(dish.getTotalSold()));

        cbDishlistId.setValue(dish.getDishListId());
        if (dish.getStatus() == 1) {
            cbStatus.setValue("Hết món");
        } else {
            cbStatus.setValue("Còn món");
        }
        cbSize.setValue(dish.getSize());
        if (dish.getMustTry() == 1) {
            cbMT.setValue("Không must try");
        } else {
            cbMT.setValue("Must try");
        }
    }

    public void InitModalDishId(int id) {
        dishId.setText("Id: " + Integer.toString(id));
    }

    // Event
    public void CloseModal(ActionEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void SubmitForm(ActionEvent event) {
        String _dishName = tfName.getText();
        int _dishPrice = Integer.parseInt(tfPrice.getText());
        String _dishDesc = tfDesc.getText();
        int _dishlistId = cbDishlistId.getValue();
        String _dishSize = cbSize.getValue();
        int _dishStatus = cbStatus.getValue().equals("Hết món") ? 1 : 2;
        int _dishMustTry = cbMT.getValue().equals("Không must try") ? 1 : 2;
        int _dishQuantity = Integer.parseInt(tfQty.getText());
        int _dishTotalSold = Integer.parseInt(tfTS.getText());

        Dish dish = new Dish(Integer.parseInt(dishId.getText().substring(4)), _dishName, _dishPrice, _dishDesc, _dishlistId, _dishSize, _dishStatus, _dishMustTry, _dishQuantity, _dishTotalSold);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (IsEdit) {
            try {
                UpdateDish(dish);
                alert.setContentText("Cập nhật thành công!");
                alert.show();
                CloseModal(event);
            } catch (Exception e) {
                alert.setContentText("Cập nhật thất bại!");
                alert.show();
                throw new RuntimeException(e);
            }
        } else {
            try {
                dish.setId(Integer.parseInt(dishId.getText().substring(4)));
                InsertDish(dish);
                alert.setContentText("Thêm mới thành công!");
                alert.show();
                CloseModal(event);
            } catch (Exception e) {
                alert.setContentText("Thêm mới thất bại!");
                alert.show();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitComboBoxs();
    }
}
