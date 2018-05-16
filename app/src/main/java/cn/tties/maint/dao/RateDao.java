package cn.tties.maint.dao;

import cn.tties.maint.entity.RateEntity;

/**
 *
 */
public class RateDao extends AbstractDao<RateEntity> {
    private static RateDao dao;

    public static RateDao getInstance() {
        if (dao == null) {
            dao = new RateDao();
        }
        return dao;
    }

    public RateEntity queryByRateId(Integer rateId) {
        if (rateId == null) {
            return null;
        }
        RateEntity rateEntity = null;
        try {
            rateEntity = db.selector(RateEntity.class).where("rateId","=",rateId).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rateEntity;
    }
}
