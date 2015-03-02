package io.relayr.api;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.relayr.websocket.WebSocketClientTest;

@Module(
        complete = false,
        library = true,
        overrides = true,
        injects = {
                MockBackendTest.class,
                MockRelayrApiTest.class,
                MockStatusApiTest.class,
                MockSubscriptionApiTest.class,
                WebSocketClientTest.class
        }
)
public class TestModule {

    private final Context app;

    public TestModule(Context context) {
        app = context;
    }

    @Provides
    @Singleton
    MockBackend provideMockBackend() {
        return new MockBackend(app);
    }

    @Provides
    @Singleton
    RelayrApi provideRelayrApi(MockBackend mockBackend) {
        return new MockRelayrApi(mockBackend);
    }

    @Provides
    @Singleton
    StatusApi provideStatusApi(MockBackend loader) {
        return new MockStatusApi(loader);
    }

    @Provides
    @Singleton
    SubscriptionApi provideSubscriptionApi(MockBackend loader) {
        return new MockSubscriptionApi(loader);
    }
}
