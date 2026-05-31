package co.edu.uptc.exceptions;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.model.ManagerApp;
import co.edu.uptc.pojo.Product;

public class ValidateProduct {
    static ManagerApp manager = ManagerApp.getInstance();
    static List<Product> list = manager.getAllProduct();

    public static Result<Product> validate(Product product) {
        List<TypingErrors> errors = new ArrayList<>();
        validateCode(product, errors);
        validateName(product, errors);
        validateDescription(product, errors);
        validateCategory(product, errors);
        validatePrice(product, errors);
        validateAmount(product, errors);
        validateDuplicates(product, errors); 
        if (errors.isEmpty())
            return Result.successful(product);
        else
            return Result.mistake(errors);
    }

    private static void validateCode(Product product, List<TypingErrors> errors) {
        if (product.getCode().isBlank()) {
            errors.add(TypingErrors.CODE_BLANK);
        } else {
            if (!product.getCode().matches("[a-zA-Z0-9]+"))
                errors.add(TypingErrors.CODE_ALPHANUMERIC_REQUIRED);
            if (product.getCode().length() < 3 || product.getCode().length() > 20)
                errors.add(TypingErrors.CODE_INVALID_LENGTH);
        }
    }

    private static void validateName(Product product, List<TypingErrors> errors) {
        if (product.getName().isBlank()) {
            errors.add(TypingErrors.CODE_NAME_BLANK);
        } else {
            if (product.getName().length() < 3)
                errors.add(TypingErrors.CODE_NAME_MIN_LENGTH);
            if (product.getName().length() > 80)
                errors.add(TypingErrors.CODE_NAME_MAX_LENGTH);
        }
    }

    private static void validateDescription(Product product, List<TypingErrors> errors) {
        if (product.getDescription() != null && product.getDescription().length() > 200)
            errors.add(TypingErrors.DESCRIPTION_MAX_LENGTH);
    }

    private static void validateCategory(Product product, List<TypingErrors> errors) {
        if (product.getCategory().isBlank()) {
            errors.add(TypingErrors.CATEGORY_BLANK);
        } else {
            if (product.getCategory().length() < 3)
                errors.add(TypingErrors.CATEGORY_MIN_LENGTH);
        }
    }

    private static void validatePrice(Product product, List<TypingErrors> errors) {
        if (product.getPrice() <= 0) {
            errors.add(TypingErrors.PRICE_MUST_BE_GREATER_THAN_ZERO);
        } else {
            if (!String.valueOf(product.getPrice()).matches("\\d+(\\.\\d{1,2})?"))
                errors.add(TypingErrors.PRICE_INVALID_DECIMALS);
        }
    }

    private static void validateAmount(Product product, List<TypingErrors> errors){
        if (product.getAmount() < 0)
            errors.add(TypingErrors.QUANTITY_MUST_BE_POSITIVE);
    }

    private static void validateDuplicates(Product product, List<TypingErrors> errors) {
        for (Product p : list) {
            if (product.getCode().equals(p.getCode()))
                errors.add(TypingErrors.DUPLICATE_CODE);
        }
    }

}