module com.dkuzmyk.cube_scrambler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dkuzmyk.cube_scrambler to javafx.fxml;
    exports com.dkuzmyk.cube_scrambler;
}