package fr.polytech.rlcalm.utils;

import fr.polytech.rlcalm.exception.InvalidFormException;

public class FormUtils {

    public static boolean isNullOrEmpty(String parameter) {
        return parameter == null || parameter.isEmpty();
    }

    public static Integer getRequiredInt(String parameter, String error) throws InvalidFormException {
        if (parameter != null) {
            try {
                return Integer.parseInt(parameter);
            } catch (Exception ignored) {}
        }
        throw new InvalidFormException(error);
    }

    public static Integer getInt(String parameter, String error) throws InvalidFormException {
        if (parameter != null) {
            try {
                return Integer.parseInt(parameter);
            } catch (Exception ignored) {
                throw new InvalidFormException(error);
            }
        }
        return null;
    }

    public static Long getLong(String parameter, String error) {
        if (parameter != null) {
            try {
                return Long.parseLong(parameter);
            } catch (Exception ignored) {
                throw new InvalidFormException(error);
            }
        }
        return null;
    }

    public static Long getLongForId(String parameter, String error) {
        if (! isNullOrEmpty(parameter)) {
            try {
                return Long.parseLong(parameter);
            } catch (Exception ignored) {
                throw new InvalidFormException(error);
            }
        }
        return null;
    }

    public static Long getRequiredLong(String parameter, String error) throws InvalidFormException {
        if (parameter != null) {
            try {
                return Long.parseLong(parameter);
            } catch (Exception ignored) {}
        }
        throw new InvalidFormException(error);
    }

    public static String notNull(String parameter, String error) {
        if (parameter != null) {
            return parameter;
        }
        throw new InvalidFormException(error);
    }
}
