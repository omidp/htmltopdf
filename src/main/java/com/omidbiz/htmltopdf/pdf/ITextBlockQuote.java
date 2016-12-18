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

public class ITextBlockQuote extends ITextObject
{


    public ITextBlockQuote()
    {
    }

    @Override
    public void createITextObject(Node node)
    {
        
    }

    @Override
    public Object getITextObject()
    {
        return null;
    }

    @Override
    public void handleTextAdd(Object elm)
    {
    }

    @Override
    public void handleAdd(Object elm)
    {
        System.out.println(elm);
    }

}
