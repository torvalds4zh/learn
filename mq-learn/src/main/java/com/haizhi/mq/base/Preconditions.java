package com.haizhi.mq.base;

import com.google.common.collect.Iterables;
import com.haizhi.mq.exceptions.MsgException;

/**
 *
 * 取自guava包中的Preconditions，区别在于抛出自定义异常
 *
 *  Created by xiaolezheng on 15/8/27.
 */
public final class Preconditions {
    private Preconditions() {}

    /**
     * 检测类型转换
     * @param obj 实体实例
     * @param clazz 要转换到的类型
     * @param <T> 类型泛型
     * @return 转换好的实例
     */
    public static <T> T checkCast(
            Object obj, Class<T> clazz) {
        if (!clazz.isInstance(obj)) {
            throw new MsgException(format("expect %s but is %s", clazz ,obj.getClass()));
        }
        return clazz.cast(obj);
    }/**
     * 检测类型转换
     * @param obj 实体实例
     * @param clazz 要转换到的类型
     * @param errorMessage 错误信息
     * @param <T> 类型泛型
     * @return 转换好的实例
     */
    public static <T> T checkCast(
            Object obj, Class<T> clazz, Object errorMessage) {
        if (!clazz.isInstance(obj)) {
            throw new MsgException(String.valueOf(errorMessage));
        }
        return clazz.cast(obj);
    }
    /**
     * 检测类型转换
     * @param obj 实体实例
     * @param clazz 要转换到的类型
     * @param errorMessage 错误信息
     * @param <T> 类型泛型
     * @return 转换好的实例
     */
    public static <T> T checkCast(
            Object obj, Class<T> clazz, String errorMessage, Object... errorMessageArgs) {
        if (!clazz.isInstance(obj)) {
            throw new MsgException(format(errorMessage, errorMessageArgs));
        }
        return clazz.cast(obj);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will
     *     be converted to a string using {@link String#valueOf(Object)}
     * @throws MsgException if {@code expression} is false
     */
    public static void checkArgument(
            boolean expression, Object errorMessage) {
        if (!expression) {
            throw new MsgException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     *
     * @param expression a boolean expression
     * @param errorMessageTemplate a template for the exception message should the
     *     check fail. The message is formed by replacing each {@code %s}
     *     placeholder in the template with an argument. These are matched by
     *     position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *     Unmatched arguments will be appended to the formatted message in square
     *     braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}.
     * @throws MsgException if {@code expression} is false
     * @throws MsgException if the check fails and either {@code
     *     errorMessageTemplate} or {@code errorMessageArgs} is null (don't let
     *     this happen)
     */
    public static void checkArgument(boolean expression,
                                     String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        if (!expression) {
            throw new MsgException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @param errorMessage the exception message to use if the check fails; will
     *     be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws MsgException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new MsgException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @param errorMessageTemplate a template for the exception message should the
     *     check fail. The message is formed by replacing each {@code %s}
     *     placeholder in the template with an argument. These are matched by
     *     position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *     Unmatched arguments will be appended to the formatted message in square
     *     braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws MsgException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference,
                                     String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            throw new MsgException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /**
     * Ensures that an Iterable reference passed as a parameter to the calling
     * method is not empty.
     *
     * @param reference an object reference
     * @param errorMessageTemplate a template for the exception message should the
     *     check fail. The message is formed by replacing each {@code %s}
     *     placeholder in the template with an argument. These are matched by
     *     position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *     Unmatched arguments will be appended to the formatted message in square
     *     braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws MsgException if {@code reference} is null
     */
    public static <T> Iterable<T> checkNotEmpty(Iterable<T> reference,
                                     String errorMessageTemplate,
                                     Object... errorMessageArgs) {
        if (Iterables.isEmpty(reference)) {
            // If either of these parameters is null, the right thing happens anyway
            throw new MsgException(
                    format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These
     * are matched by position - the first {@code %s} gets {@code args[0]}, etc.
     * If there are more arguments than placeholders, the unmatched arguments will
     * be appended to the end of the formatted message in square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s}
     *     placeholders.
     * @param args the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}. Arguments can be null.
     */
    static String format(String template,
                         Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(
                template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}