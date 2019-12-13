package com.github.peacetrue.generator;

import java.io.IOException;
import java.util.Map;

/**
 * @author xiayx
 */
public interface Generator {
    void generate(String absoluteDistPath, Map<String, Object> context) throws IOException;
}
