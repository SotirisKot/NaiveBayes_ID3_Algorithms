//p3150077
//p3150196
//p3150091
import java.io.*;

public class DataProcessing {
    public String[][] createArray(String filename){
        String line;
        String[][] data=new String[32561][15];
        String[] parts = null;
        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            int row = 0;
            while (line!=null){

                parts = line.split( "[\\s,]+");
                for(int i=0; i < parts.length; i++){
                    data[row][i] = parts[i];
                }
                row++;
                line= bufferedReader.readLine();
            }
        }catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            filename + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + filename + "'");
        }
        return data;
    }
}
