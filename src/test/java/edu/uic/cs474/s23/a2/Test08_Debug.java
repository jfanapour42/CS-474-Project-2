package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static edu.uic.cs474.s23.a2.Test01_PublicStringField.generateRandomString;

public class Test08_Debug {

    @Test
    public void testDebugMethodCalled() {
        ObjectInspector inspector = Main.getInspector();

        String s = generateRandomString();
        A a = new A();
        a.s = s;

        Map<String, String> result = inspector.describeObject(new Holder(a));

        Assert.assertEquals(Map.of("field", s), result);
    }

    @Test
    public void testStaticDebugMethodCalled() {
        ObjectInspector inspector = Main.getInspector();

        String s = generateRandomString();
        B b = new B();
        b.s = s;

        Map<String, String> result = inspector.describeObject(new Holder(b));

        Assert.assertEquals(Map.of("field", s), result);
    }

    @Test
    public void testCorrectStaticDebugMethodCalled() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(new Holder(new C()));

        Assert.assertEquals(Map.of("field", "Correct method!"), result);
    }

    @Test
    public void testCheckedException() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(new Holder(new D()));

        Assert.assertEquals(Map.of("field", "Thrown checked exception: edu.uic.cs474.s23.a2.Test08_Debug$" + "CheckedException"), result);
    }

    @Test
    public void testRuntimeException() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(new Holder(new E()));

        Assert.assertEquals(Map.of("field", "Thrown exception: java.lang.IllegalArgumentException"), result);
    }

    @Test
    public void testReflectiveOperationException() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(new Holder(new F()));

        Assert.assertEquals(Map.of("field", "Thrown checked exception: java.lang.NoSuchMethodException"), result);
    }

    public static class Holder {
        public final Object field;

        public Holder(Object field) { this.field = field; }
    }

    public static class A {
        public String s;

        public String debug() throws Exception {
            return this.s;
        }

        public String toString() {
            return null;
        }
    }

    public static class B extends A {
        public static String debug(B b) throws Exception {
            return b.s;
        }

        public String toString() {
            return null;
        }
    }

    public static class C extends B {
        public static String debug(String s) throws Exception {
            return "Wrong method String";
        }

        public static String debug(D d) throws Exception {
            return "Wrong method D";
        }

        public static String debug(B b) throws Exception {
            return "Correct method!";
        }

        public String toString() {
            return "Wrong method";
        }
    }

    public static class D {
        public static String debug(Object o) throws Exception {
            throw new CheckedException();
        }

        public String toString() {
            return null;
        }
    }

    public static class E {
        public String debug() throws Exception {
            throw new IllegalArgumentException();
        }

        public String toString() {
            return null;
        }
    }

    public static class F {
        public String debug() throws Exception {
            throw new NoSuchMethodException();
        }

        public String toString() {
            return null;
        }
    }

    public static class CheckedException extends Exception { }
}
