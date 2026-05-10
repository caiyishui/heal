package com.demo.csjbot.csjsdkdemo.entity;

import java.util.List;

public class PersonListBean {

    /**
     * msg_id : FACE_DATABASE_RSP
     * data_list : [{"id":"asdw1","name":"李和亮"},{"id":"gfhdf2","name":"齐旭川"}]
     * list_num : 2
     * all_num : 2
     * page_num : 0
     */

    private String msg_id;
    private int list_num;
    private int all_num;
    private int page_num;
    private List<DataListBean> data_list;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public int getList_num() {
        return list_num;
    }

    public void setList_num(int list_num) {
        this.list_num = list_num;
    }

    public int getAll_num() {
        return all_num;
    }

    public void setAll_num(int all_num) {
        this.all_num = all_num;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public List<DataListBean> getData_list() {
        return data_list;
    }

    public void setData_list(List<DataListBean> data_list) {
        this.data_list = data_list;
    }

    public static class DataListBean {
        /**
         * id : asdw1
         * name : 李和亮
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
