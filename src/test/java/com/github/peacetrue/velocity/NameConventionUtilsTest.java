package com.github.peacetrue.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;

/**
 * @author xiayx
 */
public class NameConventionUtilsTest {

    @Test
    public void buildUpperCamel() {
        VelocityEngine velocityEngine = VelocityUtils.buildClasspathResourceLoader();
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(NameConventionUtils.build(), writer, "test", "$upperCamel.toLowerCamel('PersonName')");
        Assert.assertEquals("personName", writer.toString());
    }
}