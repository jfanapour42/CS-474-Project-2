package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test07_Update {

    @Test
    public void testUpdateAllFields() {
        ObjectInspector inspector = Main.getInspector();

        G obj = new G();
        List l = List.of("d", "another d");
        Map<String, Object> update = new HashMap<>(Map.of(
                "a", "a",
                "b", "b",
                "c", l,
                "d", l,
                "f", "f",
                "g", "g"
        ));

        update.put("e", null);

        inspector.updateObject(obj, update);

        Assert.assertEquals("a", obj.a);
        Assert.assertEquals("b", obj.b);
        Assert.assertTrue(obj.c == l);
        Assert.assertTrue(obj.d == l);
        Assert.assertNull(obj.e);
        Assert.assertEquals("f", obj.f);
        Assert.assertEquals("g", obj.g);
    }

    @Test
    public void testUpdateSomeFields() {
        ObjectInspector inspector = Main.getInspector();

        G obj = new G();
        G unmodified = new G();

        List l = List.of("d", "another d");
        Map<String, Object> update = new HashMap<>(Map.of(
                "a", "a",
                "g", "g"
        ));

        update.put("c", null);

        inspector.updateObject(obj, update);

        Assert.assertEquals("a", obj.a);
        Assert.assertEquals(unmodified.b, obj.b);
        Assert.assertNull(obj.c);
        Assert.assertEquals(unmodified.d, obj.d);
        Assert.assertEquals(unmodified.e, obj.e);
        Assert.assertEquals(unmodified.f, obj.f);
        Assert.assertEquals("g", obj.g);
    }

    @Test
    public void testUpdatePrimitives() {
        ObjectInspector inspector = Main.getInspector();

        HP obj = new HP();
        Map<String, Object> update = Map.of(
                "a", Integer.valueOf(0),
                "b", Long.valueOf(Long.MIN_VALUE),
                "c", Float.NaN,
                "d", Double.POSITIVE_INFINITY,
                "e", Short.MAX_VALUE,
                "f", Byte.valueOf((byte)0xFF),
                "g", true,
                "h", '4'
        );

        inspector.updateObject(obj, update);

        Assert.assertEquals(0, obj.a);
        Assert.assertEquals(Long.valueOf(Long.MIN_VALUE), obj.b);
        Assert.assertEquals(Float.NaN, obj.c, 0.0F);
        Assert.assertEquals(Double.POSITIVE_INFINITY, obj.d, 0.0F);
        Assert.assertEquals(Short.MAX_VALUE, obj.e);
        Assert.assertEquals(((byte)0xFF), obj.f);
        Assert.assertEquals(true, obj.g);
        Assert.assertEquals('4', obj.h);
    }

    public static class A {
        public String a = "inherited value";
    }

    public static class B extends A {
        /*default*/ String b = "declared value in b";
    }

    public static class C extends B {
        /*default*/ Object c = "declared value in c";
    }

    public static class D extends C {
        /*default*/ List<String> d = List.of("declared, value", "in", "d");
    }

    public static class E extends D {
        public String e = "declared value in e";
    }

    public static class F extends E {
        public String f = "declared value in f";
    }

    public static class G extends F {
        public String g = "declared value in g";
    }

    public static class AP {
        public int a = 474;
    }

    public static class BP extends AP {
        public Long b = Long.MAX_VALUE;
    }

    public static class CP extends BP {
        public float c = Float.NEGATIVE_INFINITY;
    }

    public static class DP extends CP {
        public double d = Double.NaN;
    }

    public static class EP extends DP {
        public short e = Short.MIN_VALUE;
    }

    public static class FP extends EP {
        public byte f = Byte.valueOf((byte)0xAA);
    }

    public static class GP extends FP {
        public boolean g = false;
    }

    public static class HP extends GP {
        public char h = ' ';
    }
}
