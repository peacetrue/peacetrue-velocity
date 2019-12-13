package com.github.peacetrue.velocity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.JarResourceLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author xiayx
 */
public class VelocityTest {

    @Before
    public void setUp() throws Exception {
        Velocity.reset();
    }

    /**
     * 测试默认模式下的运行方式
     * <p>
     * 通过日志观察运行逻辑
     * <p>
     * Default Properties resource: org/apache/velocity/runtime/defaults/velocity.properties
     */
    @Test
    public void init() {
        Velocity.init();
    }

    /**
     * 测试文件资源加载
     */
    @Test
    public void fileResourceLoader() {
        Velocity.init();
        //默认使用文件资源加载，读取 java 命令执行目录下的模板文件
        /*
        resource.loader.file.description = Velocity File Resource Loader
        resource.loader.file.class = org.apache.velocity.runtime.resource.loader.FileResourceLoader
        resource.loader.file.path = .
        resource.loader.file.cache = false
        resource.loader.file.modification_check_interval = 2
        */
        Template template = Velocity.getTemplate("fileResourceLoader.vm");
        StringWriter writer = new StringWriter();
        template.merge(null, writer);
        Assert.assertEquals("fileResourceLoader.vm", writer.toString());
    }

    /**
     * 测试类资源加载
     */
    @Test
    public void classResourceLoader() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
        Template template = Velocity.getTemplate("templates/classResourceLoader.vm");
        StringWriter writer = new StringWriter();
        template.merge(null, writer);
        Assert.assertEquals("classResourceLoader.vm", writer.toString());
    }

    /**
     * 测试宏，默认读取 velocimacros.vtl 模板
     * <p>
     * //TODO 只能在模板内声明，不能直接调用静态方法么
     */
    @Test
    public void macros() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.setProperty("velocimacro.library.path", "templates/velocimacros.vtl");
        Velocity.init();
        StringWriter writer = new StringWriter();
        Velocity.invokeVelocimacro("noArgs", null, null, new VelocityContext(), writer);
        Assert.assertEquals("日落", writer.toString().trim());

        //Note : currently only accepts args to the VM if they are in the context.
        writer = new StringWriter();
        Velocity.invokeVelocimacro("withArgs", null, new String[]{"something"}, new VelocityContext(), writer);
        Assert.assertEquals("$arg", writer.toString().trim());

        writer = new StringWriter();
        Velocity.invokeVelocimacro("withArgs", null, null, new VelocityContext(Collections.singletonMap("arg", "日落")), writer);
        Assert.assertEquals("日落", writer.toString().trim());

        writer = new StringWriter();
        Velocity.invokeVelocimacro("withContext", null, null, new VelocityContext(Collections.singletonMap("context", "日落")), writer);
        Assert.assertEquals("日落", writer.toString().trim());

        //BodyContent 也是通过 VelocityContext 设置的 
        writer = new StringWriter();
        Velocity.invokeVelocimacro("withBodyContent", null, null, new VelocityContext(Collections.singletonMap("bodyContent", "日落")), writer);
        Assert.assertEquals("日落", writer.toString().trim());
    }

    /**
     * 测试 解析
     */
    @Test
    public void parse() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        //TEMPLATE_ROOT：模板根路径，为什么非要根路径呢
        StringWriter writer = new StringWriter();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("count", 8);
        Velocity.evaluate(velocityContext, writer, "parse", "#parse( \"templates/parse.vm\" )");
//        Assert.assertEquals("classResourceLoader.vm", writer.toString());
        System.out.println(writer);
    }

    public static String method(String name) {return name;}

//    public String method(String name) {return name;}

    /**
     * 测试静态方法调用
     */
    @Test
    public void method() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        StringWriter writer = new StringWriter();
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("methods", VelocityTest.class);
//        velocityContext.put("methods", new VelocityTest());
        Velocity.evaluate(velocityContext, writer, "method", "$methods.method('日落')");
        Assert.assertEquals("日落", writer.toString());
    }

    /**
     * 测试后缀是否可以不用 vm
     */
    @Test
    public void suffix() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
        Template template = Velocity.getTemplate("templates/suffix.java");
        StringWriter writer = new StringWriter();
        template.merge(null, writer);
        Assert.assertEquals("日落", writer.toString());
    }

    /**
     * 测试类库模板
     */
    @Test
    public void jar() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "jar");
        Velocity.setProperty("resource.loader.jar.class", JarResourceLoader.class.getName());
        Velocity.setProperty("resource.loader.jar.path", "jar:file:/opt/myfiles/jar1.jar");
        Velocity.init();

        Template template = Velocity.getTemplate("templates/suffix.java");
        StringWriter writer = new StringWriter();
        template.merge(null, writer);
        Assert.assertEquals("日落", writer.toString());
    }

    @Test
    public void listContains() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(new VelocityContext(), stringWriter, "log", "#set($foo = ['foo','bar'])\n $foo.contains('foo')");
        System.out.println(stringWriter);

    }

    @Data
    @AllArgsConstructor
    public static class Person {
        private String name;
        private String sex;
    }

    @Test
    public void listNestProperties() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        Velocity.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        Velocity.init();

        StringWriter stringWriter = new StringWriter();
        VelocityContext context = new VelocityContext();
        context.put("persons", Arrays.asList(new Person("张", "男"), new Person("王", "女")));
        Velocity.evaluate(context, stringWriter, "log", "$persons[0].name");
        System.out.println(stringWriter);

    }
}