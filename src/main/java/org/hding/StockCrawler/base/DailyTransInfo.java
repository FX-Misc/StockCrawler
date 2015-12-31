package SCPackage;

import SCPackage.Date;

public class  DailyTransInfo {
    Date date;

    float open;
    float high;
    float close;
    float low;

    long volume;
    long amount;
    public DailyTransInfo(Date date, 
                          float open, float high, 
                          float close, float low,
                          long volume, long amount) {
        this.date = new Date(date);
        this.open = open;
        this.high = high;
        this.close = close;
        this.low = low;
        this.volume = volume;
        this.amount = amount;
    }
    public DailyTransInfo(DailyTransInfo otherTrans) {
        this.date = new Date(otherTrans.date);
        this.open = otherTrans.open;
        this.high = otherTrans.high;
        this.close = otherTrans.close;
        this.low = otherTrans.low;
        this.volume = otherTrans.volume;
        this.amount = otherTrans.amount;        
    }
    public float getOpen() {
        return open;
    }
    public float getHigh() {
        return high;
    }
    public float getClose() {
        return close;
    }
    public float getLow() {
        return low;
    }
    public long getVolume() {
        return volume;
    }
    public long getAmount() {
        return amount;
    }
    public void parseFromString(String str) {
        String[] strArr = str.split(" ");
        date.parseFromString(strArr[0]);
        open = Float.parseFloat(strArr[1]);
        high = Float.parseFloat(strArr[2]);
        close = Float.parseFloat(strArr[3]);
        low = Float.parseFloat(strArr[4]);
        volume = Long.parseLong(strArr[5]);
        amount = Long.parseLong(strArr[6]);
    }
}