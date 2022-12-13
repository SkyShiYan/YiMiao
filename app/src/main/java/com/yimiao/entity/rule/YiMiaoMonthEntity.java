package com.yimiao.entity.rule;

import java.util.Date;

import com.yimiao.entity.h.HEntity;
import com.yimiao.entity.yimiao.YiMiaoDoseEntity;

// 按照月份去控制打疫苗的时间
public class YiMiaoMonthEntity implements IYiMiaoRule {

    public int month;
	public HEntity h;

	@Override
	public Date startDay(YiMiaoDoseEntity lastDose) {
		long d = h.startDate.getTime() + month * 30 * 24 * 60 * 60 * 1000l;
		return new Date(d);
	}

	@Override
	public Date endDay(YiMiaoDoseEntity lastDose) {
		long d = h.startDate.getTime() + (month + 1) * 30 * 24 * 60 * 60 * 1000l;
		return new Date(d);
	}

	@Override
	public String toString() {
		return "YiMiaoMonthEntity{" +
				"month=" + month +
				", h=" + h +
				'}';
	}
}
