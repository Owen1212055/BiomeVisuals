package com.owen1212055.biomevisuals.nms;

import sun.misc.*;

import java.lang.invoke.*;
import java.lang.reflect.*;

public class UnsafeUtils {

    private static Unsafe UNSAFE;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UNSAFE = (Unsafe) unsafeField.get(null);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public static void unsafeSet(Field field, Object obj, Object value) {
        UNSAFE.putObject(obj, UNSAFE.objectFieldOffset(field), value);
    }

    public static void unsafeStaticSet(Field field, Object value) {
        UNSAFE.putObject(UNSAFE.staticFieldBase(field), UNSAFE.staticFieldOffset(field), value);
    }

}
