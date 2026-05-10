## Integrated AAR file instructions
## [中文文档点我](http://gitlab.csjbot.com/jingweicheng/csjsdkdemo/blob/master/README_zh.md)
### Step 1 import AAR file
##### Add AAR file to LIBS file
```gradle
implementation(name: 'csjsdk-beta', ext: 'aar')
```
##### Add the following code under the Android {} structure of the app's build.gradle file
```gradle
repositories {
    flatDir {
        dirs 'libs'
    }
}
```
### Step 2: introduce dependency Library
```gradle
implementation 'io.netty:netty-all:4.1.23.Final'
```

### Step 3 initialization
##### Initialize SDK in application
##### Please make sure to authorize the code when the device has a network
```java
// Please fill in key and secret for verification
// Developer Platform http://openpro.csjbot.com
CsjRobot.authentication(this,"123", "456", new OnAuthenticationListener() {
            @Override
            public void success() {
            
            }

            @Override
            public void error() {
            
            }
        });
CsjRobot.getInstance().init(this);
```
## Robot function instruction
### Register robot connection status events
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

### Register robot wake up event
```java
        CsjRobot.getInstance().registerWakeupListener(new OnWakeupListener() {
            @Override
            public void response(int i) {
                Log.d("TAG","registerWakeupListener:i:"+i);
                mCsjBot.getTts().startSpeaking("I am here!",null);
                if (i > 0 && i < 360) {
                            if (i <= 180) {
                                CsjlogProxy.getInstance().debug("Turn left:+" + i);
                                if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                    mCsjBot.getAction().moveAngle(i,null);
                                }
                            } else {
                                CsjlogProxy.getInstance().debug("Turn right:-" + (360 - i));
                                if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                    mCsjBot.getAction().moveAngle(-(360 - i),null);
                                }
                            }
                }
            }
        });
```

### Registering robot speech recognition events
```java
       // speech recognition
        CsjRobot.getInstance().registerSpeechListener(new OnSpeechListener() {
            @Override
            public void speechInfo(String s, int i) {

                // Simple parsing example
                Log.d("TAG","registerSpeechListener:s:"+s);
                if(Speech.SPEECH_RECOGNITION_RESULT == i){ // Identified information
                    try {
                        String text = new JSONObject(s).getString("text");
                        Toast.makeText(MainActivity.this, "Recognized text:"+text, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(Speech.SPEECH_RECOGNITION_AND_ANSWER_RESULT == i){// Identified information and answers
                    try {
                        String say = new JSONObject(s).getJSONObject("result").getJSONObject("data").getString("say");
                        Toast.makeText(MainActivity.this, "Answer information obtained:"+say, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
```

### Registration robot real time image transmission event
```java
        // Camera real time picture transmission
        CsjRobot.getInstance().registerCameraListener(new OnCameraListener() {
            @Override
            public void response(Bitmap bitmap) {

            }
        });
```

### Registered robot integrated human detection event
```java
        // Comprehensive human detection
        CsjRobot.getInstance().registerDetectPersonListener(new OnDetectPersonListener() {
            @Override
            public void response(int i) {
                if(i == 0){ // Unmanned
                    Log.d("TAG","registerDetectPersonListener:Unmanned");
                }else if(i == 1){ // Someone
                    Log.d("TAG","registerDetectPersonListener:Someone");
                }
            }
        });
```

### Register face information event
```java
        // Face information
        CsjRobot.getInstance().registerFaceListener(new OnFaceListener() {
            @Override
            public void personInfo(String s) {
                // Member information from face recognition(The face will continue to trigger all the time.)
                Log.d("TAG","registerFaceListener:s:"+s);
            }

            @Override
            public void personNear(boolean b) {

                if(b){// Face close
                    Log.d("TAG","registerFaceListener:Face close");
                }else{// Face disappear
                    Log.d("TAG","registerFaceListener:Face disappear ");
                }
            }
        });
```

### Register robot head sensor events(snow)
```java
        // Robot head sensor(snow)
        CsjRobot.getInstance().registerHeadTouchListener(new OnHeadTouchListener() {
            @Override
            public void response() {
                Log.d("TAG","registerHeadTouchListener:Head sensor trigger");
            }
        });
```

