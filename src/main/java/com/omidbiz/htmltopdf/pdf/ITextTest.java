package com.omidbiz.htmltopdf.pdf;

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
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextTest
{

    public static void main(String[] args) throws URISyntaxException, DocumentException, IOException
    {
       
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
