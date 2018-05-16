package cn.tties.maint.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Description : Json转换工具类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/17
 */
public class JsonUtils {

    private static Gson mGson = new Gson();

    /**
     * 将对象准换为json字符串
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转换为实体对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(JsonElement json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转换为实体对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }


    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
//    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException {
//        return mGson.fromJson(json, type);
//    }

    public static <T> List<T> deserialize(JsonElement json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }

//    public static <T> List<T> deserializeList(JsonElement json, Class<T> clz) throws Exception {
//        List<T> pojoList = new ArrayList<T>();
//
//        List<Map<String, Object>> list = mGson.fromJson(json, new TypeToken<List<T>>() {
//        }.getType());
//        for (Map<String, Object> map : list) {
//            T pojo = clz.newInstance();
//            map2PO(map, pojo);
//            pojoList.add(pojo);
//        }
//        return pojoList;
//    }
//
//    public static <T> List<T> deserializeList(String json, Class<T> clz) throws Exception {
//        List<T> pojoList = new ArrayList<T>();
//        List<Map<String, Object>> list = mGson.fromJson(json, new TypeToken<List<T>>() {
//        }.getType());
//        for (Map<String, Object> map : list) {
//            T pojo = clz.newInstance();
//            map2PO(map, pojo);
//            pojoList.add(pojo);
//        }
//        return pojoList;
//    }

    public static <T> T map2PO(Map<String, Object> map, Object o) throws Exception {
        if (!map.isEmpty()) {
            for (String k : map.keySet()) {
                Object v = "";
                if (!k.isEmpty()) {
                    v = map.get(k);
                }
                Field[] fields = null;
                fields = o.getClass().getDeclaredFields();
                String clzName = o.getClass().getSimpleName();
                for (Field field : fields) {
                    int mod = field.getModifiers();
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }
                    if (field.getName().equalsIgnoreCase(k)) {
                        Log.d("123", field.getName());
                        field.setAccessible(true);
                        field.set(o, v);
                    }

                }
            }
        }
        return (T) o;
    }

    public Gson getGson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Date.class, new TimestampTypeAdapter()).registerTypeAdapter(Date.class, new SQLDateTypeAdapter()).create();
        return gson;
    }

    private class TimestampTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private final DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.UK);

        public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2) {
            String dateFormatAsString = format.format(new Date(src.getTime()));
            return new JsonPrimitive(dateFormatAsString);
        }

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                Date date = format.parse(json.getAsString());
                return new Date(date.getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }

    /**
     * gson使用toJson时处理数据库中的date类型的时间数据
     *
     * @author jinjinwang <br>
     *         created on Jun 9, 2013 11:48:29 AM
     */
    private class SQLDateTypeAdapter implements JsonSerializer<Date> {
        private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2) {
            String dateFormatAsString = format.format(new Date(src.getTime()));
            return new JsonPrimitive(dateFormatAsString);
        }
    }

    public static String getJsonObjectStr(Object obj) {
        return mGson.toJson(obj);
    }

    public static String getJsonArrayStr(List list) {
        JsonElement element = new Gson().toJsonTree(list);
        return element.toString();
    }
}
