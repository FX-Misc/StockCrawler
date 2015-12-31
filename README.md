# StockCrawler
 A web crawler to extract transaction information from SINA finance.
 
## Introduction
SINA finance has stored history transaction information of Chinese stocks that have gone public at the Shanghai Exchange and the Shenzhen Exchange. There are two kinds of transaction information: one is daily summary that records open, high, close, low prices, volume, and amount of each working day; the other one is detailed transaction records within one day. Not every day has detailed transaction records.

This program can extract both kinds of information from SINA finance and store locally in **.csv** and **.xls** formats, respectively. 

Happy hacking!

# StockCrawler

新浪财经的股票交易信息的网页爬虫。

## 介绍

一直想分析下股票的交易数据，特别是对于中国A股信息。一些行情和交易软件里面可能能导出来交易信息，没有尝试，可能会比较麻烦。正好新浪财经有A股的历史交易信息，所以可以从新浪上爬出来这些数据。新浪上的数据有两种：一种是每天的交易概况，有当天的开盘价，最高价，收盘价，交易量和交易金额；另一种每天的当天交易详情，具体到分钟的交易数据。不是所有的工作日都有交易详情的，只有大概最近10年才有。这个程序可以把两种交易信息都爬下来，分别存成csv和xls格式。