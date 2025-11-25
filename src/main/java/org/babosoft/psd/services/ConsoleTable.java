package org.babosoft.psd.services;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ConsoleTable {
    private final List<String> headers;
    private final List<List<String>> rows = new ArrayList<>();

    private ConsoleTable(String... headers) {
        this.headers = Arrays.asList(headers);
    }

    public ConsoleTable()  {
        this("Algoritmus", "Méret", "Futási idő (ms)", "Status");
    }

    public void addRow(Object... cells) {
        List<String> row = new ArrayList<>();
        for (Object cell : cells) {
            row.add(cell == null ? "" : cell.toString());
        }
        rows.add(row);
    }

    public void print() {
        int[] colWidths = getColWidths();
        printLine(colWidths);
        printRow(headers, colWidths);
        printLine(colWidths);
        for (List<String> row : rows) {
            printRow(row, colWidths);
        }
        printLine(colWidths);
    }

    private int[] getColWidths() {
        int[] widths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                if (i < widths.length) {
                    widths[i] = Math.max(widths[i], row.get(i).length());
                }
            }
        }
        return widths;
    }

    private void printLine(int[] colWidths) {
        for (int width : colWidths) {
            System.out.print("+-" + "-".repeat(width) + "-");
        }
        System.out.println("+");
    }

    private void printRow(List<String> row, int[] colWidths) {
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i);
            // Balra igazítás (Jobbra igazításhoz a %-jelet kell variálni)
            System.out.printf("| %-" + colWidths[i] + "s ", cell);
        }
        System.out.println("|");
    }
}
