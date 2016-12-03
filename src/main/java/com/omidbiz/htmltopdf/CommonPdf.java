package com.omidbiz.htmltopdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class CommonPdf
{

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // List<Extension> extensions = Arrays.asList(TablesExtension.create());
        PdfRenderer pr = new PdfRenderer(new File("/home/omidp/1.pdf"));        
        Parser parser = Parser.builder().extensions(Arrays.asList(pr)).build();
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader(new File("/home/omidp/temp/test.md"))))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
        }
        // Node document =
        // parser.parse("First Header  | Second Header | Third Header | ------------ | :-----------: | -----------: |");
        Node document = parser.parse(sb.toString());
        // TextContentRenderer tr = TextContentRenderer.builder().build();
        // System.out.println(tr.render(document));
//         HtmlRenderer renderer =
//         HtmlRenderer.builder().extensions(extensions).build();
        // System.out.println(renderer.render(document));
        System.out.println(pr.render(document));

    }

}
