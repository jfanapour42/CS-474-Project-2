package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Test01_PublicStringField {

    @Test
    public void testHasField() {
        ObjectInspector inspector = Main.getInspector();

        Object obj = new A();
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Set.of("field"), result.keySet());
    }

    @Test
    public void testHasConstString() {
        ObjectInspector inspector = Main.getInspector();

        Object obj = new A();
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertEquals(Map.of("field", "This is the value of the field"), result);
    }

    @Test
    public void testHasRandomString() {
        ObjectInspector inspector = Main.getInspector();

        A obj = new A();

        String randomString = generateRandomString();
        obj.field = randomString;

        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertEquals(Map.of("field", randomString), result);
    }

    public static class A {
        public String field = "This is the value of the field";
    }

    public static String generateRandomString() {
        Random r = new Random();
        char[] chars = new char[10];
        for (int i = 0 ; i < chars.length ; i++) {
            if (r.nextBoolean()) {
                // Lowercase
                int start = 'a';
                int c = r.nextInt('z'-'a');
                chars[i] = (char)('a' + c);
            } else if (r.nextBoolean()) {
                // Uppercase
                int start = 'A';
                int c = r.nextInt('Z'-'A');
                chars[i] = (char)('A' + c);
            } else {
                // Number
                int start = '0';
                int c = r.nextInt('9'-'0');
                chars[i] = (char)('0' + c);
            }
        }

        return new String(chars);
    }
}
