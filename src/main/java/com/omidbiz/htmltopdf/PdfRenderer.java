package com.omidbiz.htmltopdf;

import java.io.IOException;
import java.net.URISyntaxException;

import org.commonmark.internal.renderer.NodeRendererMap;
import org.commonmark.node.Node;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer.Builder;
import org.commonmark.renderer.html.HtmlWriter;

import com.lowagie.text.DocumentException;

public class PdfRenderer implements Renderer
{

    
    public PdfRenderer() {

    }
    
    @Override
    public void render(Node node, Appendable output)
    {
        CorePdfNodeRenderer r;
        try
        {
            r = new CorePdfNodeRenderer();
            r.render(node);
        }
        catch (URISyntaxException | DocumentException | IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public String render(Node node)
    {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

}
