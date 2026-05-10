## 集成AAR文件使用说明
### 第一步 导入aar文件
##### 将aar文件添加到libs文件中
```gradle
implementation(name: 'csjsdk-beta', ext: 'aar')
```
##### 在app的build.gradle文件的android{}结构下添加如下代码
```gradle
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
```
### 第二步 引入依赖库
```gradle
implementation 'io.netty:netty-all:4.1.23.Final'
```

### 第三步 初始化
##### 在Application中初始化SDK
##### 请保证设备端有网络的情况下进行代码授权
```java
// 在开发者平台申请的key与secret进行授权(只需授权一次即可,后续使用无需授权)
// 开发者平台网址:http://openpro.csjbot.com
CsjRobot.authentication(this,"123", "456", new OnAuthenticationListener() {
            @Override
            public void success() {
                Log.d("TAG","授权成功!");
            }

            @Override
            public void error() {
                Log.d("TAG","授权失败!");
            }
        });
CsjRobot.getInstance().init(this);
```
## 机器人功能使用说明

### 注册机器人连接状态事件
```java
       CsjRobot.getInstance().registerConnectListener(new OnConnectListener() {
            @Override
            public void success() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }

            @Override
            public void faild() {

            }

            @Override
            public void timeout() {

            }

            @Override
            public void disconnect() {

            }
        });
```

### 注册机器人唤醒事件
```java
        CsjRobot.getInstance().registerWakeupListener(new OnWakeupListener() {
            @Override
            public void response(int i) {
                Log.d("TAG","registerWakeupListener:i:"+i);
                mCsjBot.getTts().startSpeaking("我在呢!",null);
                if (i > 0 && i < 360) {
                            if (i <= 180) {
                                CsjlogProxy.getInstance().debug("向左转:+" + i);
                                if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                    mCsjBot.getAction().moveAngle(i,null);
                                }
                            } else {
                                CsjlogProxy.getInstance().debug("向右转:-" + (360 - i));
                                if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                    mCsjBot.getAction().moveAngle(-(360 - i),null);
                                }
                            }
                }
            }
        });
```

### 注册机器人语音识别事件
```java
       // 语音识别
        CsjRobot.getInstance().registerSpeechListener(new OnSpeechListener() {
            @Override
            public void speechInfo(String s, int i) {

                // 简单解析示例
                Log.d("TAG","registerSpeechListener:s:"+s);
                if(Speech.SPEECH_RECOGNITION_RESULT == i){ // 识别到的信息
                    try {
                        String text = new JSONObject(s).getString("text");
                        Toast.makeText(MainActivity.this, "识别到的文本:"+text, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(Speech.SPEECH_RECOGNITION_AND_ANSWER_RESULT == i){// 识别到的信息与的回答
                    try {
                        String say = new JSONObject(s).getJSONObject("result").getJSONObject("data").getString("say");
                        Toast.makeText(MainActivity.this, "获取的答案信息:"+say, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
```

### 注册机器人实时图像传输事件
```java
        // 摄像头实时图片传输
        CsjRobot.getInstance().registerCameraListener(new OnCameraListener() {
            @Override
            public void response(Bitmap bitmap) {

            }
        });
```

### 注册机器人综合人体检测事件
```java
        // 综合人体检测
        CsjRobot.getInstance().registerDetectPersonListener(new OnDetectPersonListener() {
            @Override
            public void response(int i) {
                if(i == 0){ // 无人
                    Log.d("TAG","registerDetectPersonListener:无人");
                }else if(i == 1){ // 有人
                    Log.d("TAG","registerDetectPersonListener:有人");
                }
            }
        });
```

### 注册人脸信息事件
```java
        // 人脸信息
        CsjRobot.getInstance().registerFaceListener(new OnFaceListener() {
            @Override
            public void personInfo(String s) {
                // 人脸识别到的会员信息(人脸一直在就会持续触发)
                Log.d("TAG","registerFaceListener:s:"+s);
            }

            @Override
            public void personNear(boolean b) {

                if(b){// 有人脸靠近
                    Log.d("TAG","registerFaceListener:有人脸靠近");
                }else{// 人脸消失
                    Log.d("TAG","registerFaceListener:人脸消失");
                }
            }
        });
```

### 注册机器人头部传感器事件(小雪)
```java
        // 机器人头部传感器(小雪)
        CsjRobot.getInstance().registerHeadTouchListener(new OnHeadTouchListener() {
            @Override
            public void response() {
                Log.d("TAG","registerHeadTouchListener:头部传感器触发");
            }
        });
```

