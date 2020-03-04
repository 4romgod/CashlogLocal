package com.silverback.lucy.cashlog.Model.ObjectTemplate;


public class MyDate {

    int year;
    int month;
    int day;
    int hours;
    int min;
    int sec;

    public MyDate() {
        this.year = 0;
        this.month = 0;
        this.day = 0;
        this.hours = 0;
        this.min = 0;
        this.sec = 0;
    }

    public MyDate(int year, int month, int day, int hours, int min, int sec) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.min = min;
        this.sec = sec;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hours=" + hours +
                ", min=" + min +
                ", sec=" + sec +
                '}';
    }
}
