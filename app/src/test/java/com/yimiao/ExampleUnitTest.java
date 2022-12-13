package com.yimiao;

import org.junit.Test;

import static org.junit.Assert.*;

import com.yimiao.entity.h.HEntity;
import com.yimiao.entity.rule.IYiMiaoRule;
import com.yimiao.entity.rule.YiMiaoEndIntervalEntity;
import com.yimiao.entity.rule.YiMiaoIntervalEntity;
import com.yimiao.entity.rule.YiMiaoMonthEntity;
import com.yimiao.entity.yimiao.YiMiaoDoseEntity;
import com.yimiao.entity.yimiao.YiMiaoEntity;
import com.yimiao.entity.yiyuan.YiYuanEntity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testYiMiaoFunc() {
        HEntity h = new HEntity();
        ArrayList<YiMiaoEntity> yiMiaoList = getYiMiaoList(h);
        YiYuanEntity yEntity = new YiYuanEntity();
        ArrayList<String> msgList = yEntity.getMakeTime(yiMiaoList);
        if (msgList != null) {
            for (int i = 0; i < msgList.size(); i++) {
                System.out.println(msgList.get(i));
            }
        }
    }

    private ArrayList<YiMiaoEntity> getYiMiaoList(HEntity h) {
        YiMiaoEntity entity;
        ArrayList<YiMiaoDoseEntity> doseEntities;
        YiMiaoDoseEntity doseEntity;
        ArrayList<IYiMiaoRule> rules;
        YiMiaoMonthEntity mRule;
        ArrayList<YiMiaoEntity> data = new ArrayList<>();

        // 创建疫苗的对象
        // 五阶轮状
        entity = new YiMiaoEntity();
        entity.name = "五阶轮状";
        entity.desc = "一共3针";
        doseEntities = new ArrayList<>();
        // 第二针
        doseEntity = new YiMiaoDoseEntity();
        doseEntity.name = "五阶轮状-第二针";
        rules = new ArrayList<>();
        YiMiaoIntervalEntity rule = new YiMiaoIntervalEntity();
        rule.minInterval = 28;
        rule.maxInterval = 70;
        rules.add(rule);
        doseEntity.rules = rules;
        doseEntities.add(doseEntity);
        // 第三针
        doseEntity = new YiMiaoDoseEntity();
        doseEntity.name = "五阶轮状-第三针";
        rules = new ArrayList<>();
        rule = new YiMiaoIntervalEntity();
        rule.minInterval = 28;
        rule.maxInterval = 70;
        rules.add(rule);
        doseEntity.rules = rules;
        doseEntities.add(doseEntity);
        entity.doseEntities = doseEntities;
        data.add(entity);

        // 五联
        entity = new YiMiaoEntity();
        entity.name = "五联";
        entity.desc = "一共4针";
        doseEntities = new ArrayList<>();
        // 第二针
        doseEntity = new YiMiaoDoseEntity();
        doseEntity.name = "五联-第二针";
        rules = new ArrayList<>();
        mRule = new YiMiaoMonthEntity();
        mRule.h = h;
        mRule.month = 3;
        rules.add(rule);
        doseEntity.rules = rules;
        doseEntities.add(doseEntity);
        // 第三针
        doseEntity = new YiMiaoDoseEntity();
        doseEntity.name = "五联-第三针";
        rules = new ArrayList<>();
        mRule = new YiMiaoMonthEntity();
        mRule.h = h;
        mRule.month = 4;
        rules.add(rule);
        doseEntity.rules = rules;
        doseEntities.add(doseEntity);
        // 第四针
//        doseEntity = new YiMiaoDoseEntity();
//        doseEntity.name = "五联-第四针";
//        rules = new ArrayList<>();
//        mRule = new YiMiaoMonthEntity();
//        mRule.h = h;
//        mRule.month = 18;
//        rules.add(rule);
//        doseEntity.rules = rules;
//        doseEntities.add(doseEntity);
        entity.doseEntities = doseEntities;
        data.add(entity);

        // 13阶肺炎
        entity = new YiMiaoEntity();
        entity.name = "13阶肺炎";
        entity.desc = "一共4针";
        doseEntities = new ArrayList<>();
        // 第二针
        doseEntity = new YiMiaoDoseEntity();
        doseEntity.name = "13阶肺炎-第二针";
        rules = new ArrayList<>();
        YiMiaoEndIntervalEntity endIntervalEntity = new YiMiaoEndIntervalEntity();
        endIntervalEntity.startDate = new Date(1672502400000L);
        endIntervalEntity.interval = 30;
        endIntervalEntity.maxMonth = 6;
        endIntervalEntity.h = h;
        rules.add(endIntervalEntity);
        doseEntity.rules = rules;
        doseEntities.add(doseEntity);
        // 第三针
        doseEntity = new YiMiaoDoseEntity();
        doseEntity.name = "13阶肺炎-第三针";
        endIntervalEntity = new YiMiaoEndIntervalEntity();
        endIntervalEntity.interval = 30;
        endIntervalEntity.maxMonth = 6;
        endIntervalEntity.h = h;
        rules.add(endIntervalEntity);
        doseEntity.rules = rules;
        doseEntities.add(doseEntity);
        // 第四针
//        doseEntity = new YiMiaoDoseEntity();
//        doseEntity.name = "13阶肺炎-第四针";
//        rules = new ArrayList<>();
//        mRule = new YiMiaoMonthEntity();
//        mRule.h = h;
//        mRule.month = 12;
//        rules.add(mRule);
//        doseEntity.rules = rules;
//        doseEntities.add(doseEntity);
        entity.doseEntities = doseEntities;
        data.add(entity);
        return data;
    }
}