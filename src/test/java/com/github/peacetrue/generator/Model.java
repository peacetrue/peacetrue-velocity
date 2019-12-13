package com.github.peacetrue.generator;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 模型
 *
 * @author xiayx
 */
@Data
public class Model implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 名称，UpperCamel格式，例如：UpperCamel */
    private String name;
    /** 属性 */
    private List<ModelProperty> properties;
    /** 注释 */
    private String comment;

}
