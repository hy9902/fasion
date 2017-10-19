package com.hydt.app;

import com.hydt.app.common.User;
import com.hydt.app.controller.FileUploadController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by bean_huang on 2017/7/13.
 */
public class Java8Test {
    private static Logger logger = LoggerFactory.getLogger(Java8Test.class);

    /**
     * Lambda 表达式,函数式接口
     * Java 8 提供了一个特殊的注解@FunctionalInterface
     * ex: Consumer,Runnable
     * Lambda 表达式的组成:
     * 1.A comma-separated list of formal parameters enclosed in parentheses
     * 2.The arrow token, ->
     * 3.A body, which consists of a single expression or a statement block.
     */
    @Test
    public void testLambda(){
        Arrays.asList(1,2,3).forEach(integer -> logger.debug(""+ Math.pow(integer,2)));
    }

    /**
     * 接口默认方法（不影响已实现的类），默认方法
     * 方法引用（::）
     * 1.类静态方法引用
     * 2.对象方法应用
     * 3.构造方法应用::new
     */
    @Test
    public void testInterfaceDefault(){

    }

    @Test
    public void testPaths() throws IOException {
        Path path = Paths.get("D:\\");
        Files.list(path).forEach(System.out::println);

        System.out.println(path.resolve("mbpg").toString());
    }

    @Test
    public void testFlatMap(){
        List<Integer> a=new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(2);
        List<Integer> b=new ArrayList<>();
        b.add(3);
        b.add(4);
        b.add(4);
        List<Integer> figures=Stream.of(a,b).flatMap(u->u.stream()).distinct().collect(Collectors.toList());
        figures.forEach(f->System.out.println(f));
    }

    @Test
    public void testDistinct(){
        List<User> list = new ArrayList<>();
        for(int i=0; i < 10; i++){
            User user = new User();
            user.setId((long) (i%3));
            user.setName(i+"");
            list.add(user);
        }
        System.out.println("--------------------------");
        list.stream().forEach(e-> System.out.println(e.toString()));

        List<User> unique = list.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(User::getId))), ArrayList::new)
        );
        System.out.println("--------------------------");
        unique.forEach(e-> System.out.println(e.toString()));

    }

    @Test
    public void convertTest() {
        List<String> collected = new ArrayList<>();
        collected.add("alpha");
        collected.add("beta");
        collected.add("cool");
        collected.add("delta");
        collected.stream().map(string -> {
            string=string.toUpperCase();
            return string;
        }).forEach(System.out::println);
        System.out.println(collected);//此处打印出来的是大写还是小写，为什么？
    }

    @Test
    public void longChainTest() {
        List<User> list = new ArrayList<>();
        for(int i=0; i < 10; i++){
            User user = new User();
            user.setId((long) (i%3));
            user.setName(i+"");
            user.setAge(i);
            list.add(user);
        }//随机生成5个person实例
        List<Integer> ages = new ArrayList<>();
        list.stream().forEach(person -> ages.add(person.getAge()));
        System.out.println(ages);//第一处打印
        ages.clear();
        list.stream().map(person -> {
            person.setAge(person.getAge() + 10);
            return person;
        }).count();
        list.stream().forEach(person -> ages.add(person.getAge()));
        System.out.println(ages);//第二处打印
//问两处打印的值是否相同，为什么？
    }
}
