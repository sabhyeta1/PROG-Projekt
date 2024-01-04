module com.example.teamarbeit {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.teamarbeit to javafx.fxml;
    exports com.example.teamarbeit;
}