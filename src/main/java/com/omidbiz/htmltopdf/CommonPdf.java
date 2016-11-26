package com.omidbiz.htmltopdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;

public class CommonPdf
{
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Parser parser = Parser.builder().build();
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("/home/omidp/temp/test.md")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if("".equals(line))
                    sb.append("\n");
                else
                    sb.append(line);
            }
        }
        Node document = parser.parse(sb.toString());
//        TextContentRenderer tr = TextContentRenderer.builder().build();
//        System.out.println(tr.render(document));
//        HtmlRenderer renderer = HtmlRenderer.builder().build();
//        System.out.println(renderer.render(document));
        PdfRenderer pr = new PdfRenderer();
        System.out.println(pr.render(document));
        
    }

}
