package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student(1, "Adrian", Arrays.asList("Mathematics", "Astronomy"));
        Student student2 = new Student(2, "Tomek", Arrays.asList("Mathematics", "Phisics"));

        List<Student> list = new ArrayList();
        list.add(student1);
        list.add(student2);

        List<Integer> ids = Arrays.asList(1, 5);

        List<Student> list1 = list.stream()
                .filter(s -> ids.stream().anyMatch(id -> id.equals(s.getId())))
                .collect(Collectors.toList());

        System.out.println(list1);


        Set<String> lessons = list.stream()
                .flatMap(s -> s.getLessons().stream())
                .collect(Collectors.toSet());

        System.out.println(lessons);


        Map<Integer, Student> map2 = list.stream()
                .collect(Collectors.toMap(s -> s.getId(), s -> s));

        System.out.println(map2);

        HashSet set = new HashSet();
        TreeSet treeSet = new TreeSet<>();

    }
    
    static class  Student {
        private int Id;
        private String name;
        private List<String> lessons;

        public Student(int id, String name, List<String> lessons) {
            this.Id = id;
            this.name = name;
            this.lessons = lessons;
        }

        public int getId() {
            return Id;
        }

        public List<String> getLessons() {
            return lessons;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "Id=" + Id +
                    ", name='" + name + '\'' +
                    ", lessons=" + lessons +
                    '}';
        }
    }
}
