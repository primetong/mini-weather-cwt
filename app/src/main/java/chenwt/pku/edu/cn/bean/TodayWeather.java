package chenwt.pku.edu.cn.bean;

import java.lang.ref.SoftReference;

/**
 * Created by Witt on 2017/10/12.
 */

public class TodayWeather {
    private String city, updatetime, wendu, shidu, pm25, quality, fengxiang,
            fengli, date, high, low, type;

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
