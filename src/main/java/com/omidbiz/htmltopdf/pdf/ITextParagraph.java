package com.omidbiz.htmltopdf.pdf;

import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.omidbiz.htmltopdf.PdfHolder;

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
    public void handleTextAdd(Object elm)
    {
        p.add(new Chunk((String) elm));
    }

    @Override
    public void handleAdd(Object elm)
    {
        if(elm instanceof HardLineBreak)
        {
            //HardLineBreak hlb = (HardLineBreak) elm;
            p.add(Chunk.NEWLINE);
        }
        if(elm instanceof SoftLineBreak)
        {
            p.add(Chunk.NEWLINE);
        }
    }

}
