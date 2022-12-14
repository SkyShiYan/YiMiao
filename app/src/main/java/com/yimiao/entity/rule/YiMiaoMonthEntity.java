package com.yimiao.entity.rule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.yimiao.entity.h.HEntity;
import com.yimiao.entity.yimiao.YiMiaoDoseEntity;

// 按照月份去控制打疫苗的时间
public class YiMiaoMonthEntity implements IYiMiaoRule {

    public int month;
	public HEntity h;

	@Override
	public Date startDay(YiMiaoDoseEntity lastDose) {
		SimpleDateFormat yformat = new SimpleDateFormat("yyyy", Locale.CHINA);
		SimpleDateFormat mformat = new SimpleDateFormat("MM", Locale.CHINA);
		SimpleDateFormat dformat = new SimpleDateFormat("dd", Locale.CHINA);
		int year = Integer.parseInt(yformat.format(h.startDate));
		int m = Integer.parseInt(mformat.format(h.startDate));

		m += month;

		if (m > 12) {
			year += 1;
			m -= 12;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		try {
			return format.parse("" + year + "-" + (m < 10 ? "0" : "") + m + "-" + dformat.format(h.startDate));
		} catch (ParseException e) {
			long d = h.startDate.getTime() + month * 30 * 24 * 60 * 60 * 1000L;
			return new Date(d);
		}
	}

	@Override
	public Date endDay(YiMiaoDoseEntity lastDose) {
		long d = h.startDate.getTime() + (month + 1) * 30 * 24 * 60 * 60 * 1000L;
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
