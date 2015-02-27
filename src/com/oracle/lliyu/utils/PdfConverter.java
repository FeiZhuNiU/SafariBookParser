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

import javax.swing.text.html.HTMLWriter;
import java.io.FileOutputStream;
import java.io.StringReader;

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
}
