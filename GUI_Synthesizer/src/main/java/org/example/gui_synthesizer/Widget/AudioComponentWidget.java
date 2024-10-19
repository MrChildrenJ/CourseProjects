package org.example.gui_synthesizer.Widget;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;
import org.example.gui_synthesizer.AudioComponent.Reverb;
import org.example.gui_synthesizer.SynthesizeApplication;
import java.util.ArrayList;

public class AudioComponentWidget extends Pane { // We need to draw something on the ACW
    protected AudioComponent        audioComponent;
    protected AnchorPane            parent;
    protected HBox                  widgetBox;
    protected String                name;
    protected Circle                connectionCircle;
    protected Line                  connectionLine;
    protected AudioComponentWidget  outputConnection;

    public AudioComponentWidget(AudioComponent ac, AnchorPane parent, String name) {
        this.audioComponent = ac;
        this.parent = parent;
        this.name = name;

        widgetBox = new HBox(10);
        widgetBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2");
        widgetBox.setPadding(new Insets(10));
        this.getChildren().add(widgetBox);

        VBox left = new VBox();
        VBox right = new VBox();
        this.setLeft(left);                 // left  - name & slide bar
        this.setRight(right);               // right - close btn & connection circle

        widgetBox.getChildren().addAll(left, right);
        this.setLayoutX(Math.max(Math.random() * parent.getWidth() - 200, 10));
        this.setLayoutY(Math.max(Math.random() * parent.getHeight() - 150, 10));

        enableDrag();
    }
    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name);
        Slider slider = new Slider();
        vbox.getChildren().addAll(title, slider);
    }
    protected void setRight(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Button close = new Button("x");
        close.setOnAction(e -> destroyWidget());

        connectionCircle = new Circle(10);
        connectionCircle.setFill(Color.BLUE);
        enableConnection(connectionCircle);

        vbox.getChildren().addAll(close, connectionCircle);
    }
    public void destroyWidget() {
        if (outputConnection != null)   disconnectFrom(outputConnection);
        removeIncomingConnections();
        parent.getChildren().remove(this);
        SynthesizeApplication.removeWidget(this);
    }
    protected void disconnectFrom(AudioComponentWidget target) {
        parent.getChildren().remove(connectionLine);
        connectionLine = null;
        if (outputConnection == target) {
            outputConnection = null;
            target.removeInput(this);
            updateConnectionLine();
        }
    }
    private void removeIncomingConnections() {
        for (Node node : new ArrayList<>(parent.getChildren()))
            if (node instanceof AudioComponentWidget widget)
                if (widget.outputConnection == this)
                    widget.disconnectFrom(this);
    }
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
            if (target != this &&
                    (target instanceof MixerWidget || target instanceof VolumeAdjusterWidget ||
                            target instanceof SpeakerWidget || target instanceof ReverbWidget)
                    && outputConnection == null) {
                connectTo(target);
            } else {
                // if not connecting to a valid target or already connected, remove the temporary line
                if (connectionLine != null) {
                    parent.getChildren().remove(connectionLine);
                    connectionLine = null;
                }
            }
            event.consume();
        });
    }
    protected AudioComponentWidget findTargetWidget(double sceneX, double sceneY) {
        for (Node node : parent.getChildren())
            if (node instanceof AudioComponentWidget widget && node != this)
                if (isCircleHit(widget.connectionCircle, sceneX, sceneY))
                    return widget;
        return null;
    }
    protected boolean isCircleHit(Circle circle, double sceneX, double sceneY) {
        Bounds circleBounds = circle.localToScene(circle.getBoundsInLocal());
        double centerX = circleBounds.getCenterX();
        double centerY = circleBounds.getCenterY();
        double distance = Math.sqrt(Math.pow(centerX - sceneX, 2) + Math.pow(centerY - sceneY, 2));
        return distance <= circle.getRadius();
    }
    protected void connectTo(AudioComponentWidget target) {
        if (target == this)             return;
        if (outputConnection != null)   disconnectFrom(outputConnection);
        outputConnection = target;
        target.addInput(this);
        updateConnectionLine();
    }
    private void updateIncomingConnections() {
        for (Node node : parent.getChildren())
            if (node instanceof AudioComponentWidget widget)
                if (widget.outputConnection == this)
                    widget.updateConnectionLine();
    }
    protected void addInput(AudioComponentWidget input)       {}
    protected void removeInput(AudioComponentWidget input)    {}
    protected void enableDrag() {
        final Delta dragDelta = new Delta();
        this.setOnMousePressed(event -> {
            dragDelta.x = this.getLayoutX() - event.getSceneX();
            dragDelta.y = this.getLayoutY() - event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() + dragDelta.x);
            this.setLayoutY(event.getSceneY() + dragDelta.y);
            updateConnectionLine();
            updateIncomingConnections();
        });
    }
    public void updateConnectionLine() {
        if (connectionLine != null && outputConnection != null) {
            Point2D startPoint = getConnectionPoint(this.connectionCircle);
            Point2D endPoint = getConnectionPoint(outputConnection.connectionCircle);

            connectionLine.setStartX(startPoint.getX());
            connectionLine.setStartY(startPoint.getY());
            connectionLine.setEndX(endPoint.getX());
            connectionLine.setEndY(endPoint.getY());
        }
    }
    private Point2D getConnectionPoint(Circle circle)   {
        return parent.sceneToLocal(circle.localToScene(circle.getCenterX(), circle.getCenterY()));
    }
//    protected Circle getConnectionCircle()              {  return connectionCircle;  }
    public AudioComponent getAudioComponent()           {  return audioComponent;  }
    private static class Delta                          {  double x, y;  }
}