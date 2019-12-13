package ${basePackageName}.service;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class ${name}DynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final ${name} demo = new ${name}();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn id = demo.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> code = demo.code;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> name = demo.name;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn creatorId = demo.creatorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createdTime = demo.createdTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn modifierId = demo.modifierId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> modifiedTime = demo.modifiedTime;


    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class ${name} extends SqlTable {
        public final SqlColumn id = column("id");

        public final SqlColumn<String> code = column("code", JDBCType.VARCHAR);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn creatorId = column("creator_id", JDBCType.VARCHAR);

        public final SqlColumn<Date> createdTime = column("created_time", JDBCType.TIMESTAMP);

        public final SqlColumn modifierId = column("modifier_id", JDBCType.VARCHAR);

        public final SqlColumn<Date> modifiedTime = column("modified_time", JDBCType.TIMESTAMP);

        public ${name}() {
            super("demo");
        }
    }
}