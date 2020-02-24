package com.ccloud.main.util;

import com.ccloud.main.util.exception.ParamsErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 自定义 JsonObject
 *
 * @author wangjie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONObject {

    private Map<String, Object> jsonMap;


    public Object getObject(String key) {
        return jsonMap.get(key);
    }

    public String getString(String key) {
        Object o = getObject(key);
        return o == null ? null : o.toString();
    }

    public String getNotNullString(String key) {
        String val = getString(key);
        if (StringUtils.isBlank(val)) {
            throw new ParamsErrorException();
        }
        return val;
    }


    public Integer getInteger(String key) {
        String o = getString(key);
        return o == null ? null : Integer.valueOf(o);
    }

    public Integer getNotNullInteger(String key) {
        Integer val = getInteger(key);
        if (val == null) {
            throw new ParamsErrorException();
        }
        return val;
    }

    public Float getFloat(String key) {
        String o = getString(key);
        return o == null ? null : Float.valueOf(o);
    }

    public Float getNotNullFloat(String key) {
        Float val = getFloat(key);
        if (val == null) {
            throw new ParamsErrorException();
        }
        return val;
    }

}
