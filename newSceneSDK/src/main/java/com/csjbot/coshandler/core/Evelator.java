package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.elevator.IElevatorReq;
import com.csjbot.coshandler.listener.OnElevatorCtrlListener;
import com.csjbot.coshandler.listener.OnElevatorStateListener;

public class Evelator implements IElevatorReq {
    private IElevatorReq elevatorReq = ReqFactory.getElevatorInstance();

    @Override
    public void openElevatorModule(OnElevatorStateListener listener) {
        elevatorReq.openElevatorModule(listener);
    }

    @Override
    public void getElevatorInfo(int elevatorId, OnElevatorStateListener listener) {
        elevatorReq.getElevatorInfo(elevatorId, listener);
    }

    @Override
    public void callInfo(int elevatorId, int currentFloor, int targetFloor, OnElevatorCtrlListener listener) {
        elevatorReq.callInfo(elevatorId, currentFloor, targetFloor, listener);
    }

    @Override
    public void callingElevator(OnElevatorCtrlListener listener) {
        elevatorReq.callingElevator(listener);
    }

    @Override
    public void enteringElevator(OnElevatorCtrlListener listener) {
        elevatorReq.enteringElevator(listener);
    }

    @Override
    public void insideElevator(OnElevatorCtrlListener listener) {
        elevatorReq.insideElevator(listener);
    }

    @Override
    public void leavingElevator(OnElevatorCtrlListener listener) {
        elevatorReq.leavingElevator(listener);
    }

    @Override
    public void outsideElevator(OnElevatorCtrlListener listener) {
        elevatorReq.outsideElevator(listener);
    }

    @Override
    public void cancelElevator(OnElevatorCtrlListener listener) {
        elevatorReq.cancelElevator(listener);
    }
}
