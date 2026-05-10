package com.csjbot.coshandler.client_req.elevator;

import com.csjbot.coshandler.listener.OnElevatorCtrlListener;
import com.csjbot.coshandler.listener.OnElevatorStateListener;

/**
 * The interface Elevator req.
 */
public interface IElevatorReq {
    /**
     * Open elevator module.
     *
     * @param listener the listener
     */
    void openElevatorModule(OnElevatorStateListener listener);

    /**
     * Gets elevator info.
     *
     * @param elevatorId the elevator id
     * @param listener   the listener
     */
    void getElevatorInfo(int elevatorId, OnElevatorStateListener listener);

    /**
     * Call info.
     *
     * @param elevatorId   the elevator id
     * @param currentFloor the current floor
     * @param targetFloor  the target floor
     * @param listener     the listener
     */
    void callInfo(int elevatorId, int currentFloor, int targetFloor, OnElevatorCtrlListener listener);

    /**
     * Calling elevator.
     *
     * @param listener the listener
     */
    void callingElevator(OnElevatorCtrlListener listener);

    /**
     * Entering elevator.
     *
     * @param listener the listener
     */
    void enteringElevator(OnElevatorCtrlListener listener);

    /**
     * Inside elevator.
     *
     * @param listener the listener
     */
    void insideElevator(OnElevatorCtrlListener listener);

    /**
     * Leaving elevator.
     *
     * @param listener the listener
     */
    void leavingElevator(OnElevatorCtrlListener listener);

    /**
     * Outside elevator.
     *
     * @param listener the listener
     */
    void outsideElevator(OnElevatorCtrlListener listener);

    /**
     * Cancel elevator.
     *
     * @param listener the listener
     */
    void cancelElevator(OnElevatorCtrlListener listener);
}
