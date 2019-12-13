package ${basePackageName}.service;

import com.github.peacetrue.spring.util.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestService${name}AutoConfiguration.class)
@Transactional
public class ${name}ServiceImplTest {

    @Autowired
    private ${name}Service demoService;

    public static final ${name}Add<Long> DEMO_ADD = new ${name}Add<>();

    static {
        DEMO_ADD.setCode("2");
        DEMO_ADD.setName("2");
        DEMO_ADD.setOperatorId(1L);
    }

    @Test
    public void add() {
        ${name}VO<Long, Long> vo = demoService.add(DEMO_ADD);
        Assert.assertEquals(vo, demoService.<Long, Long>get(new ${name}Get<>(vo.getId())));
    }

    @Test
    public void query() {
        Page<${name}VO<Long, Long>> vos = demoService.query(new ${name}Query(), new PageRequest(0, 1));
        Assert.assertEquals(vos.getTotalElements(), 1);
    }

    @Test
    public void get() {
        ${name}VO<Long, Long> vo = demoService.get(new ${name}Get<>(1L));
        Assert.assertNotNull(vo);
    }

    @Test
    public void modify() {
        ${name}VO<Long, Long> vo = demoService.get(new ${name}Get<>(1L));
        ${name}Modify<Long, Long> modify = new ${name}Modify<>();
        BeanUtils.copyProperties(vo, modify);
        modify.setCode("2");
        int count = demoService.modify(modify);
        Assert.assertEquals(count, 1);
        vo = demoService.get(new ${name}Get<>(1L));
        Assert.assertEquals(modify.getCode(), vo.getCode());
    }

    @Test
    public void delete() {
        int count = demoService.delete(new ${name}Delete<>(new Long[]{1L}));
        Assert.assertEquals(count, 1);
    }
}