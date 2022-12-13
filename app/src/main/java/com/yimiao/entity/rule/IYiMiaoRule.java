package com.yimiao.entity.rule;

import java.util.Date;

import com.yimiao.entity.yimiao.YiMiaoDoseEntity;

public interface IYiMiaoRule {
    Date startDay(YiMiaoDoseEntity lastDose);
    Date endDay(YiMiaoDoseEntity lastDose);
}
