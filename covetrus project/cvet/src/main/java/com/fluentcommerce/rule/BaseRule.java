package com.fluentcommerce.rule;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.v2.context.Context;
import com.fluentretail.rubix.v2.rule.Rule;

public abstract class BaseRule implements Rule {

    public abstract void run(ContextWrapper context);

    protected <E extends Exception> void handleException(E exception) {
        if (exception instanceof RuntimeException) {
            throw (RuntimeException) exception;
        }
        throw new RuntimeException(exception);
    }

    protected void generateLogs(ContextWrapper contextWrapper) {
        CommonUtils.generateLog(contextWrapper);
    }

    @Override
    public <C extends Context> void run(C context) {
        ContextWrapper contextWrapper = ContextWrapper.from(context);
        try {
            run(contextWrapper);
        } catch (RubixException e) {
            handleException(e);
        } catch (Exception e) {
            handleException(e);
        } finally {
            generateLogs(contextWrapper);
        }
    }
}
