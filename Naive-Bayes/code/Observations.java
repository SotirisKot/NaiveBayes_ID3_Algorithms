//p3150077
//p3150196
//p3150091
import java.util.HashSet;
import java.util.Iterator;

/*
    Η κλαση αυτη αφορα τα γνωρισματα που παρατηρουμε.Στο συγκεκριμενο dataset ειναι τα εξης:
    age, workclass, fnlwgt, education, education-num, marital-status, occupation, relationship, race, sex, capital-gain, capital-loss, hours-per-week, native-country, Class
    Καθε γνωρισμα εχει ενα συνολο τιμων οπου αποθηκευεται στο hashset ObservationValues(αφου ειναι hashset καθε τιμη που αποθηκευεται θα ειναι και η μοναδικη
    εχει υλοποιηθει καταλληλο hashcode και equals στην κλαση ObservationValues).
    Επισης, καθε γνωρισμα που παρατηρουμε εχει ενα ονομα και το ποσες διαφορετικες τιμες μπορει να παρει(πληθικο αριθμο).
    Ο κατασκευαστης της κλασης αρχικοποιει ενα γνωρισμα με το καταλληλο ονομα και δεχεται και μια στηλη, η οποια το αντιπροσωπευει στο dataset το συγκεκριμενο γνωρισμα.
    (πχ το γνωρισμα age θα εχει στηλη 0).
    Καθως, κατασκευαζει αυτο το γνωρισμα δημιουργει και το hashset για το συγκεκριμενο γνωρισμα και μετραει ποσες διαφορετικες τιμες γι αυτο υπαρχουν
    αλλα και ποσες τιμες αντιστοιχουν στην καθε κλαση,δηλαδη ποσα ατομα εχουν age=25 και ανηκουν στην κλαση των <=50K.
    Μετραει παραλληλα και ποσες φορες συνανταει την καθε τιμη(ποσες φορες βρηκε την age=25.
    Τελος,εχουμε και ενα getter που επιστρεφει μια συγκεκριμενη αξια ενος γνωρισματος με βαση το ονομα της(αν δεν την εχει ξανασυναντησει δημιουργει μια νεα αξια
    με καποιες αρχικες τιμες που χρησιμοποιουνται αργοτερα σε υπολογισμους).
 */
public class Observations {
    private String name;
    private int totalNumValues;
    private HashSet<ObservationValues> ObservationValues = new HashSet<>();

    public String getName(){
        return name;
    }

    public HashSet<ObservationValues> getObservationValues(){
        return ObservationValues;
    }

    public Iterator<ObservationValues> getIterObservValues(){
        return ObservationValues.iterator();
    }

    public int getTotalNumValues(){
        return totalNumValues;
    }

    public Observations(String [][] data, int column){
        this.name = data[0][column];
        String Classification = data[1][data[1].length-1];//επειδη εχουμε 2 κλασεις,παιρνουμε μια τυχαια εστω την <=50Κ
        for(int i=1; i<data.length; i++){
            ObservationValues.add(new ObservationValues(data[i][column]));
        }
        totalNumValues = ObservationValues.size();
        Iterator<ObservationValues> iter = ObservationValues.iterator();
        while(iter.hasNext()){
            ObservationValues observation = iter.next();
            int counter=0;
            int counterClassA=0;
            int counterClassB=0;
            for(int row=0; row<data.length; row++){
                if(data[row][column].equals(observation.getName()) ){
                    if(data[row][data[row].length-1].equals(Classification)){//συγκρινουμε την τυχαια κλαση με την κλαση της συγκεκριμενης αξιας και αν ειναι ισες
                                                                            //αυξανουμε τον αντιχτοιχο μετρητη.
                        observation.setNumberOfTimesClassA(++counterClassA);
                    }else{//επειδη γενικα εχουμε 2 κλασεις σημαινει οτι αν δεν μπει στο πρωτο if σιγουρα ανηκει στην δευτερη κλαση.
                        observation.setNumberOfTimesClassB(++counterClassB);
                    }
                    observation.setNumberOfTimes(++counter);
                }
            }
        }
    }

    public ObservationValues getObservationValue(String name,String[][] data){
        ObservationValues returnObject = null;
        Iterator<ObservationValues> iter = ObservationValues.iterator();
        while(iter.hasNext()){
            ObservationValues observation = iter.next();
            if(name.equals(observation.getName())){
                returnObject = observation;
                return returnObject;
            }
        }
        CalcProbabilities calculate = new CalcProbabilities(data);
        calculate.calcOccurancesForClasses();
        returnObject = new ObservationValues(name,0,0,0,(double) 1/(calculate.getOccurancesA()+totalNumValues),(double) 1/(calculate.getOccurancesB()+totalNumValues));
        return returnObject;
    }

}
