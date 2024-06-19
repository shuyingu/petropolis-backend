package com.capstone.petropolis;

import com.capstone.petropolis.utils.JSON;

public class TestHelper {
    public static void dump(Object o) {
        if (o == null) {
            System.out.println("null");
        } else {
            System.out.println(o.getClass().getName());
            System.out.println(JSON.to(o));
        }
    }
}
