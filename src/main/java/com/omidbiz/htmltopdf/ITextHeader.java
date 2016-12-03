package com.omidbiz.htmltopdf;

import org.commonmark.node.Heading;
import org.commonmark.node.Node;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

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
        // TODO Auto-generated method stub
        
    }

}
