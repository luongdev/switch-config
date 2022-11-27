package luongdev.switchconfig.common.util;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Utils {
    private Utils() {}


    public static String getValueByTabName(String content, String tagName) {
        String ret = null;

        if (content == null) {
            return ret;
        }

        String tagBegin = "<" + tagName + ">";
        String tagEnd = "</" + tagName + ">";

        int posBegin = content.indexOf(tagBegin);
        int posEnd = content.indexOf(tagEnd);
        if (posBegin == -1) {
            return content;
        }
        if (posEnd >= posBegin) {
            ret = content.substring(posBegin + tagBegin.length(), posEnd);
        }

        return ret;
    }


    public static void sleep() {
        try {
            Thread.currentThread();
            Thread.sleep(500);
        } catch (Exception ex) {

        }
    }

    public static Collector toSingleton() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            if (list.size() != 1) {
                throw new IllegalStateException();
            }
            return list.get(0);
        });
    }

    public static String toCamelCase(String in) {
        var sb = new StringBuilder();
        boolean capitalizeNext = false;
        for (char c : in.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else if (c == '.') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    sb.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    sb.append(c);
                }
            }
        }

        return sb.toString();
    }
}