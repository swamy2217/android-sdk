package io.relayr.api.mock;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class MockBackend {

    public static final String AUTHORISE_USER = "authorise_user.json";
    public static final String USER_INFO = "user-info.json";
    public static final String APP_INFO = "app-info.json";
    public static final String USER_DEVICES = "user_devices.json";
    public static final String USER_DEVICE = "user_device.json";
    public static final String WEB_SOCKET_READINGS = "web_socket_reading.json";
    public static final String USERS_CREATE_WUNDERBAR = "users_create_wunderbar.json";
    public static final String USERS_TRANSMITTERS = "users_transmitters.json";
    public static final String USERS_TRANSMITTER = "users_transmitter.json";
    public static final String TRANSMITTER_DEVICES = "users_transmitters_devices.json";
    public static final String APPS_DEVICES_START = "apps_devices_start.json";
    public static final String PUBLIC_DEVICES = "public_devices.json";
    public static final String BOOKMARK_DEVICE = "bookmark_device.json";
    public static final String BOOKMARKED_DEVICES = "bookmarked_devices.json";
    public static final String SERVER_STATUS = "server-info.json";
    public static final String MQTT_CREDENTIALS = "mqtt_channel.json";

    public static final String USER_ACCOUNTS = "user_accounts.json";
    public static final String USER_ACCOUNT_DEVICES = "user_accounts_devices.json";
    public static final String USER_ACCOUNT_LOGIN_URL = "user_accounts_login_url.json";

    public static final String USER_GROUP = "user_group.json";
    public static final String USER_GROUPS = "user_groups.json";

    public static final String DEVICE_MODEL = "device_model.json";
    public static final String DEVICE_MODELS = "device_models.json";
    public static final String DEVICE_MODEL_FIRMWARES = "device_model_firmwares.json";
    public static final String DEVICE_MODEL_FIRMWARE = "device_model_firmware.json";
    public static final String DEVICE_READING_MEANINGS = "device_reading_meanings.json";

    private final Context mContext;
    private final Gson mGson;

    @Inject
    public MockBackend(Context context) {
        mContext = context;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        mGson = gsonBuilder.create();
    }

    public String load(String fileName) throws Exception {
        StringBuilder fileContent = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            inputStream = mContext.getAssets().open(fileName);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        } finally {
            if (inputStreamReader != null)
                inputStreamReader.close();
            if (inputStream != null)
                inputStream.close();
            if (reader != null)
                reader.close();
        }
        return fileContent.toString();
    }

    public <T> T load(TypeToken<T> typeToken, String resource) throws Exception {
        return mGson.fromJson(load(resource), typeToken.getType());
    }

    public Object[] getWebSocketReadings() {
        try {
            List<Object> readings = load(new TypeToken<List<Object>>() {
            }, WEB_SOCKET_READINGS);
            return readings.toArray(new Object[readings.size()]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> Observable<T> createObservable(final TypeToken<T> typeToken, final String asset) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(load(typeToken, asset));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = null;
            try {
                date = sdf.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
    }

}