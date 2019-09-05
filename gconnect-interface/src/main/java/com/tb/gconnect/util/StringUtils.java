package com.tb.gconnect.util;


import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static final String BRACE_LEFT = "{";
    public static final String BRACE_RIGHT = "}";

    public StringUtils() {
    }

    public static String getDefaultIfEmpty(String str, String defaultValueStr) {
        return null != str && !"".equals(str) ? str : defaultValueStr;
    }

    public static String toString(Object obj) {
        return null == obj ? null : String.valueOf(obj);
    }

    public static String leftPadIfNecessary(String value, int length, char padChar) {
        return -1 == length ? value : leftPad(value, length, padChar);
    }

    public static String rightPadIfNecessary(String value, int length, char padChar) {
        return -1 == length ? value : rightPad(value, length, padChar);
    }

    public static String removeLeftPad(String value, char padChar) {
        if (isEmpty(value)) {
            return value;
        } else {
            int index = 0;
            char[] valueChars = value.toCharArray();

            for(int i = 0; i < valueChars.length; ++i) {
                if (valueChars[i] != padChar) {
                    index = i;
                    break;
                }
            }

            return value.substring(index);
        }
    }

    public static String removeRightPad(String value, char padChar) {
        if (isEmpty(value)) {
            return value;
        } else {
            int index = value.length() - 1;
            char[] valueChars = value.toCharArray();

            for(int i = value.length() - 1; i >= 0; --i) {
                if (valueChars[i] != padChar) {
                    index = i + 1;
                    break;
                }
            }

            return value.substring(0, index);
        }
    }

    public static String toString(Throwable e) {
        StringWriter w = new StringWriter();
        PrintWriter p = new PrintWriter(w);
        p.print(e.getClass().getName() + ": ");
        if (e.getMessage() != null) {
            p.print(e.getMessage() + "\n");
        }

        p.println();

        String var3;
        try {
            e.printStackTrace(p);
            var3 = w.toString();
        } finally {
            p.close();
        }

        return var3;
    }

    /** @deprecated */
//    @Deprecated
//    public static String parsePlaceHolder(String originalStr, Environment environment) {
//        Optional var10000 = Optional.ofNullable(originalStr).filter(org.apache.commons.lang3.StringUtils::isNotBlank);
//        environment.getClass();
//        return (String)var10000.map(environment::resolvePlaceholders).orElse(originalStr);
//    }

    public static String toRedisTagKey(String prefix, String key) {
        StringBuilder sb = new StringBuilder();
        if (isNotBlank(prefix)) {
            sb.append(prefix);
        }

        sb.append("{").append(key).append("}");
        return sb.toString();
    }
}
