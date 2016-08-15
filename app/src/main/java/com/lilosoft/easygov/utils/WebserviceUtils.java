package com.lilosoft.easygov.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by chablis on 2016/8/10.
 */
public class WebserviceUtils {
    private static int setHttpTimeOut = 20 * 1000;

    public int getSetHttpTimeOut() {
        return setHttpTimeOut;
    }

    public void setSetHttpTimeOut(int setHttpTimeOut) {
        this.setHttpTimeOut = setHttpTimeOut;
    }

    /*
    * 获取WebService数据，并以字符形式返回。
    * @param Url: WebService服务地址 (http://webservice.***.com.cn/WeatherWS.asmx)
    * @param NameSpace: WebService的服务的命名空间，可以WSDL数据中找到 (http://***.com.cn/)
    * @param MethodName: WebService的调用函数方法名称(getDataMethod)
    * @param Maps: 请求服务需要提交的数据集
    * @Return: 服务以字符类型返回请求数据
    * @Exception: 写入控制台日志
    */
    public static JSONArray getArray(String Url, String NameSpace, String MethodName, Map<String, String> RequestDatas) throws IOException, XmlPullParserException, JSONException {
        JSONObject obj=getObject(Url,NameSpace,MethodName,RequestDatas);
        if(obj!=null){
            JSONArray userInfos = obj.getJSONArray("data");
            return userInfos;
        }
        return null;
    }

    public static String getString(String Url, String NameSpace, String MethodName, Map<String, String> RequestDatas) throws IOException, XmlPullParserException, JSONException {
        JSONObject obj=getObject(Url,NameSpace,MethodName,RequestDatas);
        if(obj!=null){
            JSONArray userInfos = obj.getJSONArray("data");
            return userInfos.toString();
        }
        return null;
    }

    public static JSONObject getObject(String Url, String NameSpace, String MethodName, Map<String, String> RequestDatas) throws IOException, XmlPullParserException, JSONException {
        SoapObject request = new SoapObject(NameSpace, MethodName);
        // 设置WebService提交的数据集
        if (RequestDatas != null && !RequestDatas.isEmpty()) {
            for (Map.Entry<String, String> entry : RequestDatas.entrySet()) {
                request.addProperty(entry.getKey(), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        //"http://124.202.158.107:8022/wisdomgov/ws/channelService?wsdl=ChannelWS.wsdl"
        HttpTransportSE trans = new HttpTransportSE(Url, setHttpTimeOut);
        trans.debug = true;
        trans.call(NameSpace + MethodName, envelope);
        String a = envelope.bodyIn.toString();
        SoapObject result = (SoapObject) envelope.bodyIn;
        int count = result.getPropertyCount();
        if (count > 0) {
            SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
            JSONObject jsonO = new JSONObject(object.toString());
            return jsonO;
        }
        return null;
    }
}
