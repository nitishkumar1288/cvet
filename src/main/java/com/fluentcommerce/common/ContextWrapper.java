package com.fluentcommerce.common;

import com.fluentretail.api.v2.client.ReadOnlyFluentApiClient;
import com.fluentretail.api.v2.model.Entity;
import com.fluentretail.rubix.action.Action;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.v2.action.ActionFactory;
import com.fluentretail.rubix.v2.context.Context;

import java.util.List;

public class ContextWrapper implements Context {
    private final Context context;
    private final LogCollection logCollection;

    private ContextWrapper(Context context) {
        this.context = context;
        logCollection = new LogCollection();
    }

    public LogCollection getLogCollection() {
        return logCollection;
    }

    public void addLog(String message) {
        logCollection.add(
            LogEntry.create(getRuleName(), message)
        );
    }

    public String getRuleName() {
        return this.getClass().getSimpleName();
    }

    public Entity getEntity() {
        return context.getEntity();
    }

    public String getProp(String s) {
        return context.getProp(s);
    }

    public <T> T getProp(String s, Class<T> aClass) {
        return context.getProp(s, aClass);
    }

    public <T> List<T> getPropList(String s, Class<T> aClass) {
        return context.getPropList(s, aClass);
    }

    public ReadOnlyFluentApiClient api() {
        return context.api();
    }

    public ActionFactory action() {
        return context.action();
    }

    public Event getEvent() {
        return context.getEvent();
    }

    public Context pushAction(Action action) {
        return null;
    }

    public static ContextWrapper from(Context context) {
        if (context instanceof ContextWrapper) {
            return (ContextWrapper) context;
        }
        return new ContextWrapper(context);
    }
}
