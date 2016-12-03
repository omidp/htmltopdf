package com.omidbiz.htmltopdf;

import org.commonmark.node.Node;

public abstract class ITextObject
{

    public abstract void createITextObject(Node node);
    
    public abstract Object getITextObject();
    
    public abstract void handleAdd(Object elm);

    
}
