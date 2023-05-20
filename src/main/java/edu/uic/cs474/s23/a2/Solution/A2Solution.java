package edu.uic.cs474.s23.a2.Solution;

import edu.uic.cs474.s23.a2.ObjectInspector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class A2Solution implements ObjectInspector {
    private String findAndInvokeDebug(Object valueOfField) throws Throwable{
        String value = null;
        Method ms[] = valueOfField.getClass().getDeclaredMethods();
        for (Method m : ms) {
            if (m.getName().equals("debug") && m.getReturnType() == String.class) {
                try {
                    if (m.getParameterTypes().length == 0) {
                        value = (String) m.invoke(valueOfField);
                    } else {
                        Class<?> parameters[] = m.getParameterTypes();
                        if (Modifier.isStatic(m.getModifiers()) && parameters.length == 1 && (parameters[0] == valueOfField.getClass() || getAllSuperClassesAndInterfaces(valueOfField.getClass()).contains(parameters[0]))) {
                            value = (String) m.invoke(null,valueOfField);
                        }
                    }
                } catch (InvocationTargetException e) {
                    Throwable ex = e.getTargetException();
                    throw ex;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    //
    // Extracts value from field and formats it to its proper string
    // representation depending on whether it is a primitive, a boxed
    // primitive or normal object.
    //
    private String fieldValueToString(Field f, Object valueOfField) {
        String value;
        // check if value is null
        if (valueOfField == null) {
            value = "null";
        } else {
            // check if primitive
            if (valueOfField instanceof Integer) {
                if (f.getType() != int.class) {
                    value = "Boxed " + valueOfField;
                } else {
                    value = ((Integer) valueOfField).toString();
                }
            } else if (valueOfField instanceof Character) {
                if (f.getType() != char.class) {
                    value = "Boxed " + valueOfField;
                } else {
                    value = ((Character) valueOfField).toString();
                }
            } else if (valueOfField instanceof Boolean) {
                if (f.getType() != boolean.class) {
                    value = "Boxed " + valueOfField;
                } else {
                    value = ((Boolean) valueOfField).toString();
                }
            } else if (valueOfField instanceof Long) {
                if (f.getType() != long.class) {
                    value = "Boxed " + valueOfField + "#L";
                } else {
                    value = valueOfField + "#L";
                }
            } else if (valueOfField instanceof Double) {
                if (f.getType() != double.class) {
                    value = "Boxed " + valueOfField + "#D";
                } else {
                    value = valueOfField + "#D";
                }
            } else if (valueOfField instanceof Float) {
                if (f.getType() != float.class) {
                    value = "Boxed " + valueOfField + "#F";
                } else {
                    value = valueOfField + "#F";
                }
            } else if (valueOfField instanceof Byte) {
                if (f.getType() != byte.class) {
                    value = "Boxed 0x" + Integer.toHexString(((Byte) valueOfField).intValue());
                } else {
                    value = "0x" + Integer.toHexString(((Byte) valueOfField).intValue());
                }
            } else if (valueOfField instanceof Short) {
                if (f.getType() != short.class) {
                    value = "Boxed 0" + Integer.toOctalString(((Short) valueOfField).intValue());
                } else {
                    value = "0" + Integer.toOctalString(((Short) valueOfField).intValue());
                }
            } else {
                try {
                    value = findAndInvokeDebug(valueOfField);
                    if (value == null) {
                        value = valueOfField.toString();
                    }
                } catch (RuntimeException e) {
                    value = String.format("Thrown exception: %s", e.getClass().getName());
                } catch (Exception e) {
                    value = String.format("Thrown checked exception: %s", e.getClass().getName());
                } catch (Throwable e) {
                    value = String.format("Raised error: %s", e.getClass().getName());
                }
            }
        }
        return value;
    }


    //
    // Gets fields from class c and puts them into map ret
    //
    private void addFields(Object o, Class<?> c, Map<String, String> ret) {
        Field[] fs = c.getDeclaredFields();

        for (Field f : fs) {
            try {
                f.setAccessible(true);
                Object valueOfField;
                String key;
                String value;
                if (o != null && !Modifier.isStatic(f.getModifiers())) { // check if instance field and instance object is given
                    key = f.getName();
                    valueOfField = f.get(o);
                } else if (Modifier.isStatic(f.getModifiers())) { // check if field is static
                    key = String.format("%s.%s", c.getSimpleName(), f.getName());
                    valueOfField = f.get(null);
                } else {
                    // means f is instance variable and there is no given instance in o,
                    // therefore continue to next field
                    continue;
                }
                // get string representation of field value
                value = fieldValueToString(f, valueOfField);
                ret.put(key, value);
            } catch (ReflectiveOperationException e) {
                throw new Error(e);
            }
        }
    }

    //
    // Updates field's value given the field, object instance, and new value
    //
    private void updateFieldValue(Field f, Object o, Object newValue) {
        Class<?> fieldType = f.getType();
        // check if primitive
        try {
            if (fieldType.isPrimitive()) {
                if (fieldType == int.class) {
                    f.setInt(o, ((Integer) newValue));
                } else if (fieldType == char.class) {
                    f.setChar(o, ((Character) newValue));
                } else if (fieldType == boolean.class) {
                    f.setBoolean(o, ((Boolean) newValue));
                } else if (fieldType == long.class) {
                    f.setLong(o, ((Long) newValue));
                } else if (fieldType == double.class) {
                    f.setDouble(o, ((Double) newValue));
                } else if (fieldType == float.class) {
                    f.setFloat(o, ((Float) newValue));
                } else if (fieldType == byte.class) {
                    f.setByte(o, ((Byte) newValue));
                } else if (fieldType == short.class) {
                    f.setShort(o, ((Short) newValue));
                }
            } else {
                f.set(o, newValue);
            }
        }catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    //
    // Updates the fields of object o from class c given the map fields
    //
    private void updateFields(Object o, Class<?> c, Map<String, Object> fields) {
        Field[] fs = c.getDeclaredFields();

        for (Field f : fs) {
            f.setAccessible(true);
             if (fields.containsKey(f.getName())) {
                 Object value = fields.get(f.getName());
                 updateFieldValue(f, o, value);
             }
        }
    }

    //
    // Peforms recursive fuctionality for getAllSuperClassesAndInterfaces
    //
    private void getAllSuperClassesAndInterfacesRec(Class<?> c, ArrayList<Class<?>> result) {
        ArrayList<Class<?>> directSuperClasses = new ArrayList<>();
        if (c.getSuperclass() != null) {
            directSuperClasses.add(c.getSuperclass()); // add superClass
        }
        Class<?>[] interfaces = c.getInterfaces();
        Collections.addAll(directSuperClasses, interfaces);

        if (!directSuperClasses.isEmpty()) {
            result.addAll(directSuperClasses);
            for (Class<?> parent : directSuperClasses) {
                getAllSuperClassesAndInterfacesRec(parent, result);
            }
        }
    }

    //
    // Returns an ArrayList of all superclasses and interfaces of class c
    //
    private ArrayList<Class<?>> getAllSuperClassesAndInterfaces(Class<?> c) {
        ArrayList<Class<?>> result = new ArrayList<>();
        getAllSuperClassesAndInterfacesRec(c, result);
        return result;
    }

    @Override
    public Map<String, String> describeObject(Object o) {
        Map<String, String> ret = new HashMap<>();
        Class<?> c;
        Object oInstance;

        // check if object of type class is passed as o
        if (o instanceof java.lang.Class) {
            c = (java.lang.Class<?>) o;
            oInstance = null;
        } else {
            c = o.getClass();
            oInstance = o;
        }
        // Adds fields from Class c declaration
        addFields(oInstance, c, ret);
        // Add inherited fields from parent classes or interfaces of c
        ArrayList<Class<?>> superClassOrInterfaces = getAllSuperClassesAndInterfaces(c);
        for (Class<?> superClassOrInterface : superClassOrInterfaces) {
            addFields(oInstance, superClassOrInterface, ret);
        }
        return ret;
    }

    @Override
    public void updateObject(Object o, Map<String, Object> fields) {
        Class<?> c = o.getClass();

        // Adds fields from Class c declaration
        updateFields(o, c, fields);
        // Add inherited fields from parent classes or interfaces of c
        ArrayList<Class<?>> superClassOrInterfaces = getAllSuperClassesAndInterfaces(c);
        for (Class<?> superClassOrInterface : superClassOrInterfaces) {
            updateFields(o, superClassOrInterface, fields);
        }
    }
}