### Robot action
```java
        // Robot action
        Action action = CsjRobot.getInstance().getAction();

        // Action reset
        action.reset();

        // Body movements(bodyPart:Limb position,action:Corresponding action)
        action.action(2,6);// Bow

        // Start to wave left and right(intervalTime:Interval time)
        action.startWaveHands(1000);

        // Stop waving left and right
        action.stopWaveHands();

        // Start dancing
        action.startDance();

        // Start dancing
        action.stopDance();

        // Get the position information of the current robot
        action.getPosition(new OnPositionListener() {
            @Override
            public void positionInfo(String s) {
                // Return   JSON
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

		//Move method (direction: direction 0: front 1: back 2: left 3: right)
		//Short distance
        action.move(0);

        // Navigation method (robot navigates to a point)
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
                // Notice upon arrival
            }

            @Override
            public void messageSendResult(String s) {
                // Notification after successful navigation message sending
            }

            @Override
            public void cancelResult(String s) {

            }

            @Override
            public void goHome() {

            }
        });

        // Notification after successful navigation message sending
        action.cancelNavi(new OnNaviListener() {
            @Override
            public void moveResult(String s) {

            }

            @Override
            public void messageSendResult(String s) {

            }

            @Override
            public void cancelResult(String s) {
                // Robot cancel navigation notification
            }

            @Override
            public void goHome() {

            }
        });

        // Go to a specific angle()
        action.goAngle(180);

        // Stepping angle(Rotation>0:Turn left，Rotation<0:Turn right)
        action.moveAngle(180, new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // Arrival angle notification
            }
        });

        // Go back charging
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
                // Charging notice
            }
        });

		//Save map information of current robot
		Action. Savemap();
		Action. Savemap("name");

		//Loading saved robot map information
		Action. Loadmap();
		Action. Loadmap("name");

		//Robot speed setting (0.1-0.7, default 0.5)
		Action. Setspeed (0.6f);


        // Get all map
        action.getMapList(new OnMapListListener() {
            @Override
            public void response(String s) {

            }
        });

		//Navigation status query
        action.search(new OnNaviSearchListener() {
            @Override
            public void searchResult(String s) {
                /*{
                    "msg_id":"NAVI_GET_STATUS_RSP",
                     "state":0,
                     "error_code":0
                  }*/
                // state:0 IDEL, 1: navigating
            }
        });

		//Nodding
		Action. Nodaction();
		
		//snow right arm swings
		Action. Snowrightarm();
		
		//snow left arm swings
		Action. Snowleftarm();
		
		//Xiaoxue swings her arms
		Action. Snowdoublearm();
		
		//Alice looks up
		Action. Aliceheadup();
		
		//Alice looks down
		Action. Aliceheaddown();
		
		//Alice head reset
		Action. Aliceheadhreset();
		
		//Alice raised her left arm
		Action. Aliceleftarmup();
		
		//Alice put her left arm down
		Action. Aliceleftarmdown();
		
		//Alice raises her right arm
		Action. Alicereightarmup();
		
		//Alice put her right arm down
		Action. Alicereightarmdown();
		
		//Swing times of snow left arm
		Action. Snowleftarmswing (20);
		
		//Swing times of snow right arm
		Action. Snowrightarmswing (20);
		
		//Times of snow arms swinging
		Action. Snowdoublearmswing (20);

        // Turn left
        action.turnLeft(new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // Completion notice
            }
        });

        // 向右转
        action.turnRight(new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // Completion notice
            }
        });

        // Move to the left
        action.moveLeft();

        // Move to the right
        action.moveRight();

        // Forward
        action.moveForward();

        // Backward
        action.moveBack();
```

### TTS(speech synthesis)
```java
        // Robot TTS
        ISpeechSpeak speak = CsjRobot.getInstance().getTts();

        // Begin to speak
        speak.startSpeaking("How do you do!", new OnSpeakListener() {
            @Override
            public void onSpeakBegin() {
                // Begin Speak
            }

            @Override
            public void onCompleted(SpeechError speechError) {
                // Finish speaking
            }
        });

		// Stop talking
		Speak. Stopspeaking();
		
		// Stop talking
		Speak. Pausespeaking();
		
		// Talk again
		Speak. Resumespeaking();
		
		// Speaking or not
		Speak. Isspeaking();
		
		// You can also use your own implementation of TTS (inherited from ispeechspeak interface)
		Csjrobot. Getinstance(). Settts (null);
```

### Speech recognition (Chinese only)
```java
		//Robot voice
		Speech speech = csjrobot. Getinstance(). Getspeech();
		
		//Enable iFLYTEK voice service (enabled by default, no operation required)
		Speech. Startspeech service();
		
		//Turn off iFLYTEK voice service
		Speech. Closespeechservice();
		
		//Turn on multiple recognition
		Speech. Startisr();
		
		//Turn off multiple recognition
		Speech. Stopisr();
		
		//Turn on single identification
		Speech. Startonceisr();
		
		//Turn off single recognition
		Speech. Stoponceisr();
		
		//Manual wake-up robot
		Speech. Openmicro();
		
		//Get answers manually
		Speech. GetResult ("what's your name? ", new onspeechgetresultlistener(){
		
			@Override
			Public void response (string s){
			}
		};
```

