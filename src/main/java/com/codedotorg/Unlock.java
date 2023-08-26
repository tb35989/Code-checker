package com.codedotorg;

import com.codedotorg.modelmanager.CameraController;
import com.codedotorg.modelmanager.ModelManager;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Unlock {

    /** The main window of the app */
    private Stage window;

    /** The LockScene of the app */
    private LockScene lock;

    /** The MainScene of the app */
    private MainScene app;

    /** The AppLogic to handle the logic of the app */
    private AppLogic logic;

    /** Manages the TensorFlow model used for image classification */
    private ModelManager model;

    /** Controls the camera capture and provides frames to the TensorFlow model for classification */
    private CameraController cameraController;

    /** The Timeline to manage how often a prediction is made */
    private Timeline timeline;

    /**
     * Constructor for the Unlock class.
     * Initializes the window, model, camera capture, app scenes, and logic.
     *
     * @param primaryStage the primary stage for the application
     */
    public Unlock(Stage primaryStage) {
        // Set up the window using the primaryStage
        setUpWindow(primaryStage);

        // Set up the model and camera capture
        cameraController = new CameraController();
        model = new ModelManager();

        // Set up the app scenes and logic
        lock = new LockScene();
        app = new MainScene();
        logic = new AppLogic();
    }

    /**
     * Sets up the window to point to the primaryStage, sets the title of the window to "Unlock the App",
     * and adds a shutdown hook to stop the camera capture when the app is closed.
     *
     * @param primaryStage the primary stage of the application
     */
    public void setUpWindow(Stage primaryStage) {
        // Set up window to point to the primaryStage
        window = primaryStage;

        // Set the title of the window
        window.setTitle("Unlock the App");

        // Shutdown hook to stop the camera capture when the app is closed
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cameraController.stopCapture();
        }));
    }

    /**
     * This method is called when the application is started. It loads the lock scene and updates the app.
     */
    public void startApp() {
        loadLockScene();
        updateApp();
    }

    /**
     * Loads the lock scene by resetting the app to starting defaults, displaying the window,
     * capturing the camera view and setting the model for the cameraController object, retrieving
     * the Loading object, and showing the loading animation while the camera is loading.
     */
    public void loadLockScene() {
        // Set the app to starting defaults
        resetApp();

        // Display the window
        window.show();

        // Capture the camera view and set the model for the cameraController object
        cameraController.captureCamera(lock.getCameraView(), model);

        // Retrieve the Loading object
        Loading cameraLoading = lock.getLoadingAnimation();

        // Show the loading animation while the camera is loading
        cameraLoading.showLoadingAnimation(lock.getCameraView());
    }

    /**
     * Updates the app by getting the predicted class and score from the CameraController,
     * showing the user's response and confidence score in the app, adding the user's response
     * to the pin so far, displaying the current pin so far, and checking if all four numbers
     * have been given. If all four numbers have been given, creates a pause transition of 3 seconds,
     * sets the action to execute after the pause, and starts the pause transition.
     */
    public void updateApp() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            // Get the predicted class and score from the CameraController
            String predictedClass = cameraController.getPredictedClass();
            double predictedScore = cameraController.getPredictedScore();

            if (predictedClass != null) {
                // Show the user's response and confidence score in the app
                lock.showUserResponse(predictedClass, predictedScore);

                // Add the user's response to the pin so far
                String userPin = logic.createUserPin(predictedClass);

                // Displays the current pin so far
                lock.setPinLabel(userPin);

                // Check if all four numbers have been given
                if (userPin.length() == 4) {
                    // Create a pause transition of 3 seconds
                    PauseTransition pause = new PauseTransition(Duration.seconds(3));

                    // Set the action to execute after the pause
                    pause.setOnFinished(e -> {
                        String pinStatus = logic.getPinStatus(userPin);
                        loadMainScene(pinStatus);
                    });

                    // Start the pause transition
                    pause.play();
                }
            }
        }));

        // Specify that the animation should repeat indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the animation
        timeline.play();
    }

    /**
     * Loads the main scene with the given PIN status.
     * Retrieves the tryAgainButton from the MainScene and sets it to reset the app when clicked.
     * Creates the MainScene layout and sets it in the window.
     * Stops the timeline.
     *
     * @param pinStatus the PIN status to load the main scene with
     */
    public void loadMainScene(String pinStatus) {
        // Retrieve the tryAgainButton from the MainScene
        Button tryAgainButton = app.getTryAgainButton();

        // Set the tryAgainButton to reset the app when clicked
        tryAgainButton.setOnAction(event -> {
            resetApp();
        });

        // Create the MainScene layout
        Scene mainScene = app.createMainScene(pinStatus, cameraController);

        // Set the MainScene in the window
        window.setScene(mainScene);

        // Stop the timeline
        timeline.stop();
    }

    /**
     * Resets the app by resetting the AppLogic, creating a new LockScene, setting
     * the LockScene in the window, and playing the timeline if it is not null.
     */
    public void resetApp() {
        // Reset the AppLogic
        logic.resetLogic();

        // Create the LockScene for the app
        Scene lockScene = lock.createLockScene(cameraController);

        // Set the LockScene in the window
        window.setScene(lockScene);

        // Play the timeline if it is not null
        if (timeline != null) {
            timeline.play();
        }
    }

}
