package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static edu.uic.cs474.s23.a2.Test01_PublicStringField.generateRandomString;

public class Test04_PrivateFields {

    @Test
    public void testNotVisibleFields() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        for (int count = 0 ; count < 1_000 ; count++) {
            int     a    = r.nextInt();
            String  a_expected = Integer.toString(a);

            Long    b;
            if (r.nextBoolean()) { b = null; } else { b = Long.valueOf(r.nextLong()); }
            String b_expected = (b == null ? "null" : "Boxed " + Long.toString(b) + "#L");

            double  c    = r.nextDouble();
            String c_expected = Double.toString(c) + "#D";

            Float   d;
            if (r.nextBoolean()) { d = null; } else { d = Float.valueOf(r.nextFloat()); }
            String d_expected = (d == null ? "null" : "Boxed " + Float.toString(d) + "#F");

            short   e    = (short) r.nextInt();
            String e_expected = "0" + Integer.toOctalString(e);

            String  f;
            if (r.nextBoolean()) { f = null; } else { f = generateRandomString(); }
            String f_expected = (f == null ? "null" : f);

            Object g;
            String g_expected;
            switch (r.nextInt(4)) {
                case 0:
                    g = b;
                    g_expected = b_expected;
                    break;
                case 1:
                    g = d;
                    g_expected = d_expected;
                    break;
                case 2:
                    g = f;
                    g_expected = f_expected;
                    break;
                default:
                    g = new Object();
                    g_expected = "" + g;
                    break;
            }

            A obj = new A(a,b,c,d,e,f,g);

            Map<String, String> result = inspector.describeObject(obj);
            Map<String, String> expected = Map.of(
                    "a", a_expected,
                    "b", b_expected,
                    "c", c_expected,
                    "d", d_expected,
                    "e", e_expected,
                    "f", f_expected,
                    "g", g_expected
            );

            Assert.assertEquals(expected, result);
        }
    }

    public static class A {
        private int a;
        protected Long b;
        /*default*/ double c;
        private Float d;
        protected short e;
        /*default*/ String f;
        private Object g;

        public A(int a, Long b, double c, Float d, short e, String f, Object g) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
        }
    }
}
