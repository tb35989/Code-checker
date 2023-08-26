package com.codedotorg;

import com.codedotorg.modelmanager.CameraController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainScene {

    /** The root layout of the lock scene */
    private VBox rootLayout;
    
    /** Button to try again */
    private Button tryAgainButton;

    /** Button to exit the app */
    private Button exitButton;

    /** Displays whether or not the pin is correct */
    private Label pinCorrectLabel;

    /**
     * Constructs a new MainScene object with a "Try again" button and an "Exit" button.
     */
    public MainScene() {
        tryAgainButton = new Button("Try again");
        exitButton = new Button("Exit");
        pinCorrectLabel = new Label("");
    }

    /**
     * Creates the main scene for the application.
     *
     * @param cameraController the camera controller for the scene
     * @return the main scene for the application
     */
    public Scene createMainScene(String pinStatus, CameraController cameraController) {
        // Set the action for when the exit button is clicked
        createExitButtonAction(cameraController);

        // Create the layout for the scene
        rootLayout = new VBox(20);
        rootLayout.setAlignment(Pos.CENTER);

        // Update the pinCorrectLabel with the pin status
        pinCorrectLabel.setText(pinStatus);

        // Add the label and buttons to the layout
        rootLayout.getChildren().addAll(pinCorrectLabel, tryAgainButton, exitButton);

        // Create the scene with the layout
        Scene mainScene = new Scene(rootLayout, 600, 750);

        // Add the stylesheet to the scene
        mainScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Return the scene
        return mainScene;
    }

    /**
     * Returns the try again button.
     * 
     * @return the try again button
     */
    public Button getTryAgainButton() {
        return tryAgainButton;
    }

    /**
     * Sets the action for the exit button. When clicked, it
     * stops the camera capture and exits the program.
     */
    private void createExitButtonAction(CameraController cameraController) {
        exitButton.setOnAction(event -> {
            cameraController.stopCapture();
            System.exit(0);
        });
    }
    
}
