package com.yimiao.entity.yimiao;

import java.util.ArrayList;

public class YiMiaoEntity {

    // 疫苗名字
    public String name;
    // 疫苗描述
    public String desc;
    // 疫苗所有的针次
    public ArrayList<YiMiaoDoseEntity> doseEntities;
    // 当前疫苗所有针次的时间
    public String msg;

    @Override
    public String toString() {
        return "YiMiaoEntity{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", doseEntities=" + doseEntities.toString() +
                ", msg='" + msg + '\'' +
                '}';
    }
}
