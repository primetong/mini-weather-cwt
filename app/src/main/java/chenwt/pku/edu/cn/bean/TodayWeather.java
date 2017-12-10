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

    private String city = "无", updatetime = "未", wendu = "无信息", shidu = "无信息",
            fengxiang = "无风向信息", fengli = "无信息", date = "无日期信息",
            high = "无", low = "无", type = "该地区没有天气信息";
    private String pm25 = "该地区", quality = "没有PM2.5信息"; //由于部分地区不返回PM2.5数值与质量以及相关天气信息，返回的是null值，需要设初值防止空指针异常
    private String yesterdayDate = "昨日无信息", yesterdayHigh = "无", yesterdayLow = "无", yesterdayType = "无",
            //todayDate = "今日无信息", todayHigh = "无", todayLow = "无", todayType = "无",
            tomorrowDate = "明日无信息", tomorrowHigh = "无", tomorrowLow = "无", tomorrowType = "无",
            future2Date = "后日无信息", future2High = "无", future2Low = "无", future2Type = "无",
            future3Date = "未来3天无信息", future3High = "无", future3Low = "无", future3Type = "无",
            future4Date = "未来4天无信息", future4High = "无", future4Low = "无", future4Type = "无";

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

    public String getYesterdayDate() {
        return yesterdayDate;
    }

    public String getYesterdayHigh() {
        return yesterdayHigh;
    }

    public String getYesterdayLow() {
        return yesterdayLow;
    }

    public String getYesterdayType() {
        return yesterdayType;
    }

    public String getTomorrowDate() {
        return tomorrowDate;
    }

    public String getTomorrowHigh() {
        return tomorrowHigh;
    }

    public String getTomorrowLow() {
        return tomorrowLow;
    }

    public String getTomorrowType() {
        return tomorrowType;
    }

    public String getFuture2Date() {
        return future2Date;
    }

    public String getFuture2High() {
        return future2High;
    }

    public String getFuture2Low() {
        return future2Low;
    }

    public String getFuture2Type() {
        return future2Type;
    }

    public String getFuture3Date() {
        return future3Date;
    }

    public String getFuture3High() {
        return future3High;
    }

    public String getFuture3Low() {
        return future3Low;
    }

    public String getFuture3Type() {
        return future3Type;
    }

    public String getFuture4Date() {
        return future4Date;
    }

    public String getFuture4High() {
        return future4High;
    }

    public String getFuture4Low() {
        return future4Low;
    }

    public String getFuture4Type() {
        return future4Type;
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

    public void setYesterdayDate(String yesterdayDate) {
        this.yesterdayDate = yesterdayDate;
    }

    public void setYesterdayHigh(String yesterdayHigh) {
        this.yesterdayHigh = yesterdayHigh;
    }

    public void setYesterdayLow(String yesterdayLow) {
        this.yesterdayLow = yesterdayLow;
    }

    public void setYesterdayType(String yesterdayType) {
        this.yesterdayType = yesterdayType;
    }

    public void setTomorrowDate(String tomorrowDate) {
        this.tomorrowDate = tomorrowDate;
    }

    public void setTomorrowHigh(String tomorrowHigh) {
        this.tomorrowHigh = tomorrowHigh;
    }

    public void setTomorrowLow(String tomorrowLow) {
        this.tomorrowLow = tomorrowLow;
    }

    public void setTomorrowType(String tomorrowType) {
        this.tomorrowType = tomorrowType;
    }

    public void setFuture2Date(String future2Date) {
        this.future2Date = future2Date;
    }

    public void setFuture2High(String future2High) {
        this.future2High = future2High;
    }

    public void setFuture2Low(String future2Low) {
        this.future2Low = future2Low;
    }

    public void setFuture2Type(String future2Type) {
        this.future2Type = future2Type;
    }

    public void setFuture3Date(String future3Date) {
        this.future3Date = future3Date;
    }

    public void setFuture3High(String future3High) {
        this.future3High = future3High;
    }

    public void setFuture3Low(String future3Low) {
        this.future3Low = future3Low;
    }

    public void setFuture3Type(String future3Type) {
        this.future3Type = future3Type;
    }

    public void setFuture4Date(String future4Date) {
        this.future4Date = future4Date;
    }

    public void setFuture4High(String future4High) {
        this.future4High = future4High;
    }

    public void setFuture4Low(String future4Low) {
        this.future4Low = future4Low;
    }

    public void setFuture4Type(String future4Type) {
        this.future4Type = future4Type;
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
        editor.putString("YESDATE", yesterdayDate);
        editor.putString("YESHIGH", yesterdayHigh);
        editor.putString("YESLOW", yesterdayLow);
        editor.putString("YESTYPE", yesterdayType);
        editor.putString("TOMDATE", tomorrowDate);
        editor.putString("TOMHIGH", tomorrowHigh);
        editor.putString("TOMLOW", tomorrowLow);
        editor.putString("TOMTYPE", tomorrowType);
        editor.putString("FU2DATE", future2Date);
        editor.putString("FU2HIGH", future2High);
        editor.putString("FU2LOW", future2Low);
        editor.putString("FU2TYPE", future2Type);
        editor.putString("FU3DATE", future3Date);
        editor.putString("FU3HIGH", future3High);
        editor.putString("FU3LOW", future3Low);
        editor.putString("FU3TYPE", future3Type);
        editor.putString("FU4DATE", future4Date);
        editor.putString("FU4HIGH", future4High);
        editor.putString("FU4LOW", future4Low);
        editor.putString("FU4TYPE", future4Type);
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
