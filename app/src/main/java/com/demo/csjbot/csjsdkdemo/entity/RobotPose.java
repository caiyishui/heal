package com.demo.csjbot.csjsdkdemo.entity;

public class RobotPose {
    private String poseName;
    private PosBean pos;


    public PosBean getPos() {
        return pos;
    }

    public void setPos(PosBean pos) {
        this.pos = pos;
    }

    public String getPoseName() {
        return poseName;
    }

    public void setPoseName(String poseName) {
        this.poseName = poseName;
    }

    public static class PosBean {
        /**
         * x : 2
         * y : 1
         * z : 0
         * rotation : 30
         */

        private float x;
        private float y;
        private float z;
        private float rotation;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getZ() {
            return z;
        }

        public void setZ(float z) {
            this.z = z;
        }

        public float getRotation() {
            return rotation;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }

        @Override
        public String toString() {
            return "PosBean{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", rotation=" + rotation +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RobotPose{" +
                "poseName='" + poseName + '\'' +
                ", pos=" + pos +
                '}';
    }
}
