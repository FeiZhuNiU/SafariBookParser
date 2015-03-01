package com.oracle.lliyu.utils;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 2/27/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URL;

public class PdfConverter
{
    public static int cnt = 1;
    public static void convertToPDF(String htmlString)
    {
        Document document = new Document();

        try
        {
            PdfWriter.getInstance(document, new FileOutputStream("d:\\" + cnt++ +".pdf"));
            document.open();

            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(htmlString));

            document.close();

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public static void convertToPDFwithHtmlString(String htmlString)
    {
        try
        {
            FileWriter fileWriter = new FileWriter("D:\\temp.html");
            fileWriter.write(htmlString);
            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        OutputStream os = null;
        try
        {
            os = new FileOutputStream("d:\\test.pdf");
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(new File("D:\\temp.html"));
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(os != null)
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    public static void convertToPDFwithUrl(String urlString)  //work fir simple html   like www.baidu.ocm
    {

        WebClient webClient = new WebClient();
        OutputStream os = null;
        try
        {
            HtmlPage page = webClient.getPage(new URL(urlString));
            os = new FileOutputStream("d:\\test.pdf");
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(page,urlString);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(os != null)
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }
}
