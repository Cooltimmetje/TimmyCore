package me.Cooltimmetje.TimmyCore.Utilities.TableUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public final class TableArrayGenerator {

    private ArrayList<TableRow> rows;

    public TableArrayGenerator(){
        this.rows = new ArrayList<TableRow>();
    }

    public TableArrayGenerator(TableRow... tableRows){
        this.rows = new ArrayList<TableRow>(Arrays.asList(tableRows));
    }

    public void addRow(TableRow tableRow){
        rows.add(tableRow);
    }

    public String[][] generateArray(){
        String[][] strings = new String[rows.size()][rows.get(0).getLength()];
        Iterator<TableRow> iterator = rows.iterator();
        int i = 0;
        while (iterator.hasNext()){
            Iterator<String> innerIterator = iterator.next().getIterator();
            int j = 0;
            while(innerIterator.hasNext()){
                strings[i][j] = innerIterator.next();
                j++;
            }
            i++;
        }
        return strings;
    }

}
