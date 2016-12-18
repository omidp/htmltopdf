package com.omidbiz.htmltopdf.pdf;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.commonmark.node.Link;
import org.commonmark.node.Node;

import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextImage extends ITextObject
{
    
    Image image;

    public ITextImage()
    {
        
    }

    @Override
    public void createITextObject(Node node)
    {
        if(node instanceof org.commonmark.node.Image)
        {
            org.commonmark.node.Image img = (org.commonmark.node.Image) node;
            String des = img.getDestination();
            try
            {
                image = Image.getInstance(new URL(des));
            }
            catch (BadElementException | IOException e)
            {
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public Object getITextObject()
    {
        return image;
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
