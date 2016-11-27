package com.omidbiz.htmltopdf;

import org.commonmark.renderer.NodeRenderer;

public interface PdfNodeRendererFactory
{

    NodeRenderer create(PdfNodeRendererContext context);
    
}
