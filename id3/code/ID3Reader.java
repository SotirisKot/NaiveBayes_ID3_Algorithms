//p3150077
//p3150196
//p3150091
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ID3Reader {
    public static ArrayList<String[]> readData(String fileName) throws IOException {
        BufferedReader myReader = new BufferedReader(new FileReader(fileName));
        int columns = myReader.readLine().split(",").length;
        ArrayList<String[]> data = new ArrayList<String[]>();
        String line;
        while ((line = myReader.readLine()) != null) {
            String[] rowData = new String[columns];
            for (int i = 0; i < columns; i++) {
                String[] value = line.split(",", columns);
                rowData[i] = value[i];
            }
            data.add(rowData);
        }

        return data;
    }
}