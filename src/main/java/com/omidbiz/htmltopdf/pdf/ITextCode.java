package com.omidbiz.htmltopdf.pdf;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.Node;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextCode extends ITextObject
{

    PdfPTable pdfTable;

    public ITextCode()
    {

    }

    @Override
    public void createITextObject(Node node)
    {
        FencedCodeBlock c = (FencedCodeBlock) node;
        pdfTable = new PdfPTable(1);
        pdfTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        pdfTable.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        String info = c.getInfo();
        if(info != null && "java".equalsIgnoreCase(info))
        {
            
        }
        com.lowagie.text.Paragraph para = new com.lowagie.text.Paragraph(new Chunk(c.getLiteral()));
        para.setAlignment(Element.ALIGN_RIGHT);
        
//        para.setIndentationRight(c.getFenceIndent());
        cell.setBackgroundColor(Color.LIGHT_GRAY);
//        cell.setFollowingIndent(c.getFenceIndent());
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        cell.addElement(para);
        cell.setPadding(10f);
        pdfTable.addCell(cell);
    }

    @Override
    public Object getITextObject()
    {
        return pdfTable;
    }

    @Override
    public void handleTextAdd(Object elm)
    {
    }

    @Override
    public void handleAdd(Object elm)
    {

    }

}
