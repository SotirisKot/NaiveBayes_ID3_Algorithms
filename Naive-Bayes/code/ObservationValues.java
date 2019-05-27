//p3150077
//p3150196
//p3150091
/*
    Αντιπροσωπευει τις αξιες που ανηκουν σε ενα γνωρισμα.Δηλαδη, ολες τις ηλικιες που υπαρχουν κατω απο το γνωρισμα age.
    Καθε αξια εχει τα εξης πεδια: ονομα, ποσες φορες βρεθηκε στην κλαση <=50Κ , ποσες στην κλαση >50Κ, ποσες φορες βρεθηκε γενικα,
    και την πιθανοτητα το γνωρισμα να εχει την συγκεκριμενη αξια δεδομενου οτι ανηκει σε μια απο τις 2 κλασεις.
 */
public class ObservationValues {
    private String name;
    private int NumberOfTimesClassA;
    private int NumberOfTimesClassB;
    private int NumberOfTimes;
    private double condProbabilityA;
    private double condProbabilityB;

    public ObservationValues(String name){
        this.name = name;
    }//δημιουργει αξια με βαση το ονομα της

    public ObservationValues(String name,int num, int numA, int numB, double ProbA, double ProbB){//δημιοιυργει μια νεα αξια οταν αυτη δεν υπαρχει στο dataset.
        this.name = name;
        this.NumberOfTimes = num;
        this.NumberOfTimesClassA = numA;
        this.NumberOfTimesClassB = numB;
        this.condProbabilityA = ProbA;
        this.condProbabilityB = ProbB;
    }

    //getters
    public String getName(){
        return name;
    }

    public int getNumberOfTimesClassA(){
        return NumberOfTimesClassA;
    }

    public int getNumberOfTimesClassB(){
        return NumberOfTimesClassB;
    }

    public int getNumberOfTimes(){
        return NumberOfTimes;
    }

    public double getCondProbabilityA(){

        return condProbabilityA;
    }

    public double getCondProbabilityB(){
        return condProbabilityB;
    }

    //κανουμε override το hashcode και την equals ωστε το hashset να μπορει να βρει μια συγκεκριμενη τιμη και να την συγκρινει με μια αλλη
    //για να δει αν ηδη την εχει αποθηκευμενη.
    public int hashCode(){
        return name.hashCode();
    }

    public boolean equals(Object object){
        boolean flag = true;
        if(object == null || getClass() != object.getClass()){
            flag = false;
        }else if(!name.equals(((ObservationValues)object).name)){
            flag = false;
        }
        return flag;
    }

    //setters.
    public void setName(String name){
        this.name = name;
    }

    public void setNumberOfTimesClassA(int num){
        this.NumberOfTimesClassA = num;
    }

    public void setNumberOfTimesClassB(int num){
        this.NumberOfTimesClassB = num;
    }

    public void setNumberOfTimes(int num){
        this.NumberOfTimes = num;
    }

    public void setCondProbabilityA(double likelihood){
        this.condProbabilityA = likelihood;
    }

    public void setCondProbabilityB(double likelihood){
        this.condProbabilityB = likelihood;
    }
}
