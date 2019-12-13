package com.github.peacetrue.generator;

import com.github.peacetrue.util.FolderResolver;
import com.github.peacetrue.velocity.NameConventionUtils;
import com.github.peacetrue.velocity.VelocityUtils;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.util.Map;

/**
 * @author xiayx
 */
@Setter
public class GeneratorImpl implements Generator {

    private VelocityEngine velocityEngine;
    private String templateFolderPath;

    @Override
    public void generate(String absoluteDistPath, Map<String, Object> context) throws IOException {
        Context velocityContext = NameConventionUtils.build();
        context.forEach(velocityContext::put);
        VelocityUtils.mergeAllClasspath(velocityEngine, templateFolderPath,
                path -> !(path.startsWith("{projectName}-service-api")
                        || path.startsWith("{projectName}-service-mybatis")
                        || path.endsWith("settings.gradle.vm"))
                        || path.endsWith("Placeholder.adoc"),
                VelocityUtils.MERGE_CONVENTION, velocityContext,
                absoluteDistPath, VelocityUtils.CONVERTER_CONVENTION);
        FolderResolver.resolve(absoluteDistPath, context);
    }
}
