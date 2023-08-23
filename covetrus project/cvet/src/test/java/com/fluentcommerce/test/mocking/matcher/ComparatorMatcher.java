package com.fluentcommerce.test.mocking.matcher;

import com.apollographql.apollo.api.Query;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.Comparator;

public class ComparatorMatcher<T> extends BaseMatcher<T> {
    private final Comparator<T> comparator;
    private final T object;
    public ComparatorMatcher(T object, Comparator<T> comparator) {
        this.object = object;
        this.comparator = comparator;
    }

    public static <T extends Query> ComparatorMatcher<T> of(T object, Comparator<T> comparator) {
        return new ComparatorMatcher<>(object, comparator);
    }

    @Override
    public boolean matches(Object o) {
        if (o == null) return false;
        if (!object.getClass().equals(o.getClass())) return false;

        T object2 = (T) o;
        return comparator.compare(object, object2) == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Comparator matcher for class: " + object.getClass());
    }
}
