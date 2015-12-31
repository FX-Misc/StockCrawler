package org.hding.StockCrawler;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert;

import org.hding.StockCrawler.util.Utils;
import org.hding.StockCrawler.base.Date;
import org.hding.StockCrawler.base.StockInfo;

/**
 * Created by hding on 12/31/15.
 */
public class StockCrawlerTest extends TestCase {

    @Test(timeout = 20000)
    public void testStockCrawler() throws Exception {
        StockInfo stock = new StockInfo(Utils.stockType.SZ, 2108);
        Date start = new Date(2015, 9, 9);
        Date end = new Date(2015, 9, 10);
        Crawler sc = new Crawler(stock);
        sc.setStartDate(start);
        sc.setEndDate(end);
        sc.run();
        Assert.assertTrue(true);
    }
}