package com.omidbiz.htmltopdf;

import java.io.IOException;
import java.net.URISyntaxException;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ElementTags;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.MultiColumnText;
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
        PdfHolder ph = new PdfHolder();
        ph.open();
        PdfPTable table = new PdfPTable(2);
        table.setTableEvent(new MyTblEvt());
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        Chunk chunk = new Chunk("منیبتلنبیتمنتیسمتبنمسیتبمنستیمبنتسمن"
                + "نسیتبسی منبتیسمنملیس \n"
                + " بنیستلمیسب \n"
                + " ");
        chunk.setFont(ph.getFont());
        //
        Paragraph para = new Paragraph(chunk);
        para.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(para);
        cell.setCellEvent(new MyCellEvt());
        table.addCell(cell);
        table.addCell(cell);        
        ph.addToDocument(table);
        ph.close();
    }
    
    
    public static class MyTblEvt implements PdfPTableEvent
    {

        @Override
        public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases)
        {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
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
