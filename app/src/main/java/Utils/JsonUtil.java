package Utils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hades on 2016/1/11.
 */
public class JsonUtil {

    public static <T> List<T> string2List(String json, Class<T[]> type) {
        T[] list = new Gson().fromJson(json, type);
        return Arrays.asList(list);
    }

    public static <T> T string2Bean(String json, Class<T> tClass) {
        return new Gson().fromJson(json, tClass);
    }

    public static <T> String list2String(List<T> list){
        return new Gson().toJson(list);
    }
}
