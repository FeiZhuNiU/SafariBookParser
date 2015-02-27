package com.oracle.lliyu.utils;
/*===========================================================================+
 |      Copyright (c) 2014 Oracle Corporation, Redwood Shores, CA, USA       |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 |           Created by lliyu on 2/27/2015  (lin.yu@oracle.com)              |
 +===========================================================================*/

import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
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

    public static void convertToPDFwithUrlString(String urlString)
    {
        Document document = new Document();
        PdfWriter pdfWriter = null;
        try
        {

            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("d:\\" + cnt++ +".pdf"));
            document.open();

            URL url = new URL(urlString);
            InputStreamReader fis = new InputStreamReader(url.openStream());

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(pdfWriter, document, fis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            document.close();
            pdfWriter.close();
        }
    }
}
