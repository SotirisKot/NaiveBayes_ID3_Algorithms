//p3150077
//p3150196
//p3150091
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        double PosteriorProbA,PosteriorProbB;//τα νουμερα που θα συγκρινουμε για να αποφασισουμε σε ποια κλαση ανηκει το καθε ατομο.
        DataProcessing data = new DataProcessing();
        ArrayList<Double> ProbabilityA = new ArrayList<>();//αποθηκευονται οι conditional probabilities της καθε αξιας του καθε γνωρισματος για ενα ατομο οταν υποθετουμε
                                                          //οτι ανηκει στην πρωτη κλαση
        ArrayList<Double> ProbabilityB = new ArrayList<>();//το αντιστοιχο οταν υποθετουμε οτι ανηκει στην 2η κλαση.
        ArrayList<Observations> ObsForTest = new ArrayList<>();
        String name = "Dataset.txt";
        String dataAr[][] = data.createArray(name);//δημιουργουμε τον πινακα με τα δεδομενα
        System.out.println("----CREATED THE TRAINING DATASET----");
        CalcProbabilities calculate = new CalcProbabilities(dataAr);
        System.out.println("----TRAINING THE ALGORITHM----");
        calculate.calcPriorProb();//υπολογιζουμε τις πιθανοτητες των κλασεων. οι conditional probs υπολογιζονται πιο κατω.
        Observations predict;
        for(int i=0; i < dataAr[0].length-2; i++) {
        	predict = new Observations(dataAr, i);
            calculate.calcCondProb(predict);
            ObsForTest.add(predict);
        }
        System.out.println("----TRAINING ENDED----");
        System.out.println("----TESTING----");
        String[] input;
        String line;
        String filename = "Testing.txt";
        String ClassA = "<=50K";
        String ClassB = ">50K";
        Observations testPredict;
        String correctAns;
        int CorrectAnswers = 0;//ποσες σωστες απαντησεις εχουμε δωσει.
        int TotalAnswers = 1;//ποσες ερωτησεις εχουμε γενικα ή αλλιως ποσα ατομα εχουμε.
        double accuracy;//ακριβεια του αλγοριθμου
        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            while (line!=null){
                input = line.split( "[\\s,]+");
                for(int i=0; i < input.length-2; i++){//καθε line ειναι και ενα ξεχωριστο ατομο.
                	testPredict = ObsForTest.get(i);
                    ProbabilityA.add(testPredict.getObservationValue(input[i],dataAr).getCondProbabilityA());//αποθηκευουμε την καθε μια με βαση τις κλασεις που εχουμε.
                    ProbabilityB.add(testPredict.getObservationValue(input[i],dataAr).getCondProbabilityB());
                }
                //υπολογιζουμε τις Posterior probs τις οποια θα συγκρινει ο αλγοριθμος για να αποφασισει.
                PosteriorProbA = calculate.getPriorProbabilities().get(ClassA);
                PosteriorProbB = calculate.getPriorProbabilities().get(ClassB);
                for(int i =0; i < ProbabilityA.size(); i++){
                    //εχουμε γινομενο γιατι υποθετουμε οτι οι αξιες ειναι ανεξαρτητες μεταξυ τους.
                    PosteriorProbA =   PosteriorProbA * ProbabilityA.get(i);
                    PosteriorProbB =   PosteriorProbB * ProbabilityB.get(i);
                }
                //δινουμε απαντηση αναλογα με τη συγκριση
                if(PosteriorProbA > PosteriorProbB){
                    System.out.println("The classification of the person is: " + ClassA + " with posterior probability : " + PosteriorProbA + "   over    " + PosteriorProbB);
                    correctAns = "<=50K.";
                }else{
                    System.out.println("The classification of the person is: " + ClassB + " with posterior probability : " + PosteriorProbB + "   over     "+ PosteriorProbA);
                    correctAns = ">50K.";
                }
                if(input[14].equals(correctAns)){//αν ειναι σωστη η απαντηση αυξανουμε τον μετρητη.
                    CorrectAnswers++;
                }
                line= bufferedReader.readLine();//διαβαζουμε νεα γραμμη που σημαινει νεο ατομο
                //αρα αδειαζουμε τα ArrayList που εχουμε και μηδενιζουμε τις Posterior probs.
                ProbabilityA.clear();
                ProbabilityB.clear();
                PosteriorProbA = 0;
                PosteriorProbB = 0;
                TotalAnswers++;//αυξανουμε τον μετρητη των συνολικων ατομων στο Testing.txt
            }
            accuracy = (double) CorrectAnswers / TotalAnswers;//ακριβεια του αλγοριθμου.
            System.out.println("The algorithm has accuracy of : " + accuracy*100 + "% ");
        }catch(FileNotFoundException ex){
            System.out.println(
                    "Unable to open file '" +
                            filename + "'");
        }catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + filename + "'");
        }
    }
}