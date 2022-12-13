package com.yimiao.entity.yimiao;

import java.util.ArrayList;
import java.util.Date;

import com.yimiao.entity.rule.IYiMiaoRule;

// 疫苗的每一针信息
public class YiMiaoDoseEntity {
    // 当前针的名称
    public String name;
    // 当前针的开始时间、结束时间（为其他医院的规则做时间判定，不在这个时间范围之内的不行的）
    public Date startDate = null;
    public Date endDate = null;
    // 确定打针时间
    public Date confirmDate;
    public YiMiaoDoseEntity lastDose;
    public ArrayList<IYiMiaoRule> rules;

    public void makeDate() {
        IYiMiaoRule rule;
        for (int i = 0; i < rules.size(); i++) {
            rule = rules.get(i);
            if (startDate == null) {
                startDate = rule.startDay(lastDose);
            } else {
                Date getStartDate = rule.startDay(lastDose);
                if (startDate.before(getStartDate)) {
                    startDate = getStartDate;
                }
            }
            if (endDate == null) {
                endDate = rule.endDay(lastDose);
            } else {
                Date getEndDate = rule.endDay(lastDose);
                if (endDate.after(getEndDate)) {
                    endDate = getEndDate;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "YiMiaoDoseEntity{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", confirmDate=" + confirmDate +
                ", laseDose=" + (lastDose == null ? "空" : lastDose.toString()) +
                ", rules=" + rules.toString() +
                '}';
    }
}
