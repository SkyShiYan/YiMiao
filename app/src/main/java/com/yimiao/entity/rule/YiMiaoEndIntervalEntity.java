package com.yimiao.entity.rule;

import java.util.Date;

import com.yimiao.entity.h.HEntity;
import com.yimiao.entity.yimiao.YiMiaoDoseEntity;

// 根据上一次打针有最小间隔和最大间隔
public class YiMiaoEndIntervalEntity implements IYiMiaoRule {

    public Date startDate;
    // 间隔(天)
    public int interval;
    // 最久的时间（月）
    public int maxMonth;
    public HEntity h;

    @Override
    public Date startDay(YiMiaoDoseEntity lastDose) {
        if (lastDose == null) {
            if (startDate == null) {
                return new Date();
            } else {
                return startDate;
            }
        } else {
            if (lastDose.confirmDate == null) {
                if (startDate == null) {
                    return new Date();
                } else {
                    return startDate;
                }
            }
            return new Date(lastDose.confirmDate.getTime() + interval * 24 * 60 * 60 * 1000L);
        }
    }

    @Override
    public Date endDay(YiMiaoDoseEntity lastDose) {
        Date d = new Date(h.startDate.getTime() + (maxMonth + 1) * 30 * 24 * 60 * 60 * 1000L);
        return d;
    }
}
