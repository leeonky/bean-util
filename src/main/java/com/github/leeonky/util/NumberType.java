package com.github.leeonky.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static java.util.Arrays.asList;

public class NumberType {
    private static final List<Class<?>> NUMBER_TYPES = asList(Byte.class, Short.class, Integer.class, Long.class,
            Float.class, Double.class, BigInteger.class, BigDecimal.class);
    private double doubleEpsilon = 0.0000001d;
    private float floatEpsilon = 0.000001f;

    @SuppressWarnings("unchecked")
    public static Class<? extends Number> calculationType(Class<? extends Number> number1, Class<? extends Number> number2) {
        Class boxedType1 = boxedClass(number1);
        Class boxedType2 = boxedClass(number2);
        if (isFloatAndBigInteger(boxedType1, boxedType2) || isFloatAndBigInteger(boxedType2, boxedType1))
            return BigDecimal.class;
        return NUMBER_TYPES.indexOf(boxedType1) > NUMBER_TYPES.indexOf(boxedType2) ? boxedType1 : boxedType2;
    }

    private static boolean isFloatAndBigInteger(Class<?> boxedType1, Class<?> boxedType2) {
        return boxedType1.equals(BigInteger.class) && (boxedType2.equals(Float.class) || boxedType2.equals(Double.class));
    }

    public static Class<?> boxedClass(Class<?> source) {
        if (source.isPrimitive()) {
            if (source == char.class)
                return Character.class;
            if (source == int.class)
                return Integer.class;
            if (source == short.class)
                return Short.class;
            if (source == long.class)
                return Long.class;
            if (source == float.class)
                return Float.class;
            if (source == double.class)
                return Double.class;
            if (source == boolean.class)
                return Boolean.class;
        }
        return source;
    }

    public Number plus(Number left, Number right) {
        Class<? extends Number> type = calculationType(left.getClass(), right.getClass());
        Number leftInSameType = convert(left, type);
        Number rightInSameType = convert(right, type);
        if (type.equals(Byte.class))
            return (byte) leftInSameType + (byte) rightInSameType;
        if (type.equals(Short.class))
            return (short) leftInSameType + (short) rightInSameType;
        if (type.equals(Integer.class))
            return (int) leftInSameType + (int) rightInSameType;
        if (type.equals(Long.class))
            return (long) leftInSameType + (long) rightInSameType;
        if (type.equals(Float.class))
            return (float) leftInSameType + (float) rightInSameType;
        if (type.equals(Double.class))
            return (double) leftInSameType + (double) rightInSameType;
        if (type.equals(BigInteger.class))
            return ((BigInteger) leftInSameType).add((BigInteger) rightInSameType);
        if (type.equals(BigDecimal.class))
            return ((BigDecimal) leftInSameType).add((BigDecimal) rightInSameType);
        throw new IllegalArgumentException("unsupported type " + type);
    }

    public Number subtract(Number left, Number right) {
        Class<? extends Number> type = calculationType(left.getClass(), right.getClass());
        Number leftInSameType = convert(left, type);
        Number rightInSameType = convert(right, type);
        if (type.equals(Byte.class))
            return (byte) leftInSameType - (byte) rightInSameType;
        if (type.equals(Short.class))
            return (short) leftInSameType - (short) rightInSameType;
        if (type.equals(Integer.class))
            return (int) leftInSameType - (int) rightInSameType;
        if (type.equals(Long.class))
            return (long) leftInSameType - (long) rightInSameType;
        if (type.equals(Float.class))
            return (float) leftInSameType - (float) rightInSameType;
        if (type.equals(Double.class))
            return (double) leftInSameType - (double) rightInSameType;
        if (type.equals(BigInteger.class))
            return ((BigInteger) leftInSameType).subtract((BigInteger) rightInSameType);
        if (type.equals(BigDecimal.class))
            return ((BigDecimal) leftInSameType).subtract((BigDecimal) rightInSameType);
        throw new IllegalArgumentException("unsupported type " + type);
    }

    public Number divide(Number left, Number right) {
        Class<? extends Number> type = calculationType(left.getClass(), right.getClass());
        Number leftInSameType = convert(left, type);
        Number rightInSameType = convert(right, type);
        if (type.equals(Byte.class))
            return (byte) leftInSameType / (byte) rightInSameType;
        if (type.equals(Short.class))
            return (short) leftInSameType / (short) rightInSameType;
        if (type.equals(Integer.class))
            return (int) leftInSameType / (int) rightInSameType;
        if (type.equals(Long.class))
            return (long) leftInSameType / (long) rightInSameType;
        if (type.equals(Float.class))
            return (float) leftInSameType / (float) rightInSameType;
        if (type.equals(Double.class))
            return (double) leftInSameType / (double) rightInSameType;
        if (type.equals(BigInteger.class))
            return ((BigInteger) leftInSameType).divide((BigInteger) rightInSameType);
        if (type.equals(BigDecimal.class))
            return ((BigDecimal) leftInSameType).divide((BigDecimal) rightInSameType);
        throw new IllegalArgumentException("unsupported type " + type);
    }

