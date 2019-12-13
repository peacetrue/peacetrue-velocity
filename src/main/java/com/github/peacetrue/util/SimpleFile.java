package com.github.peacetrue.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleFile implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 文件路径 */
    private String path;
    /** 是否目录 */
    private boolean isDirectory;

}
