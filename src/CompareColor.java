import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The primary use of this program is to show the user all of the colors that can be used in JavaFX's Color class.
 */
public class CompareColor extends Application implements Initializable {

    public static final ObservableList<String> colors;

    static {
        List<String> allColors = new ArrayList<>();

        try {
            for (Field f : Color.class.getFields()) {
                System.out.println(f.getName());
                if (f.get(null) instanceof Color)
                    allColors.add(f.getName());
            }
        } catch (IllegalAccessException e) {} // oh well

        colors = FXCollections.observableArrayList(allColors);

        System.out.println(colors);
    }

    @FXML
    private ChoiceBox<String> leftColor, rightColor;

    @FXML
    private AnchorPane leftPane, rightPane;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CompareColor.fxml"));

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Compare JavaFX Colors");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftColor.setItems(colors);
        rightColor.setItems(colors);

        leftColor.setValue("WHITE");
        rightColor.setValue("BLACK");

        leftColor.onActionProperty().setValue(v -> changeColor());
        rightColor.onActionProperty().setValue(v -> changeColor());
    }

    public void changeColor() {
        leftPane.setStyle("-fx-background-color: #" + Color.valueOf(leftColor.getValue()).toString().substring(2));
        rightPane.setStyle("-fx-background-color: #" + Color.valueOf(rightColor.getValue()).toString().substring(2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
