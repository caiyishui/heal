package com.demo.csjbot.csjsdkdemo.entity;

import java.util.List;

public class PersonDetectBean {


    /**
     * msg_id : FACE_DETECT_FACE_LIST_NTF
     * face_num : 1
     * face_list : [{"face_detect":{"age":18,"gender":0,"smile":60},"face_recg":{"confidence":18,"name":"tt","person_id":"personid15952353708500-156"}}]
     */

    private String msg_id;
    private int face_num;
    private List<FaceListBean> face_list;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public int getFace_num() {
        return face_num;
    }

    public void setFace_num(int face_num) {
        this.face_num = face_num;
    }

    public List<FaceListBean> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListBean> face_list) {
        this.face_list = face_list;
    }

    public static class FaceListBean {
        /**
         * face_detect : {"age":18,"gender":0,"smile":60}
         * face_recg : {"confidence":18,"name":"tt","person_id":"personid15952353708500-156"}
         */

        private FaceDetectBean face_detect;
        private FaceRecgBean face_recg;

        public FaceDetectBean getFace_detect() {
            return face_detect;
        }

        public void setFace_detect(FaceDetectBean face_detect) {
            this.face_detect = face_detect;
        }

        public FaceRecgBean getFace_recg() {
            return face_recg;
        }

        public void setFace_recg(FaceRecgBean face_recg) {
            this.face_recg = face_recg;
        }

        public static class FaceDetectBean {
            /**
             * age : 18
             * gender : 0
             * smile : 60
             */

            private int age;
            private int gender;
            private int smile;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getSmile() {
                return smile;
            }

            public void setSmile(int smile) {
                this.smile = smile;
            }
        }

        public static class FaceRecgBean {
            /**
             * confidence : 18
             * name : tt
             * person_id : personid15952353708500-156
             */

            private int confidence;
            private String name;
            private String person_id;

            public int getConfidence() {
                return confidence;
            }

            public void setConfidence(int confidence) {
                this.confidence = confidence;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPerson_id() {
                return person_id;
            }

            public void setPerson_id(String person_id) {
                this.person_id = person_id;
            }
        }
    }
}
