package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static edu.uic.cs474.s23.a2.Test01_PublicStringField.generateRandomString;

public class Test03_ToStringNullPrimitives {

    @Test
    public void testToString() {
        ObjectInspector inspector = Main.getInspector();

        C obj = new C();
        Map<String, String> result = inspector.describeObject(obj);

        Assert.assertTrue(result.containsKey("field"));
        Assert.assertEquals(obj.field.f, result.get("field"));
    }

    @Test
    public void testPrimitives() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        for (int count = 0 ; count < 1_000 ; count++) {
            HasPrimitiveFields obj = new HasPrimitiveFields();
            int     i    = r.nextInt();
            long    l    = r.nextLong();
            float   f    = r.nextFloat();
            double  d    = r.nextDouble();
            short   s    = (short) r.nextInt();
            byte    b    = (byte) r.nextInt();
            boolean bool = r.nextBoolean();
            char    c    = (char) r.nextInt();

            obj.i = i;
            obj.l = l;
            obj.f = f;
            obj.d = d;
            obj.s = s;
            obj.b = b;
            obj.bool = bool;
            obj.c = c;

            Map<String, String> result = inspector.describeObject(obj);
            Map<String, String> expected = Map.of(
                    "i", Integer.toString(i),
                    "l", (Long.toString(l) + "#L"),
                    "f", (Float.toString(f) + "#F"),
                    "d", (Double.toString(d) + "#D"),
                    "s", "0" + Integer.toOctalString(s),
                    "b", "0x" + Integer.toHexString(b),
                    "bool", Boolean.toString(bool),
                    "c", Character.toString(c)
            );

            Assert.assertEquals(expected, result);
        }
    }

    @Test
    public void testBoxedStaticType() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        for (int count = 0 ; count < 1_000 ; count++) {
            HasBoxedFields obj = new HasBoxedFields();
            Integer   i    = Integer.valueOf(r.nextInt());
            Long      l    = Long.valueOf(r.nextLong());
            Float     f    = Float.valueOf(r.nextFloat());
            Double    d    = Double.valueOf(r.nextDouble());
            Short     s    = Short.valueOf((short) r.nextInt());
            Byte      b    = Byte.valueOf((byte) r.nextInt());
            Boolean   bool = Boolean.valueOf(r.nextBoolean());
            Character c    = Character.valueOf((char) r.nextInt());

            obj.i_b = i;
            obj.l_b = l;
            obj.f_b = f;
            obj.d_b = d;
            obj.s_b = s;
            obj.b_b = b;
            obj.bool_b = bool;
            obj.c_b = c;

            Map<String, String> result = inspector.describeObject(obj);
            Map<String, String> expected = Map.of(
                    "i_b", "Boxed " + Integer.toString(i),
                    "l_b", "Boxed " + (Long.toString(l) + "#L"),
                    "f_b", "Boxed " + (Float.toString(f) + "#F"),
                    "d_b", "Boxed " + (Double.toString(d) + "#D"),
                    "s_b", "Boxed 0" + Integer.toOctalString(s),
                    "b_b", "Boxed 0x" + Integer.toHexString(b),
                    "bool_b", "Boxed " + Boolean.toString(bool),
                    "c_b", "Boxed " + Character.toString(c)
            );

            Assert.assertEquals(expected, result);
        }
    }

    @Test
    public void testBoxedDynamicType() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        for (int count = 0 ; count < 1_000 ; count++) {
            HasBoxedObjectFields obj = new HasBoxedObjectFields();
            Integer   i    = Integer.valueOf(r.nextInt());
            Long      l    = Long.valueOf(r.nextLong());
            Float     f    = Float.valueOf(r.nextFloat());
            Double    d    = Double.valueOf(r.nextDouble());
            Short     s    = Short.valueOf((short) r.nextInt());
            Byte      b    = Byte.valueOf((byte) r.nextInt());
            Boolean   bool = Boolean.valueOf(r.nextBoolean());
            Character c    = Character.valueOf((char) r.nextInt());

            obj.i_b = i;
            obj.l_b = l;
            obj.f_b = f;
            obj.d_b = d;
            obj.s_b = s;
            obj.b_b = b;
            obj.bool_b = bool;
            obj.c_b = c;

            Map<String, String> result = inspector.describeObject(obj);
            Map<String, String> expected = Map.of(
                    "i_b", "Boxed " + Integer.toString(i),
                    "l_b", "Boxed " + (Long.toString(l) + "#L"),
                    "f_b", "Boxed " + (Float.toString(f) + "#F"),
                    "d_b", "Boxed " + (Double.toString(d) + "#D"),
                    "s_b", "Boxed 0" + Integer.toOctalString(s),
                    "b_b", "Boxed 0x" + Integer.toHexString(b),
                    "bool_b", "Boxed " + Boolean.toString(bool),
                    "c_b", "Boxed " + Character.toString(c)
            );

            Assert.assertEquals(expected, result);
        }
    }

    @Test
    public void testWeirdBoxed() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();

        HasBoxedFields obj = new HasBoxedFields();
        Integer   i    = Integer.MAX_VALUE;
        Long      l    = Long.MIN_VALUE;
        Float     f    = Float.NaN;
        Double    d    = Double.POSITIVE_INFINITY;
        Short     s    = Short.MAX_VALUE;
        Byte      b    = Byte.MIN_VALUE;
        Boolean   bool = Boolean.FALSE;
        Character c    = Character.MAX_HIGH_SURROGATE;

        obj.i_b = i;
        obj.l_b = l;
        obj.f_b = f;
        obj.d_b = d;
        obj.s_b = s;
        obj.b_b = b;
        obj.bool_b = bool;
        obj.c_b = c;

        Map<String, String> result = inspector.describeObject(obj);
        Map<String, String> expected = Map.of(
                "i_b", "Boxed " + Integer.toString(i),
                "l_b", "Boxed " + (Long.toString(l) + "#L"),
                "f_b", "Boxed " + (Float.toString(f) + "#F"),
                "d_b", "Boxed " + (Double.toString(d) + "#D"),
                "s_b", "Boxed 0" + Integer.toOctalString(s),
                "b_b", "Boxed 0x" + Integer.toHexString(b),
                "bool_b", "Boxed " + Boolean.toString(bool),
                "c_b", "Boxed " + Character.toString(c)
        );

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testNull() {
        ObjectInspector inspector = Main.getInspector();
        Random r = new Random();


        for (int count = 0 ; count < 1_000 ; count++) {

            Object[] objs = new Object[] {
                Integer.valueOf(r.nextInt()),
                Long.valueOf(r.nextLong()),
                Float.valueOf(r.nextFloat()),
                Double.valueOf(r.nextDouble()),
                Short.valueOf((short) r.nextInt()),
                Byte.valueOf((byte) r.nextInt()),
                Boolean.valueOf(r.nextBoolean()),
                Character.valueOf((char) r.nextInt()),
                "Constant String",
                generateRandomString(),
                new C()
            };

            String[] expected;

            {
                int i = 0;
                expected = new String[]{
                        "Boxed " + Integer.toString((Integer) objs[i++]),
                        "Boxed " + (Long.toString((Long) objs[i++]) + "#L"),
                        "Boxed " + (Float.toString((Float) objs[i++]) + "#F"),
                        "Boxed " + (Double.toString((Double) objs[i++]) + "#D"),
                        "Boxed 0" + Integer.toOctalString((short)objs[i++]),
                        "Boxed 0x" + Integer.toHexString((byte)objs[i++]),
                        "Boxed " + Boolean.toString((Boolean)objs[i++]),
                        "Boxed " + Character.toString((Character)objs[i++]),
                        (String)objs[i++],
                        (String)objs[i++],
                        objs[i++].toString()
                };
            }

            HasNull obj = new HasNull();
            int expectedNonNull = r.nextInt(objs.length);
            obj.notNull = objs[expectedNonNull];

            obj.alwaysNull = null;

            int expectedMaybeNull = r.nextInt(objs.length);
            boolean isNull = r.nextBoolean();
            if (isNull) {
                obj.maybeNull = null;
            } else {
                obj.maybeNull = objs[expectedMaybeNull];
            }

            Map<String, String> result = inspector.describeObject(obj);
            Map<String, String> expectedMap = Map.of(
                    "notNull", expected[expectedNonNull],
                    "alwaysNull", "null",
                    "maybeNull", (isNull) ? "null" : expected[expectedMaybeNull]
            );

            Assert.assertEquals(expectedMap, result);
        }


    }

    public static class C {
        public HasToString field = new HasToString();
    }

    public static class HasToString {
        /*default*/ String f = null;

        public String toString() {
            this.f = generateRandomString();
            return this.f;
        }
    }

    public static class HasPrimitiveFields {
        public int     i;
        public long    l;
        public float   f;
        public double  d;
        public short   s;
        public byte    b;
        public boolean bool;
        public char    c;
    }

    public static class HasBoxedFields {
        public Integer   i_b;
        public Long      l_b;
        public Float     f_b;
        public Double    d_b;
        public Short     s_b;
        public Byte      b_b;
        public Boolean   bool_b;
        public Character c_b;
    }

    public static class HasBoxedObjectFields {
        public Object i_b;
        public Object l_b;
        public Object f_b;
        public Object d_b;
        public Object s_b;
        public Object b_b;
        public Object bool_b;
        public Object c_b;
    }

    public static class HasNull {
        public Object notNull;
        public Object alwaysNull;
        public Object maybeNull;
    }
}
