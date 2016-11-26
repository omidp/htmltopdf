package com.omidbiz.htmltopdf;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class CommonPdf
{
    
    public static void main(String[] args)
    {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is \n *Sparta* ");
//        TextContentRenderer tr = TextContentRenderer.builder().build();
//        System.out.println(tr.render(document));
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        System.out.println(renderer.render(document));
        PdfRenderer pr = new PdfRenderer();
        System.out.println(pr.render(document));
        
    }

}
