package ${basePackageName}.controller;

import ${basePackageName}.service.*;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


/**
 * @author xiayx
 */
@RequestMapping(value = "${peacetrue.demo.urls.base-path}")
@SuppressWarnings("unchecked")
public class ${name}Controller {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ${name}Service demoService;

    @ResponseBody
    @PostMapping(value = "${peacetrue.demo.urls.add}")
    public ${name}VO add(${name}Add params) {
        logger.info("新增信息[{}]", params);
        return demoService.add(BeanUtils.map(params, ${name}Add.class));
    }

    @ResponseBody
    @GetMapping(value = "${peacetrue.demo.urls.query}", params = "page")
    public Page<${name}VO<Object, Object>> query(${name}Query params,
                                              @PageableDefault(sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("分页查询信息[{}]", params);
        return demoService.query(BeanUtils.map(params, ${name}Query.class), pageable);
    }

    @ResponseBody
    @GetMapping(value = "${peacetrue.demo.urls.get}", params = {"!page"})
    public ${name}VO get(${name}Get params) {
        logger.info("获取信息[{}]详情", params);
        return demoService.get(BeanUtils.map(params, ${name}Get.class));
    }

    @ResponseBody
    @PutMapping(value = "${peacetrue.demo.urls.modify}")
    public int modify(${name}Modify params) {
        logger.info("修改信息[{}]", params);
        return demoService.modify(BeanUtils.map(params, ${name}Modify.class));
    }

    @ResponseBody
    @DeleteMapping(value = "${peacetrue.demo.urls.delete}")
    public int delete(${name}Delete params) {
        logger.info("删除信息[{}]", params);
        return demoService.delete(BeanUtils.map(params, ${name}Delete.class));
    }


}
