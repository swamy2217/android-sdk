package io.relayr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import javax.inject.Inject;

import io.relayr.TestEnvironment;
import io.relayr.model.Bookmark;
import io.relayr.model.BookmarkDevice;
import io.relayr.model.CreateWunderBar;
import io.relayr.model.Device;
import io.relayr.model.Transmitter;
import rx.Observer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class MockUserApiTest extends TestEnvironment {

    private final String ID = "4f1ddffb-d9fa-456b-a73e-33daa6284c39";

    @Inject UserApi mockApi;

    @Captor private ArgumentCaptor<List<Device>> userDevicesCaptor;
    @Captor private ArgumentCaptor<CreateWunderBar> wunderBarCaptor;
    @Captor private ArgumentCaptor<List<Transmitter>> transmittersCaptor;
    @Captor private ArgumentCaptor<List<BookmarkDevice>> bookmarkDevicesCaptor;
    @Captor private ArgumentCaptor<Bookmark> bookmarkCaptor;

    @Mock private Observer subscriber;

    @Before
    public void init() {
        super.init();
        inject();
        initSdk();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserDevicesTest() {
        mockApi.getUserDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(userDevicesCaptor.capture());

        assertThat(userDevicesCaptor.getValue().size()).isEqualTo(4);
        assertThat(userDevicesCaptor.getValue().get(0).getModel().getName())
                .isEqualTo("Wunderbar Thermometer & Humidity Sensor");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createWunderBarTest() {
        mockApi.createWunderBar(ID).subscribe(subscriber);

        verify(subscriber).onNext(wunderBarCaptor.capture());

        assertThat(wunderBarCaptor.getValue().getMasterModule().getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getTransmittersTest() {
        mockApi.getTransmitters(ID).subscribe(subscriber);

        verify(subscriber).onNext(transmittersCaptor.capture());

        assertThat(transmittersCaptor.getValue().size()).isEqualTo(2);
        assertThat(transmittersCaptor.getValue().get(0).getName())
                .isEqualTo("My Wunderbar Master Module");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getBookmarkedDevicesTest() {
        mockApi.getBookmarkedDevices(ID).subscribe(subscriber);

        verify(subscriber).onNext(bookmarkDevicesCaptor.capture());

        assertThat(bookmarkDevicesCaptor.getValue().size()).isEqualTo(1);
        assertThat(bookmarkDevicesCaptor.getValue().get(0).getName()).isEqualTo("DanasDevice");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void bookmarkDeviceTest() {
        mockApi.bookmarkPublicDevice(ID, ID).subscribe(subscriber);

        verify(subscriber).onNext(bookmarkCaptor.capture());

        assertThat(bookmarkCaptor.getValue().getUserId()).isEqualTo("c70faa9f-5eda-49d8-be91-a7e4b1beeca1");
    }
}