package com.fluentcommerce.common;

import com.fluentretail.api.model.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogEntry {
    String title;
    String message;
    List<Attribute> attributes;

    public LogEntry() {
        attributes = new ArrayList<>();
    }

    public LogEntry addAttribute(Attribute attribute) {
        Objects.requireNonNull(attribute);
        attributes.add(attribute);
        return this;
    }

    public LogEntry addAttribute(String name, Attribute.Type type, Object value){
        Attribute attribute = Attribute.builder().name(name).attributeType(type).value(value).build();
        return addAttribute(attribute);
    }

    public static LogEntry create(String title, String message, List<Attribute> attributes) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(message);

        LogEntry result = new LogEntry();
        result.title = title;
        result.message = message;

        if (attributes != null)
            result.attributes = attributes;
        return result;
    }

    public static LogEntry create(String title, String message) {
        return create(title, message, null);
    }
}
