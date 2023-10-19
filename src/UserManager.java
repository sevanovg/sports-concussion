import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserManager {
    public static final String MED_PRACTITIONER_ROLE = "Sport Medical Practitioner";
    public static final String ATHLETE_ROLE = "Athlete";

    private List<User> users;
    private User currentUser;

    public UserManager() {
        this.users = new ArrayList<User>();
    }
    
    /**
     * Returns an Athlete objec with a given name. If it doesn't exists - adds to a list of users and returns.
     */
    public Athlete getAthlete(String name) {
        for (User user : users) {
            if (user.getName().equals(name) && user.getRole().equals(ATHLETE_ROLE)) {
                return (Athlete)user;
            }
        }
        Athlete athlete = new Athlete(name);
        this.users.add(athlete);
        return athlete;
    }

    /**
     * Returns a Medical Practitioner object with a given name. 
     * If it doesn't exists - adds to a list of users and returns.
     */
    public MedicalPractitioner getMedicalPractitioner(String name) {
        for (User user : users) {
            if (user.getName().equals(name) && user.getRole().equals(MED_PRACTITIONER_ROLE)) {
                return (MedicalPractitioner)user;
            }
        }
        MedicalPractitioner practitioner = new MedicalPractitioner(name);
        this.users.add(practitioner);
        return practitioner;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Returns a List of Athletes who have entered at least 1 set of symptoms
     */
    public List<Athlete> getAllAthletesWithSymptoms() {
        List<Athlete> resultList = new LinkedList<>();
        for (User user : users) {
            //Get only athletes out of all users
            if (user.getRole() == ATHLETE_ROLE) {
                Athlete athlete = (Athlete) user;
                
                //Get only athletes who have entered symptoms for at least 1 game
                if (athlete.getSymptomHistory().size() > 0) {
                    resultList.add((Athlete) user);
                }
            }
        }
        return resultList;
    }

}
