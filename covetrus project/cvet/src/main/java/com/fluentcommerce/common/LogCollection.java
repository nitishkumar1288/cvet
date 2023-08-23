package com.fluentcommerce.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fluentcommerce.util.LogUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
@Slf4j
public class LogCollection extends ArrayList<LogEntry> {
    public LogCollection() {
        super();
    }

    @Override
    public String toString(){
        try {
            return LogUtils.serialize(this);
        } catch (JsonProcessingException e) {
            log.info("Exception has occurred");
        }
        return "";
    }
}
