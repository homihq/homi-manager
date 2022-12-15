package com.homihq.manager.gateway.apidef;

import com.homihq.manager.support.NameUtils;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

@Data
public class FilterDefinition {

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();


    public FilterDefinition() {
    }

    public FilterDefinition(String text) {
        int eqIdx = text.indexOf('=');
        if (eqIdx <= 0) {
            setName(text);
            return;
        }
        setName(text.substring(0, eqIdx));

        String[] args = tokenizeToStringArray(text.substring(eqIdx + 1), ",");

        for (int i = 0; i < args.length; i++) {
            this.args.put(NameUtils.generateName(i), args[i]);
        }
    }

}
