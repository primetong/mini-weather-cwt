package chenwt.pku.edu.cn.bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Witt on 2017/10/12.
 */

public class TodayWeather {     //公共类用于保存共有今日天气数据，存入（setXXX）和读取（getXXX）都通过面向对象的方法来处理

    private SharedPreferences sp; //实例化SharedPreference对象，用于存储联网解析之后获取的所有数据

    private String city, updatetime, wendu, shidu, fengxiang,
            fengli, date, high, low, type;
    private String pm25 = "该地区", quality = "没有PM2.5信息"; //由于部分地区不返回PM2.5数值与质量，返回的是null值，需要设初值防止空指针异常

    //通过工具提供的功能生成get方法。
    public String getCity(){
        return city;
    }

    public String getUpdatetime(){
        return updatetime;
    }

    public String getWendu(){
        return wendu;
    }

    public String getShidu(){
        return shidu;
    }

    public String getPm25(){
        return pm25;
    }

    public String getQuality(){
        return quality;
    }

    public String getFengxiang(){
        return fengxiang;
    }

    public String getFengli(){
        return fengli;
    }

    public String getDate(){
        return date;
    }

    public String getHigh(){
        return high;
    }

    public String getLow(){
        return low;
    }

    public String getType(){
        return type;
    }
    //通过工具提供的功能生成set方法。
    public void setCity(String city){
        this.city = city;
    }

    public void setUpdatetime(String updatetime){
        this.updatetime = updatetime;
    }

    public void setWendu(String wendu){
        this.wendu = wendu;
    }

    public void setShidu(String shidu){
        this.shidu = shidu;
    }

    public void setPm25(String pm25){
        this.pm25 = pm25;
    }

    public void setQuality(String quality){
        this.quality = quality;
    }

    public void setFengxiang(String fengxiang){
        this.fengxiang = fengxiang;
    }

    public void setFengli(String fengli){
        this.fengli = fengli;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setHigh(String high){
        this.high = high;
    }

    public void setLow(String low){
        this.low = low;
    }

    public void setType(String type){
        this.type = type;
    }
    //用于存储联网解析之后获取的所有数据↓
    public void saveAllData(Activity activity){
        sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("CITY", city);
        editor.putString("UPDATETIME", updatetime);
        editor.putString("WENDU", wendu);
        editor.putString("SHIDU", shidu);
        editor.putString("PM25", pm25);
        editor.putString("QUALITY", quality);
        //editor.putString("FENGXIANG", fengxiang);
        editor.putString("FENGLI", fengli);
        editor.putString("DATE", date);
        editor.putString("HIGH", high);
        editor.putString("LOW", low);
        editor.putString("TYPE", type);
        editor.commit();
    }
    //通过工具提供的功能生成toString方法。
    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", quality='" + quality + '\'' +
                ", fengli='" + fengli + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
