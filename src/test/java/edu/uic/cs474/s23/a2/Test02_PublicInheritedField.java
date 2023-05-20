package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static edu.uic.cs474.s23.a2.Test01_PublicStringField.generateRandomString;

public class Test02_PublicInheritedField {

    @Test
    public void testHasConstDeclaredString() {
        ObjectInspector inspector = Main.getInspector();

        Object obj = new B();
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertTrue(result.containsKey("declared"));
        Assert.assertEquals("declared value", result.get("declared"));
    }

    @Test
    public void testHasRandomDeclaredString() {
        ObjectInspector inspector = Main.getInspector();

        B obj = new B();
        String randomString = generateRandomString();
        obj.declared = randomString;
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertTrue(result.containsKey("declared"));
        Assert.assertEquals(randomString, result.get("declared"));
    }

    @Test
    public void testHasConstInheritedString() {
        ObjectInspector inspector = Main.getInspector();

        Object obj = new B();
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertTrue(result.containsKey("inherited"));
        Assert.assertEquals("inherited value", result.get("inherited"));
    }

    @Test
    public void testHasRandomInheritedString() {
        ObjectInspector inspector = Main.getInspector();

        B obj = new B();
        String randomString = generateRandomString();
        obj.inherited = randomString;
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertTrue(result.containsKey("inherited"));
        Assert.assertEquals(randomString, result.get("inherited"));
    }

    @Test
    public void testAllConst() {
        ObjectInspector inspector = Main.getInspector();

        Object obj = new G();
        Map<String, String> result = inspector.describeObject(obj);

        Map<String, String> expected = Map.of(
                "inherited", "inherited value",
                "declared", "declared value",
                "c", "declared value",
                "d", "declared value",
                "e", "declared value in e",
                "f", "declared value",
                "g", "declared value in g"
        );
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testAllRandom() {
        ObjectInspector inspector = Main.getInspector();

        G obj = new G();

        String a = generateRandomString();
        String b = generateRandomString();
        String c = generateRandomString();
        String d = generateRandomString();
        String e = generateRandomString();
        String f = generateRandomString();
        String g = generateRandomString();

        obj.inherited = a;
        obj.declared  = b;
        obj.c  = c;
        obj.d  = d;
        obj.e  = e;
        obj.f  = f;
        obj.g  = g;

        Map<String, String> result = inspector.describeObject(obj);

        Map<String, String> expected = Map.of(
                "inherited", a,
                "declared", b,
                "c", c,
                "d", d,
                "e", e,
                "f", f,
                "g", g
        );
        Assert.assertEquals(expected, result);
    }

    public static class A {
        public String inherited = "inherited value";
    }

    public static class B extends A {
        public String declared = "declared value";
    }

    public static class C extends B {
        public String c = "declared value";
    }

    public static class D extends C {
        public String d = "declared value";
    }

    public static class E extends D {
        public String e = "declared value in e";
    }

    public static class F extends E {
        public String f = "declared value";
    }

    public static class G extends F {
        public String g = "declared value in g";
    }
}
