package co.edu.uptc.exceptions;

public class ErrorTranslator {

    public String translate(TypingErrors error) {
        return switch (error) {
            case NAME_MIN_LENGTH -> "El nombre debe tener mínimo 3 caracteres";
            case LASTNAME_MIN_LENGTH -> "El apellido debe tener mínimo 3 caracteres";
            case NAME_EMPTY -> "El nombre no puede estar contenido de solo espacios en blanco";
            case LAST_NAME_EMPTY -> "El apellido no puede estar contenido de solo espacios en blanco";
            case INVALID_BIRTH_DATE -> "La fecha de nacimiento no puede ser mayor a la fecha actual";
            case BIRTHDATE_BLANK -> "La fecha no debe de estar vacia";
            case INVALID_EMAIL -> "El correo electrónico debe tener un formato válido ejemplo: nombre@dominio.com";
            case EMAIL_MAX_LENGHT -> "El correo electronico excede los 100 caracteres";
            case EMAIL_EMPTY -> "El correo electronico no puede estar contenido de solo espacios en blanco";
            case INVALID_IDENTITY_CARD -> "La tarjeta de identidad debe tener entre 10 y 11 dígitos numéricos";
            case INVALID_ID_CARD -> "La cédula de ciudadanía debe tener entre 7 y 10 dígitos numéricos";
            case INVALID_PASSPORT -> "El pasaporte debe tener exactamente 9 caracteres alfanuméricos";
            case INVALID_FOREIGN_ID -> "La cédula de extranjería debe tener entre 6 y 7 dígitos numéricos";
            case INVALID_OTHER_DOCUMENT -> "El documento no debe superar los 20 caracteres";
            case GENDER_BLANK -> "Debe seleccionar una opción";
            case DUPLICATE_EMAIL -> "El correo electrónico ya está en uso";
            case DUPLICATE_DOCUMENT -> "Ya existe una cuenta con este número de documento";
            case PERSON_NOT_FOUND -> "No se encuentra registro de esta persona";
            
            case CODE_ALPHANUMERIC_REQUIRED -> "El código del producto debe ser alfanumérico"; 
            case CODE_INVALID_LENGTH -> "El código del producto debe tener entre 3 y 20 caracteres."; 
            case CODE_BLANK -> "El código del producto no debe estar compuesto únicamente por espacios en blanco."; 
            case CODE_NAME_BLANK -> "El nombre del producto no debe estar compuesto únicamente por espacios en blanco."; 
            case CODE_NAME_MIN_LENGTH -> "El nombre del producto debe tener mínimo 3 caracteres."; 
            case CODE_NAME_MAX_LENGTH -> "El nombre del producto debe tener máximo 80 caracteres."; 
            case DESCRIPTION_MAX_LENGTH -> "La descripción del producto debe tener máximo 200 caracteres"; 
            case CATEGORY_MIN_LENGTH -> "La categoría del producto debe tener mínimo 3 caracteres."; 
            case CATEGORY_BLANK -> "La categoría del producto no debe estar compuesta únicamente por espacios en blanco."; 
            case PRICE_MUST_BE_GREATER_THAN_ZERO -> "El precio unitario debe ser mayor que cero."; 
            case PRICE_INVALID_DECIMALS -> "El precio unitario debe tener máximo dos cifras decimales."; 
            case QUANTITY_MUST_BE_POSITIVE -> "La cantidad disponible debe ser mayor o igual que cero"; 
            case PRODUCT_NOT_FOUND -> "No se encuentra registro de este producto"; 
            case DUPLICATE_CODE -> "Ya existe un producto con este codigo"; 
            default -> "Error desconocido"; 
        };
    }
}
