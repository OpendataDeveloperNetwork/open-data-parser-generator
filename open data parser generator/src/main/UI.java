package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Math.*;

import Csv.CsvParser;
import Csv.CsvRow;
import Json.JSONParser;
import Json.JSONElement;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * UI.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451
 * @version 2017
 */
public class UI extends Application {
    boolean removeFirstLine;
    String csvFilePath = "";
    String jsonSchemaFilePath = "";
    String javascriptFilePath = "";
    CsvParser parser = null;
    //TableView of CSV file
    private TableView<ObservableList<String>> table = new TableView<>();
    //Gridpanes to hold JSONHeaders and dropdown menus
    GridPane gridpaneJson = new GridPane();
    GridPane gridpaneCsv = new GridPane();
    //List of all JSON Headers
    ArrayList<String> jsonHead;
    //List of all CSV Headers
    ArrayList<String> csvHead;
    //Final result of mapping header 
    Map<String, String> saveBothHead;
    //List of Dropdowns
    ArrayList<ComboBox> boxArray = new ArrayList<ComboBox>();
    JSONParser jsonParser = null;
    /**
     * @see javafx.application.Application#start(javafx.stage.Stage)
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new Group());
        BorderPane bPane = new BorderPane();
        stage.setTitle("Table View Sample");
        stage.setWidth(1000);
        stage.setHeight(650);

        final Label label = new Label("CSV File");
        label.setFont(new Font("Arial", 20));

        HBox hboxCSV = new HBox(); //CSV DIRECTORY HBOX
        HBox hboxJSON = new HBox(); //JSON DIRECTORY HBOX
        VBox vboxButtons = new VBox(); //CSV AND JSON DIRECTORY VBOX
        VBox vboxBottom = new VBox(); 
        HBox hboxBottom = new HBox();
        HBox hboxDropDown = new HBox();
        ScrollPane scrollPaneBottom = new ScrollPane();

        scrollPaneBottom.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        hboxCSV.setSpacing(10);
        hboxJSON.setSpacing(10);
        vboxBottom.setSpacing(5);



        // File Chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // CSV JSON Filepath
        Text csvPath = new Text();
        csvPath.setText("Not Selected");
        Text jsonSchemaPath = new Text();
        jsonSchemaPath.setText("Not Selected");

        // File Chooser Button
        // CSV Open Button
        final Button openButton = new Button("Open CSV File");
        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                clear();
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    openCSV(file, csvPath);
                    clear();
                    if(jsonHead != null) {
                        createCsvComboBox();
                    }
                }
            }
        });

        // File Chooser Button
        // JSON Schema Open Button
        final Button openButton2 = new Button("Open JSON Schema File");
        openButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    openJSONSchema(file, jsonSchemaPath);
                    if((!csvFilePath.equals("")) && csvHead == null) {
                        createCsvComboBox();
                    }
                }
            }
        });

        //Save button calls Script Generator
        final Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                ArrayList<String> left = new ArrayList<String>();
                ArrayList<String> right = new ArrayList<String>();
                for(int i=0; i<boxArray.size() ;i++) {               
                    left.add(jsonHead.get(i));
                    right.add(Integer.toString(csvHead.indexOf((String)boxArray.get(i).getValue())));
                    System.out.println("Added:" + jsonHead.get(i));
                }
                String location = "";

                final DirectoryChooser directoryChooser =
                        new DirectoryChooser();
                final File selectedDirectory =
                        directoryChooser.showDialog(stage);
                if (selectedDirectory != null) {
                    location = selectedDirectory.getAbsolutePath();
                }

                ScriptGenerator.generateScript(left, right, location, jsonParser.getObject());
                System.out.println(location);
                stage.close();
            }
        });
        
        //Changes dropdowns to give Alphabetical values instead of first row of the csv.
        final Button removeFirstRowButton = new Button("Remove First Row");
        removeFirstRowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                createIndexCsvComboBox();
            }
        });

        hboxCSV.getChildren().addAll(openButton, csvPath); //ADDS OPEN CSV BUTTON 
        hboxJSON.getChildren().addAll(openButton2, jsonSchemaPath); //ADDS OPEN JSON BUTTON
        vboxBottom.getChildren().addAll(label, table); //ADDS Label to Table
        vboxButtons.getChildren().addAll(hboxCSV, hboxJSON, removeFirstRowButton); //adds file chooser buttons to vboxButtons 

        gridpaneJson.setVgap(19);
        gridpaneCsv.setVgap(10);

        //adds dropdowns, save buttons, and table to bottom hbox
        hboxBottom.getChildren().addAll(gridpaneJson, gridpaneCsv, saveButton, vboxBottom);

        //Sets Buttons top of bPane
        bPane.setTop(vboxButtons);
        //Sets Dropdowns and table bottom of bPane
        bPane.setBottom(hboxBottom);
        //Wrap bPane in ScrollPane to allow scrolling
        scrollPaneBottom.setPrefSize(1000, 600);
        scrollPaneBottom.setContent(bPane);

        vboxBottom.setPrefSize(600,500);
        table.prefWidthProperty().bind(vboxBottom.widthProperty());

        //Add scrollPane to scene
        ((Group) scene.getRoot()).getChildren().add(scrollPaneBottom);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main Function
     * @param args
     */
    public static void main(String[] args) {
        UI.launch(args);
    }

