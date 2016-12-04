package com.omidbiz.htmltopdf.pdf;

import org.commonmark.node.Node;

public abstract class ITextObject
{

    public abstract void createITextObject(Node node);
    
    public abstract Object getITextObject();
    
    /**
     * @param elm always String pass to add
     */
    public abstract void handleTextAdd(Object elm);
    
    /**
     * @param elm pass Element to add to
     */
    public abstract void handleAdd(Object elm);

    
}
