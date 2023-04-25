package com.comp301.async;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Tutorial adapted from: https://docs.oracle.com/javafx/2/get_started/hello_world.htm
 */
public class AsynchGui extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Asynch GUI Demo");

        VBox main_pane = new VBox();

        HBox button_slider_pane = new HBox();

        Slider slider = new Slider(0, 30, 15);
        Button btn = new Button();
        btn.setText("Go");

        button_slider_pane.getChildren().add(slider);
        button_slider_pane.getChildren().add(btn);

        HBox display_pane = new HBox();
        display_pane.getChildren().add(new Label("Count Max: "));

        Label count_max = new Label();
        count_max.setMinWidth(75);
        display_pane.getChildren().add(count_max);

        display_pane.getChildren().add(new Label("Current Count: "));

        Label current_count = new Label();
        current_count.setMinWidth(75);
        display_pane.getChildren().add(current_count);

        main_pane.getChildren().add(button_slider_pane);
        main_pane.getChildren().add(display_pane);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                count_max.setText(Integer.toString((int) slider.getValue()));
            }
        });

        btn.setOnAction((ActionEvent e) -> {
            btn.setDisable(true);
            Thread t = new Thread(() -> {
                int count_max_val = (int) slider.getValue();
                for (int i = 0; i < count_max_val; i++) {
                    final int count_to_display = i;
                    Platform.runLater(() -> {
                        current_count.setText(Integer.toString(count_to_display));
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
                Platform.runLater(() -> {
                    current_count.setText(Double.toString(count_max_val));
                    btn.setDisable(false);
                });
            });
            t.start();
        });

        Scene scene = new Scene(main_pane);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
