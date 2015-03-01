package com.oracle.lliyu;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 2/26/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.oracle.lliyu.utils.PdfConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.*;

public class SafariBookParser
{
    private static String safariBookOnlineWebSite = "http://techbus.safaribooksonline.com";
    private static String bodyClassName = "htmlcontent";
    private static String navbarRightAttriKey = "id";
    private static String navbarRightAttriValue = "navbarright";


    public static void main(String[] args) throws Exception
    {
        if(args.length!=1)
        {
            System.out.println("wrong number of parameters");
            return;
        }

        //String url = args[0];
        String url = "http://bbs.sjtu.edu.cn";
        PdfConverter.convertToPDFwithUrl(url);
        //PdfConverter.convertToPDFwithHtmlString(url);
        //downloadBookStartFromUrl(url);
    }

    private static void downloadBookStartFromUrl(String url) throws Exception
    {
        Boolean hasNextPage = true;
        Boolean isFirstPage = true;
        String nextUrlRelativePath="";

        while(hasNextPage)
        {
            if(!isFirstPage)
            {
                url = safariBookOnlineWebSite + nextUrlRelativePath;
            }
            isFirstPage = false;

            System.out.println("parsing:" + url);
//            StringBuffer html = getHtml(url);
//            Document doc = Jsoup.parse(html.toString());
//            Document doc = Jsoup.connect("http://www.baidu.com/").get();

            Document doc = getHtmlPage(url);

            nextUrlRelativePath = getNextPageLink(doc);
            if(nextUrlRelativePath.equalsIgnoreCase("/"))
                hasNextPage = false;

            String htmlNeedsToConvert = getHtmlInterested(doc);
            PdfConverter.convertToPDFwithHtmlString(htmlNeedsToConvert);
            hasNextPage = false;

        }
    }


    /*
    doc is the whole html we get

    in this method , we should synthesize the html that needs to be converted
     */
    private static String getHtmlInterested(Document doc)
    {
        String ret;
        Document doc_copy = doc.clone();

        Elements contents = doc_copy.getElementsByClass(bodyClassName);
        System.out.println("html content size: " + contents.size());
        if(contents.size()==0)
        {
            System.out.println("can not find element with classname : " + bodyClassName);
            return "";
        }

        /*
            delete all child node in body
            move htmlcontent to here under body
         */
        Elements bodyEles = doc_copy.getElementsByTag("body");
        if(contents.size()==0)
        {
            System.out.println("can not find element with tag : " + "body");
            return "";
        }
        Element bodyEle = bodyEles.first();

        while(bodyEle.childNodes().size()>0){
            bodyEle.childNodes().get(0).remove();
        }
        bodyEle.appendChild(contents.first());

        ret = doc_copy.html();

        ret = ret.replaceAll("img src=\"","img src=\""+safariBookOnlineWebSite);

        return ret;
    }

    private static String getNextPageLink(Document doc)
    {
        String ret;
        Elements nextPageLinks = doc.getElementsByAttributeValue(navbarRightAttriKey, navbarRightAttriValue);
        System.out.println("nextPageLink content size: " + nextPageLinks.size());
        if(nextPageLinks.size()==0)
        {
            System.out.println("can not find navigationbar_Right with" +
                                       " attributeKey : " +
                                       navbarRightAttriKey +
                                       " and attributeValue: "+ navbarRightAttriValue);
            return "/";
        }

        // get the relative path of next page   last page should be "/"
        ret = nextPageLinks.first().attributes().get("href");
        return ret;
    }
    private static Document getHtmlPage(String url) throws Exception
    {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cn-proxy.jp.oracle.com", 80));
        //get whole html page
        Document doc = Jsoup.parse(new URL(url).openConnection().getInputStream(), "UTF-8", url);
        return doc;
    }

}

