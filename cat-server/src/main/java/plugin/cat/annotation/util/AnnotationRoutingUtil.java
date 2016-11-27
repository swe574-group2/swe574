package plugin.cat.annotation.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import plugin.cat.annotation.model.Annotation;

import java.util.concurrent.TimeUnit;

/**
 * Created by Arda on 11/27/2016.
 */
public class AnnotationRoutingUtil {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static String makeHttpGet(String url) {
        OkHttpClient client = createHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void createAnnotation(Annotation annotation) {
        OkHttpClient client = createHttpClient();
        Gson gson = createGson();
        String formBodyStr = gson.toJson(annotation);
        RequestBody formBody = RequestBody.create(MEDIA_TYPE_JSON, formBodyStr);

        System.out.println("createAnnotation " + formBodyStr);

        Request request = new Request.Builder()
                .url("http://localhost:8080/annotation/")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("createAnnotation " + response.body().string());
            System.out.println("createAnnotation code = " + response.code());
//            return response.code();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Gson createGson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        return gson;
    }

    private static OkHttpClient createHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .build();

        return client;
    }
}
