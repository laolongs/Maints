package cn.tties.maint.dao;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.entity.EquipmentItemEntity;

/**
 *
 */
public class EquipmentItemDao extends AbstractDao<EquipmentItemEntity> {
    private static EquipmentItemDao dao;

    public static EquipmentItemDao getInstance() {
        if (dao == null) {
            dao = new EquipmentItemDao();
        }
        return dao;
    }

    public List<EquipmentItemEntity> queryByEquipId(Integer equipId) {
        List<EquipmentItemEntity> list = null;
        try {
            list = db.selector(EquipmentItemEntity.class).where("equipmentId", "=", equipId)
                    .orderBy("itemOrder").findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<EquipmentItemEntity>() : list;
    }
}
