package SCPackage;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import SCPackage.Utils;
import SCPackage.Date;
import SCPackage.StockInfo;
import SCPackage.DownloadHelper;

public class XlsDownloader {
    private Date date;
    private StockInfo stock;
    private String parentPath;
    public XlsDownloader(Date date, StockInfo stock, String parentPath) {
        this.date = new Date(date);
        this.stock = new StockInfo(stock);
        this.parentPath = parentPath;
    }
    private String genPath() {
        return Utils.genPath(parentPath, stock) + "/" +
               date.getYear() + "/" +
               Utils.preZero(date.getMonth(), Utils.valType.NL, 2);
    }
    private String genFileName() {
        return stock.toString() + "&" + 
               date.toString() + "&" +
               "DailyDetails" + ".xls";
    }
    public boolean execute() throws Exception {
        URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("market.finance.sina.com.cn")
            .setPath("/downxls.php")
            .setParameter("date", date.toString())
            .setParameter("symbol", stock.toString())
            .build();
        DownloadHelper download = new DownloadHelper(uri, "Content-Disposition");
        InputStream is = download.getInputStream();
        if (is == null) {
            return false;
        }
        String filePath = genPath() + "/" + 
                          genFileName();
        System.out.println(filePath);
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileOutputStream fileOut = new FileOutputStream(file);
        byte[] buffer = new byte[Utils.BUFFER_SIZE];  
        int length = 0;  
        while ((length = is.read(buffer)) != -1) {  
            fileOut.write(buffer, 0, length);  
        }
        is.close();  
        fileOut.flush();  
        fileOut.close();
        download.close();
        return true;     
    }
}