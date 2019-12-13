package com.github.peacetrue.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;

import java.io.IOException;

/**
 * @author xiayx
 */
public class VelocityUtilsTest {

    @Test
    public void mergeAllClasspath() throws IOException {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("velocimacro.library.path", "template1/velocimacros.vtl");
        VelocityContext context = new VelocityContext();
        context.put("serviceApiDescription", "日落");
        VelocityUtils.mergeAllClasspath(velocityEngine, "template1", context, "/Users/xiayx/Documents/Projects/peacetrue-velocity/src/test/resources/templates2");
    }
}