package com.chenjin.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TestStream1 {
    @Test
    public void test01() {
        /**
         * 筛选出满足条件的集合
         */
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> collect = strings.stream().filter(v -> v.equals("abc")).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test02() {
        /**
         * 输出十个随机数
         */
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    @Test
    public void test03() {
        /**
         * 将所有list对应乘2
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> collect = numbers.stream().map(v -> v * 2).distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test04() {
        /**
         * 获取空字符串数量
         */
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }
    @Test
    public void test05() {
        /**
         * 对流进行排序
         */
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        strings.stream().limit(10).sorted().forEach(System.out::println);
    }
    @Test
    public void test06() {
        /**
         * 聚合成list或者别的
         */
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        /**
         * 用逗号分隔  生成字符串
         */
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);
    }
    @Test
    public void test07() {
        /**
         * 统计结果的收集器
         */
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt(x -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }
}
