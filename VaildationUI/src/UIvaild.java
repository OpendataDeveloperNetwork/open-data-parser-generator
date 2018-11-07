

// 4 Nov ,2018 Eva Au
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UIvaild extends Application {
    public UIvaild() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(new Group());
        BorderPane bPane = new BorderPane();
        VBox vboxButtons = new VBox();
        HBox hboxJSON = new HBox();
        HBox hboxOutput = new HBox();
        HBox hboxBottom = new HBox();

        Text outputPath = new Text();
        outputPath.setText("Not Selected");
        Text jsonSchemaPath = new Text();
        jsonSchemaPath.setText("Not Selected");
        // Change name?
        final Label label = new Label("Input JS file");
        label.setFont(new Font("Arial", 20));

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // File Chooser Button
        final Button openButton = new Button("Open output.js file");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {

                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {

                }
            }
        });

        final Button openButton2 = new Button("Open JSON Schema File");
        openButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                }
            }
        });

        final Button saveButton = new Button("Check");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                final DirectoryChooser directoryChooser = new DirectoryChooser();

                String location = "";
                final File selectedDirectory = directoryChooser.showDialog(primaryStage);
                if (selectedDirectory != null) {
                }
                System.out.println(location);
            }
        });

        hboxOutput.setSpacing(10);
        hboxJSON.setSpacing(10);
        hboxBottom.setSpacing(50);
        // ScrollBar
        ScrollPane scrollPaneBottom = new ScrollPane();
        scrollPaneBottom.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        primaryStage.setTitle("UI vaild");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(650);

        scrollPaneBottom.setPrefSize(1000, 600);
        scrollPaneBottom.setContent(bPane);

        hboxOutput.getChildren().addAll(openButton, outputPath);
        hboxJSON.getChildren().addAll(openButton2, jsonSchemaPath);
        vboxButtons.getChildren().addAll(hboxOutput, hboxJSON);

        hboxBottom.getChildren().addAll(saveButton);
        hboxBottom.setPrefSize(300, 200);

        bPane.setTop(vboxButtons);
        bPane.setBottom(hboxBottom);

        // Add scrollPane to scene
        ((Group) scene.getRoot()).getChildren().add(scrollPaneBottom);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        UIvaild.launch(args);
    }

}
