package com.github.peacetrue.generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型属性
 *
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelProperty {

    /** 名称 */
    private String name;
    /** 类型 */
    private Class<?> type;
    /** 注释 */
    private String comment;

}
