package lab02.util;

import com.google.gson.*;
import lab02.data.RequestData;
import lombok.Getter;

import java.lang.reflect.Type;

public class SingletonGson implements JsonDeserializer<RequestData> {
    @Getter
    private static final Gson instance = new GsonBuilder()
            .registerTypeAdapter(RequestData.class, new SingletonGson()) // Регистрируем текущий класс как адаптер
            .setPrettyPrinting()
            .create();

    private SingletonGson() {
    }

    @Override
    public RequestData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Парсим массив x_array из строк в double[]
        JsonArray xArray = jsonObject.getAsJsonArray("x_array");
        double[] x = new double[xArray.size()];
        for (int i = 0; i < xArray.size(); i++) {
            x[i] = Double.parseDouble(xArray.get(i).getAsString());
        }

        // Парсим y и r как double
        double y = jsonObject.get("y").getAsDouble();
        double r = jsonObject.get("r").getAsDouble();

        // Возвращаем новый объект RequestData
        return new RequestData(x, y, r);
    }
}
