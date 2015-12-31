package org.hding.StockCrawler.util;

import SCPackage.StockInfo;

public class Utils {
    public final static int BUFFER_SIZE = 10 * 1024;
    public static String preZero(int val, int pre, int width) {
        String valString = Integer.toString(val);
        String preString = "";
        if (pre != Utils.valType.NL) {
            preString = Integer.toString(pre);
        }
        String ret = preString;
        for (int i = 0; i < width - valString.length() - preString.length(); i++) {
            ret += "0";
        }
        ret += valString;
        return ret;
    }
    public static String genPath(String parentPath, StockInfo stock) {
        return parentPath + "/" +
               stock.typeToString() + "/" +
               stock.toString();        
    }
    public class valType {
        public final static int NL = -1;
    }
    public class stockType {
        public final static int IV = 0;
        public final static int SZ = 1;
        public final static int SH = 2;
    }
    public class Fees {
        //public final static float 
    }
}