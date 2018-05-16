package cn.tties.maint.dao;

import org.xutils.db.sqlite.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.entity.AreaEntity;

/**
 *
 */
public class AreaDao extends AbstractDao<AreaEntity> {
    private static AreaDao dao;

    public static AreaDao getInstance() {
        if (dao == null) {
            dao = new AreaDao();
        }
        return dao;
    }

    public List<AreaEntity> queryProvince() {
        List<AreaEntity> list =  new ArrayList<AreaEntity>();
        try {
            WhereBuilder builder = WhereBuilder.b().and("pid", "=", null);
            list = db.selector(AreaEntity.class).where(builder).orderBy("areaOrder asc").findAll();
            //TODO
            List<AreaEntity> listnew = new ArrayList<>();
            for (AreaEntity entity : list) {
                if (entity.getAreaName().equals("浙江省")) {
                    listnew.add(entity);
                    return listnew;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list == null ? new ArrayList<AreaEntity>() : list;
    }
}
