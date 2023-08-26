# Unlock the App

Unlock the App is a JavaFX application that uses image classification to generate and validate a pin for unlocking purposes. The application captures live camera feed, processes the images using a TensorFlow model, and updates the GUI accordingly.

## 🔎 Existing Code

### Unlock.java

The `Unlock` class controls the locking/unlocking mechanism flow, handles predicted classes, displays user responses, and manages the updating timeline.

**Suggested Modifications and New Features:**

* Allow configuration of confidence score thresholds.

### LockScene.java

The `LockScene` class represents the initial "locked" state of the application and provides features like displaying the camera feed, loading animations, and showing user responses.

**Suggested Modifications and New Features:**

* Personalize the locked interface with custom backgrounds or themes.
* Display countdown or status updates based on pin entry progress.

### MainScene.java

The `MainScene` class sets up the main scene of the application. This includes components like the validation label and action buttons.

**Suggested Modifications and New Features:**

* Introduce robust error handling and user-friendly messages.
* Allow users to toggle camera feed display.
* Implement features to customize UI elements like button colors and label fonts.

### Loading.java

The `Loading` class manages the loading animations and labels shown during camera initialization or data processing.

**Suggested Modifications and New Features:**

* Introduce different loading animations for variety.
* Display a loading percentage or estimated time remaining.
* Implement a retry or cancel mechanism for prolonged loading times.

## ✅ TO DO: AppLogic.java

The `AppLogic` class manages the logic behind pin creation, validation, and user interactions based on the predicted class from the image classification results.

Implement the following methods in `AppLogic.java` to incorporate your Teachable Machine model in the app.

`createUserPin(String predictedClass)`

* Based on the `predictedClass`, append the digit to the user's PIN.
* Return the updated user PIN as a string.

`checkPinLength()`

* Check the length of the `user` attribute.
* Return `true` if the length is equal to 4, otherwise return `false`.

`getPinStatus(String userPin)`

* Compare `userPin` with the stored `pin`.
* Return `"correct"` if the PINs match.
* Return `"incorrect"` if they don't match.

`createRandomPin()`

* Implement logic to generate a random 4-digit PIN between 1000 and 9999.
* Return the generated PIN as a string.
