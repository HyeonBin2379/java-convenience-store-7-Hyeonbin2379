package store.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Dao<T> {

    private static final String DIRECTORY_PATH = "src/main/resources/";

    public abstract void initialize() throws FileNotFoundException;

    public abstract List<T> getAll();

    public final List<String> readData(String filename) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileReader(DIRECTORY_PATH + filename));
        List<String> fileData = new ArrayList<>();

        fileReader.nextLine();
        while (fileReader.hasNext()) {
            fileData.add(fileReader.nextLine());
        }
        return fileData;
    }
}
