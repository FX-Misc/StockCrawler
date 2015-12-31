package org.hding.StockCrawler;

import org.junit.Test;
import org.junit.Assert;

import org.hding.StockCrawler.util.Utils;
import org.hding.StockCrawler.base.Date;
import org.hding.StockCrawler.base.StockInfo;

/**
 * Created by hding on 12/31/15.
 * Test
 */
public class CrawlerTest{

    @Test(timeout = 20000)
    public void testStockCrawler(){
        StockInfo stock = new StockInfo(Utils.stockType.SZ, 2108);
        Date start = new Date(2015, 9, 10);
        Date end = new Date(2015, 9, 10);
        Crawler sc;
        try {
            sc = new Crawler(stock);
            sc.setStartDate(start);
            sc.setEndDate(end);
            sc.setParentPath(System.getProperties().getProperty("user.home") + "/SCTest");
            sc.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }
}