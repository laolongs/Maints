package cn.tties.maint.dao;

import org.xutils.db.sqlite.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.entity.PatrolItemEntity;

/**
 *
 */
public class PatrolItemDao extends AbstractDao<PatrolItemEntity> {
    private static PatrolItemDao dao;

    public static PatrolItemDao getInstance() {
        if (dao == null) {
            dao = new PatrolItemDao();
        }
        return dao;
    }

    public PatrolItemEntity queryByPatrolItemId(Integer itemId) {
        PatrolItemEntity entity = null;
        try {
            WhereBuilder builder = WhereBuilder.b().and("patrolItemId", "=", itemId);
            entity = db.selector(PatrolItemEntity.class).where(builder).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public List<PatrolItemEntity> queryByEquipId(Integer equipId) {
        List<PatrolItemEntity> list =  new ArrayList<PatrolItemEntity>();
        try {
            WhereBuilder builder = WhereBuilder.b().and("equipmentId", "=", equipId);
            list = db.selector(PatrolItemEntity.class).where(builder).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<PatrolItemEntity>() : list;
    }

    public List<PatrolItemEntity> queryByEquipmentId(int equipmentId) {
        List<PatrolItemEntity> list = null;
        try {
            list = db.selector(PatrolItemEntity.class).where("equipmentId", "=", equipmentId).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<PatrolItemEntity>() : list;
    }
}
