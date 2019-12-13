package ${basePackageName}.service;

import com.github.pagehelper.PageHelper;
import com.github.peacetrue.core.Range;
import com.github.peacetrue.mybatis.dynamic.MybatisDynamicUtils;
import com.github.peacetrue.pagehelper.PageHelperUtils;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.util.EntityNotFoundException;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ${basePackageName}.service.${name}DynamicSqlSupport.*;

/**
 * @author xiayx
 */
public class ${name}ServiceImpl implements ${name}Service {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${name}Mapper demoMapper;

    @Override
    public  ${name}VO add(${name}Add params) {
        logger.info("新增信息[{}]", params);
        ${name} demo = new ${name}<>();
        BeanUtils.copyProperties(params, demo);
        demo.setCreatorId(params.getOperatorId());
        demo.setCreatedTime(new Date());
        demo.setModifierId(params.getOperatorId());
        demo.setModifiedTime(demo.getCreatedTime());
        logger.debug("保存信息[{}]", demo);
        int count = demoMapper.insertSelective(demo);
        logger.debug("共影响[{}]条记录", count);
        return to(demo);
    }

    private  ${name}VO to(${name} demo) {
        ${name}VO vo = new ${name}VO<>();
        BeanUtils.copyProperties(demo, vo);
        return vo;
    }

    @Override
    public  Page<${name}VO> query(@Nullable ${name}Query params, @Nullable Pageable pageable) {
        logger.info("分页查询信息[{}]", params);
        if (params == null) params = ${name}Query.DEFAULT;
        if (params.getCreatedTime() == null) params.setCreatedTime(new Range.Date());
        if (pageable == null) pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "createdTime"));
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        List<${name}> entities = demoMapper.selectByExample()
                .where(code, SqlBuilder.isLikeWhenPresent(MybatisDynamicUtils.likeValue(params.getCode())))
                .and(name, SqlBuilder.isEqualToWhenPresent(params.getName()))
                .and(createdTime, SqlBuilder.isGreaterThanOrEqualToWhenPresent(params.getCreatedTime().getLowerBound()))
                .and(createdTime, SqlBuilder.isLessThanWhenPresent(MybatisDynamicUtils.endDateValue(params.getCreatedTime().getUpperBound())))
                .orderBy(MybatisDynamicUtils.orders(demo, pageable.getSort()))
                .build().execute();
        logger.debug("共取得'{}'条记录", entities.size());
        if (entities.isEmpty()) return new PageImpl<>(Collections.emptyList());

        List<${name}VO> vos = entities.stream().map(this::to).collect(Collectors.toList());
        return new PageImpl<>(vos, pageable, PageHelperUtils.getTotal(entities));
    }

    @Override
    @SuppressWarnings("unchecked")
    public  ${name}VO get(${name}Get params) {
        logger.info("获取符合条件[{}]的信息", params);
        return demoMapper.selectByExample()
                .where((SqlColumn<Object>) id, SqlBuilder.isEqualTo(params.getId()))
                .build().execute().stream()
                .reduce((l, r) -> r)
                .map(this::to)
                .orElseThrow(() -> new EntityNotFoundException(${name}.class, "id", params.getId()));
    }

    @Override
    public  int modify(${name}Modify params) {
        logger.info("修改信息[{}]", params);
        ${name} demo = new ${name}<>();
        BeanUtils.copyProperties(params, demo);
        demo.setModifierId(params.getOperatorId());
        demo.setModifiedTime(new Date());
        int count = demoMapper.updateByPrimaryKeySelective(demo);
        logger.debug("共影响[{}]条记录", count);
        return count;
    }

    @Override
    public  int delete(${name}Delete params) {
        logger.info("删除信息[{}]", params);
        int count = params.getId().length == 1
                ? demoMapper.deleteByPrimaryKey(params.getId()[0])
                : demoMapper.deleteInPrimaryKey(Arrays.asList(params.getId()));
        logger.debug("共影响[{}]条记录", count);
        return count;
    }
}
