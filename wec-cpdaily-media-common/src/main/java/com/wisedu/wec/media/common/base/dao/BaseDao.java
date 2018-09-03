package com.wisedu.wec.media.common.base.dao;


import com.wisedu.wec.media.common.exception.DaoException;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * B-bean
 * E-example
 * K-key
 * M-mapper
 * <p>
 * Created by huhaichao on 2017/6/8.
 */
@Repository
public abstract class BaseDao<B, E, K extends Serializable, M> {
    public abstract M getMapper();

    public long countByExample(E example) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method countByExample;
        try {
            countByExample = clazz.getDeclaredMethod("countByExample", example.getClass());
            return (Long) countByExample.invoke(mapper, example);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int deleteByExample(E example) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method deleteByPrimaryKey;
        try {
            deleteByPrimaryKey = clazz.getDeclaredMethod("deleteByExample", example.getClass());
            return (Integer) deleteByPrimaryKey.invoke(mapper, example);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int deleteByPrimaryKey(K id) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method deleteByPrimaryKey;
        try {
            deleteByPrimaryKey = clazz.getDeclaredMethod("deleteByPrimaryKey", id.getClass());
            return (Integer) deleteByPrimaryKey.invoke(mapper, id);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int insert(B bean) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method insert;
        try {
            insert = clazz.getDeclaredMethod("insert", bean.getClass());
            return (Integer) insert.invoke(mapper, bean);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int insertSelective(B bean) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method insertSelective;
        try {
            insertSelective = clazz.getDeclaredMethod("insertSelective", bean.getClass());
            return (Integer) insertSelective.invoke(mapper, bean);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public List<B> selectByExample(E example) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method selectByExample;
        try {
            selectByExample = clazz.getDeclaredMethod("selectByExample", example.getClass());
            Object o = selectByExample.invoke(mapper, example);
            return o == null ? null : (List<B>) o;
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public B getByExample(E example) {
        List<B> list = selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public B selectByPrimaryKey(K id) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method selectByPrimaryKey;
        try {
            selectByPrimaryKey = clazz.getDeclaredMethod("selectByPrimaryKey", id.getClass());
            return (B) selectByPrimaryKey.invoke(mapper, id);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int updateByExampleSelective(B bean, E example) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method updateByExampleSelective;
        try {
            updateByExampleSelective = clazz.getDeclaredMethod("updateByExampleSelective", bean.getClass(), example.getClass());
            return (Integer) updateByExampleSelective.invoke(mapper, bean, example);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int updateByExample(B bean, E example) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method updateByExample;
        try {
            updateByExample = clazz.getDeclaredMethod("updateByExample", bean.getClass(), example.getClass());
            return (Integer) updateByExample.invoke(mapper, bean, example);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int updateByPrimaryKeySelective(B bean) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method updateByPrimaryKeySelective;
        try {
            updateByPrimaryKeySelective = clazz.getDeclaredMethod("updateByPrimaryKeySelective", bean.getClass());
            return (Integer) updateByPrimaryKeySelective.invoke(mapper, bean);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }

    public int updateByPrimaryKey(B bean) {
        M mapper = getMapper();
        Class<M> clazz = (Class<M>) mapper.getClass();
        Method updateByPrimaryKey;
        try {
            updateByPrimaryKey = clazz.getDeclaredMethod("updateByPrimaryKey", bean.getClass());
            return (Integer) updateByPrimaryKey.invoke(mapper, bean);
        } catch (ReflectiveOperationException e) {
            throw new DaoException("数据读写异常", e);
        }
    }
}