    public Number multiply(Number left, Number right) {
        Class<? extends Number> type = calculationType(left.getClass(), right.getClass());
        Number leftInSameType = convert(left, type);
        Number rightInSameType = convert(right, type);
        if (type.equals(Byte.class))
            return (byte) leftInSameType * (byte) rightInSameType;
        if (type.equals(Short.class))
            return (short) leftInSameType * (short) rightInSameType;
        if (type.equals(Integer.class))
            return (int) leftInSameType * (int) rightInSameType;
        if (type.equals(Long.class))
            return (long) leftInSameType * (long) rightInSameType;
        if (type.equals(Float.class))
            return (float) leftInSameType * (float) rightInSameType;
        if (type.equals(Double.class))
            return (double) leftInSameType * (double) rightInSameType;
        if (type.equals(BigInteger.class))
            return ((BigInteger) leftInSameType).multiply((BigInteger) rightInSameType);
        if (type.equals(BigDecimal.class))
            return ((BigDecimal) leftInSameType).multiply((BigDecimal) rightInSameType);
        throw new IllegalArgumentException("unsupported type " + type);
    }

    public int compare(Number left, Number right) {
        Class<? extends Number> type = calculationType(left.getClass(), right.getClass());
        Number leftInSameType = convert(left, type);
        Number rightInSameType = convert(right, type);
        if (type.equals(Byte.class))
            return Byte.compare((byte) leftInSameType, (byte) rightInSameType);
        if (type.equals(Short.class))
            return Short.compare((short) leftInSameType, (short) rightInSameType);
        if (type.equals(Integer.class))
            return Integer.compare((int) leftInSameType, (int) rightInSameType);
        if (type.equals(Long.class))
            return Long.compare((long) leftInSameType, (long) rightInSameType);
        if (type.equals(Float.class)) {
            float sub = (float) leftInSameType - (float) rightInSameType;
            if (sub > floatEpsilon)
                return 1;
            if (sub < -floatEpsilon)
                return -1;
            return 0;
        }
        if (type.equals(Double.class)) {
            double sub = (double) leftInSameType - (double) rightInSameType;
            if (sub > doubleEpsilon)
                return 1;
            if (sub < -doubleEpsilon)
                return -1;
            return 0;
        }
        if (type.equals(BigInteger.class))
            return ((BigInteger) leftInSameType).compareTo((BigInteger) rightInSameType);
        if (type.equals(BigDecimal.class))
            return ((BigDecimal) leftInSameType).compareTo((BigDecimal) rightInSameType);
        throw new IllegalArgumentException("unsupported type " + type);
    }

    public Number negate(Number left) {
        Class<?> type = boxedClass(left.getClass());
        if (type.equals(Byte.class))
            return (byte) -(byte) left;
        if (type.equals(Short.class))
            return (short) -(short) left;
        if (type.equals(Integer.class))
            return -(int) left;
        if (type.equals(Long.class))
            return -(long) left;
        if (type.equals(Float.class))
            return -(float) left;
        if (type.equals(Double.class))
            return -(double) left;
        if (type.equals(BigInteger.class))
            return ((BigInteger) left).negate();
        if (type.equals(BigDecimal.class))
            return ((BigDecimal) left).negate();
        throw new IllegalArgumentException("unsupported type " + type);
    }

    public double getDoubleEpsilon() {
        return doubleEpsilon;
    }

    public void setDoubleEpsilon(double doubleEpsilon) {
        this.doubleEpsilon = doubleEpsilon;
    }

    public float getFloatEpsilon() {
        return floatEpsilon;
    }

    public void setFloatEpsilon(float floatEpsilon) {
        this.floatEpsilon = floatEpsilon;
    }

    @SuppressWarnings("unchecked")
    public <T extends Number> T convert(Number number, Class<T> type) {
        Number result = null;
        if (type.isInstance(number))
            return (T) number;
        if (type.equals(byte.class) || type.equals(Byte.class))
            result = byteValue(number);
        if (type.equals(short.class) || type.equals(Short.class))
            result = shortValue(number);
        if (type.equals(int.class) || type.equals(Integer.class))
            result = intValue(number);
        if (type.equals(long.class) || type.equals(Long.class))
            result = longValue(number);
        if (type.equals(float.class) || type.equals(Float.class))
            result = floatValue(number);
        if (type.equals(double.class) || type.equals(Double.class))
            result = doubleValue(number);
        if (type.equals(BigInteger.class))
            result = bigIntegerValue(number);
        if (type.equals(BigDecimal.class))
            result = bigDecimalValue(number);
        if (result == null)
            throw new IllegalArgumentException(String.format("Cannot convert %s to %s", number, type.getName()));
        return (T) result;
    }

    public BigDecimal bigDecimalValue(Number number) {
        if (number instanceof Byte
                || number instanceof Short
                || number instanceof Integer
                || number instanceof Long)
            return BigDecimal.valueOf(number.longValue());
        if (number instanceof BigDecimal)
            return (BigDecimal) number;
        if (number instanceof BigInteger)
            return new BigDecimal((BigInteger) number);
        if (number instanceof Float && Float.isFinite(number.floatValue()))
            return BigDecimal.valueOf(number.floatValue());
        if (number instanceof Double && Double.isFinite(number.doubleValue()))
            return BigDecimal.valueOf(number.doubleValue());
        return null;
    }

