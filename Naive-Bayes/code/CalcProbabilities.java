//p3150077
//p3150196
//p3150091
import java.util.HashMap;
import java.util.Iterator;

/*
    Στη συγκεκριμενη κλαση γινονται ορισμενοι υπολογισμοι και κυριως υπολογισμοι πιθανοτητων.
    Καθε αντικειμενο αυτης της κλασης εχει τα εξης πεδια: εναν δισδιαστατο πινακα που αντιπροσωπευει το dataset,
    ενα αντικειμενο που αντιπροσωπευει ενα γνωρισμα
    occurancesA,occurancesB : αντιπροσωπευουν ποσες φορες υπαρχει στο dataset η καθε κλαση.
    HashMap<String,Double> priorProbabilities : αποθηκευονται οι πιθανοτητες να εχουμε την καθε κλαση(πιθανοτητα να ειναι <=50K και πιθανοτητα να ειναι >50K.
 */
public class CalcProbabilities {
    private String[][] data;
    private Observations classObservation;
    private int occurancesA,occurancesB;
    private HashMap<String,Double> priorProbabilities = new HashMap<>();

    public CalcProbabilities(String[][] data){//κατασκευαστης που αρχικοποιει και τον πινακα με τα δεδομενα.
        this.data = data;
    }

    //getters
    public int getOccurancesA(){
        return occurancesA;
    }

    public int getOccurancesB(){
        return occurancesB;
    }

    public HashMap<String,Double> getPriorProbabilities(){ return priorProbabilities; }

    public void calcPriorProb() {//υπολογιζει τις πιθανοτητες που εχουμε για καθε κλαση και τις αποθηκευει στο hashmap.
        classObservation = new Observations(data, data[0].length-1);//η κλαση ειναι και αυτη ενα γνωρισμα.
        double probability;
        Iterator<ObservationValues> iter = classObservation.getIterObservValues();
        while (iter.hasNext()){
            ObservationValues observationValue = iter.next();
            probability = (double)observationValue.getNumberOfTimes() / (data.length-1);//ποσες φορες υπαρχει η καθε κλαση / ποσες υπαρχουν συνολικα.
            priorProbabilities.put(observationValue.getName(),probability);
        }

    }

    public void calcCondProb(Observations CondObservation){//υπολογιζει τα conditional probabilities και εφαρμοζει εξομαλυνση Laplace.
        double likelihoodA,likelihoodB;
        calcOccurancesForClasses();//μεθοδος που υπολογιζει ποσες φορες υπαρχει η καθε κλαση.
        Iterator<ObservationValues> iter = CondObservation.getIterObservValues();
        while(iter.hasNext()){
            ObservationValues observationValue = iter.next();
            //το likelihoodA: (φορες που υπαρχει η συγκεκριμενη αξια ενος γνωρισματος στην κλασηΑ(<=50Κ) + 1 / φορες που υπαρχει η κλασηΑ + τον πληθικο αριθμο του συγκεκριμενου γνωρισματος
            //το likelihoodB: (φορες που υπαρχει η συγκεκριμενη αξια ενος γνωρισματος στην κλασηΒ(>50Κ) + 1 / φορες που υπαρχει η κλασηΒ + τον πληθικο αριθμο του συγκεκριμενου γνωρισματος
            likelihoodA = (double) (observationValue.getNumberOfTimesClassA() + 1) / (occurancesA + CondObservation.getTotalNumValues());
            likelihoodB = (double) (observationValue.getNumberOfTimesClassB() + 1) / (occurancesB + CondObservation.getTotalNumValues());
            //θετουμε τις πιθανοτητες που αντιστοιχουν στη συγκεκριμενη τιμη του συγκεκριμενου γνωρισματος.
            observationValue.setCondProbabilityA(likelihoodA);
            observationValue.setCondProbabilityB(likelihoodB);
        }

    }

    public void calcOccurancesForClasses() {//μετραει ποσες φορες υπαρχει η καθε κλαση στο dataset( count(<=50K) kai count(>50K)).
        classObservation = new Observations(data, data[0].length-1);
        String Classification = data[1][data[1].length-1];
        Iterator<ObservationValues> iter = classObservation.getIterObservValues();
        while (iter.hasNext()){
            ObservationValues observationValue = iter.next();
            if(observationValue.getName().equals(Classification)){
                occurancesA = observationValue.getNumberOfTimes();
            }else{
                occurancesB = observationValue.getNumberOfTimes();
            }
        }
    }
}
