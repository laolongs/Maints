package cn.tties.maint.dao;

import cn.tties.maint.entity.PatrolItemTypeEntity;

/**
 *
 */
public class PatrolItemTypeDao extends AbstractDao<PatrolItemTypeEntity> {
    private static PatrolItemTypeDao dao;

    public static PatrolItemTypeDao getInstance() {
        if (dao == null) {
            dao = new PatrolItemTypeDao();
        }
        return dao;
    }

     public PatrolItemTypeEntity queryByEquipIdAndItemTypeId(Integer equipmentId, Integer patrolItemTypeId) {
         PatrolItemTypeEntity entity = null;
        try {
            entity = db.selector(PatrolItemTypeEntity.class).where("patrolItemTypeId", "=", patrolItemTypeId).and("equipmentId", "=", equipmentId).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
