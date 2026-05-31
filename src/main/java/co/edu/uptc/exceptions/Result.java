package co.edu.uptc.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private List<TypingErrors> errors;
    private T data;
    private boolean success;

    private Result(T data, List<TypingErrors> errors, boolean success) {
        this.data = data;
        this.errors = errors;
        this.success = success;
    }

    public static <T> Result<T> successful(T data) {
        return new Result<>(data, new ArrayList<>(), true);
    }

    public static <T> Result<T> mistake(List<TypingErrors> errors) {
        return new Result<>(null, errors, false);
    }

    public List<TypingErrors> getErrors() {
        return errors;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
