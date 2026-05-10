package com.csjbot.coshandler.client_req;


import com.csjbot.coshandler.client_req.body_action.BodyActonReqImpl;
import com.csjbot.coshandler.client_req.body_action.IBodyActionReq;
import com.csjbot.coshandler.client_req.chassis.ChassisReqImpl;
import com.csjbot.coshandler.client_req.chassis.IChassisReq;
import com.csjbot.coshandler.client_req.config.ConfigReqImpl;
import com.csjbot.coshandler.client_req.config.IConfigReq;
import com.csjbot.coshandler.client_req.custom_service.CustomServiceImpl;
import com.csjbot.coshandler.client_req.custom_service.ICustomServiceReq;
import com.csjbot.coshandler.client_req.elevator.ElevatorReqImpl;
import com.csjbot.coshandler.client_req.elevator.IElevatorReq;
import com.csjbot.coshandler.client_req.expression.ExpressionReqImpl;
import com.csjbot.coshandler.client_req.expression.IExpressionReq;
import com.csjbot.coshandler.client_req.extra_func.ExtraFunctionReqImpl;
import com.csjbot.coshandler.client_req.extra_func.IExtraFunctionReq;
import com.csjbot.coshandler.client_req.face.FaceReqImpl;
import com.csjbot.coshandler.client_req.face.IFaceReq;
import com.csjbot.coshandler.client_req.print.IPrintReq;
import com.csjbot.coshandler.client_req.print.PrintReqImpl;
import com.csjbot.coshandler.client_req.robot_state.IRobotStateReq;
import com.csjbot.coshandler.client_req.robot_state.RobotStateReqImpl;
import com.csjbot.coshandler.client_req.sn.ISNReq;
import com.csjbot.coshandler.client_req.sn.SNReqImpl;
import com.csjbot.coshandler.client_req.speech.ISpeechReq;
import com.csjbot.coshandler.client_req.speech.SpeechReqImpl;
import com.csjbot.coshandler.client_req.version.IVersionReq;
import com.csjbot.coshandler.client_req.version.VersionReqImpl;

/**
 * 提供是接口实现类的实例
 * Created by jingwc on 2017/8/14.
 */

public class ReqFactory {

    /* 提供语音功能请求实例 */
    public static ISpeechReq getSpeechReqInstance() {
        return new SpeechReqImpl();
    }

    /* 提供人脸识别功能请求实例 */
    public static IFaceReq getFaceReqInstance() {
        return new FaceReqImpl();
    }

    /* 提供上肢体功能请求实例 */
    public static IBodyActionReq getBodyActionReqInstance() {
        return new BodyActonReqImpl();
    }

    /* 提供表情功能请求实例 */
    public static IExpressionReq getExpressionReqInstance() {
        return new ExpressionReqImpl();
    }

    /* 提供底盘(移动行走)功能请求实例 */
    public static IChassisReq getChassisReqInstance() {
        return new ChassisReqImpl();
    }

    /* 提供打印功能请求实例 */
    public static IPrintReq getPrintReqInstantce() {
        return new PrintReqImpl();
    }

    /* 获取Sn请求实例 */
    public static ISNReq getSnReqInstance() {
        return new SNReqImpl();
    }

    /* 获取机器人状态实例 */
    public static IRobotStateReq getRobotStateInstantce() {
        return new RobotStateReqImpl();
    }


    /* 获取人工客服请求实例 */
    public static ICustomServiceReq getCustomServiceInstantce() {
        return new CustomServiceImpl();
    }

    public static IVersionReq getVersionInstance() {
        return new VersionReqImpl();
    }

    public static IConfigReq getConfigReqInstance() {
        return new ConfigReqImpl();
    }

    public static IElevatorReq getElevatorInstance() {
        return new ElevatorReqImpl();
    }

    /**
     * 获取额外功能的实例
     */
    public static IExtraFunctionReq getExtraFunctionInstance() {
        return new ExtraFunctionReqImpl();
    }
}
