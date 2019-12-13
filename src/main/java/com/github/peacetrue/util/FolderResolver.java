package com.github.peacetrue.util;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 目录解析器
 *
 * @author xiayx
 */
@Data
public class FolderResolver {

    private static Logger logger = LoggerFactory.getLogger(FolderResolver.class);

    public static void resolve(String absoluteFolderPath, Map<String, Object> context) throws IOException {
        logger.info("解析目录[{}]", absoluteFolderPath);
        resolve(Paths.get(absoluteFolderPath), context);
    }

    private static void resolve(Path absoluteFolderPath, Map<String, Object> context) throws IOException {
        Files.walkFileTree(absoluteFolderPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (dir.equals(absoluteFolderPath)) {
                    return super.preVisitDirectory(dir, attrs);
                }

                if (this.move(dir, true)) {
                    resolve(absoluteFolderPath, context);
                    return FileVisitResult.TERMINATE;
                } else {
                    logger.trace("目录[{}]未使用占位符，无需解析", dir);
                    return super.preVisitDirectory(dir, attrs);
                }
            }

            public boolean move(Path dir, boolean isDirectory) throws IOException {
                String filename = dir.getFileName().toString();
                String resolvedFilename = resolvePlaceholder(filename, context);
                if (filename.equals(resolvedFilename)) return false;
                logger.debug("解析文件名[{}]", dir);
                Path target = dir.resolveSibling(resolvedFilename);
                if (Files.notExists(target)) {
                    if (isDirectory) Files.createDirectories(target);
                    else Files.createFile(target);
                }
                logger.debug("将源文件[{}]移动至解析后文件[{}]", dir, target);
                FileSystemUtils.copyRecursively(dir.toFile(), target.toFile());
                FileSystemUtils.deleteRecursively(dir.toFile());
                return true;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                logger.debug("解析文件[{}]", file);
                this.move(file, false);
                return super.visitFile(file, attrs);
            }
        });
    }

    static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(.*?)}");

    private static String resolvePlaceholder(String string, Map<String, Object> context) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(string);
        StringBuffer resolved = new StringBuffer();
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            logger.debug("取得占位符[{}]", placeholder);
            String actualValue = context.get(placeholder).toString();
            logger.debug("取得占位符[{}]对应的实际值[{}]", placeholder, actualValue);
            String replacement = actualValue.replaceAll("\\.", "/");
            matcher.appendReplacement(resolved, replacement);
        }
        matcher.appendTail(resolved);
        return resolved.toString();
    }

}
