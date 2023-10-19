import java.util.Queue;
import java.util.Deque;
import java.util.LinkedList;

public class Athlete extends User {
    private Deque<Symptoms> symptomHistory; //symptoms for the last 5 entries

    public Athlete(String name) {
        this.name = name;
        this.role = UserManager.ATHLETE_ROLE;
        symptomHistory = new LinkedList<>();
    }

    /**
     * Adds a new set of symptoms
     */
    public void addSymptoms(Symptoms symptoms) {

        //if history already contains 5 symptom entries then remove the oldest one according to FIFO
        if (symptomHistory.size() == 5) {
            symptomHistory.poll();
        }

        //add a new symptom set to the history
        symptomHistory.add(symptoms);
    }

    public Queue<Symptoms> getSymptomHistory() {
        return symptomHistory;
    }

    /**
     * Gets a Risk Rating for the current Athlet
     */
    public String getRiskRating() {
        String atRiskRating = Symptoms.NO_RATING;
        if (symptomHistory.size() >= 2) {
            // get the symptoms for the last 2 games
            Symptoms lastGameSymptoms = symptomHistory.removeLast();
            Symptoms prevGameSymptoms = symptomHistory.getLast();

            //return the removed value to symptom history
            symptomHistory.addLast(lastGameSymptoms);

            //get rating best on last 2 games
            atRiskRating = Symptoms.getSymptomRating(prevGameSymptoms, lastGameSymptoms);
        }
        return atRiskRating;
    }

    /**
     * Gets Symptoms which were last entered for the current Athlete
     */
    public Symptoms getLastSymptoms () {
        if (symptomHistory.peekLast() != null) {
            return symptomHistory.peekLast();
        } else {
            return null;
        }
    }
}
