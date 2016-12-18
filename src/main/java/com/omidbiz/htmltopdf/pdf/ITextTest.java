package com.omidbiz.htmltopdf.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfWriter;

public class ITextTest
{

    public static void main(String[] args) throws URISyntaxException, DocumentException, IOException
    {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("/home/omidp/1.pdf"));
        pdfWriter.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        document.open();
        Paragraph title2 = new Paragraph("This is Chapter 2", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)));
        Chapter chapter2 = new Chapter(title2, 2);
        chapter2.setNumberDepth(0);
        Paragraph someText = new Paragraph("This is some text");
        chapter2.add(someText);
        Paragraph title21 = new Paragraph("This is Section 1 in Chapter 2", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
        Section section1 = chapter2.addSection(title21);
        Paragraph someSectionText = new Paragraph("This is some silly paragraph in a chapter and/or section. It contains some text to test the functionality of Chapters and Section.");
        section1.add(someSectionText);
        document.add(chapter2);
        document.close();
        
    }
    
    
    public static class MyTblEvt implements PdfPTableEvent
    {

        @Override
        public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases)
        {
            System.out.println("tableLayout event");
        }
        
    }
    
    public static class MyCellEvt implements PdfPCellEvent
    {

        @Override
        public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases)
        {
            System.out.println("CEELLLL EVT");
            System.out.println(canvases.length);
        }
        
    }
}