    public BigInteger bigIntegerValue(Number number) {
        if (number instanceof BigInteger)
            return (BigInteger) number;
        if (number instanceof Byte
                || number instanceof Short
                || number instanceof Integer
                || number instanceof Long)
            return BigInteger.valueOf(number.longValue());
        if (number instanceof Float && Float.isFinite(number.floatValue())) {
            BigInteger bigInteger = BigDecimal.valueOf(number.floatValue()).toBigInteger();
            if (bigInteger.floatValue() == number.floatValue())
                return bigInteger;
        }
        if (number instanceof Double && Double.isFinite(number.doubleValue())) {
            BigInteger bigInteger = BigDecimal.valueOf(number.doubleValue()).toBigInteger();
            if (bigInteger.doubleValue() == number.doubleValue())
                return bigInteger;
        }
        if (number instanceof BigDecimal) {
            BigInteger bigInteger = ((BigDecimal) number).toBigInteger();
            if (new BigDecimal(bigInteger).compareTo((BigDecimal) number) == 0)
                return bigInteger;
        }
        return null;
    }

    public Double doubleValue(Number number) {
        double converted = number.doubleValue();
        return (number instanceof Byte
                || number instanceof Short
                || number instanceof Integer
                || (number instanceof Long && number.longValue() == (long) converted)
                || number instanceof Float
                || number instanceof Double
                || (number instanceof BigInteger && Double.isFinite(converted)
                && BigDecimal.valueOf(converted).compareTo(new BigDecimal(number.toString())) == 0)
                || (number instanceof BigDecimal && Double.isFinite(converted)
                && BigDecimal.valueOf(converted).compareTo((BigDecimal) number) == 0)
        ) ? converted : null;
    }

    public Float floatValue(Number number) {
        float converted = number.floatValue();
        return (number instanceof Byte
                || number instanceof Short
                || (number instanceof Integer && number.intValue() == (int) converted)
                || (number instanceof Long && number.longValue() == (long) converted)
                || number instanceof Float
                || (number instanceof Double && number.doubleValue() == (double) converted)
                || (number instanceof BigInteger && Float.isFinite(converted)
                && BigDecimal.valueOf(converted).compareTo(new BigDecimal(number.toString())) == 0)
                || (number instanceof BigDecimal && Float.isFinite(converted)
                && BigDecimal.valueOf(converted).compareTo((BigDecimal) number) == 0)
        ) ? converted : null;
    }

    public Long longValue(Number number) {
        long converted = number.longValue();
        return (number instanceof Byte
                || number instanceof Short
                || number instanceof Integer
                || number instanceof Long
                || (number instanceof Float && number.floatValue() == (float) converted)
                || (number instanceof Double && number.doubleValue() == (double) converted)
                || (number instanceof BigInteger && BigInteger.valueOf(converted).compareTo((BigInteger) number) == 0)
                || (number instanceof BigDecimal && BigDecimal.valueOf(converted).compareTo((BigDecimal) number) == 0))
                ? converted : null;
    }

    public Integer intValue(Number number) {
        int converted = number.intValue();
        return (number instanceof Byte
                || number instanceof Short
                || number instanceof Integer
                || (number instanceof Long && number.longValue() == (long) converted)
                || (number instanceof Float && number.floatValue() == (float) converted)
                || (number instanceof Double && number.doubleValue() == (double) converted)
                || (number instanceof BigInteger && BigInteger.valueOf(converted).compareTo((BigInteger) number) == 0)
                || (number instanceof BigDecimal && BigDecimal.valueOf(converted).compareTo((BigDecimal) number) == 0))
                ? converted : null;
    }

    public Short shortValue(Number number) {
        short converted = number.shortValue();
        return (number instanceof Byte
                || number instanceof Short
                || number instanceof Integer && number.intValue() == (int) converted
                || number instanceof Long && number.longValue() == (long) converted
                || number instanceof Float && number.floatValue() == (float) converted
                || number instanceof Double && number.doubleValue() == (double) converted
                || number instanceof BigInteger && BigInteger.valueOf(converted).compareTo((BigInteger) number) == 0
                || number instanceof BigDecimal && BigDecimal.valueOf(converted).compareTo((BigDecimal) number) == 0)
                ? converted : null;
    }

    public Byte byteValue(Number number) {
        byte converted = number.byteValue();
        return (number instanceof Byte
                || number instanceof Short && number.shortValue() == (short) converted
                || number instanceof Integer && number.intValue() == (int) converted
                || number instanceof Long && number.longValue() == (long) converted
                || number instanceof Float && number.floatValue() == (float) converted
                || number instanceof Double && number.doubleValue() == (double) converted
                || number instanceof BigInteger && BigInteger.valueOf(converted).compareTo((BigInteger) number) == 0
                || number instanceof BigDecimal && BigDecimal.valueOf(converted).compareTo((BigDecimal) number) == 0)
                ? converted : null;
    }
}
