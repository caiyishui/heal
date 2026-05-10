package com.csjbot.coshandler.aiui.aiui_soft.send_msg;

import java.util.List;

/**
 * Copyright (c) 2019, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2019/04/09 0009-18:50.
 * Email: puyz@csjbot.com
 */

public class RawDataChatBean {

    /**
     * msg_id : SPEECH_ISR_LAST_RESULT_NTF
     * result : {"data":{"actionList":[],"answer":"我想吃红烧肉","graphic":"{\"type\":\"2\",\"answer\":\"我想吃红烧肉\",\"imgFile\":[{\"url\":\"http://csjbot-test.su.bcebos.com/mkYycGynG8KsP5SwBn7EKwHwfy7YRxdHmfXaTKmM.jpg\"},{\"url\":\"http://csjbot-test.su.bcebos.com/MFxpxFMmS6Q4FEsEPymmxkNJHd5RyCWZPmrbYJB3.jpg\"}]}","say":"我想吃红烧肉","type":"satisfy"},"error_code":0,"text":"我想吃红烧肉。"}
     */

    private String msg_id;
    private ResultBean result;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * data : {"actionList":[],"answer":"我想吃红烧肉","graphic":"{\"type\":\"2\",\"answer\":\"我想吃红烧肉\",\"imgFile\":[{\"url\":\"http://csjbot-test.su.bcebos.com/mkYycGynG8KsP5SwBn7EKwHwfy7YRxdHmfXaTKmM.jpg\"},{\"url\":\"http://csjbot-test.su.bcebos.com/MFxpxFMmS6Q4FEsEPymmxkNJHd5RyCWZPmrbYJB3.jpg\"}]}","say":"我想吃红烧肉","type":"satisfy"}
         * error_code : 0
         * text : 我想吃红烧肉。
         */

        private DataBean data;
        private int error_code;
        private String text;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }



        public static class DataBean {
            /**
             * actionList : []
             * answer : 我想吃红烧肉
             * graphic : {"type":"2","answer":"我想吃红烧肉","imgFile":[{"url":"http://csjbot-test.su.bcebos.com/mkYycGynG8KsP5SwBn7EKwHwfy7YRxdHmfXaTKmM.jpg"},{"url":"http://csjbot-test.su.bcebos.com/MFxpxFMmS6Q4FEsEPymmxkNJHd5RyCWZPmrbYJB3.jpg"}]}
             * say : 我想吃红烧肉
             * type : satisfy
             */

            private String answer;
            private String graphic;
            private String say;
            private String type;
            private List<?> actionList;
            private String serviceId;

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getGraphic() {
                return graphic;
            }

            public void setGraphic(String graphic) {
                this.graphic = graphic;
            }

            public String getSay() {
                return say;
            }

            public void setSay(String say) {
                this.say = say;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<?> getActionList() {
                return actionList;
            }

            public void setActionList(List<?> actionList) {
                this.actionList = actionList;
            }
        }
    }
}
