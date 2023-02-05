package ru.otus;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class HelloOtus {
    public static void main(String... args) {
        List<Integer> example = new ArrayList<>();
        int min = 0;
        int max = 100;
        for (int i = min; i < max; i++) {
            example.add(i);
        }

        System.out.println(Lists.reverse(example));
    }
}