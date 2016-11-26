package com.omidbiz.htmltopdf;

import org.commonmark.internal.renderer.NodeRendererMap;
import org.commonmark.node.Node;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer.Builder;
import org.commonmark.renderer.html.HtmlWriter;

public class PdfRenderer implements Renderer
{

    
    public PdfRenderer() {

    }
    
    @Override
    public void render(Node node, Appendable output)
    {
        CorePdfNodeRenderer r = new CorePdfNodeRenderer();
        r.render(node);
        
    }

    @Override
    public String render(Node node)
    {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

}
