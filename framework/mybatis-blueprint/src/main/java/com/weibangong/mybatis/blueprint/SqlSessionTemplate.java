package com.weibangong.mybatis.blueprint;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by jianghailong on 16/2/27.
 */
@Slf4j
public class SqlSessionTemplate implements SqlSession {

    private final SqlSessionFactory sessionFactory;

    private SqlSession sessionProxy;

    public SqlSessionTemplate(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.sessionProxy = (SqlSession) Proxy.newProxyInstance(
                SqlSessionFactory.class.getClassLoader(),
                new Class[] { SqlSession.class },
                new SqlSessionInterceptor());
    }

    @Override
    public <T> T selectOne(String statement) {
        return sessionProxy.selectOne(statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return sessionProxy.selectOne(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return sessionProxy.selectList(statement);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return sessionProxy.selectList(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return sessionProxy.selectList(statement, parameter, rowBounds);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return sessionProxy.selectMap(statement, mapKey);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return sessionProxy.selectMap(statement, parameter, mapKey);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return sessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
    }

    @Override
    public void select(String statement, Object parameter, ResultHandler handler) {
        sessionProxy.select(statement, parameter, handler);
    }

    @Override
    public void select(String statement, ResultHandler handler) {
        sessionProxy.select(statement, handler);
    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        sessionProxy.select(statement, parameter, rowBounds, handler);
    }

    @Override
    public int insert(String statement) {
        return sessionProxy.insert(statement);
    }

    @Override
    public int insert(String statement, Object parameter) {
        return sessionProxy.insert(statement, parameter);
    }

    @Override
    public int update(String statement) {
        return sessionProxy.update(statement);
    }

    @Override
    public int update(String statement, Object parameter) {
        return sessionProxy.update(statement, parameter);
    }

    @Override
    public int delete(String statement) {
        return sessionProxy.delete(statement);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return sessionProxy.delete(statement, parameter);
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
    }

    @Override
    public void commit(boolean force) {
        throw new UnsupportedOperationException("Manual commit is not allowed over a managed SqlSession");
    }

    @Override
    public void rollback() {
        throw new UnsupportedOperationException("Manual commit is not allowed over a managed SqlSession");
    }

    @Override
    public void rollback(boolean force) {
        throw new UnsupportedOperationException("Manual commit is not allowed over a managed SqlSession");
    }

    @Override
    public List<BatchResult> flushStatements() {
        return sessionProxy.flushStatements();
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Manual commit is not allowed over a managed SqlSession");
    }

    @Override
    public void clearCache() {
        sessionProxy.clearCache();
    }

    @Override
    public Configuration getConfiguration() {
        return sessionProxy.getConfiguration();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }

    @Override
    public Connection getConnection() {
        return sessionProxy.getConnection();
    }

    private class SqlSessionInterceptor implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SqlSession session = sessionFactory.openSession();
            try  {
                Object result = method.invoke(session, args);
                session.commit(true);
                return result;
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
    }
}
