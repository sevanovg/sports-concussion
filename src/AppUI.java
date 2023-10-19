import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class AppUI {
    //Menu and prompt designs
    private static final String SEPARATOR = "-------------------------\n";
    private static final String CURRENT_USER_INFO =
                                            "Your user group: %s\n" +
                                            "Your name: %s\n";
    private static final String ATHLETE_MENU = SEPARATOR +
                                            CURRENT_USER_INFO +
                                            "\n" +
                                            "1. Enter Symptoms\n" +
                                            "2. Display Symptoms Summary\n" +
                                            "3. Am I At Risk?\n" +
                                            "4. Change User\n" +
                                            "5. Exit App\n" +
                                            "\n";
    private static final String MED_MENU = SEPARATOR +
                                            CURRENT_USER_INFO +
                                            "\n" +
                                            "1. Athletes' Risky Condition Assessment Report\n" +
                                            "2. Change User\n" +
                                            "3. Exit\n" +
                                            "\n";
    private static final String USER_SELECTION_MENU = SEPARATOR +
                                            "Select user group:\n" +
                                            "1. Athlete\n" +
                                            "2. Sport Medical Practitioner\n";
    private static final String USER_NAME_ENTRY_MENU = "Enter your name or ID (50 symbols maximum): ";
    private static final String SELECT_OPTION_MESSAGE = "Select option: ";
    private static final String SYMPTOM_ENTRY_PROMPT = "Please enter your %s score (none (0), " +
                                            "mild (1-2), moderate (3-4), " +
                                            "& severe (5-6)): ";

    private static final String SYMPTOM_SUMMARY_REPORT_ENTRY =  
                                            "Total number of symptoms: %d\n" +
                                            "Symptom severity score: %d\n" +
                                            "Overall rating: %s" +
                                            "\n";
    private static final String FOR_MED_SUMMARY_REPORT_ENTRY =SEPARATOR + "Athlete's name: %s\n" +
                                            SEPARATOR + SYMPTOM_SUMMARY_REPORT_ENTRY;                                   
    private static final String FOR_ATHLETE_SUMMARY_REPORT_ENTRY = "Game/Practice number %d\n" +
                                            SEPARATOR + SYMPTOM_SUMMARY_REPORT_ENTRY;      
    private static final String RETURN_TO_MENU = "Press enter to return to the menu";
    private static final String SYMPTOMS_ACCEPTED = "Thank you. Your data was accepted";
    private static final String AT_RISK_RATING = SEPARATOR +
                                            "Your At Risk rating is: ";
    private static final String NO_ATHLETES_WITH_SYMPTOMS_MESSAGE = SEPARATOR + "There are no athletes " + 
                                            "who have entered their symptoms yet.";
                                                                                                 
    //Selection options for user group selection menu
    private static final String SELECTED_MED_PRACT = "2";
    private static final String SELECTED_ATHLETE= "1";
    
    //Selection options for Athlete menu
    private static final String ATHLETE_SELECTED_ENTER_SYMPTOMS = "1";
    private static final String ATHLETE_SELECTED_DISPLAY_SYMPTOMS= "2";
    private static final String ATHLETE_SELECTED_AT_RISK = "3";
    private static final String ATHLETE_SELECTED_CHANGE_USER = "4";
    private static final String ATHLETE_SELECTED_EXIT = "5";

    //Selection options for Medical Practitioner menu
    private static final String MED_SELECTED_REVIEW_RISK = "1";
    private static final String MED_SELECTED_CHANGE_USER = "2";
    private static final String MED_SELECTED_EXIT = "3";

    //Incorrect input messages
    private static final String INCORRECT_SELECTION_STRING = "Incorrect selection";
    private static final String INCORRECT_NAME_STRING = "Incorrect name/ID";
    private static final String INCORRECT_SYMPTOM_ENTRY = "Incorrect value entered";

    //Symptoms
    private static final String[] SYMPTOM_TEXT_ARRAY = {"headache",
                                                        "pressure in head",
                                                        "neck pain",
                                                        "nausea or vomiting",
                                                        "dizziness",
                                                        "blurred vision",
                                                        "balance problems",
                                                        "sensitivity to light",
                                                        "sensitivity to noise",
                                                        "feeling slowed down",
                                                        "feeling like 'in a fog'",
                                                        "don't feel right",
                                                        "difficulty concentrating",
                                                        "difficulty remembering",
                                                        "fatigue or low energy",
                                                        "confusion",
                                                        "drowsiness",
                                                        "trouble falling asleep",
                                                        "more emotional",
                                                        "irritability",
                                                        "sadness",
                                                        "nervous or anxious"};

    //Color codes
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_GREEN = "\u001B[32m";
    private static final String COLOR_YELLOW = "\u001B[33m";
    private static final String COLOR_RESET = "\u001B[0m";

    private static final String BACKGROUND_RED = "\u001B[41m";
    private static final String BACKGROUND_GREEN = "\u001B[42m";
    private static final String BACKGROUND_YELLOW = "\u001B[43m";

    //Other constants
    private static final int MAX_NAME_LENGTH = 50;
                 
    UserManager userManager;
    Scanner scanner;
    
    public AppUI() {
        this.userManager = new UserManager();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws Exception {
        AppUI appInstance = new AppUI();
        appInstance.displayUserSelectionMenu();
    }  
    
    private void displayUserSelectionMenu() {
        System.out.println(USER_SELECTION_MENU); //display menu
        String selectedRole = getValidMenuSelection(Arrays.asList("1", "2")); //get role selection
        String userName = getValidName(); //get user name
        switch (selectedRole) {
            case SELECTED_ATHLETE:
                //go to the Athlete user path
                userManager.setCurrentUser(userManager.getAthlete(userName));
                displayAthleteMenu();
                break;
            case SELECTED_MED_PRACT:
                //go to the Med Practitioner user path
                userManager.setCurrentUser(userManager.getMedicalPractitioner(userName));
                displayMedPractitionerMenu();
                break;
        }
    }

    private void displayAthleteMenu() {
        User user = userManager.getCurrentUser();
        System.out.print(String.format(ATHLETE_MENU, user.getRole(), user.getName())); //print menu template
        String selection = getValidMenuSelection(Arrays.asList("1", "2", "3", "4", "5")); //get user selection
        
        //perform the acction according to what option the user has been selected
        switch (selection) {
            case ATHLETE_SELECTED_ENTER_SYMPTOMS:
                displaySymptomEntryPrompt();
                break;
            case ATHLETE_SELECTED_DISPLAY_SYMPTOMS:
                displaySymptomSummaryReport();
                break;
            case ATHLETE_SELECTED_AT_RISK:
                displayAtRiskReport();
                break;
            case ATHLETE_SELECTED_CHANGE_USER:
                displayUserSelectionMenu();
                break;
            case ATHLETE_SELECTED_EXIT:
                System.exit(0);
        }

    }

    private void displayMedPractitionerMenu() {
        User user = userManager.getCurrentUser();
        System.out.println(String.format(MED_MENU, user.getRole(), user.getName())); //print menu template
        String selection = getValidMenuSelection(Arrays.asList("1", "2", "3")); //get user selection
        
        //perform the acction according to what option the user has been selected
        switch (selection) {
            case MED_SELECTED_REVIEW_RISK:
                displayRiskReviewReport();
                break;
            case MED_SELECTED_CHANGE_USER:
                displayUserSelectionMenu();
                break;
            case MED_SELECTED_EXIT:
                System.exit(0);
        }
    }

    /**
     * Displays risk review report for medical practitioners.
     * Includes the data for all the athletes based on their last entered symptoms.
     * Included data: total number of symptoms, total severity score, risky condition review 
     */
    private void displayRiskReviewReport() {
         //get all the registered athletes (who have at least entered their names)
         List<Athlete> allAthletesWithSymptoms = userManager.getAllAthletesWithSymptoms();

         //If no athletes have entered their symptoms, display the according message
         if (allAthletesWithSymptoms.size() == 0) {
            System.out.println(NO_ATHLETES_WITH_SYMPTOMS_MESSAGE);
         } else {
            //If there are athletes with symptoms then display all of them
            for (Athlete athlete : allAthletesWithSymptoms) {
                Symptoms symptoms = athlete.getLastSymptoms();
                System.out.println(String.format(FOR_MED_SUMMARY_REPORT_ENTRY, athlete.getName(),
                                                symptoms.getTotalNumberOfSymptoms(),
                                                symptoms.getSymptomSeverityScore(),
                                                getColorString(athlete.getRiskRating())));
            }
         }

        //Display an option to return to the menu
        System.out.println(RETURN_TO_MENU);
        scanner.nextLine();
        displayMedPractitionerMenu();
    }

    /**
     * Displays symptom entry prompt. Recieves valid input for each of the 22 symptoms.
     * Creates a Symptoms object based on the entered symptoms and adds a Symptoms object to
     * the current Athlete object
     */
    private void displaySymptomEntryPrompt() {       
         //visual separator
         System.out.println(SEPARATOR);

         //get the inputs for 22 symptoms and save in the int array
        int[] symptomInput = new int[22];
        for (int i = 0; i < 22; i++) {
            //get the valid input from 0 to 6
            String symptomPrompt = String.format(SYMPTOM_ENTRY_PROMPT, SYMPTOM_TEXT_ARRAY[i]);
            System.out.print(symptomPrompt);
            String userInput = this.scanner.nextLine();
            List<String> validInput = Arrays.asList("0", "1", "2", "3", "4", "5", "6");
            while (!validInput.contains(userInput)) {
                System.out.println(INCORRECT_SYMPTOM_ENTRY);
                System.out.print(symptomPrompt);
                userInput = this.scanner.nextLine();
            }

            //save the input in the array
            symptomInput[i] = Integer.parseInt(userInput);
        }

        //Confirm that data was accepted
        System.out.println(SYMPTOMS_ACCEPTED);

        //create a Symptoms ojbect
        Symptoms symptoms = new Symptoms(symptomInput);

        //assign a Symptoms object to a currently logged in Athlete
        Athlete curAthlete = (Athlete) userManager.getCurrentUser();
        curAthlete.addSymptoms(symptoms);

        //return back to the main Athlete menu
        displayAthleteMenu();
    }

    /**
    * Displays symptom summary report for the last 5 games/practices for the current athlete
    */
    private void displaySymptomSummaryReport() {

         //get current athlete and their symptom history
        Athlete curAthlete = (Athlete) userManager.getCurrentUser();
        Queue<Symptoms> sympHistory = curAthlete.getSymptomHistory();

        //display current user info
        System.out.println(String.format(CURRENT_USER_INFO, curAthlete.getRole(), curAthlete.getName()));

        //display summary info for each entry in the sympHistory
        Symptoms prevSymptoms = null; //symptoms from the previous game
        int gameCounter = 1;
        for (Symptoms symptoms : sympHistory) {
            //get the information to display
            int sympSeverityScore = symptoms.getSymptomSeverityScore();
            int totalNumberOfSymptoms = symptoms.getTotalNumberOfSymptoms();
            String symptomRating = Symptoms.NO_RATING;
            if (prevSymptoms != null) {
                symptomRating = Symptoms.getSymptomRating(prevSymptoms, symptoms);
            }
            
            //display the summary for the game
            System.out.println(String.format(FOR_ATHLETE_SUMMARY_REPORT_ENTRY, gameCounter, 
            totalNumberOfSymptoms, sympSeverityScore, symptomRating));

            gameCounter++;
            prevSymptoms = symptoms; //update previous symptoms
        }

        //provide option to return to the main menu
        System.out.println(RETURN_TO_MENU);
        scanner.nextLine();
        displayAthleteMenu();
    }

    /**
    * Displays at risk report for the current athlete based on last 2 entries of symptoms
    */
    private void displayAtRiskReport() {
        //get current athlete and their symptom history
        Athlete curAthlete = (Athlete) userManager.getCurrentUser();

        //get at risk rating based on 2 last games
        String atRiskRating = curAthlete.getRiskRating();

        //display current user info
        System.out.println(String.format(CURRENT_USER_INFO, curAthlete.getRole(), curAthlete.getName()));

        //Display the rating
        System.out.print(AT_RISK_RATING);
        System.out.println(getColorString(atRiskRating));

         //provide option to return to the main menu
        System.out.println(RETURN_TO_MENU);
        scanner.nextLine();
        displayAthleteMenu();
    }

    /**
    * Converts textual rating into an according color rectangle
    */
    private String getColorString(String ratingString) {
        switch(ratingString) {
            case Symptoms.NO_DIFFERENCE:
                return COLOR_GREEN + BACKGROUND_GREEN + "   " + COLOR_RESET;
            case Symptoms.UNSURE:
                return COLOR_YELLOW + BACKGROUND_YELLOW + "   " + COLOR_RESET;
            case Symptoms.VERY_DIFFERENT:
                return COLOR_RED + BACKGROUND_RED + "   " + COLOR_RESET;
            default:
                return ratingString;
        }
    }

    /**
     * Gets a valid name/ID from the user
     * @return: Validated name/ID provided by the user
     */
    private String getValidName() {
        System.out.print(USER_NAME_ENTRY_MENU); //display the menu template
        String userInput = scanner.nextLine();
        while (userInput.length() == 0 || userInput.length() > MAX_NAME_LENGTH) {
            System.out.println(INCORRECT_NAME_STRING);
            System.out.print(USER_NAME_ENTRY_MENU);
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    /**
    * Gets a valid menu selection from the user based on a provided list of valid inputs
    */
    private String getValidMenuSelection(List<String> validInputs) {
        System.out.print(SELECT_OPTION_MESSAGE);
        String userInput = scanner.nextLine();
        while (!validInputs.contains(userInput)) {
            System.out.println(INCORRECT_SELECTION_STRING);
            System.out.print(SELECT_OPTION_MESSAGE);
            userInput = scanner.nextLine();
        }
        return userInput;
    }
}