### 机器人动作
```java
        // 机器人动作
        Action action = CsjRobot.getInstance().getAction();

        // 动作重置
        action.reset();

        // 肢体动作(bodyPart:肢体部位,action:对应动作)
        action.action(2,6);// 低头

        // 开始左右摆手(intervalTime:间隔时间)
        action.startWaveHands(1000);

        // 停止左右摆手
        action.stopWaveHands();

        // 开始跳舞
        action.startDance();

        // 停止跳舞
        action.stopDance();

        // 获取当前机器人的位置信息
        action.getPosition(new OnPositionListener() {
            @Override
            public void positionInfo(String s) {
                // 返回json
                /*{
                    "msg_id":"NAVI_GET_CURPOS_RSP",
                        "x":”0”,
                    "y":”0”,
                    "z":”0”,
                    "rotation":”0”,
                    "error_code":0
                  }*/
            }
        });

        // 移动方法(direction:方向 0:前 1:后 2:左 3:右)
        // 移动距离较短
        action.move(0);

        // 导航方法(机器人导航到某个点)
        String json = "";
        /*{
                    "x":2,
                    "y":1,
                    "z":0,
                    "rotation":30
                 }*/
        action.navi(json, new OnNaviListener() {
            @Override
            public void moveResult(String s) {
                // 到达后通知
            }

            @Override
            public void messageSendResult(String s) {
                // 导航消息发送成功后的通知
            }

            @Override
            public void cancelResult(String s) {

            }

            @Override
            public void goHome() {

            }
        });

        // 机器人取消当前导航
        action.cancelNavi(new OnNaviListener() {
            @Override
            public void moveResult(String s) {

            }

            @Override
            public void messageSendResult(String s) {

            }

            @Override
            public void cancelResult(String s) {
                // 机器人取消导航通知
            }

            @Override
            public void goHome() {

            }
        });

        // 转至特定角度()
        action.goAngle(180);

        // 步进角度(Rotation>0:向左转，Rotation<0:向右转)
        action.moveAngle(180, new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // 到达角度通知
            }
        });

        // 回去充电
        action.goHome(new OnNaviListener() {
            @Override
            public void moveResult(String s) {

            }

            @Override
            public void messageSendResult(String s) {

            }

            @Override
            public void cancelResult(String s) {

            }

            @Override
            public void goHome() {
                // 充电通知
            }
        });

        // 保存当前机器人的地图信息
        action.saveMap();
        action.saveMap(String name);

        // 加载保存的机器人地图信息
        action.loadMap();
        action.loadMap(String name);

        // 机器人速度设置(0.1-0.7,默认0.5)
        action.setSpeed(0.6f);
        
        // 获取所有地图
        action.getMapList(new OnMapListListener() {
            @Override
            public void response(String s) {

            }
        });

        // 导航状态查询
        action.search(new OnNaviSearchListener() {
            @Override
            public void searchResult(String s) {
                /*{
                    "msg_id":"NAVI_GET_STATUS_RSP",
                     "state":0,
                     "error_code":0
                  }*/
                // state:0空闲,1:正在导航
            }
        });

        // 点头动作
        action.nodAction();

        // 小雪右臂摆动
        action.snowRightArm();

        // 小雪左臂摆动
        action.snowLeftArm();

        // 小雪双臂摆动
        action.snowDoubleArm();

        // 爱丽丝抬头
        action.AliceHeadUp();

        // 爱丽丝头低头
        action.AliceHeadDown();

        // 爱丽丝头部重置
        action.AliceHeadHReset();

        // 爱丽丝左手臂抬起
        action.AliceLeftArmUp();

        // 爱丽丝左手臂放下
        action.AliceLeftArmDown();

        // 爱丽丝右手臂抬起
        action.AliceRightArmUp();

        // 爱丽丝右手臂放下
        action.AliceRightArmDown();

        // 小雪左臂摆动次数
        action.SnowLeftArmSwing(20);

        // 小雪右臂摆动次数
        action.SnowRightArmSwing(20);

        // 小雪双臂摆动次数
        action.SnowDoubleArmSwing(20);

        // 向左转
        action.turnLeft(new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // 完成通知
            }
        });

        // 向右转
        action.turnRight(new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // 完成通知
            }
        });

        // 向左移动
        action.moveLeft();

        // 向右移动
        action.moveRight();

        // 前进
        action.moveForward();

        // 后退
        action.moveBack();
```

### TTS(语音合成)
```java
        // 机器人TTS
        ISpeechSpeak speak = CsjRobot.getInstance().getTts();

        // 开始说话
        speak.startSpeaking("你好呀！", new OnSpeakListener() {
            @Override
            public void onSpeakBegin() {
                // 说话前
            }

            @Override
            public void onCompleted(SpeechError speechError) {
                // 说话完成
            }
        });

        // 停止说话
        speak.stopSpeaking();

        // 暂停说话
        speak.pauseSpeaking();

        // 重新说话
        speak.resumeSpeaking();

        // 是否正在说话
        speak.isSpeaking();

        // 也可以使用自己实现的TTS(继承自ISpeechSpeak接口)
        CsjRobot.getInstance().setTts(null);
```

### 语音识别
```java
        // 机器人语音
        Speech speech = CsjRobot.getInstance().getSpeech();

        // 开启讯飞语音服务(默认开启，无需手动调用)
        speech.startSpeechService();

        // 关闭讯飞语音服务
        speech.closeSpeechService();

        // 开启多次识别
        speech.startIsr();

        // 关闭多次识别
        speech.stopIsr();

        // 开启单次识别
        speech.startOnceIsr();

        // 关闭单次识别
        speech.stopOnceIsr();

        // 手动唤醒机器人
        speech.openMicro();

        // 手动去获取问题的答案
        speech.getResult("你叫什么名字？", new OnSpeechGetResultListener() {
            @Override
            public void response(String s) {

            }
        });
```

