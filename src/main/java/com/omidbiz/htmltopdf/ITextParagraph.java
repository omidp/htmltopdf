package com.omidbiz.htmltopdf;

import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.StrongEmphasis;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

public class ITextParagraph extends ITextObject
{

    org.commonmark.node.Paragraph header;
    Paragraph p;
    private Font font;

    public ITextParagraph(org.commonmark.node.Paragraph header)
    {
        this.header = header;
        this.p = new Paragraph();
        this.font = PdfHolder.getFont();
        p.setFont(font);
        p.setAlignment(Element.ALIGN_LEFT);
    }

    @Override
    public void createITextObject(Node node)
    {
         if(node instanceof StrongEmphasis)
             font.setStyle(Font.BOLD);
         p.setFont(font);
    }

    @Override
    public Object getITextObject()
    {
        return p;
    }

    @Override
    public void handleAdd(Object elm)
    {
        p.add(new Chunk((String) elm));
    }

}
