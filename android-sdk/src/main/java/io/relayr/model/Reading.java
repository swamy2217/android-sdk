package io.relayr.model;

public class Reading {

    public long ts;
    public float hum;
    public float temp;
    public float snd_level;
    public float light;
    public float prox;
    public LightColorProx.Color clr;
    public AccelGyroscope.Accelerometer accel;
    public AccelGyroscope.Gyroscope gyro;

    /*public final String timestamp;
    public final Map<String, String> value;

    public Reading(String timestamp, Map<String, String> value) {
        this.timestamp = timestamp;
        this.value = value;
    }*/
}
