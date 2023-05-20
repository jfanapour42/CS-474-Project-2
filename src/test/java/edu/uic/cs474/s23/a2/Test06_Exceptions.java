package edu.uic.cs474.s23.a2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.NoSuchElementException;

public class Test06_Exceptions {

    @Test
    public void testException() {
        ObjectInspector inspector = Main.getInspector();

        {
            B obj = new B();
            obj.a.ex = new RuntimeException();

            Assert.assertEquals(Map.of("a", "Thrown exception: java.lang.RuntimeException"), inspector.describeObject(obj));
        }

        {
            B obj = new B();
            obj.a.ex = new NoSuchElementException();

            Assert.assertEquals(Map.of("a", "Thrown exception: java.util.NoSuchElementException"), inspector.describeObject(obj));
        }

        {
            B obj = new B();
            Assert.assertEquals(Map.of("a", ""), inspector.describeObject(obj));
        }
    }

    @Test
    public void testError() {
        ObjectInspector inspector = Main.getInspector();

        {
            B obj = new B();
            obj.a.er = new Error();

            Assert.assertEquals(Map.of("a", "Raised error: java.lang.Error"), inspector.describeObject(obj));
        }

        {
            B obj = new B();
            obj.a.er = new CustomError();

            Assert.assertEquals(Map.of("a", "Raised error: " + "edu.uic.cs474.s23.a2.Test06_Exceptions$" + "CustomError"), inspector.describeObject(obj));
        }

        {
            B obj = new B();
            Assert.assertEquals(Map.of("a", ""), inspector.describeObject(obj));
        }
    }

    public static class A {
        public RuntimeException ex;
        public Error er;

        public String toString() {
            if (this.ex != null)
                throw this.ex;
            else if (this.er != null)
                throw this.er;
            else
                return "";
        }
    }

    public static class CustomError extends Error { }

    public static class B {
        public A a = new A();
    }
}
