package com.omidbiz.htmltopdf;

import org.commonmark.node.Node;

public interface PdfNodeRendererContext
{

    PdfHolder getHolder();
    
    void render(Node node);
}
