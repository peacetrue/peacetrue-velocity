package com.github.peacetrue.velocity;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

/**
 * 命名约定
 *
 * @author xiayx
 */
public class NameConventionUtils {

    /** UpperCamel */
    public static class UpperCamel {

        private static final Converter<String, String> TO_LOWER_CAMEL = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL);
        private static final Converter<String, String> TO_LOWER_HYPHEN = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);
        private static final Converter<String, String> TO_LOWER_UNDERSCORE = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

        /** lowerCamel */
        public static String toLowerCamel(String name) {
            return TO_LOWER_CAMEL.convert(name);
        }

        /** lower-hyphen */
        public static String toLowerHyphen(String name) {
            return TO_LOWER_HYPHEN.convert(name);
        }

        /** lower_underscore */
        public static String toLowerUnderscore(String name) {
            return TO_LOWER_UNDERSCORE.convert(name);
        }
    }

    public static void setUpperCamel(Context context, String key) {
        context.put(key, NameConventionUtils.UpperCamel.class);
    }

    public static void setUpperCamel(Context context) {
        setUpperCamel(context, "UpperCamel");
    }

    public static class LowerCamel {

        private static final Converter<String, String> TO_UPPER_CAMEL = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL);
        private static final Converter<String, String> TO_LOWER_HYPHEN = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);

        private static final Converter<String, String> TO_LOWER_UNDERSCORE = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

        /** upperCamel */
        public static String toUpperCamel(String name) {
            return TO_UPPER_CAMEL.convert(name);
        }

        /** lower-hyphen */
        public static String toLowerHyphen(String name) {
            return TO_LOWER_HYPHEN.convert(name);
        }

        /** lower_underscore */
        public static String toLowerUnderscore(String name) {
            return TO_LOWER_UNDERSCORE.convert(name);
        }
    }

    public static void setLowerCamel(Context context, String key) {
        context.put(key, NameConventionUtils.LowerCamel.class);
    }

    public static void setLowerCamel(Context context) {
        setLowerCamel(context, "LowerCamel");
    }

    public static Context build() {
        Context context = new VelocityContext();
        setUpperCamel(context);
        setLowerCamel(context);
        return context;
    }

}
