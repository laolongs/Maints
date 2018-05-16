package cn.tties.maint.dao;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.entity.OverhaulItemEntity;

/**
 *
 */
public class OverhaulItemDao extends AbstractDao<OverhaulItemEntity> {
    private static OverhaulItemDao dao;

    public static OverhaulItemDao getInstance() {
        if (dao == null) {
            dao = new OverhaulItemDao();
        }
        return dao;
    }
    public List<OverhaulItemEntity> queryByType(Integer type) {
        List<OverhaulItemEntity> list = null;
        try {
            list = db.selector(OverhaulItemEntity.class).where("itemType", "=", type).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<OverhaulItemEntity>() : list;
    }
}
