package org.example.gui_synthesizer;

import org.example.gui_synthesizer.AudioComponent.*;
import org.example.gui_synthesizer.Wave.*;
import org.example.gui_synthesizer.Widget.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.util.ArrayList;

import static java.lang.System.out;

public class SynthesizeApplication extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 960, 720);
        stage.setTitle("Synthesizer App");

        // topPanel
        Label titleLabel = new Label("Synthesizer App");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setPadding(new Insets(10));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        // rightPanel
        VBox rightPane = new VBox(15);
        rightPane.setPadding(new Insets(10));
        rightPane.setStyle("-fx-background-color: DARKOLIVEGREEN;");

        Button sineWaveBtn = new Button("Create a SineWave");
        Button squareWaveBtn = new Button("Create a SquareWave");
        Button VFSineWaveBtn = new Button("Create a VFSineWave");
        Button whiteNoiseBtn = new Button("Create a WhiteNoise");
        Button mixerBtn = new Button("Create a Mixer");
        Button volumeAdjusterBtn = new Button("Create a VolumeAdjuster");
        Button linearRampBtn = new Button("Create a LinearRamp");
        Button reverbBtn = new Button("Create a Reverb");
        Button clearAllWidgetsBtn = new Button("Clear All Widgets");

        sineWaveBtn.setOnAction(e -> createComponent("SineWave"));
        squareWaveBtn.setOnAction(e -> createComponent("SquareWave"));
        VFSineWaveBtn.setOnAction(e -> createComponent("VFSineWave"));
        whiteNoiseBtn.setOnAction(e -> createComponent("WhiteNoise"));
        mixerBtn.setOnAction(e -> createComponent("Mixer"));
        volumeAdjusterBtn.setOnAction(e -> createComponent("VolumeAdjuster"));
        linearRampBtn.setOnAction(e -> createComponent("LinearRamp"));
        reverbBtn.setOnAction(e -> createComponent("Reverb"));
        clearAllWidgetsBtn.setOnAction(e -> clearAllWidgets());

        sineWaveBtn.setMaxWidth(Double.MAX_VALUE);
        squareWaveBtn.setMaxWidth(Double.MAX_VALUE);
        whiteNoiseBtn.setMaxWidth(Double.MAX_VALUE);
        mixerBtn.setMaxWidth(Double.MAX_VALUE);
        volumeAdjusterBtn.setMaxWidth(Double.MAX_VALUE);
        VFSineWaveBtn.setMaxWidth(Double.MAX_VALUE);
        linearRampBtn.setMaxWidth(Double.MAX_VALUE);
        reverbBtn.setMaxWidth(Double.MAX_VALUE);
        clearAllWidgetsBtn.setMaxWidth(Double.MAX_VALUE);

        sineWaveBtn.setAlignment(Pos.CENTER);
        squareWaveBtn.setAlignment(Pos.CENTER);
        whiteNoiseBtn.setAlignment(Pos.CENTER);
        mixerBtn.setAlignment(Pos.CENTER);
        volumeAdjusterBtn.setAlignment(Pos.CENTER);
        VFSineWaveBtn.setAlignment(Pos.CENTER);
        linearRampBtn.setAlignment(Pos.CENTER);
        reverbBtn.setAlignment(Pos.CENTER);
        clearAllWidgetsBtn.setAlignment(Pos.CENTER);

        rightPane.getChildren().addAll(sineWaveBtn, squareWaveBtn, VFSineWaveBtn, whiteNoiseBtn,
                mixerBtn, volumeAdjusterBtn, linearRampBtn, reverbBtn ,clearAllWidgetsBtn);
        rightPane.setPrefWidth(200);

        // centerPanel
        centerPanel.setStyle("-fx-background-color: CORNFLOWERBLUE;");
        Speaker speaker = new Speaker();
        speakerWidget = new SpeakerWidget(speaker, centerPanel, "Speaker");
        speakerWidget.setLayoutX(450);
        speakerWidget.setLayoutY(200);
        centerPanel.getChildren().add(speakerWidget);
        allWidgets.add(speakerWidget);

        // bottomPane
        HBox bottomPane = new HBox();
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setStyle("-fx-background-color: DARKSLATEBLUE;");
        Button playBtn = new Button("Play");
        playBtn.setOnAction(e -> play());
        bottomPane.getChildren().add(playBtn);
        bottomPane.setPrefHeight(50);

        // Layout
        root.setCenter(centerPanel);
        root.setRight(rightPane);
        root.setTop(titleLabel);
        root.setBottom(bottomPane);

        int spaceBetweenPanel = 2;
        BorderPane.setMargin(titleLabel, new Insets(spaceBetweenPanel));
        BorderPane.setMargin(centerPanel, new Insets(spaceBetweenPanel));
        BorderPane.setMargin(rightPane, new Insets(spaceBetweenPanel));
        BorderPane.setMargin(bottomPane, new Insets(spaceBetweenPanel));

        // Display
        stage.setScene(scene);
        stage.show();
    }
    public static void removeWidget(AudioComponentWidget acw) {
        allWidgets.remove(acw);
    }
    private void createComponent(String name) {
        AudioComponentWidget widget = switch (name) {
            case "SineWave" -> new SineWaveWidget(new SineWave(440), centerPanel, name);
            case "SquareWave" -> new SquareWaveWidget(new SquareWave(440), centerPanel, name);
            case "VFSineWave" -> new VFSineWaveWidget(new VFSineWave(), centerPanel, name);
            case "WhiteNoise" -> new WhiteNoiseWidget(new WhiteNoise(), centerPanel, name);
            case "Mixer" -> new MixerWidget(new Mixer(), centerPanel, name);
            case "VolumeAdjuster" -> new VolumeAdjusterWidget(new VolumeAdjuster(2.0), centerPanel, name);
            case "LinearRamp" -> new LinearRampWidget(new LinearRamp(50, 2000), centerPanel, name);
            case "Reverb" -> new ReverbWidget(new Reverb(0, 1), centerPanel, name);
            default -> null;
        };
        if (widget != null) {
            allWidgets.add(widget);
            centerPanel.getChildren().add(widget);
        }
    }
    private void play() {
        try {
            Clip c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            AudioListener listener = new AudioListener(c);

            //Mixer mixer = new Mixer();
            Speaker speaker = (Speaker) speakerWidget.getAudioComponent();
            AudioClip clip = speaker.getClip();
            byte[] data = clip.getData();

            c.open(format16, data, 0, data.length);
            out.print("playing...");
            c.start();
            c.loop(0);
            c.addLineListener(listener);
            System.out.println( "Done." );
        } catch (LineUnavailableException e) {
            out.println("A clip can not be opened.");
        }
    }
    private void clearAllWidgets() {
        ArrayList<AudioComponentWidget> widgetsToRemove = new ArrayList<>(allWidgets);
        widgetsToRemove.remove(speakerWidget);

        for (AudioComponentWidget widget : widgetsToRemove) {
            widget.destroyWidget(); // This method should handle removing connections and lines
        }

        // Clear all widgets from the centerPanel except Speaker
        centerPanel.getChildren().clear();
        centerPanel.getChildren().add(speakerWidget);

        // Clear all widgets from allWidgets except Speaker
        allWidgets.clear();
        allWidgets.add(speakerWidget);

        // Clear all connections to Speaker
        //speakerWidget.clearConnections();

        // Update the UI
        centerPanel.requestLayout();
    }

    public static SpeakerWidget speakerWidget;
    AnchorPane centerPanel = new AnchorPane();
    private static ArrayList<AudioComponentWidget> allWidgets = new ArrayList<>();
    private static ArrayList<AudioComponentWidget> widgetsConnectedToSpeaker = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }
}