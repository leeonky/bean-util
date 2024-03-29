package com.github.leeonky.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionPropertyTest {

    public static class Bean {
        public String[] array;
        public String str;
        public Iterable<String> iterable;
        public Iterable<?> uncheckedIterable;
        public Iterable rawIterable;
        public ListWithProperty listWithProperty;
    }

    public static class ListWithProperty extends ArrayList<String> {
        public int property;
    }

    @Nested
    class ElementType {
        private final BeanClass<Bean> beanClass = BeanClass.create(Bean.class);

        @Test
        void get_element_or_property_type() {
            assertThat(beanClass.getPropertyReader("array").getType().getElementOrPropertyType().getType()).isEqualTo(String.class);
            assertThat(beanClass.getPropertyReader("str").getType().getElementOrPropertyType().getType()).isEqualTo(String.class);
        }

        @Nested
        class Array {

            @Test
            void get_element_type() {
                assertThat(beanClass.getPropertyReader("array").getType().getElementType().getType())
                        .isEqualTo(String.class);

                assertThat(beanClass.getPropertyReader("array").getType().getElementType().getType())
                        .isEqualTo(String.class);
            }
        }

        @Nested
        class Collections {

            @Test
            void get_element_type() {
                assertThat(beanClass.getPropertyReader("iterable").getType().getElementType().getType())
                        .isEqualTo(String.class);

                assertThat(beanClass.getPropertyReader("iterable").getType().getElementType().getType())
                        .isEqualTo(String.class);
            }

            @Test
            void should_return_object_class_when_generic_type_params_not_specify() {
                assertThat(beanClass.getPropertyReader("uncheckedIterable").getType().getElementType().getType()).isEqualTo(Object.class);
                assertThat(beanClass.getPropertyReader("rawIterable").getType().getElementType().getType()).isEqualTo(Object.class);
            }
        }
    }

    @Nested
    class CreateReadWrite {

        @Test
        void should_raise_error_when_type_is_not_collection() {
            assertThrows(IllegalStateException.class, () -> BeanClass.create(Integer.class).createCollection(emptyList()));
        }

        @Nested
        class Array {

            @Test
            void support_create_with_elements() {
                BeanClass<String[]> beanClass = BeanClass.create(String[].class);

                Object collection = CollectionHelper.createCollection(asList("a", "b"), beanClass);

                assertThat(collection).isEqualTo(new String[]{"a", "b"});
            }
        }

        @Nested
        class Collections {

            @Test
            void support_create_list_with_elements() {
                BeanClass<Iterable> beanClass = BeanClass.create(Iterable.class);

                Object collection = CollectionHelper.createCollection(asList("a", "b"), beanClass);

                assertThat(collection).isEqualTo(asList("a", "b"));
            }

            @Test
            void support_create_set_with_elements() {
                BeanClass<Set> beanClass = BeanClass.create(Set.class);

                Object collection = CollectionHelper.createCollection(asList("a", "b"), beanClass);

                assertThat(collection).isEqualTo(new LinkedHashSet<>(asList("a", "b")));
            }

            @Test
            void support_create_class_instance() {
                BeanClass<LinkedList> beanClass = BeanClass.create(LinkedList.class);

                Object collection = CollectionHelper.createCollection(asList("a", "b"), beanClass);

                assertThat(collection).isEqualTo(new LinkedList<>(asList("a", "b")));
            }
        }
    }

    @Nested
    class SupportElementReadWrite {

        @Nested
        class Read {

            @Test
            void get_element_property_type_of_array_or_collection() {
                BeanClass<Bean> beanClass = BeanClass.create(Bean.class);

                assertThat(beanClass.getPropertyReader("iterable").getType().getPropertyReader("0").getType().getType())
                        .isEqualTo(String.class);

                assertThat(beanClass.getPropertyReader("array").getType().getPropertyReader("0").getType().getType())
                        .isEqualTo(String.class);
            }

            @Test
            void get_property_property_type_of_list() {
                BeanClass<Bean> beanClass = BeanClass.create(Bean.class);

                assertThat(beanClass.getPropertyReader("listWithProperty").getType().getPropertyReader("property").getType().getType())
                        .isEqualTo(int.class);
            }

            @Test
            void read_array_value_by_index() {
                int[] ints = new int[]{2, 3};
                BeanClass<int[]> beanClass = BeanClass.create(int[].class);

                assertThat(beanClass.getPropertyValue(ints, "0")).isEqualTo(2);

                BeanClass<List> listBeanClass = BeanClass.create(List.class);

                assertThat(listBeanClass.getPropertyValue(asList("", "hello"), "1")).isEqualTo("hello");
            }

            @Test
            void read_list_property() {
                Bean bean = new Bean();
                bean.listWithProperty = new ListWithProperty();
                bean.listWithProperty.property = 1000;
                assertThat(BeanClass.create(Bean.class).getPropertyChainValue(bean, "listWithProperty.property"))
                        .isEqualTo(1000);
            }
        }

        @Nested
        class Write {

            @Test
            void get_element_property_type_in_array_or_collection() {
                BeanClass<Bean> beanClass = BeanClass.create(Bean.class);

                assertThat(beanClass.getPropertyReader("iterable").getType().getPropertyWriter("0").getType().getType())
                        .isEqualTo(String.class);

                assertThat(beanClass.getPropertyReader("array").getType().getPropertyWriter("0").getType().getType())
                        .isEqualTo(String.class);
            }

            @Test
            void get_property_property_type_of_list() {
                BeanClass<Bean> beanClass = BeanClass.create(Bean.class);

                assertThat(beanClass.getPropertyWriter("listWithProperty").getType().getPropertyReader("property").getType().getType())
                        .isEqualTo(int.class);
            }

            @Test
            void write_array_value_by_index() {
                int[] ints = new int[]{2, 3};
                BeanClass<int[]> beanClass = BeanClass.create(int[].class);

                beanClass.setPropertyValue(ints, "0", 0);

                assertThat(ints[0]).isEqualTo(0);

                BeanClass<List> listBeanClass = BeanClass.create(List.class);
                List<String> stringList = new ArrayList<>();
                stringList.add("");
                stringList.add("");
                listBeanClass.setPropertyValue(stringList, "0", "hello");

                assertThat(stringList).containsOnly("hello", "");
            }

            @Test
            void write_list_property() {
                Bean bean = new Bean();
                bean.listWithProperty = new ListWithProperty();

                BeanClass.createFrom(bean.listWithProperty).setPropertyValue(bean.listWithProperty, "property", 2000);
                assertThat(bean.listWithProperty.property).isEqualTo(2000);
            }

            @Test
            void should_raise_error_when_collection_not_support_set() {
                assertThrows(CannotSetElementByIndexException.class, () ->
                        BeanClass.create(Set.class).setPropertyValue(new HashSet(), "1", null));
            }
        }

        @Nested
        class _Property {

            @Test
            void get_collection_element_property() {
                BeanClass<?> type = BeanClass.create(Bean.class).getPropertyReader("iterable").getType();

                Property<?> property = type.getProperty("0");

                assertThat(property)
                        .hasFieldOrPropertyWithValue("name", "0")
                        .hasFieldOrPropertyWithValue("beanType", type);
            }

            @Test
            void get_collection_property() {
                BeanClass<?> type = BeanClass.create(Bean.class).getPropertyReader("listWithProperty").getType();

                assertThat(type.getProperty("property")).isEqualTo(type.getProperty("property"));
            }
        }
    }
}
