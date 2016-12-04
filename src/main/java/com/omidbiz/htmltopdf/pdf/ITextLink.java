package com.omidbiz.htmltopdf.pdf;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import org.commonmark.node.Link;
import org.commonmark.node.Node;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextLink extends ITextObject
{

    Chunk chunk;

    public ITextLink()
    {
        chunk = new Chunk();
        chunk.setFont(PdfHolder.getFont());
    }

    @Override
    public void createITextObject(Node node)
    {
        
        Link link = (Link) node;
        Anchor anchor = new Anchor();        
        try
        {
            chunk.setAnchor(new URL(link.getDestination()));
            Font font = chunk.getFont();
            font.setColor(Color.blue);
            chunk.setFont(font);
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Object getITextObject()
    {
        return chunk;
    }

    @Override
    public void handleTextAdd(Object elm)
    {
        chunk.append((String)elm);
    }

    @Override
    public void handleAdd(Object elm)
    {
        // TODO Auto-generated method stub
        
    }

}
