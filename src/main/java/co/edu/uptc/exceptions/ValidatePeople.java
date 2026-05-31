package co.edu.uptc.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import co.edu.uptc.model.ManagerApp;
import co.edu.uptc.pojo.Person;

public class ValidatePeople {
    static ManagerApp manager = ManagerApp.getInstance();
    static List<Person> list = manager.getAll();

    public static Result<Person> validate(Person person) {
        List<TypingErrors> errors = new ArrayList<>();
        validateName(person, errors);
        validateLastName(person, errors);
        validateGender(person, errors);
        validateBirthDate(person, errors);
        validateEmail(person, errors);
        validateDocument(person, errors);
        validateDuplicates(person, errors);
        if (errors.isEmpty())
            return Result.successful(person);
        else
            return Result.mistake(errors);
    }

    private static void validateName(Person person, List<TypingErrors> errors) {
        if (person.getName().isBlank()) errors.add(TypingErrors.NAME_EMPTY);
        else if (person.getName().length() < 3) errors.add(TypingErrors.NAME_MIN_LENGTH);
    }

    private static void validateLastName(Person person, List<TypingErrors> errors) {
        if (person.getLastName().isBlank()) errors.add(TypingErrors.LAST_NAME_EMPTY);
        else if (person.getLastName().length() < 3) errors.add(TypingErrors.LASTNAME_MIN_LENGTH);
    }

    private static void validateGender(Person person, List<TypingErrors> errors) {
        if (person.getGender() == null) errors.add(TypingErrors.GENDER_BLANK);
    }

    private static void validateBirthDate(Person person, List<TypingErrors> errors) {
        if (person.getBirthDate() == null) errors.add(TypingErrors.BIRTHDATE_BLANK);
        else if (!person.getBirthDate().before(new Date())) errors.add(TypingErrors.INVALID_BIRTH_DATE);
    }

    private static void validateEmail(Person person, List<TypingErrors> errors) {
        String email = person.getEmail();
        if (email.isBlank()) { errors.add(TypingErrors.EMAIL_EMPTY); return; }
        if (email.length() > 100) errors.add(TypingErrors.EMAIL_MAX_LENGHT);
        if (email.contains(" ")) errors.add(TypingErrors.INVALID_EMAIL);
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty() || !email.contains("."))
            errors.add(TypingErrors.INVALID_EMAIL);
    }

    private static void validateDocument(Person person, List<TypingErrors> errors) {
        String doc = person.getNumberDocument();
        switch (person.getDocumentType()) {
            case "Tarjeta de Identidad" -> {
                if (!doc.matches("[0-9]+") || doc.length() < 10 || doc.length() > 11)
                    errors.add(TypingErrors.INVALID_IDENTITY_CARD);
            }
            case "Cédula de Ciudadanía" -> {
                if (!doc.matches("[0-9]+") || doc.length() < 7 || doc.length() > 10)
                    errors.add(TypingErrors.INVALID_ID_CARD);
            }
            case "Cédula de Extranjería" -> {
                if (!doc.matches("[0-9]+") || doc.length() < 6 || doc.length() > 7)
                    errors.add(TypingErrors.INVALID_FOREIGN_ID);
            }
            case "Pasaporte" -> {
                if (!doc.matches("[a-zA-Z0-9]+") || doc.length() != 9)
                    errors.add(TypingErrors.INVALID_PASSPORT);
            }
            case "Otro" -> {
                if (doc.length() > 20) errors.add(TypingErrors.INVALID_OTHER_DOCUMENT);
            }
        }
    }

    private static void validateDuplicates(Person person, List<TypingErrors> errors) {
        for (Person p : list) {
            if (person.getEmail().equals(p.getEmail()))
                errors.add(TypingErrors.DUPLICATE_EMAIL);
            if (person.getDocumentType().equals(p.getDocumentType()) && person.getNumberDocument().equals(p.getNumberDocument()))
                errors.add(TypingErrors.DUPLICATE_DOCUMENT);
        }
    }
    
    
}