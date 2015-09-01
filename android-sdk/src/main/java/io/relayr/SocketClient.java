package io.relayr;

import io.relayr.model.Device;
import io.relayr.model.Reading;
import rx.Observable;

public interface SocketClient {

    /**
     * Subscribes an app to a device channel. Enables the app to receive data from the device.
     * @param device The device object to be subscribed to.
     */
    Observable<Reading> subscribe(Device device);

    /**
     * Unsubscribes an app from a device channel, stopping and cleaning up the connection.
     * @param sensorId the Id of {@link io.relayr.model.TransmitterDevice}
     */
    void unSubscribe(final String sensorId);

    /**
     * Publish data from device. Device needs to be created in order to publish data to platform.
     * Every published object should be in the form of {@link Reading}
     * @param deviceId id of the device to publish data
     * @param reading  data to publish - object value should be defined by the model
     */
    Observable<Void> publish(String deviceId, Reading reading) throws Exception;

}
