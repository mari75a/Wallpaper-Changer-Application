import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UnsplashAPI {
    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/photos/random?client_id=";
    private static final String ACCESS_KEY = "PbXmN8gzOA82U-5Li9anFCdmCc5DKt4eFMgsPRNV6PY"; // Replace with your Unsplash API access key

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    
   
    public  BufferedImage getRandomImage() throws IOException {
        Request request = new Request.Builder()
                .url(UNSPLASH_API_URL + ACCESS_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JsonObject jsonResponse = gson.fromJson(response.body().string(), JsonObject.class);
            String imageUrl = jsonResponse.get("urls").getAsJsonObject().get("regular").getAsString();

            try (InputStream in = new URL(imageUrl).openStream()) {
                return ImageIO.read(in);
            }
        }
    }
}
