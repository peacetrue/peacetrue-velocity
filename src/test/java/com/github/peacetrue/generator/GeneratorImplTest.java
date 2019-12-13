package com.github.peacetrue.generator;

import com.github.peacetrue.velocity.VelocityUtils;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayx
 */
public class GeneratorImplTest {

    @Test
    public void generateModuleStructure() throws IOException {
        //关于重复生成的覆盖和更新问题
        GeneratorImpl generator = new GeneratorImpl();
        generator.setVelocityEngine(VelocityUtils.buildClasspathResourceLoader());
        generator.setTemplateFolderPath("template-model-structure");
        String absoluteDistPath = "/Users/xiayx/Documents/Projects/peacetrue-demo";
        HashMap<String, Object> context = new HashMap<>();
        context.put("projectName", "peacetrue-demo");
        context.put("basePackageName", "com.github.peacetrue.demo");
        context.put("moduleName", "Demo");
        context.put("module-name", "demo");
        context.put("moduleDescription", "示例");
        generator.generate(absoluteDistPath, context);
    }

    private static final Map<Integer, Class> MAP = new HashMap<>();
    private static final Map<String, Integer> MAP_ = new HashMap<>();

    static {
        MAP.put(Types.BIT, Boolean.class);
        MAP.put(Types.TINYINT, Byte.class);
        MAP.put(Types.SMALLINT, Short.class);
        MAP.put(Types.INTEGER, Integer.class);
        MAP.put(Types.BIGINT, Long.class);
        MAP.put(Types.FLOAT, Float.class);
        MAP.put(Types.REAL, Float.class);
        MAP.put(Types.DOUBLE, Double.class);
        MAP.put(Types.NUMERIC, BigDecimal.class);
        MAP.put(Types.DECIMAL, BigDecimal.class);
        MAP.put(Types.CHAR, String.class);
        MAP.put(Types.VARCHAR, String.class);
        MAP.put(Types.LONGVARCHAR, String.class);
        MAP.put(Types.DATE, java.util.Date.class);
        MAP.put(Types.TIME, Time.class);
        MAP.put(Types.TIMESTAMP, Timestamp.class);
        MAP.put(Types.BINARY, Byte[].class);
        MAP.put(Types.VARBINARY, Byte[].class);
        MAP.put(Types.LONGVARBINARY, Byte[].class);
        MAP.forEach((key, value) -> MAP_.put(value.getName(), key));
    }

    @Test
    public void generateModuleContent() throws IOException {
        GeneratorImpl generator = new GeneratorImpl();
        VelocityEngine velocityEngine = VelocityUtils.buildClasspathResourceLoader();
        velocityEngine.setProperty("velocimacro.library.path", "template-model-content/velocimacros.vtl");
        generator.setVelocityEngine(velocityEngine);
        generator.setTemplateFolderPath("template-model-content");
        String absoluteDistPath = "/Users/xiayx/Documents/Projects/peacetrue-demo";
        HashMap<String, Object> context = new HashMap<>();
        context.put("projectName", "peacetrue-demo");
        context.put("basePackageName", "com.github.peacetrue.demo");
        context.put("moduleName", "demo");
        context.put("module-name", "demo");
        context.put("moduleDescription", "示例");
        context.put("name", "Demo");
        /*private Id id;
    private String code;
    private String name;
    private OperatorId creatorId;
    private Date createdTime;
    private OperatorId modifierId;
    private Date modifiedTime;*/
        context.put("properties", Arrays.asList(
                new ModelProperty("id", Long.class, "主键"),
                new ModelProperty("code", String.class, "编码"),
                new ModelProperty("name", String.class, "名称"),
                new ModelProperty("creatorId", String.class, "创建者主键"),
                new ModelProperty("createdTime", Date.class, "创建时间"),
                new ModelProperty("modifierId", String.class, "修改者主键"),
                new ModelProperty("modifiedTime", Date.class, "修改时间")
        ));
        context.put("comment", "示例");
        context.put("javaTypeToSqlType", MAP_);
        generator.generate(absoluteDistPath, context);
    }

    private static Logger logger = LoggerFactory.getLogger(GeneratorImplTest.class);

    @Test
    public void name() throws IOException {
        String path = "/Users/xiayx/Documents/Projects/peacetrue-velocity/src/test/resources/template-model-structure";
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                logger.info("dir[{}]", dir);
                if (dir.getFileName().toString().contains("{moduleName}")) {
                    Files.move(dir, Paths.get(dir.toString().replace("{moduleName}", "{domainName}")), StandardCopyOption.REPLACE_EXISTING);
                } else if (dir.getFileName().toString().contains("{module-name}")) {
                    Files.move(dir, Paths.get(dir.toString().replace("{module-name}", "{moduleName}")), StandardCopyOption.REPLACE_EXISTING);
                }
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                logger.info("file[{}]", file);
                if (file.getFileName().toString().contains("{moduleName}")) {
                    Files.move(file, Paths.get(file.toString().replace("{moduleName}", "{domainName}")), StandardCopyOption.REPLACE_EXISTING);
                } else if (file.getFileName().toString().contains("{module-name}")) {
                    Files.move(file, Paths.get(file.toString().replace("{module-name}", "{moduleName}")), StandardCopyOption.REPLACE_EXISTING);
                }
                return super.visitFile(file, attrs);
            }
        });
    }
}