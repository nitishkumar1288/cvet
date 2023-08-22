package com.fluentcommerce.test.mocking.scene;

import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.ResponseReader;
import com.fluentcommerce.test.mocking.matcher.ClassTypeMatcher;
import com.fluentcommerce.test.mocking.matcher.ComparatorMatcher;
import com.fluentcommerce.test.mocking.matcher.PredicateMatcher;
import com.fluentretail.rubix.v2.context.Context;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.Predicate;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

public class SceneBuilder {

    @Deprecated
    public void mock(Class<? extends Query> query, String fileName, Context context){
        try {
            String fileContent = readTestResource(fileName);
            Object response = readResponse(query, fileContent);
            when(context.api().query(argThat(ClassTypeMatcher.of(query)))).thenReturn((Operation.Data) response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Query> void mock(T query, Comparator<T> comparator, String fileName, Context context) {
        try {
            Class<? extends Query> clazz = query.getClass();
            String fileContent = readTestResource(fileName);
            Object response = readResponse(clazz, fileContent);
            when(
                    context.api().query(argThat(ComparatorMatcher.of(query, comparator)))
            ).thenReturn((Operation.Data) response);
        }catch (Exception e) {
            throw new RuntimeException(String.format("Exception: %s happened while mocking. Query: %s  File: %s",
                    e.getMessage(), query.getClass(), fileName), e);
        }
    }

    public <T extends Query> void mock(Class<T> clazz, Predicate<T> predicate, String fileName, Context context) {
        try {
            String fileContent = readTestResource(fileName);
            Object response = readResponse(clazz, fileContent);
            when(context.api().query(argThat(PredicateMatcher.of(clazz, predicate)))).thenReturn((Operation.Data) response);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Exception: %s happened while mocking. Query: %s  File: %s",
                    e.getMessage(), clazz, fileName), e);
        }
    }

    private Object readResponse(Class<? extends Query> clazz, String fileContent) throws Exception {
        String mapperClassName = clazz.getName() + "$Data$Mapper";
        Class<?> mapperClass = Class.forName(mapperClassName);
        Object mapperInstance = mapperClass.getConstructor().newInstance();
        return mapperClass.getMethod("map", ResponseReader.class).invoke(mapperInstance, new JsonResponseReader(fileContent));
    }

    public String readTestResource(String fileName) {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        try {
            if (resource == null) throw new RuntimeException("Couldn't find resource file: " + fileName);
            Path path = Paths.get(resource.toURI());
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error while creating URI: " + e.getMessage());
        } catch (IOException e) {
            throw  new RuntimeException("Error while reading file content: " + fileName);
        }
    }
}
