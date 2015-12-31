package SCPackage;

import SCPackage.DailyTransInfo;
import SCPackage.Date;
import SCPackage.StockInfo;
import SCPackage.Utils;
import SCPackage.DownloadHelper;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.http.client.utils.URIBuilder;

public class ExtractDailyTrans {
    private static Map<String, String> map;
    private static int year = Utils.valType.NL;
    private static int quarter = Utils.valType.NL;
    public ExtractDailyTrans() {
        //
    }
    private int dateToQuarter(Date date) {
        return (date.month - 1) / 3 + 1;
    }
    private StringBuilder inputStreamToStringBuilder(InputStream is) throws Exception {
        StringBuilder ret = new StringBuilder();
        byte[] buffer = new byte[Utils.BUFFER_SIZE];  
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            ret.append(new String(buffer, 0, length));
        }
        return ret;
    }
    private boolean jsoupImpl(InputStream is) throws Exception {
        Document doc = Jsoup.parse(inputStreamToStringBuilder(is).toString());
        Element element = doc.getElementById("FundHoldSharesTable");
        if (element == null) {
            return false;
        }
        element = element.getElementsByTag("tbody").first();
        Elements elements = element.getElementsByTag("tr");
        // for (Element node : elements) {
        //     System.out.println(node.text());
        // }
        for (int i = 1; i < elements.size(); i++) {
            String text = elements.get(i).text();
            map.put(text.split(" ")[0], text);
        }
        return true;
    }
    private boolean extractRun(StockInfo stock, Date date) throws Exception {
        String path = "/corp/go.php/vMS_MarketHistory/stockid/" + 
                      stock.numberToString() + 
                      ".phtml";
        URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("vip.stock.finance.sina.com.cn")
            .setPath(path)
            .setParameter("year", String.valueOf(date.year))
            .setParameter("jidu", String.valueOf(dateToQuarter(date)))
            .build();        
        DownloadHelper download = new DownloadHelper(uri);
        InputStream is = download.getInputStream();
        boolean ret = jsoupImpl(is);
        is.close();
        download.close();
        return ret;
    } 
    public String extract(StockInfo stock, Date date) throws Exception {
        String trans = null;
        if (year == date.year && 
            quarter == dateToQuarter(date)) {
            trans = map.get(date.toString());
        } else {
            map = new HashMap<String, String>(70);
            if (extractRun(stock, date)) {
                year = date.year;
                quarter = dateToQuarter(date);
                trans = map.get(date.toString());
            } else {
                year = Utils.valType.NL;
                quarter = Utils.valType.NL;
                trans = null;
            }
        }
        return trans;
    }
    private int tryQuarter(StockInfo stock, String year) throws Exception {
        String path = "/corp/go.php/vMS_MarketHistory/stockid/" + 
                      stock.numberToString() + 
                      ".phtml";
        for (int i = 1; i <= 4; i++) {
            URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("vip.stock.finance.sina.com.cn")
                .setPath(path)
                .setParameter("year", year)
                .setParameter("jidu", String.valueOf(i))
                .build();
            DownloadHelper download = new DownloadHelper(uri);
            InputStream is = download.getInputStream();
            Document doc = Jsoup.parse(inputStreamToStringBuilder(is).toString()); 
            is.close();
            download.close(); 
            Element element = doc.getElementById("FundHoldSharesTable");
            if (element != null) {
                //find first quarter
                return i;
            }           
        }        
        return 1;
    }
    public Date getStartDate(StockInfo stock) throws Exception {
        String path = "/corp/go.php/vMS_MarketHistory/stockid/" + 
                      stock.numberToString() + 
                      ".phtml";
        URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("vip.stock.finance.sina.com.cn")
            .setPath(path)
            .setParameter("year", "1980")
            .setParameter("jidu", "1")
            .build();        
        DownloadHelper download = new DownloadHelper(uri);
        InputStream is = download.getInputStream();
        Document doc = Jsoup.parse(inputStreamToStringBuilder(is).toString());
        is.close();
        download.close(); 
        Elements select = doc.getElementsByAttributeValue("name", "year");
        if (select == null) {
            return null;
        }
        //System.out.println(select.size());
        Elements years = select.get(0).getElementsByTag("option");
        String year = years.get(years.size() - 1).text();
        //System.out.println(year);
        return Date.quarterToDate(Integer.parseInt(year), tryQuarter(stock, year));
    }
}