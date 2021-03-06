package org.hding.StockCrawler;

import org.hding.StockCrawler.util.Utils;
import org.hding.StockCrawler.base.Date;
import org.hding.StockCrawler.base.StockInfo;
import org.hding.StockCrawler.base.DateIterator;
import org.hding.StockCrawler.action.XlsDownloader;
import org.hding.StockCrawler.action.ExtractDailyTrans;

import java.io.FileWriter;
import java.io.File;

import java.util.Calendar;


public class Crawler {
    private StockInfo stock;
    private Date startDate;
    private Date endDate;
    private String parentPath;
    public Crawler() throws Exception {
        stock = null;
        startDate = null;
        endDate = null;
        parentPath = null;
        // Calendar c = Calendar.getInstance();
        // endDate = new Date(c.get(Calendar.YEAR),
        //                    c.get(Calendar.MONTH),
        //                    c.get(Calendar.DATE));
        // File directory = new File("");
        // parentPath = directory.getCanonicalPath() + "/data";
    }
    public Crawler(StockInfo stock) throws Exception {
        this();
        this.stock = new StockInfo(stock);
        //this.startDate = new Date(trans.getStartDate(stock));
    }
    public void setStock(StockInfo stock) throws Exception {
        this.stock = new StockInfo(stock);
    }
    public void setStartDate(Date start) {
        this.startDate = new Date(start);
    }
    public void setEndDate(Date end) {
        this.endDate = new Date(end);
    }
    public void setParentPath(String path) {
        this.parentPath = path;
    }
    public void run() throws Exception {
        ExtractDailyTrans trans = new ExtractDailyTrans();
        if (startDate == null) {
            startDate = trans.getStartDate(stock);
        }
        if (endDate == null) {
            Calendar c = Calendar.getInstance();
            endDate.setYear(c.get(Calendar.YEAR));
            endDate.setMonth(c.get(Calendar.MONTH));
            endDate.setDay(c.get(Calendar.DATE));
        }
        if (parentPath == null) {
            File directory = new File("");
            parentPath = directory.getCanonicalPath() + "/data";
        }
        String fileName = Utils.genPath(parentPath, stock) + "/" +
                "DailyTransSummary.csv";
        File file =new File(fileName);
        boolean newFileCreate = false;
        if(!file.exists()){
            //System.out.println("File creates");
            file.getParentFile().mkdirs();
            file.createNewFile();
            newFileCreate = true;
        }
        FileWriter fw = new FileWriter(fileName, true);
        if (newFileCreate) {
            //handle csv header
            fw.write("Date,Open,High,Low,Close,Volume,Amout\n");
        }
        DateIterator di = new DateIterator(startDate, endDate);
        XlsDownloader download = new XlsDownloader(startDate, stock, parentPath);
        System.out.println(download.execute());
        String tranString = trans.extract(stock, startDate);
        if (tranString != null) {
            fw.write(tranString.replaceAll(" ", ",") + "\n");
        }
        while (di.hasNext()) {
            Date date = di.next();
            download = new XlsDownloader(date, stock, parentPath);
            System.out.println(download.execute());
            tranString = trans.extract(stock, date);
            if (tranString != null) {
                fw.write(tranString.replaceAll(" ", ",") + "\n");
            }
        }
        fw.close();
    }
}