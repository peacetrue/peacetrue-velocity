package com.github.peacetrue.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xiayx
 */
public class ClasspathUtilsTest {

    @Test
    public void findFiles() throws IOException {
        List<SimpleFile> templates = ClasspathUtils.findFiles("org/slf4j/impl/StaticLoggerBinder.class");
        Assert.assertEquals(templates.size(), 1);
        templates = ClasspathUtils.findFiles("templates");
        Assert.assertEquals(templates.size(), 9);

        templates = ClasspathUtils.findFiles("empty");
        Assert.assertEquals(templates.size(), 0);

        templates = ClasspathUtils.findFiles("com/github/peacetrue/util");
        System.out.println(templates.stream().map(Objects::toString).collect(Collectors.joining("\n")));
    }

    @Test
    public void findFilesFromEmpty() throws IOException {
        List<SimpleFile> templates = ClasspathUtils.findFiles("");
        System.out.println(templates.stream().map(Objects::toString).collect(Collectors.joining("\n")));
    }

    /**
     * @throws IOException
     */
    @Test
    public void getSystemResources() throws IOException {
        //空字符，会搜索 classpath，不会搜索类库
        //其他，会搜索 classpath 和 类库
        //也就是说，没有方法可以直接遍历所有文件
        Enumeration<URL> resources = ClassLoader.getSystemResources("com/");
        while (resources.hasMoreElements()) {
            System.out.println(resources.nextElement());
        }
    }
}