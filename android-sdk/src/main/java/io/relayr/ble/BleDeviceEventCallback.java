package io.relayr.ble;

interface BleDeviceEventCallback {
    void onModeSwitch(BleDeviceMode mode, BleDevice device);
    void onDeviceDiscovered(BleDevice device);
    void onDeviceConnectedToMasterModuleDiscovered(BleDevice device);
}