package fr.polytech.rlcalm.utils;

public class FormUtils {

    public static Integer getRequiredInt(String parameter) throws IllegalArgumentException {
        if (parameter != null) {
            try {
                return Integer.parseInt(parameter);
            } catch (Exception ignored) {}
        }
        throw new IllegalArgumentException();
    }

    public static Integer getInt(String parameter) throws IllegalArgumentException {
        if (parameter != null) {
            try {
                return Integer.parseInt(parameter);
            } catch (Exception ignored) {
                throw new IllegalArgumentException();
            }
        }
        return null;
    }

    public static Long getLong(String parameter) {
        if (parameter != null) {
            try {
                return Long.parseLong(parameter);
            } catch (Exception ignored) {
                throw new IllegalArgumentException();
            }
        }
        return null;
    }

    public static Long getRequiredLong(String parameter) throws IllegalArgumentException {
        if (parameter != null) {
            try {
                return Long.parseLong(parameter);
            } catch (Exception ignored) {}
        }
        throw new IllegalArgumentException();
    }

    public static String notNull(String city) {
        if (city != null) {
            return city;
        }
        throw new IllegalArgumentException();
    }
}
