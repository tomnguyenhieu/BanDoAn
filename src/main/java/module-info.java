module com.edu.duongdua.core.bandoan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.edu.duongdua.core.bandoan to javafx.fxml;
    exports com.edu.duongdua.core.bandoan;
    exports com.edu.duongdua.core.bandoan.Models;
    opens com.edu.duongdua.core.bandoan.Models to javafx.fxml;
    opens com.edu.duongdua.core.bandoan.Controllers to javafx.fxml;
}