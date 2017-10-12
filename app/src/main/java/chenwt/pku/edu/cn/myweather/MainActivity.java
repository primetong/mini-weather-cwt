package chenwt.pku.edu.cn.myweather;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import chenwt.pku.edu.cn.bean.TodayWeather;
import chenwt.pku.edu.cn.util.NetUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int UPDATE_TODAY_WEATHER = 1;

    private TextView cityTv, timeTv, temperNowTv,humidityTv, weekTv, pmDataTv, pmQualityTv,
            temperatureTv, climateTv, windTv, city_name_Tv;
    private ImageView weatherImg, pmImg;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);

        //↓调用检测网络连接状态方法
        if(NetUtil.getNetworkState(this) != NetUtil.NETWORK_NONE){
            Log.d("myWeather", "网络Online！");
            if(NetUtil.getNetworkState(this) == NetUtil.NETWORK_MOBILE)
                Toast.makeText(MainActivity.this, "移动网络在线", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "WiFi在线" ,Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("myWeather", "网络Offline！");
            Toast.makeText(MainActivity.this, "连不上网络哟~请检查网络设置~" ,Toast.LENGTH_LONG).show();
        }

        initview();

        ImageView mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        //onCreate方法的小尾巴
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_update_btn){
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code", "101010100");
            Log.d("myWeather", cityCode);

            if(NetUtil.getNetworkState(this) != NetUtil.NETWORK_NONE){
                Log.d("myWeather", "网络Online！");
                if(NetUtil.getNetworkState(this) == NetUtil.NETWORK_MOBILE) {
                    Toast.makeText(MainActivity.this, "正在使用移动网络为您更新数据", Toast.LENGTH_SHORT).show();
                    queryWeatherCode(cityCode);
                }
                else {
                    Toast.makeText(MainActivity.this, "正在使用WiFi为您更新数据", Toast.LENGTH_SHORT).show();
                    queryWeatherCode(cityCode);
                }
            }
            else{
                Log.d("myWeather", "网络Offline！");
                Toast.makeText(MainActivity.this, "连不上网络哟~请检查网络设置~" ,Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 初始化控件内容函数↓
     * *@param void
     */
    void initview(){
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        temperNowTv = (TextView) findViewById(R.id.temperature_now);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        temperNowTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
    }

    /**
     * 获取网络（天气）数据函数↓
     * @param cityCode
     */
    private void queryWeatherCode(String cityCode){
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlcon = null;
                TodayWeather todayWeather = null;
                try{
                    URL url = new URL(address);
                    urlcon = (HttpURLConnection) url.openConnection();
                    urlcon.setRequestMethod("GET");
                    urlcon.setConnectTimeout(8000);
                    urlcon.setReadTimeout(8000);
                    if (urlcon.getResponseCode() == 200){       //状态码200为正常
                        InputStream is = urlcon.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                        StringBuilder response = new StringBuilder();
                        String strdata;
                        while ((strdata = reader.readLine()) != null){
                            response.append(strdata);
                            Log.d("myWeather", strdata);
                        }
                        String responseStr = response.toString();
                        Log.d("myWeather", responseStr);

                        todayWeather = parseXML(responseStr);
                        if (todayWeather != null){
                            Log.d("myWeather", todayWeather.toString());

                            Message msg = new Message();
                            msg.what = UPDATE_TODAY_WEATHER;
                            msg.obj = todayWeather;
                            mHandler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlcon != null){
                        urlcon.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * XML解析函数↓
     * @param xmldata
     */
    private TodayWeather parseXML(String xmldata){
        TodayWeather todayWeather = null;
        int fengxiangCount = 0, fengliCount = 0, dateCount = 0,  highCount = 0, lowCount = 0, typeCount = 0;
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML解析函数开始解析");
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:  //判断当前读到的事件类型是否为文档开始事件
                        break;
                    case XmlPullParser.START_TAG:   //判断当前读到的事件类型是否为标签元素开始事件
                        if (xmlPullParser.getName().equals("resp"))
                            todayWeather = new TodayWeather();
                        if (todayWeather != null){
                            if (xmlPullParser.getName().equals("city")){
                                eventType = xmlPullParser.next();
                                todayWeather.setCity(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")){
                                eventType = xmlPullParser.next();
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            }
                            else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh(xmlPullParser.getText());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setLow(xmlPullParser.getText());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setType(xmlPullParser.getText());
                                typeCount++;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:     //判断当前读到的事件类型是否为标签元素结束事件
                        break;
                }
                eventType = xmlPullParser.next();       //进入下一个元素并触发相应事件
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayWeather;
    }

    /**
     * updateTodayWeather函数用于更新UI中的控件↓
     * *@object TodayWeather
     */
    void updateTodayWeather(TodayWeather todayWeather){
        String weatherType = todayWeather.getType();
        int pm25State = Integer.parseInt(todayWeather.getPm25());

        city_name_Tv.setText(todayWeather.getCity() + "天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime() + "发布");
        temperNowTv.setText("温度：" + todayWeather.getWendu() + "℃");
        humidityTv.setText("湿度：" + todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(todayWeather.getHigh() + "~" + todayWeather.getLow());
        climateTv.setText(weatherType);
        windTv.setText("风力：" + todayWeather.getFengli());

        switch (weatherType){
            case "晴":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
                break;
            case "暴雪":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                break;
            case "暴雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                break;
            case "大暴雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                break;
            case "大雪":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
                break;
            case "大雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_dayu);
                break;
            case "多云":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                break;
            case "雷阵雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                break;
            case "雷阵雨冰雹":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                break;
            case "沙尘暴":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                break;
            case "特大暴雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                break;
            case "雾":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
                break;
            case "小雪":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                break;
            case "小雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                break;
            case "阴":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
                break;
            case "雨夹雪":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                break;
            case "阵雪":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                break;
            case "阵雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                break;
            case "中雪":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                break;
            case "中雨":
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                break;
            default:
                weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
                break;
        }

        if (pm25State >= 0 & pm25State <= 50)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
        else if (pm25State >= 51 & pm25State <= 100)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
        else if (pm25State >= 101 & pm25State <= 150)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
        else if (pm25State >= 151 & pm25State <= 200)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
        else if (pm25State >= 201 & pm25State <= 300)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
        else if (pm25State >= 301)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        else
            pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);

        Toast.makeText(MainActivity.this, "天气已更新成功！", Toast.LENGTH_SHORT).show();
    }

}
