package com.codedotorg;

public class AppLogic {

    /** The pin to unlock the app */
    private String pin;

    /** The pin the user has provided */
    private String user;

    /**
     * Constructor for the AppLogic class.
     * Initializes the pin with a random value and user with an empty string.
     */
    public AppLogic() {
        pin = createRandomPin();
        user = "";
    }

    /**
     * Creates a user PIN based on the predicted class.
     * @param predictedClass the predicted class from the machine learning model
     * @return the user PIN as a string
     */
    public String createUserPin(String predictedClass) {
        if (predictedClass.equals("1")) {
            user += "1";
        }
        if (predictedClass.equals("2")) {
            user += "2";
        }
        if (predictedClass.equals("3")) {
            user += "3";
        }
        if (predictedClass.equals("4")) {
            user += "4";
        }
        if (predictedClass.equals("0")) {
            user += "0";
        }
        return user;
    }

    /**
     * Checks if the length of the user's PIN is equal to 4.
     * @return true if the length of the user's PIN is equal to 4, false otherwise.
     */
    public boolean checkPinLength() {
        if (user.length() == 4) {
            return true;
        }
        return false;
    }

    /**
     * Returns the status of the user's input PIN.
     * @param userPin the PIN entered by the user
     * @return a string indicating whether the PIN is correct or not
     */
    public String getPinStatus(String userPin) {
        if (userPin.equals(pin)) {
            return "Correct";
        }
        return "Incorrect";
    }
    
    /**
     * Resets the logic of the application by generating
     * a new random PIN and clearing the user field.
     */
    public void resetLogic() {
        pin = createRandomPin();
        user = "";
    }

    /**
     * Generates a random 4-digit PIN number.
     * @return the generated PIN number as a string.
     * 
     * 
     * NOTE: PIN IS ONLY COMPOSED OF DIGITS 0-4!!!
     * 
     * 
     */
    private String createRandomPin() {
        int temp;
        String newPin = "";
        for (int i = 0; i < 4; i++) {
            temp = (int) (Math.random() * 5);
            newPin = newPin + temp;
        }
        return newPin;
    }

}