### 人脸识别信息
```java
        // 机器人人脸识别
        Face face = CsjRobot.getInstance().getFace();

        // 打开摄像头(打开视频流传输 默认开启)
        face.openVideo();

        // 关闭摄像头
        face.closeVideo();

        // 启动人脸识别服务(默认开启)
        face.startFaceService();

        // 关闭人脸识别服务
        face.closeFaceService();


        // 摄像头拍照
        face.snapshot(new OnSnapshotoListener() {
            @Override
            public void response(String s) {
                /*
                * {
                    "error_code": 0,
                    "face_position": 0,
                    "msg_id":”FACE_SNAPSHOT_RESULT_RSP"
                    } */
                // erro_code : 0表示有人脸 其他表示无人脸
            }
        });

        // 人脸注册(保存当前拍照的人脸)
        face.saveFace("张三", new OnFaceSaveListener() {
            @Override
            public void response(String s) {
                /*
                * {
                    "msg_id":"FACE_SAVE_RSP",
                    “person_id”:”personx20170107161021mRJOVw”,
                       “error_code":0
                   }*/

                // error_cdoe : 0 成功 , 40002 人脸已经注册,40003 人脸姓名格式错误
            }
        });

        // 人脸信息删除
        face.faceDel("faceId");

        // 人脸信息批量删除
        face.faceDelList("faceIdsJson");

        // 获取所有人脸数据库
        face.getFaceDatabase(new OnGetAllFaceListener() {
            @Override
            public void personList(String s) {
                // s
            }
        });
```

### 机器人状态
```java
        // 机器人状态
        State state = CsjRobot.getInstance().getState();

        // 机器人的连接状态
        state.isConnect();

        // 机器人的电量值
        state.getElectricity();

        // 机器人当前充电状态
        state.getChargeState();

        // 机器人关机
        state.shutdown();

        // 机器人重启
        state.reboot();

        // 获取机器人电量信息
        state.getBattery(new OnRobotStateListener() {
            @Override
            public void getBattery(int i) {
                // 返回的电量值
            }

            @Override
            public void getCharge(int i) {

            }
        });

        // 获取机器人的充电状态
        state.getCharge(new OnRobotStateListener() {
            @Override
            public void getBattery(int i) {

            }

            @Override
            public void getCharge(int i) {
                // 返回的充电状态
            }
        });


        // 机器人检查信息
        state.checkSelf(new OnWarningCheckSelfListener() {
            @Override
            public void response(String s) {
                // 返回机器人的检查信息
            }
        });

        // 手动获取综合人体检测的信息
        state.getPerson(new OnDetectPersonListener() {
            @Override
            public void response(int i) {
                // 返回的状态
                // i==0 无人 i==1 有人
            }
        });
        
        // 获取机器人的SN信息
        state.getSN(new OnSNListener() {
            @Override
            public void response(String s) {
                // 返回的SN信息
            }
        });
```

### 机器人表情
```java
        // 机器人表情
        Expression expression = CsjRobot.getInstance().getExpression();

        // 获取机器人表情
        expression.getExpression(new OnExpressionListener() {
            @Override
            public void response(int i) {
                // 返回的表情
            }
        });

        // 开心
        expression.happy();

        // 悲伤
        expression.sadness();

        // 惊讶
        expression.surprised();

        // 微笑
        expression.smile();

        // 正常
        expression.normal();

        // 愤怒
        expression.angry();

        // 闪电
        expression.lightning();

        // 困倦
        expression.sleepiness();
```

### 机器人版本
```java
        // 机器人版本信息
        Version version = CsjRobot.getInstance().getVersion();

        // 获取机器人版本信息
        version.getVersion(new OnGetVersionListener() {
            @Override
            public void response(String s) {
                // 返回的版本信息
            }
        });

        // 底层软件检查
        version.softwareCheck(new OnUpgradeListener() {
            @Override
            public void checkRsp(int i) {
                if (i == 60002) {//已是最新版本

                } else if (i == 60001) {//没有获取到版本信息，请检查网络

                } else if (i == 0) {//正常更新

                }
            }

            @Override
            public void upgradeRsp(int i) {

            }

            @Override
            public void upgradeProgress(int i) {

            }
        });


        // 底层软件更新
        version.softwareUpgrade(new OnUpgradeListener() {
            @Override
            public void checkRsp(int i) {

            }

            @Override
            public void upgradeRsp(int i) {

            }

            @Override
            public void upgradeProgress(int i) {
                // 更新进度
            }
        });
```

### 其他功能
```java
        // 其他功能
        Extra extra = CsjRobot.getInstance().getExtra();

        // 获取机器人的热词列表
        extra.getHotWords(new OnHotWordsListener() {
            @Override
            public void hotWords(List<String> list) {
                // 返回的热词列表
            }
        });

        // 设置热词功能
        extra.setHotWords(null);

        // 恢复出厂设置
        extra.resetRobot();
```