### Face recognition information
```java
		//Robot face recognition
		Face = csjrobot. Getinstance(). Getface();
		
		//Turn on the camera (video streaming is on by default)
		Face. Openvideo();
		
		//Turn off camera
		Face. Closevideo();
		
		//Start face recognition service (on by default)
		Face. Startfaceservice();
		
		//Turn off face recognition service
		Face. Closefaceservice();

        // Camera take photos
        face.snapshot(new OnSnapshotoListener() {
            @Override
            public void response(String s) {
                /*
                * {
                    "error_code": 0,
                    "face_position": 0,
                    "msg_id":”FACE_SNAPSHOT_RESULT_RSP"
                    } */
                // erro_code : 0 for have face other for no face
            }
        });

        // Face registration (save the currently photographed face)
        face.saveFace("ZhangSan", new OnFaceSaveListener() {
            @Override
            public void response(String s) {
                /*
                * {
                    "msg_id":"FACE_SAVE_RSP",
                    “person_id”:”personx20170107161021mRJOVw”,
                       “error_code":0
                   }*/

                // error_cdoe : 0 success, 40002 face registered, 40003 face name format error
            }
        });

        // Face information deletion
        face.faceDel("faceId");

        // Mass deletion of face information
        face.faceDelList("faceIdsJson");

        // Get all face databases
        face.getFaceDatabase(new OnGetAllFaceListener() {
            @Override
            public void personList(String s) {
                // s
            }
        });
```

### Robot status
```java
        // Robot status
        State state = CsjRobot.getInstance().getState();

        // Robot connection status
        state.isConnect();

        // Robot's power value
        state.getElectricity();

        // Current charging state of robot
        state.getChargeState();

        // Robot shutdown
        state.shutdown();

        // Robot restart
        state.reboot();

        // Get robot power information
        state.getBattery(new OnRobotStateListener() {
            @Override
            public void getBattery(int i) {
                // Power value returned
            }

            @Override
            public void getCharge(int i) {

            }
        });

        // Get the charging state of the robot
        state.getCharge(new OnRobotStateListener() {
            @Override
            public void getBattery(int i) {

            }

            @Override
            public void getCharge(int i) {
                // State of charge returned
            }
        });


        // Robot inspection information
        state.checkSelf(new OnWarningCheckSelfListener() {
            @Override
            public void response(String s) {
                // Return inspection information of robot
            }
        });

        // Manual access to comprehensive human detection information
        state.getPerson(new OnDetectPersonListener() {
            @Override
            public void response(int i) {
                //Status returned
				//I = = 0 nobody I = = 1 someone
            }
        });
        
        // Get Sn information of robot
        state.getSN(new OnSNListener() {
            @Override
            public void response(String s) {
                // Sn information returned
            }
        });
```

### Robot expression
```java
        // Robot expression
        Expression expression = CsjRobot.getInstance().getExpression();

        // Get robot expression
        expression.getExpression(new OnExpressionListener() {
            @Override
            public void response(int i) {
                // Expression of return
            }
        });

		// happy
		Expression. Happy();
		
		// grief
		Expression. Sadness();
		
		// surprise
		Expression. Guaranteed();
		
		// smile
		Expression. Smile();
		
		// normal
		Expression. Normal();
		
		// anger
		Expression. Angle();
		
		// lightning
		Expression. Lightning();
		
		// sleepiness
		Expression. Sleepiness();
```

### Robot version
```java
        // Robot version information
        Version version = CsjRobot.getInstance().getVersion();

        // Get robot version information
        version.getVersion(new OnGetVersionListener() {
            @Override
            public void response(String s) {
                // Version information returned
            }
        });

        // Bottom software check
        version.softwareCheck(new OnUpgradeListener() {
            @Override
            public void checkRsp(int i) {
                if (i == 60002) {//Is the latest version

                } else if (i == 60001) {//No version information was obtained, please check the network

                } else if (i == 0) {//Normal renewal

                }
            }

            @Override
            public void upgradeRsp(int i) {

            }

            @Override
            public void upgradeProgress(int i) {

            }
        });


        // Underlying software update
        version.softwareUpgrade(new OnUpgradeListener() {
            @Override
            public void checkRsp(int i) {

            }

            @Override
            public void upgradeRsp(int i) {

            }

            @Override
            public void upgradeProgress(int i) {
                // Update progress
            }
        });
```

### Other functions
```java
        // Other functions
        Extra extra = CsjRobot.getInstance().getExtra();

        // Get hot word list of robot
        extra.getHotWords(new OnHotWordsListener() {
            @Override
            public void hotWords(List<String> list) {
                // List of hot words returned
            }
        });

        // Set hot word function
        extra.setHotWords(null);

        // Restore factory settings
        extra.resetRobot();
```