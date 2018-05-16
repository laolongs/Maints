package cn.tties.maint.dao;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.MyApplication;
import cn.tties.maint.entity.AbstractEntity;

/**
 *
 */
public abstract class AbstractDao<T extends AbstractEntity> {

    public static DbManager db = x.getDb(MyApplication.getDaoConfig());

    public void dropTable() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            db.dropTable(entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T queryById(Integer id) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T entity = null;
        try {
            entity = db.selector(entityClass).where("id", "=", id).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }


    public <T> List<T> queryAll() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        List list = null;
        try {
            list = db.selector(entityClass).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = list == null ? new ArrayList<T>() : list;
        return list;
    }

    public Boolean save(AbstractEntity entity) {
        try {
            db.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean save(List<T> entity) {
        try {
            db.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean saveBindingId(AbstractEntity entity) {
        try {
            db.saveBindingId(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean saveBindingId(List<T> entity) {
        try {
            db.saveBindingId(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean update(AbstractEntity entity) {
        try {
            db.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean update(List<T> entity) {
        try {
            db.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean del(AbstractEntity entity) {
        try {
            db.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean del(List<AbstractEntity> entity) {
        try {
            db.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void saveOrUpdate(T entity) throws DbException {
        db.saveOrUpdate(entity);
    }

    public Boolean saveOrUpdate(List<T> entity) {
        try {
            db.saveOrUpdate(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
