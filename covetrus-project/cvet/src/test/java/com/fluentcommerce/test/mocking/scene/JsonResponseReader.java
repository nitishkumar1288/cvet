package com.fluentcommerce.test.mocking.scene;

import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ResponseReader;
import com.apollographql.apollo.api.ScalarType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fluentretail.graphql.type.CustomType;
import lombok.SneakyThrows;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.util.Constants.UTC_DATE_FORMATTER;

public class JsonResponseReader implements ResponseReader {
    private JsonNode node;

    public JsonResponseReader(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        node = objectMapper.readValue(json, JsonNode.class).get("data");
    }

    public JsonResponseReader(JsonNode node) {
        this.node = node;
    }

    @Override
    public String readString(ResponseField responseField) {
        if (responseField.responseName().equals("__typename")) {
            JsonNode typename = this.node.get("__typename");
            if (typename == null) return "node";
            else return typename.asText();
        }

        JsonNode node = this.node.get(responseField.responseName());
        return node == null ? null : node.asText();
    }

    @Override
    public Integer readInt(ResponseField responseField) {
        JsonNode node = this.node.get(responseField.responseName());
        return node == null ? null : node.asInt();
    }

    @Override
    public Long readLong(ResponseField responseField) {
        JsonNode node = this.node.get(responseField.responseName());
        return node == null ? null : node.asLong();
    }

    @Override
    public Double readDouble(ResponseField responseField) {
        JsonNode node = this.node.get(responseField.responseName());
        return node == null ? null : node.asDouble();
    }

    @Override
    public Boolean readBoolean(ResponseField responseField) {
        JsonNode node = this.node.get(responseField.responseName());
        return node == null ? null : node.asBoolean();
    }

    @Override
    public <T> T readObject(ResponseField responseField, ObjectReader<T> objectReader) {
        JsonNode node = this.node.get(responseField.responseName());
        if (node == null) return null;
        if (node instanceof NullNode) return null;


        JsonResponseReader reader = new JsonResponseReader(node);
        return objectReader.read(reader);
    }

    @Override
    public <T> List<T> readList(ResponseField responseField, ListReader<T> listReader) {
        JsonNode node = this.node.get(responseField.responseName());
        if (node == null) return new ArrayList<>();

        if (node.isArray()) {
            List<T> list = new ArrayList<>();
            for (int index = 0; index < node.size(); index++) {
                JsonNode child = node.get(index);
                T t = listReader.read(new JsonListItemReader(child));
                list.add(t);
            }
            return list;
        }
        return new ArrayList<>();
    }

    // TODO this part needs to be updates as we need more type handling
    @SneakyThrows
    @Override
    public <T> T readCustomType(ResponseField.CustomTypeField customTypeField) {
        JsonNode child = node.get(customTypeField.responseName());
        if (child == null) return null;
        if (child.isNull()) return null;

        if (CustomType.DATETIME.equals(customTypeField.scalarType())) {
            String asText = child.asText();
            SimpleDateFormat dateFormat = new SimpleDateFormat(UTC_DATE_FORMATTER);
            return (T) dateFormat.parse(asText);
        }

        if (child.isTextual()) return (T) child.asText();
        if (child.isNumber()) return (T) Integer.valueOf(child.asInt());
        if (child.isBoolean()) return (T) Boolean.valueOf(child.asBoolean());
        if (child.isObject()) return (T) child;
        if (child.isArray()) return (T) child;
        return null;
    }

    @Override
    public <T> T readConditional(ResponseField responseField, ConditionalTypeReader<T> conditionalTypeReader) {
        return conditionalTypeReader.read("typeName", this);
    }

    public static class JsonListItemReader implements ListItemReader{
        private JsonNode node;
        public JsonListItemReader(JsonNode node) {
            this.node = node;
        }

        @Override
        public String readString() {
            return node.asText();
        }

        @Override
        public Integer readInt() {
            return node.asInt();
        }

        @Override
        public Long readLong() {
            return node.asLong();
        }

        @Override
        public Double readDouble() {
            return node.asDouble();
        }

        @Override
        public Boolean readBoolean() {
            return node.asBoolean();
        }

        @Override
        public <T> T readCustomType(ScalarType scalarType) {
            return null;
        }

        @Override
        public <T> T readObject(ObjectReader<T> objectReader) {
            return objectReader.read(new JsonResponseReader(node));
        }

        @Override
        public <T> List<T> readList(ListReader<T> listReader) {
            if (node.isArray()) {
                List<T> list = new ArrayList<>();
                for (int index = 0; index < node.size(); index++) {
                    JsonNode child = node.get(index);
                    T t = listReader.read(new JsonListItemReader(child));
                    list.add(t);
                }
                return list;
            }
            return new ArrayList<>();
        }
    }
}
