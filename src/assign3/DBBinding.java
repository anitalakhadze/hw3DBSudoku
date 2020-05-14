package assign3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DBBinding {
    private String key;
    private String value;
    private BufferedReader in;

    public DBBinding(String fileName) throws IOException {
        this.key = key;
        this.value = value;
        in = new BufferedReader(new FileReader(fileName));
        while (true){
            String line = in.readLine();
            String parsedLine = parseLine(line);
            if (line == null) break;
        }
    }

    private String parseLine(String line) {
        return line.trim();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
