package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.gui_synthesizer.AudioComponent.*;

public class LinearRampWidget extends AudioComponentWidget {
    protected static int numOfLRW = 1;
    private final LinearRamp linearRamp;

    public LinearRampWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        this.linearRamp = (LinearRamp) ac;
        ++numOfLRW;
    }
    @Override
    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + " #" + numOfLRW);

        // Start value control
        Label startLabel = new Label("Start:");
        TextField startInput = new TextField("50");
        startInput.setPrefWidth(60);

        // Stop value control
        Label stopLabel = new Label("Stop:");
        TextField stopInput = new TextField("2000");
        stopInput.setPrefWidth(60);

        HBox startBox = new HBox(5, startLabel, startInput);
        HBox stopBox = new HBox(5, stopLabel, stopInput);

        startInput.setOnAction(e -> updateLinearRamp(false, startInput.getText()));
        stopInput.setOnAction(e -> updateLinearRamp(true, stopInput.getText()));

        vbox.getChildren().addAll(title, startBox, stopBox);
    }
    private void updateLinearRamp(boolean isStop, String value) {
        try {
            double newValue = Double.parseDouble(value);
            if (newValue >= 50 && newValue <= 10000) {
                if (isStop)     linearRamp.setStop(newValue);
                else            linearRamp.setStart(newValue);
            } else {
                if (isStop)     ((TextField) ((HBox) widgetBox.getChildren().get(2)).getChildren().get(1)).setText("2000");
                else            ((TextField) ((HBox) widgetBox.getChildren().get(1)).getChildren().get(1)).setText("50");
            }
        } catch (NumberFormatException e) {
            // Reset to default if invalid input
            if (isStop)         ((TextField) ((HBox) widgetBox.getChildren().get(2)).getChildren().get(1)).setText("2000");
            else                ((TextField) ((HBox) widgetBox.getChildren().get(1)).getChildren().get(1)).setText("50");
        }
    }
    @Override
    protected void enableConnection(Circle circle) {    // execute only on connectionCircle
        circle.setOnMousePressed(event -> {             // triggered when mouse button is pressed
            Point2D circleCenter = parent.sceneToLocal(circle.localToScene(circle.getCenterX(), circle.getCenterY()));
            connectionLine = new Line(
                    circleCenter.getX(),
                    circleCenter.getY(),
                    circleCenter.getX(),
                    circleCenter.getY()
            );
            parent.getChildren().add(connectionLine);
            event.consume();
        });

        circle.setOnMouseDragged(event -> {         // triggered when mouse cursor is dragged
            if (connectionLine != null) {
                Point2D localPoint = parent.sceneToLocal(event.getSceneX(), event.getSceneY());
                connectionLine.setEndX(localPoint.getX());
                connectionLine.setEndY(localPoint.getY());
            }
            event.consume();
        });

        circle.setOnMouseReleased(event -> {
            AudioComponentWidget target = findTargetWidget(event.getSceneX(), event.getSceneY());
            if (target != this && (target instanceof VFSineWaveWidget) && outputConnection == null) {
                connectTo(target);
            } else if (connectionLine != null) {
                    parent.getChildren().remove(connectionLine);
                    connectionLine = null;
            }
            event.consume();
        });
    }
}