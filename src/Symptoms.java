public class Symptoms {
    //Risk Ratings
    public static final String NO_DIFFERENCE = "No difference";
    public static final String UNSURE = "Unsure";
    public static final String VERY_DIFFERENT = "Very different";
    public static final String NO_RATING = "No rating as there's no previous game to compare with";

    private int[] symptomsArray;

    public Symptoms(int[] symptomsArray) {
        this.symptomsArray = symptomsArray;
    }

    public int getTotalNumberOfSymptoms() {
        int total = 0;
        for (int score : symptomsArray) {
            if (score > 0) {
                total++;
            }
        }
        return total;
    }

    public int getSymptomSeverityScore() {
        int total = 0;
        for (int score : symptomsArray) {
            total += score;
        }
        return total;
    }

    /**
     * Calculates Symptom Risk Rating based on the last 2 sets of Symptoms (last 2 games)
     * @param symp1: symptoms for the game before the last one
     * @param symp2: symptoms for the last game
     * @return: string with a symptom rating
     */
    public static String getSymptomRating(Symptoms symp1, Symptoms symp2) {
        int sympDifference = symp2.getTotalNumberOfSymptoms() - symp1.getTotalNumberOfSymptoms();
        int severityScore = symp2.getSymptomSeverityScore();
        if (sympDifference < 3 && severityScore < 10) {
            return NO_DIFFERENCE;
        } else if (sympDifference < 3 && severityScore >= 10) {
            return UNSURE;
        } else {
            return VERY_DIFFERENT;
        }
    }

}
