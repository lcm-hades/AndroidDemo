package com.hades.Sample.act;

import java.util.List;

/**
 * Created by Hades on 2015/12/30.
 */
public class CityBean {

    /**
     * child : [{"id":"1","area_name":"东城"},{"id":"2","area_name":"西城"},{"id":"3","area_name":"崇文"},{"id":"4","area_name":"宣武"},{"id":"5","area_name":"朝阳"},{"id":"6","area_name":"丰台"},{"id":"7","area_name":"石景山"},{"id":"8","area_name":"海淀"},{"id":"9","area_name":"门头沟"},{"id":"10","area_name":"房山"},{"id":"11","area_name":"通州"},{"id":"12","area_name":"顺义"},{"id":"13","area_name":"昌平"},{"id":"14","area_name":"大兴"},{"id":"15","area_name":"怀柔"},{"id":"16","area_name":"平谷"},{"id":"17","area_name":"密云"},{"id":"18","area_name":"延庆"}]
     * id : 1
     * city_name : 北京
     * code : 010
     */

    private String id;
    private String city_name;
    private String code;
    /**
     * id : 1
     * area_name : 东城
     */

    private List<ChildEntity> child;

    public void setId(String id) {
        this.id = id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setChild(List<ChildEntity> child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCode() {
        return code;
    }

    public List<ChildEntity> getChild() {
        return child;
    }

    public static class ChildEntity {
        private String id;
        private String area_name;

        public void setId(String id) {
            this.id = id;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getId() {
            return id;
        }

        public String getArea_name() {
            return area_name;
        }
    }
}
