package SCPackage;

import SCPackage.Utils;

public class Date implements Comparable<Date>{
    int year;
    int month;
    int day;
    public Date() {
        this.year = -1;
        this.month = -1;
        this.day = -1;
    }
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    //Copy constructor;
    public Date(Date otherDate) {
        this.year = otherDate.year;
        this.month = otherDate.month;
        this.day = otherDate.day;
    }
    public int maxDay() {
        //1 3 5 7 8 10 12
        if (this.month == 1 ||
            this.month == 3 ||
            this.month == 5 ||
            this.month == 7 ||
            this.month == 8 ||
            this.month == 10 ||
            this.month == 12) {
            return 31;
        } else if (this.month == 2 &&
                   this.year % 4 == 0) {
            //leap year
            return 29;
        } else if (this.month == 2 &&
                   this.year % 4 != 0) {
            return 28;
        } else {
            return 30;            
        }
    }
    public int maxDay(Date otherDate) {
        //1 3 5 7 8 10 12
        if (otherDate.month == 1 ||
            otherDate.month == 3 ||
            otherDate.month == 5 ||
            otherDate.month == 7 ||
            otherDate.month == 8 ||
            otherDate.month == 10 ||
            otherDate.month == 12) {
            return 31;
        } else if (otherDate.month == 2 &&
                   otherDate.year % 4 == 0) {
            //leap year
            return 29;
        } else if (otherDate.month == 2 &&
                   otherDate.year % 4 != 0) {
            return 28;
        } else {
            return 30;            
        }
    }
    @Override
    public int compareTo(Date otherDate) {
        if (this.year < otherDate.year) {
            return -1;
        } else if (this.year > otherDate.year) {
            return 1;
        }
        if (this.month < otherDate.month) {
            return -1;
        } else if (this.month > otherDate.month) {
            return 1;
        }
        if (this.day < otherDate.day) {
            return -1;
        } else if (this.day > otherDate.day) {
            return 1;
        }
        return 0;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Date)) {
            return false;
        }
        Date date = (Date) o;
        return year == date.year &&
               month == date.month &&
               day == date.day;
    }
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + year;
        result = 37 * result + month;
        result = 37 * result + day;
        return result;
    }
    public int getYear() {
        return this.year;
    }
    public int getMonth() {
        return this.month;
    }
    public int getDay() {
        return this.day;
    }
    public String toString() {
        return Utils.preZero(year, Utils.valType.NL, 4) + "-" + 
               Utils.preZero(month, Utils.valType.NL, 2) + "-" + 
               Utils.preZero(day, Utils.valType.NL, 2);
    }
    public static Date quarterToDate(int year, int quarter) {
        Date ret = new Date();
        ret.year = year;
        ret.month = (quarter - 1) * quarter + 1;
        ret.day = 1;
        return ret;
    }
    public void parseFromString(String str) {
        String[] strArr = str.split("-");
        year = Integer.parseInt(strArr[0]);
        month = Integer.parseInt(strArr[1]);
        day = Integer.parseInt(strArr[2]);
    }
}