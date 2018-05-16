package cn.tties.maint.dao;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.EquipmentItemEntity;

/**
 *
 */
public class EquipmentDao extends AbstractDao<EquipmentEntity> {

    private static EquipmentDao dao;

    public static EquipmentDao getInstance() {
        if (dao == null) {
            dao = new EquipmentDao();
        }
        return dao;
    }

    public List<EquipmentEntity> queryByPid(Integer pid) {
        List<EquipmentEntity> list = null;
        try {
            list = db.selector(EquipmentEntity.class).where("pid", "=", pid).orderBy("equipmentOrder", false).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<EquipmentEntity>() : list;
    }

    public List<EquipmentEntity> queryByLevel(int level) {
        List<EquipmentEntity> list = null;
        try {
            list = db.selector(EquipmentEntity.class).where("equipmentLevel", "=", level).orderBy("equipmentOrder", false).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<EquipmentEntity>() : list;
    }

    public List<EquipmentEntity> queryByPidAndCequipId(Integer pid) {
        List<EquipmentEntity> list = null;
        try {
            list = db.selector(EquipmentEntity.class).where("pid", "=", pid).orderBy("equipmentOrder", false).findAll();
            list = list == null ? new ArrayList<EquipmentEntity>() : list;
            EquipmentItemDao dao = new EquipmentItemDao();
            for (EquipmentEntity entity: list) {
                List<EquipmentItemEntity> clist = dao.queryByEquipId(entity.getEquipmentId());
                clist = clist == null ? new ArrayList<EquipmentItemEntity>() : clist;
                entity.setItemList(clist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public EquipmentEntity queryById(Integer id) {
        List<EquipmentEntity> list = null;
        try {
            list = db.selector(EquipmentEntity.class).where("equipmentId", "=", id).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new EquipmentEntity() : list.get(0);
    }
}