    /**
     * Returns a character for given n
     * @param n
     * @return
     */
    private static String getString(int n) {
        char[] buf = new char[(int) floor(log(25 * (n + 1)) / log(26))];
        for (int i = buf.length - 1; i >= 0; i--) {
            n--;
            buf[i] = (char) ('A' + n % 26);
            n /= 26;
        }
        return new String(buf);
    }

    /**
     * Populates gridpaneCSV
     */
    public void createIndexCsvComboBox() {

        int listViewColumns = parser.getCsv().getData().get(0).getData().size();
        csvHead = new ArrayList<String>();
        if (listViewColumns != 0) {
            System.out.println("Size of the array going to create " + listViewColumns);
            for (int i = 0; i < listViewColumns; i++) {
                csvHead.add(getString(i));
            }
        }
        gridpaneCsv.getChildren().clear();
        boxArray = new ArrayList<ComboBox>();

        // create more combobox depends on size of JSON schema size
        for (int i = 0; i < csvHead.size(); i++) {
            ComboBox temp = new ComboBox();
            for (String x : csvHead) {
                temp.getItems().add(x);
            }
            boxArray.add(temp);
            gridpaneCsv.add(temp, 0, i);

        }
    }


    /**
     * Populates gridpaneCSV
     */
    public void createCsvComboBox() {

        int listViewColumns = parser.getCsv().getData().get(0).getData().size();
        csvHead = new ArrayList<String>();
        csvHead.add("");
        if (listViewColumns != 0) {
            System.out.println("Size of the array going to create " + listViewColumns);
            for (int i = 0; i < listViewColumns; i++) {
                csvHead.add(parser.getCsv().getData().get(0).getValue(i));
            }

            // create more combobox depends on size of JSON schema size
            for (int i = 0; i < jsonHead.size(); i++) {
                ComboBox temp = new ComboBox();
                for (String x : csvHead) {
                    temp.getItems().add(x);
                }
                boxArray.add(temp);

                gridpaneCsv.add(temp, 0, i);
            }
        } else {
            System.out.println("CSV have no columns");
        }
    }

    /**
     * clear Tableview to allow opening of new Tableview.
     */
    public void clear() {
        ObservableList<ObservableList<String>> all, single;
        all = table.getItems();
        single = table.getSelectionModel().getSelectedItems();
        single.forEach(all::remove);
    }



    /**
     * Opens and processes CSV file
     * @param file
     * @param a
     */
    private void openCSV(File file, Text a) {
        table.getItems().clear(); // Reset in case of second import

        // Set Path to CSV
        csvFilePath = file.getAbsolutePath();
        a.setText(csvFilePath);
        parser = new CsvParser(csvFilePath); // Parse CSV

        // Dimensions
        int numColumns = parser.getCsv().getData().get(0).getData().size();
        System.out.println("Columns=" + numColumns);
        int numRows = parser.getCsv().getData().size();
        System.out.println("Rows=" + numRows);

        // Adds Columns
        for (int i = 0; i < numColumns; i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>("" + i);
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
            table.getColumns().add(column);
        }

        // Adds Data into columns
        for (int i = 0; i < numRows; i++) {
            ArrayList<String> temp = (parser.getCsv().getData().get(i).getData());

            table.getItems().add(FXCollections.observableArrayList(temp));
        }
    }

    /**
     * Opens and Processes JSON Schema File
     * @param file
     * @param a
     */
    private void openJSONSchema(File file, Text a) {
        jsonSchemaFilePath = file.getAbsolutePath();
        a.setText(jsonSchemaFilePath);
        jsonParser = new JSONParser(jsonSchemaFilePath);
        jsonHead = (ArrayList<String>) jsonParser.getCompleteLeafNodes();
        jsonHead = deleteSubstring(jsonHead);
        System.out.println();
        for(String text : jsonHead) {
            System.out.println(text);
        }
        for (int i = 0; i < jsonHead.size(); i++) {
            Text temp = new Text(jsonHead.get(i));
            gridpaneJson.add(temp, 0, i);
        }
    }

    /**
     * @param jsonHead2
     * @return
     */
    private ArrayList<String> deleteSubstring(ArrayList<String> array) {
        ArrayList<String> temp = array;
        ArrayList<String> delete = new ArrayList<String>();
        for(String a : temp) {
            for(String b: temp) {
                if(a.contains(b) && a!= b) {
                    delete.add(b);
                }
            }
        }
        for(String a : delete) {
            temp.remove(a);
        }

        return temp;
    }

}
