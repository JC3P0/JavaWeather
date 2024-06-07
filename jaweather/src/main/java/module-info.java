module javaweathers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;

    opens javaweathers to javafx.fxml;
    exports javaweathers;
}
