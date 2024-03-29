package com.github.leeonky.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.function.BiConsumer;

import static java.lang.Integer.parseInt;

class CollectionDataPropertyWriter<T> extends DataPropertyAccessor<T> implements PropertyWriter<T> {
    @SuppressWarnings("unchecked")
    private final BiConsumer<T, Object> SETTER = (bean, value) -> {
        Class<T> type = getBeanType().getType();
        int index = parseInt(getName());
        if (type.isArray())
            Array.set(bean, index, value);
        else if (List.class.isAssignableFrom(type))
            ((List<Object>) bean).set(index, value);
        else
            throw new CannotSetElementByIndexException(type);
    };

    public CollectionDataPropertyWriter(BeanClass<T> beanClass, String name, BeanClass<?> type) {
        super(beanClass, name, type);
    }

    @Override
    public BiConsumer<T, Object> setter() {
        return SETTER;
    }
}
