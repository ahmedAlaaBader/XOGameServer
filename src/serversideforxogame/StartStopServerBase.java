package serversideforxogame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartStopServerBase extends AnchorPane {
    public static String selectStatus = new String("Stop");

    protected final ImageView backGroundPic;
    protected final Button startBtn;
    protected final Button stopBtn;
    protected final PieChart pieChartGraph;

    private static final double BUTTON_WIDTH = 232.0;
    private static final double BUTTON_HEIGHT = 38.0;
    private static final double BUTTON_FONT_SIZE = 20.0;
    private static final String BUTTON_FONT_STYLE = "System Italic";
    private static final String BACKGROUND_IMAGE_PATH = "/images/background.jfif";

    public StartStopServerBase() {
        backGroundPic = new ImageView();
        Image image = new Image(getClass().getResource(BACKGROUND_IMAGE_PATH).toExternalForm());
        if (image.isError()) {
            System.err.println("Error loading image: " + image.getException());
        } else {
            backGroundPic.setImage(image);
        }
        backGroundPic.setPreserveRatio(false);
        backGroundPic.setCursor(Cursor.DEFAULT);

        stopBtn = createButton("Stop");
        stopBtn.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            selectStatus = stopBtn.getText();
        });

        startBtn = createButton("Start");
        startBtn.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            selectStatus = startBtn.getText();
        });

        VBox buttonContainer = new VBox(20, startBtn, stopBtn);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPrefWidth(BUTTON_WIDTH);
        buttonContainer.setMaxWidth(BUTTON_WIDTH);

        pieChartGraph = createPieChart();
        pieChartGraph.setPrefSize(200, 200); // Set preferred size for PieChart

        VBox mainContainer = new VBox(20, buttonContainer, pieChartGraph);
        mainContainer.setAlignment(Pos.CENTER);
        setMinHeight(500.0);
        setMinWidth(600.0);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);

        // Bind image view dimensions to the pane dimensions
        backGroundPic.fitWidthProperty().bind(widthProperty());
        backGroundPic.fitHeightProperty().bind(heightProperty());

        // Set anchors to center the VBox
        AnchorPane.setTopAnchor(mainContainer, 0.0);
        AnchorPane.setBottomAnchor(mainContainer, 0.0);
        AnchorPane.setLeftAnchor(mainContainer, 0.0);
        AnchorPane.setRightAnchor(mainContainer, 0.0);

        getChildren().add(backGroundPic);
        getChildren().add(mainContainer);

        // Add CSS
        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        this.getStyleClass().add("background");
    }

    private Button createButton(String text) {
        Button button = new Button();
        button.setAlignment(Pos.CENTER);
        button.setBlendMode(javafx.scene.effect.BlendMode.COLOR_BURN);
        button.setMnemonicParsing(false);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setPrefWidth(BUTTON_WIDTH);
        button.setText(text);
        button.setFont(new Font(BUTTON_FONT_STYLE, BUTTON_FONT_SIZE));
        button.setCursor(Cursor.HAND);
        button.getStyleClass().add("custom-button");
        return button;
    }

    private PieChart createPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Active players", 30),
                        new PieChart.Data("Inactive players", 50),
                        new PieChart.Data("Offline players", 20));
        final PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Players Status");
        pieChart.getStyleClass().add("custom-piechart");
        return pieChart;
    }
}
