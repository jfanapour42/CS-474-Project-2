package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static edu.uic.cs474.s23.a2.Test01_PublicStringField.generateRandomString;

public class Test05_StaticFields {

    @Test
    public void testStaticFields() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        for (int count = 0 ; count < 1_000 ; count++) {
            String a;

            if (r.nextBoolean()) {
                a = generateRandomString();
            } else {
                a = null;
            }

            Object b;
            if (r.nextBoolean()) {
                b = generateRandomString();
            } else if (r.nextBoolean()) {
                b = a;
            } else if (r.nextBoolean()) {
                b = new Object();
            } else {
                b = null;
            }

            int c = r.nextInt();

            A.a = a;
            B.b = b;
            C.c = c;

            {
                Map<String, String> result = inspector.describeObject(A.class);
                Map<String, String> expected = Map.of(
                        "A" + "." + "a", (a == null ? "null" : a)
                );
                Assert.assertEquals(expected, result);
            }

            {
                Map<String, String> result = inspector.describeObject(B.class);
                Map<String, String> expected = Map.of(
                        "A" + "." + "a", (a == null ? "null" : a),
                        "B" + "." + "b", (b == null ? "null" : "" + b)
                );
                Assert.assertEquals(expected, result);
            }

            {
                Map<String, String> result = inspector.describeObject(C.class);
                Map<String, String> expected = Map.of(
                        "A" + "." + "a", (a == null ? "null" : a),
                        "B" + "." + "b", (b == null ? "null" : "" + b),
                        "C" + "." + "c", Integer.toString(c)
                );
                Assert.assertEquals(expected, result);
            }
        }
    }

    @Test
    public void testStaticAndInstanceFields() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        for (int count = 0 ; count < 1_000 ; count++) {
            String a;

            if (r.nextBoolean()) {
                a = generateRandomString();
            } else {
                a = null;
            }

            Object b;
            if (r.nextBoolean()) {
                b = generateRandomString();
            } else if (r.nextBoolean()) {
                b = a;
            } else if (r.nextBoolean()) {
                b = new Object();
            } else {
                b = null;
            }

            int c = r.nextInt();

            A.a = a;
            B.b = b;
            C.c = c;

            String aa;

            if (r.nextBoolean()) {
                aa = generateRandomString();
            } else {
                aa = null;
            }

            Object bb;
            if (r.nextBoolean()) {
                bb = generateRandomString();
            } else if (r.nextBoolean()) {
                bb = a;
            } else if (r.nextBoolean()) {
                bb = new Object();
            } else {
                bb = null;
            }

            int cc = r.nextInt();

            C obj = new C(aa,bb,cc);

            {
                Map<String, String> result = inspector.describeObject(obj);
                Map<String, String> expected = Map.of(
                        "A" + "." + "a", (a == null ? "null" : a),
                        "B" + "." + "b", (b == null ? "null" : "" + b),
                        "C" + "." + "c", Integer.toString(c),
                        "aa", (aa == null ? "null" : aa),
                        "bb", (bb == null ? "null" : "" + bb),
                        "cc", Integer.toString(cc)
                );
                Assert.assertEquals(expected, result);
            }
        }
    }

    public static class A {
        public static String a;
        protected String aa;
    }

    public static class B extends A {
        /*default*/ static Object b;
        public Object bb;
    }

    public static class C extends B {
        protected static int c;
        protected int cc;

        public C(String aa, Object bb, int cc) { this.aa = aa; this.bb = bb; this.cc = cc; }
    }
}
