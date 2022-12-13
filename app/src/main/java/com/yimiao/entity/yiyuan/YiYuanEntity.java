package com.yimiao.entity.yiyuan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.yimiao.entity.h.HEntity;
import com.yimiao.entity.yimiao.YiMiaoDoseEntity;
import com.yimiao.entity.yimiao.YiMiaoEntity;

public class YiYuanEntity {

    // 每周4， 5， 6
    public final int[] rules = {4, 5, 6};
    public final Date[] dateRules = {new Date(1674230400000L), new Date(1674316800000L), new Date(1674403200000L),
            new Date(1674489600000L), new Date(1674576000000L), new Date(1674662400000L), new Date(1674748800000L),
            new Date(1674835200000L)};
    // 打一次之后需要间隔14天
    public final int nextTime = 14;
    private ArrayList<List<YiMiaoEntity>> res = new ArrayList<>();

    public ArrayList<String> getMakeTime(ArrayList<YiMiaoEntity> yiMiaoList) {
        getList(null, copy(yiMiaoList));
        System.out.println("xxxxx-------" + res.size());

        YiMiaoEntity yiMiao;
        ArrayList<String> data = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        for (int k = 0; k < res.size(); k++) {
            if (!isDoseEmpty(res.get(k))) {
                StringBuilder sb = new StringBuilder();

                ArrayList<YiMiaoDoseEntity> sortList = new ArrayList<>();

                for (int i = 0; i < res.get(k).size(); i++) {
                    // 获取疫苗
                    yiMiao = res.get(k).get(i);
//                    sb.append(yiMiao.name);
//                    sb.append("打针时间：");
                    if (yiMiao.doseEntities != null && yiMiao.doseEntities.size() > 0) {
                        sortList.addAll(yiMiao.doseEntities);
                        for (int j = 0; j < yiMiao.doseEntities.size(); j++) {
//                            sb.append(yiMiao.doseEntities.get(j).name);
//                            sb.append(" 时间：");
                            if (yiMiao.doseEntities.get(j).confirmDate != null) {
//                                sb.append(format.format(yiMiao.doseEntities.get(j).confirmDate));
//                                sb.append("  ");
                            }
                        }
                    }
//                    sb.append("\n");
                }

                Collections.sort(sortList, (t1, t2) -> {
                    if (t1.confirmDate.getTime() == t2.confirmDate.getTime()) {
                        return 0;
                    }
                    return t1.confirmDate.after(t2.confirmDate) ? 1 : -1;
                });


                for (int i = 0; i < sortList.size(); i++) {
                    sb.append(sortList.get(i).name);
                    sb.append(" 时间：");
                    sb.append(format.format(sortList.get(i).confirmDate));
                    sb.append("  ");
                }
                sb.append("\n");
                data.add(sb.toString());
            }
        }

        return data;
    }

    private List<YiMiaoEntity> getList(Date startDate, List<YiMiaoEntity> yiMiaoListCopy) {
        if (startDate != null && startDate.getTime() > 1735488000000L) {
            return null;
        }

        // 第一批
        Date date_1 = getNextAppointmentDate(startDate);

        YiMiaoEntity yiMiao;
        YiMiaoDoseEntity dose;
        boolean isErr = false;
        // 可用的针次
        ArrayList<YiMiaoDoseEntity> doseList = new ArrayList<>();
        for (int i = 0; i < yiMiaoListCopy.size(); i++) {
            if (isErr) {
                break;
            }

            yiMiao = yiMiaoListCopy.get(i);
            if (yiMiao.doseEntities != null && yiMiao.doseEntities.size() > 0) {
                for (int j = 0; j < yiMiao.doseEntities.size(); j++) {
                    dose = yiMiao.doseEntities.get(j);
                    if (dose.confirmDate == null) {
                        if (j > 0) {
                            dose.lastDose = yiMiao.doseEntities.get(j - 1);
                        }
                        dose.makeDate();
                        if (dose.endDate.before(date_1)) {
                            isErr = true;
                            break;
                        }
                        if (dose.startDate.after(date_1)) {
                            break;
                        }
                        doseList.add(dose);
                        break;
                    }
                }
            }
        }

        if (isErr) {

//            System.out.println("sssss-----ErrReturn：-" + yiMiaoList.toString());
            return null;
        } else {
            // 给每一个可以打的疫苗都增加一个可能性
            for (int i = 0; i < doseList.size(); i++) {
                if (i > 0) {
                    for (int j = 0; j < i; j++) {
                        doseList.get(j).confirmDate = null;
                    }
                }
                // 当前可用的时间给当前的列表
                doseList.get(i).confirmDate = date_1;

                if (!isDoseEmpty(yiMiaoListCopy)) {
                    // 有成功的数据
//                    System.out.println("sssss-----正确返回：-" + yiMiaoListCopy.toString());
                    this.res.add(copy(yiMiaoListCopy));
                    continue;
                }
                List<YiMiaoEntity> yiMiaoListCopy_1 = copy(yiMiaoListCopy);
                getList(date_1, yiMiaoListCopy_1);
            }

            // 当前选择的时间也可以不大
            for (int i = 0; i < doseList.size(); i++) {
                doseList.get(i).confirmDate = null;
            }
            List<YiMiaoEntity> yiMiaoListCopy_1 = copy(yiMiaoListCopy);
            getList(date_1, yiMiaoListCopy_1);
        }
        return null;
    }

