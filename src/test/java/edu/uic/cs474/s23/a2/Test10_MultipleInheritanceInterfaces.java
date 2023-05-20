package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class Test10_MultipleInheritanceInterfaces {

    @Test
    public void testSingleInheritanceInterfaces() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(I1.class);
        Map<String, String> expected = Map.of(
                "I1"+"."+"i1", "I1",
                "I9"+"."+"i9", "I9"
        );
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSingleInheritanceInstance() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(new B());
        Map<String, String> expected = Map.of(
                "I1"+"."+"i1", "I1",
                "I9"+"."+"i9", "I9",
                "b", "B"
        );
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMultipleInheritanceInterfaces() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(A.class);
        Map<String, String> expected = Map.of(
                "I1"+"."+"i1", "I1",
                "I3"+"."+"i3", "I3",
                "I4"+"."+"i4", "I4",
                "I6"+"."+"i6", "I6",
                "I9"+"."+"i9", "I9"
        );
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMultipleInheritanceInstance() {
        ObjectInspector inspector = Main.getInspector();

        Map<String, String> result = inspector.describeObject(new A());
        Map<String, String> expected = Map.of(
                "I1"+"."+"i1", "I1",
                "I3"+"."+"i3", "I3",
                "I4"+"."+"i4", "I4",
                "I6"+"."+"i6", "I6",
                "I9"+"."+"i9", "I9",
                "a", "A",
                "b", "B"
        );
        Assert.assertEquals(expected, result);
    }

    public static class B implements I1 {
        public String b = "B";
    }

    public static class A extends B implements I1, I2 {
        public String a = "A";
    }

    public static interface I1 extends I9 {
        public static String i1 = "I1";
    }

    public static interface I2 extends I3, I4 {}

    public static interface I3 {
        public static String i3 = "I3";
    }

    public static interface I4 extends I5, I6 {
        public static String i4 = "I4";
    }

    public static interface I5 {}

    public static interface I6 {
        public static String i6 = "I6";
    }

    public static interface I9 {
        public static String i9 = "I9";
    }
}
