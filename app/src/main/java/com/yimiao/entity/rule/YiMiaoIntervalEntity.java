package com.yimiao.entity.rule;

import java.util.Date;

import com.yimiao.entity.h.HEntity;
import com.yimiao.entity.yimiao.YiMiaoDoseEntity;

// 根据上一次打针有最小间隔和最大间隔
public class YiMiaoIntervalEntity implements IYiMiaoRule {

    // 最小间隔时间、最大间隔时间（天）
    public int minInterval;
    public int maxInterval;
    public HEntity h;

    @Override
    public Date startDay(YiMiaoDoseEntity lastDose) {
        if (lastDose == null || lastDose.confirmDate == null) {
            return new Date();
        }
        return new Date(lastDose.confirmDate.getTime() + minInterval * 24 * 60 * 60 * 1000L);
    }

    @Override
    public Date endDay(YiMiaoDoseEntity lastDose) {
        Date endDate;
        if (lastDose == null || lastDose.confirmDate == null) {
            endDate = startDay(lastDose);
        } else {
            endDate = lastDose.confirmDate;
        }
        return new Date(endDate.getTime() + maxInterval * 24 * 60 * 60 * 1000L);
    }

    @Override
    public String toString() {
        return "YiMiaoIntervalEntity{" +
                "minInterval=" + minInterval +
                ", maxInterval=" + maxInterval +
                ", h=" + h +
                '}';
    }
}
