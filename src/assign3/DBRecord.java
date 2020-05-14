package assign3;

import java.util.ArrayList;
import java.util.Collection;

public class DBRecord {
    private ArrayList<DBBinding> dbRecords;
    private boolean selected = false;

    public DBRecord(ArrayList<DBBinding> dbRecords) {
        this.dbRecords = dbRecords;
    }

//    public DBRecord(String line){
//        this(parseLine(line));
//    }

//    private static Collection<DBBinding> parseLine(String line) {
//
//    }

    @Override
    public String toString() {
        return super.toString();
    }
}
