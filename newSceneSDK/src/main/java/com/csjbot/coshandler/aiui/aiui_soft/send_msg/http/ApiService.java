package com.csjbot.coshandler.aiui.aiui_soft.send_msg.http;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jingwc on 2018/3/29.
 */
public interface ApiService {
    @POST("csjbot-service/api/robotQaInfo/QaRequest")
    Observable<ResponseBody> getAnswerV3(@Query("sn") String sn, @Body RequestBody body);

    @GET("csjbot-service/api/aiui/v1/getBySn")
    Observable<ResponseBody> getAiuiKey(@Query("sn") String sn, @Query("mac") String mac);
}
