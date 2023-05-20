package edu.uic.cs474.s23.a2;

import java.util.Map;

public interface ObjectInspector {
    Map<String, String> describeObject(Object o);

    void updateObject(Object o, Map<String, Object> fields);

}
