package com.codedotorg;

import java.util.Arrays;
import java.util.List;

import com.codedotorg.modelmanager.CameraController;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LockScene {

    /** The root layout of the lock scene */
    private VBox rootLayout;

    /** Displays the camera feed in the app */
    private ImageView cameraView;

    /** Displays the title of the app */
    private Label titleLabel;

    /** Displays the predicted class and confidence score */
    private Label predictionLabel;

    /** Displays a prompt to tell the user to give a number */
    private Label promptLabel;

    /** Displays the numbers the user has provided so far */
    private Label currentPinLabel;

    /** Button to exit the app */
    private Button exitButton;

    /** The loading animation while the camera is loading */
    private Loading cameraLoading;

    /** Whether or not this is the first time the camera has loaded */
    private boolean firstCapture;

    /**
     * Constructs a new LockScene object.
     * Initializes the cameraView, exitButton, titleLabel, predictionLabel,
     * promptLabel, currentPinLabel, cameraLoading, and firstCapture.
     */
    public LockScene() {
        cameraView = new ImageView();
        cameraView.setId("camera");

        exitButton = new Button("Exit");

        titleLabel = new Label("Unlock Me!");
        titleLabel.setId("titleLabel");

        predictionLabel = new Label("");
        promptLabel = new Label("What is the PIN?");
        currentPinLabel = new Label("----");

        cameraLoading = new Loading();
        firstCapture = true;
    }

    /**
     * Returns the camera view ImageView object.
     *
     * @return the camera view ImageView object
     */
    public ImageView getCameraView() {
        return cameraView;
    }

    /**
     * Returns the loading animation
     * 
     * @return the Loading object for the loading animation
     */
    public Loading getLoadingAnimation() {
        return cameraLoading;
    }

    /**
     * Creates a new lock scene with the given camera controller.
     * 
     * @param cameraController the camera controller to use for the scene
     * @return the newly created lock scene
     */
    public Scene createLockScene(CameraController cameraController) {
        // Set the action for when the exit button is clicked
        createExitButtonAction(cameraController);

        // Initialize the root layout
        rootLayout = new VBox();
        rootLayout.setAlignment(Pos.CENTER);

        // Get list of scene components
        List<Node> sceneComponents = getComponentsAsList();

        // Add the scene components to the root layout
        rootLayout.getChildren().addAll(sceneComponents);

        // Hide the loading animation if not the first capture
        checkFirstCapture();

        // Create a new scene and set the layout as its root
        Scene lockScene = new Scene(rootLayout, 600, 750);

        // Attach the stylesheet to the scene
        lockScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Return the scene
        return lockScene;
    }

    /**
     * Displays the predicted user response on the UI.
     * 
     * @param predictedClass The predicted class of the user response.
     * @param predictedScore The predicted score of the user response.
     */
    public void showUserResponse(String predictedClass, double predictedScore) {
        // Hide the loading animation
        cameraLoading.hideLoadingAnimation(rootLayout, cameraView);
        
        // Get the predicted class without the leading number
        String user = predictedClass.substring(predictedClass.indexOf(" ") + 1);

        // Convert the predicted score to an integer percentage
        int percentage = (int)(predictedScore * 100);

        // Create a String with the predicted class and confidence score
        String userResult = "User: " + user + " (" + percentage + "% Confidence)";

        // Update the predictionLabel to show the user's response and score
        Platform.runLater(() -> predictionLabel.setText(userResult));
    }

    /**
     * Sets the text of the current pin label to the specified pin.
     * If the pin is less than 4 characters, the label will be padded with hyphens.
     * @param currentPin the current pin to display in the label
     */
    public void setPinLabel(String currentPin) {
        String pinText = "";

        pinText += currentPin;

        while (pinText.length() < 4) {
            pinText += "-";
        }

        currentPinLabel.setText(pinText);
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

    /**
     * Creates a spacer region with the specified height.
     * 
     * @param amount the preferred height of the spacer region
     * @return a Region object with the specified height
     */
    private Region createSpacer(double amount) {
        Region temp = new Region();
        temp.setPrefHeight(amount);
        return temp;
    }

    /**
     * Returns a list of all the components in the lock scene.
     * The list includes the title label, prompt label, camera loading label,
     * camera view, progress indicator, current pin label, prediction label,
     * exit button, and spacers for above and below the camera view and above
     * the exit button.
     *
     * @return a list of all the components in the lock scene
     */
    private List<Node> getComponentsAsList() {
        // Create spacers for above and below the camera view
        Region cameraSpacer1 = createSpacer(20);
        Region cameraSpacer2 = createSpacer(20);

        // Create spacer for above the exit button
        Region buttonSpacer = createSpacer(10);

        List<Node> tempList = Arrays.asList(
            titleLabel,
            promptLabel,
            cameraLoading.getCameraLoadingLabel(),
            cameraSpacer1,
            cameraView,
            cameraSpacer2,
            cameraLoading.getProgressIndicator(),
            currentPinLabel,
            predictionLabel,
            buttonSpacer,
            exitButton
        );

        return tempList;
    }

    /**
     * Checks if this is the first time the camera has captured an image.
     * If it is not the first capture, hides the loading animation. If it
     * is the first capture, sets the firstCapture flag to false.
     */
    private void checkFirstCapture() {
        if (!firstCapture) {
            cameraLoading.hideLoadingAnimation(rootLayout, cameraView);
        }
        else {
            firstCapture = false;
        }
    }
    
}
