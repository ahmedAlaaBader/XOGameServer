package serversideforxogame;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class selectModeBase2 extends AnchorPane {

    protected final ImageView backGroundPic;
    protected final VBox vBox;
    protected final PieChart pieChartGraph;
    protected final Button startBtn;
    protected final Button stopBtn;

    public selectModeBase2() {

        backGroundPic = new ImageView();
        vBox = new VBox();
        pieChartGraph = new PieChart();
        startBtn = new Button();
        stopBtn = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(400.0);
        setMinWidth(600.0);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        AnchorPane.setBottomAnchor(backGroundPic, -3.0);
        AnchorPane.setLeftAnchor(backGroundPic, 0.0);
        AnchorPane.setRightAnchor(backGroundPic, -4.0);
        AnchorPane.setTopAnchor(backGroundPic, 0.0);
        backGroundPic.setFitHeight(403.0);
        backGroundPic.setFitWidth(604.0);
        backGroundPic.setId("backGround");
        backGroundPic.setLayoutX(168.0);
        backGroundPic.setPickOnBounds(true);
        backGroundPic.setPreserveRatio(false);

        AnchorPane.setBottomAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 184.0);
        AnchorPane.setRightAnchor(vBox, 184.0);
        AnchorPane.setTopAnchor(vBox, 20.0);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        vBox.setPrefHeight(200.0);
        vBox.setPrefWidth(100.0);

        startBtn.setAlignment(javafx.geometry.Pos.CENTER);
        startBtn.setBlendMode(javafx.scene.effect.BlendMode.COLOR_BURN);
        startBtn.setMnemonicParsing(false);
        startBtn.setPrefHeight(38.0);
        startBtn.setPrefWidth(232.0);
        startBtn.setText("Start");
        startBtn.setFont(new Font("System Italic", 20.0));

        stopBtn.setAlignment(javafx.geometry.Pos.CENTER);
        stopBtn.setBlendMode(javafx.scene.effect.BlendMode.COLOR_BURN);
        stopBtn.setMnemonicParsing(false);
        stopBtn.setPrefHeight(38.0);
        stopBtn.setPrefWidth(232.0);
        stopBtn.setText("Stop");
        stopBtn.setFont(new Font("System Italic", 20.0));

        getChildren().add(backGroundPic);
        vBox.getChildren().add(pieChartGraph);
        vBox.getChildren().add(startBtn);
        vBox.getChildren().add(stopBtn);
        getChildren().add(vBox);

    }
}
