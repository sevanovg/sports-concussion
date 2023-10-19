public class MedicalPractitioner extends User {
    public MedicalPractitioner(String name) {
        this.name = name;
        this.role = UserManager.MED_PRACTITIONER_ROLE;
    }
}
