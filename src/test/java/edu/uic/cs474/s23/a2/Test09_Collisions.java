package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static edu.uic.cs474.s23.a2.Test01_PublicStringField.generateRandomString;

public class Test09_Collisions {

    @Test
    public void testReadCollisions() {
        return;
//        ObjectInspector inspector = Main.getInspector();
//        Random r = new Random();
//
//        int a = r.nextInt();
//        String b = generateRandomString();
//        Object c = List.of( Integer.valueOf(r.nextInt()), generateRandomString());
//        Object d = null;
//        Object e = Integer.valueOf(r.nextInt());
//
//        E obj = new E(a,b,c,d,e);
//        Map<String, String> expected = Map.of(
//                "a", Integer.toString(a),
//                "B" + ".this." + "field", b,
//                "C" + ".this." + "field", c.toString(),
//                "d", "null",
//                "E" + ".this." + "field", "Boxed " + Integer.toString((Integer)e)
//        );
//        Map<String, String> actual = inspector.describeObject(obj);
//
//        Assert.assertEquals(expected, actual);
    }

    public static class A {
        private int a = 474;

        public A(int a) {
            this.a = a;
        }
    }

    public static class B extends A {
        private String field = "CS474";

        public B(int a, String field) {
            super(a);
            this.field = field;
        }
    }

    public static class C extends B {
        private Object field = List.of("CS", "474");

        public C(int a, String b, Object field) {
            super(a,b);
            this.field = field;
        }
    }

    public static class D extends C {
        public Object d = null;

        public D(int a, String b, Object c, Object field) {
            super(a,b,c);
            this.d = field;
        }
    }

    public static class E extends D {
        public Object field = "E";

        public E(int a, String b, Object c, Object d, Object field) {
            super(a,b,c,d);
            this.field = field;
        }
    }
}
