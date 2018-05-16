package cn.tties.maint.widget;

import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by guider on 2018/1/6.
 */

public class JsonResponseParser implements ResponseParser {

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
      return  new Gson().fromJson(result, resultClass);

    }

    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }
}
