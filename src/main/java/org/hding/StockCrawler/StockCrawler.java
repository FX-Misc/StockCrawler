package SCPackage;

import SCPackage.Utils;
import SCPackage.Date;
import SCPackage.StockInfo;
import SCPackage.DateIterator;
import SCPackage.XlsDownloader;
import SCPackage.DailyTransInfo;
import SCPackage.ExtractDailyTrans;

import java.io.FileWriter;  
import java.io.File;

import java.util.Calendar;


public class StockCrawler {
    private StockInfo stock;
    private Date startDate;
    private Date endDate;
    private String parentPath;
    public StockCrawler() throws Exception {
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
    public StockCrawler(StockInfo stock) throws Exception {
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
            endDate.year = c.get(Calendar.YEAR);
            endDate.month = c.get(Calendar.MONTH);
            endDate.day = c.get(Calendar.DATE);
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