    private boolean isDoseEmpty(List<YiMiaoEntity> yiMiaoList) {
        YiMiaoEntity yiMiao;
        YiMiaoDoseEntity dose;
        for (int i = 0; i < yiMiaoList.size(); i++) {
            // 获取疫苗
            yiMiao = yiMiaoList.get(i);
            if (yiMiao.doseEntities != null && yiMiao.doseEntities.size() > 0) {
                for (int j = 0; j < yiMiao.doseEntities.size(); j++) {
                    dose = yiMiao.doseEntities.get(j);
                    // 已经设置过打疫苗时间，需要查找下一个
                    if (dose.confirmDate == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Date getNextAppointmentDate(Date currentAppointDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        HashMap<Integer, Integer> ruleMap = new HashMap<>();
        for (int i = 0; i < rules.length; i++) {
            ruleMap.put(rules[i], rules[i]);
        }
        HashMap<Long, Date> dateRuleMap = new HashMap<>();
        for (int i = 0; i < dateRules.length; i++) {
            dateRuleMap.put(dateRules[i].getTime(), dateRules[i]);
        }
        if (currentAppointDate == null) {
            // 当前时间最近的周四、周五、周六
            Date d = new Date();
            try {
                d = format.parse(format.format(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int w = getWeek(d);
            int index = 100;
            while ((dateRuleMap.containsKey(d.getTime()) || !ruleMap.containsKey(w)) && index > 0) {
                d = new Date(d.getTime() + 24 * 60 * 60 * 1000l);
                w = getWeek(d);
                index--;
            }
            if (index == 0) {
                return null;
            }

            return d;
        } else {
            Date d = new Date();
            try {
                d = format.parse(format.format(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            while (d.getTime() < currentAppointDate.getTime() + nextTime * 24 * 60 * 60 * 1000l) {
                d = new Date(d.getTime() + 24 * 60 * 60 * 1000l);
            }

            int w = getWeek(d);
            int index = 100;
            while ((dateRuleMap.containsKey(d.getTime()) || !ruleMap.containsKey(w)) && index > 0) {
                d = new Date(d.getTime() + 24 * 60 * 60 * 1000l);
                w = getWeek(d);
                index--;
            }
            if (index == 0) {
                return null;
            }

            return d;
        }
    }

    private int getWeek(Date date) {
        // String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        int[] weeks = {0, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    private List<YiMiaoEntity> copy(List<YiMiaoEntity> yiMiaoList) {
        if (yiMiaoList == null) {
            return null;
        }
        List<YiMiaoEntity> copy = new ArrayList<>();
        for (int i = 0; i < yiMiaoList.size(); i++) {
            YiMiaoEntity item = yiMiaoList.get(i);
            YiMiaoEntity itemCopy = new YiMiaoEntity();
            itemCopy.name = item.name;
            itemCopy.desc = item.desc;
            itemCopy.msg = item.msg;

            if (item.doseEntities != null) {
                itemCopy.doseEntities = new ArrayList<>();
                for (int j = 0; j < item.doseEntities.size(); j++) {
                    YiMiaoDoseEntity itemItem = item.doseEntities.get(j);
                    YiMiaoDoseEntity itemItemCopy = new YiMiaoDoseEntity();
                    itemItemCopy.name = itemItem.name;
                    itemItemCopy.startDate = itemItem.startDate;
                    itemItemCopy.endDate = itemItem.endDate;
                    itemItemCopy.confirmDate = itemItem.confirmDate;
                    if (j > 0 && itemItemCopy.lastDose == null) {
                        itemItemCopy.lastDose = itemCopy.doseEntities.get(itemCopy.doseEntities.size() - 1);
                    }
                    itemItemCopy.rules = itemItem.rules;
                    itemCopy.doseEntities.add(itemItemCopy);
                }
            }
            copy.add(itemCopy);
        }
        return copy;
    }
}
