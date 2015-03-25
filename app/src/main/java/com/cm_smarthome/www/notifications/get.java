package com.cm_smarthome.www.notifications;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdminPond on 21/3/2558.
 */
public class get {

    protected String x;

    public void getS()
    {
        String url = "http://www.cm-smarthome.com/android/status.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();//คำอธิบายอยู่ด้านล่าง
        params.add(new BasicNameValuePair("ID", "1"));//คำอธิบายอยู่ด้านล่าง
        String resultServer  = getHttpPost(url,params);//คำอธิบายอยู่ด้านล่าง

        //ส่งไป PHP แล้ว PHP รับ $strWhere = $_POST["ID"]; เผื่อแอพต้อง login
        //เพื่อแจ้งเตือนระบุคนไปเลยแต่ถ้าไม่มีการระบุก็ มะต้องแก้ก่อได้ หื้อ มัน ส่งไป เฉยๆๆ มะ ได้ เอา ไป Query
        //ถ้าต้องการแก้ แก้ Method getHttpPost ตวยเนอะ ฮาทำเผื่อคิงต้องการเฉยๆๆ

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            x = c.getString("Status");

            Log.e("GET",x);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
