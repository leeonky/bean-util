package com.github.leeonky.util;

import java.lang.reflect.Type;

abstract class AbstractProperty<T> implements Property<T> {
    private final BeanClass beanClass;

    AbstractProperty(BeanClass<T> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public Object tryConvert(Object value) {
        return beanClass.getConverter().tryConvert(getPropertyType(), value);
    }

    @Override
    public BeanClass<T> getBeanClass() {
        return beanClass;
    }

    protected abstract Type provideGenericType();

    @Override
    public GenericType getGenericType() {
        return GenericType.createGenericType(provideGenericType());
    }
}