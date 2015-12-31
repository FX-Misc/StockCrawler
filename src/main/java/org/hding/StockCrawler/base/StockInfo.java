package org.hding.StockCrawler.base;

import org.hding.StockCrawler.util.Utils;

public class StockInfo {
    int type;
    int number;
    String name;
    public StockInfo(StockInfo otherStock) {
        this.type = otherStock.type;
        this.number = otherStock.number;
        this.name = otherStock.name;
    }
    public StockInfo() {
        this.type = Utils.stockType.IV;
        this.number = Utils.valType.NL;
        this.name = "";
    }
    public StockInfo(int type, int number) {
        this.type = type;
        this.number = number;
        this.name = "";
    }
    public StockInfo(int type, int number, String name) {
        this.type = type;
        this.number = number;
        this.name = name;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return this.type;
    }
    public int getNumber() {
        return this.number;
    }
    public String getName() {
        return this.name;
    }
    public String numberToString() {
        String ret = "";
        switch (type) {
            case Utils.stockType.SZ:
                ret += Utils.preZero(number, Utils.valType.NL, 6);
                break;
            case Utils.stockType.SH:
                ret += Utils.preZero(number, 6, 6);
                break;
        }
        return ret;       
    }
    public String typeToString() {
        String ret = "";
        switch (type) {
            case Utils.stockType.SZ:
                ret += "sz";
                break;
            case Utils.stockType.SH:
                ret += "sh";
                break;
        }
        return ret;
    }
    public String toString() {
        return typeToString() + numberToString();
    }
} 