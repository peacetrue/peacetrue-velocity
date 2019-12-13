package com.github.peacetrue.velocity;

import com.github.peacetrue.util.ClasspathUtils;
import com.github.peacetrue.util.SimpleFile;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xiayx
 */
public abstract class VelocityUtils {

    public static final Logger logger = LoggerFactory.getLogger(VelocityUtils.class);
    public static final Predicate<String> IGNORE_CONVENTION = path -> path.startsWith("include") || path.endsWith(".vtl") || path.endsWith("Placeholder.adoc");
    public static final Predicate<String> MERGE_CONVENTION = path -> path.endsWith(".vm");
    public static final Function<String, String> CONVERTER_CONVENTION = path -> path.endsWith(".vm") ? path.substring(0, path.length() - 3) : path;

    public static void setClasspathResourceLoader(VelocityEngine velocityEngine) {
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
    }

    public static VelocityEngine buildClasspathResourceLoader() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        return velocityEngine;
    }

    /**
     * 解析所有模板
     *
     * @param velocityEngine      模板引擎
     * @param templateFolderPath  模板目录，存放模板的目录
     * @param ignoredTemplateFile 忽略的模板文件，忽略后不进行任何处理
     * @param mergedTemplateFile  解析的模板文件，不需要解析则直接拷贝
     * @param context             解析上下文
     * @param distFolderPath      目标目录，模板生成文件存储的目录
     * @param distFileConverter   目标文件转换器，如果模板文件以vm结尾，则需要转换为文件后缀
     * @throws IOException 解析过程中发生读写异常
     */
    public static void mergeAllClasspath(VelocityEngine velocityEngine, String templateFolderPath,
                                         Predicate<String> ignoredTemplateFile, Predicate<String> mergedTemplateFile,
                                         Context context, String distFolderPath, Function<String, String> distFileConverter) throws IOException {
        logger.info("将类路径[{}]下的模板全部输出到目标目录[{}]下", templateFolderPath, distFolderPath);
        templateFolderPath = ClasspathUtils.format(templateFolderPath);
        List<SimpleFile> templateFiles = ClasspathUtils.findFiles(templateFolderPath);
        templateFiles.remove(0);
        logger.debug("取得类路径[{}]下模板共[{}]个（排除模板目录）", templateFolderPath, templateFiles.size());
        Path distPathObject = Paths.get(distFolderPath);
        for (SimpleFile templateFile : templateFiles) {
            String relativeTemplateFile = templateFile.getPath().substring(templateFolderPath.length() + 1);
            if (ignoredTemplateFile.test(relativeTemplateFile)) {
                logger.debug("模板文件[{}]被设置为忽略", templateFile);
                continue;
            }
            Path distFilePath = distPathObject.resolve(distFileConverter.apply(relativeTemplateFile));
            logger.debug("根据模板文件[{}]生成目标文件[{}]", templateFile.getPath(), distFilePath);
            if (templateFile.isDirectory()) {
                if (Files.notExists(distFilePath)) Files.createDirectories(distFilePath);
            } else {
                Path distParentFilePath = distFilePath.resolveSibling("");
                if (Files.notExists(distParentFilePath)) Files.createDirectories(distParentFilePath);
                if (mergedTemplateFile.test(relativeTemplateFile)) {
                    logger.debug("模板文件[{}]被解析", templateFile.getPath());
                    Template template = velocityEngine.getTemplate(templateFile.getPath());
                    FileWriter writer = new FileWriter(distFilePath.toFile());
                    template.merge(context, writer);
                    writer.flush();
                    writer.close();
                } else {
                    logger.debug("模板文件[{}]被拷贝", templateFile.getPath());
                    Files.copy(VelocityUtils.class.getResourceAsStream("/" + templateFile.getPath()), distFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static void mergeAllClasspath(VelocityEngine velocityEngine, String templateFolderPath, Context context, String distFolderPath) throws IOException {
        mergeAllClasspath(velocityEngine, templateFolderPath, IGNORE_CONVENTION, MERGE_CONVENTION, context, distFolderPath, CONVERTER_CONVENTION);
    }

}
