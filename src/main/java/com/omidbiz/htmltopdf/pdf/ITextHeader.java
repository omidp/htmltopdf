package com.omidbiz.htmltopdf.pdf;

import org.commonmark.node.Heading;
import org.commonmark.node.Node;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfWriter;
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextHeader extends ITextObject
{

    Heading header;
    Paragraph p;

    public ITextHeader()
    {
    }

    @Override
    public void createITextObject(Node node)
    {
        this.header = (Heading) node;
        p = new Paragraph();
        Font font = PdfHolder.getFont();
        font.setStyle(Font.BOLD);
        p.setFont(font);
        p.setAlignment(Element.ALIGN_LEFT);
    }

    @Override
    public Object getITextObject()
    {
        return p;
    }

    @Override
    public void handleTextAdd(Object elm)
    {
        p.add(new Chunk((String) elm));
    }

    @Override
    public void handleAdd(Object elm)
    {
        if(this.header.getLevel() == 1)
        {
            PdfWriter writer = (PdfWriter) elm;
            writer.getRootOutline().addKid(new PdfOutline(writer.getRootOutline(), new PdfDestination(PdfDestination.FITH), p));
        }
    }

}
