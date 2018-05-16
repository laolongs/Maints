package cn.tties.maint.dao;

import cn.tties.maint.entity.IndustryEntity;

/**
 *
 */
public class IndustryDao extends AbstractDao<IndustryEntity> {
    private static IndustryDao dao;

    public static IndustryDao getInstance() {
        if (dao == null) {
            dao = new IndustryDao();
        }
        return dao;
    }
}
