package com.aihs.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class FooServiceImpl implements FooService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FooService fooService;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO foo(bar) VALUES ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class,propagation = Propagation.NESTED)
    public void insertThenRollBack() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO foo(bar) VALUES ('BBB')");
        throw new RollbackException();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void invokeInsertThenRollBack() throws RollbackException{
        jdbcTemplate.execute("INSERT INTO foo(bar) VALUES ('AAA')");
        try{
            fooService.insertThenRollBack();
        }catch (RollbackException e){
            log.error("RollbackEception:",e);
        }
//        throw new RuntimeException();
    }
}
