package co.edu.uptc.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class FileManager<T> {
    private final Path csvPath;
    private final Function<T, String> toCsvRow;
    private final Function<String, T> fromCsvRow;
    private final Predicate<T> validator;

    public FileManager(Path csvPath,Function<T, String> toCsvRow,Function<String, T> fromCsvRow,Predicate<T> validator) {
        this.csvPath = csvPath;
        this.toCsvRow = toCsvRow;
        this.fromCsvRow = fromCsvRow;
        this.validator = validator == null ? t -> true : validator;
    }

    public void append(T item) throws IOException {
        if (!validator.test(item)) {
            throw new IllegalArgumentException("El item no cumple la validacion antes de guardar");
        }
        try (BufferedWriter bw = Files.newBufferedWriter(
                csvPath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,      
                StandardOpenOption.APPEND
        )) {
            bw.write(toCsvRow.apply(item));
            bw.newLine();
        }
    }

    public ArrayList<T> readAll() throws IOException {
        if (!Files.exists(csvPath)) {
            return new ArrayList<>();
        }
        ArrayList<T> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(fromCsvRow.apply(line));
            }
        }
        return result;
    }

    private Path createTempFile() throws IOException {
        Path parent = csvPath.toAbsolutePath().getParent();
        String prefix = csvPath.getFileName().toString();
        return Files.createTempFile(parent, prefix, ".tmp");
    }

    private void replaceCsvWith(Path temp) throws IOException {
        Files.move(temp, csvPath,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
    }

    private int writeFilteredLines(Path temp, Predicate<T> shouldDelete) throws IOException {
        int deletedCount = 0;
        try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
            BufferedWriter bw = Files.newBufferedWriter(temp, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
            String line;
            while ((line = br.readLine()) != null) {
                T item = fromCsvRow.apply(line);
                if (shouldDelete.test(item)) {
                    deletedCount++;
                    continue;
                }
                bw.write(line);
                bw.newLine();
            }
        }
        return deletedCount;
    }

    public int deleteIf(Predicate<T> shouldDelete) throws IOException {
        if (fromCsvRow == null) throw new IllegalStateException("fromCsvRow no fue provisto; usa el constructor con parser para poder borrar");
        if (!Files.exists(csvPath)) return 0;
        Path temp = createTempFile();
        int deletedCount = writeFilteredLines(temp, shouldDelete);
        replaceCsvWith(temp);
        return deletedCount;
    }

    private int writeUpdatedLines(Path temp, Predicate<T> shouldUpdate, Function<T, T> updater) throws IOException {
        int updatedCount = 0;
        try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
            BufferedWriter bw = Files.newBufferedWriter(temp, StandardCharsets.UTF_8,StandardOpenOption.TRUNCATE_EXISTING)) {
            String line;
            while ((line = br.readLine()) != null) {
                T item = fromCsvRow.apply(line);
                if (shouldUpdate.test(item)) {
                    item = updater.apply(item);
                    updatedCount++;
                }
                bw.write(toCsvRow.apply(item));
                bw.newLine();
            }
        }
        return updatedCount;
    }

    public int updateIf(Predicate<T> shouldUpdate, Function<T, T> updater) throws IOException {
        if (fromCsvRow == null) throw new IllegalStateException("fromCsvRow no fue provisto");
        if (!Files.exists(csvPath)) return 0;
        Path temp = createTempFile();
        int updatedCount = writeUpdatedLines(temp, shouldUpdate, updater);
        replaceCsvWith(temp);
        return updatedCount;
    }
}
