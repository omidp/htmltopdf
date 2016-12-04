package com.omidbiz.htmltopdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.lowagie.text.DocumentException;

/**
 * @author Omid Pourhadi
 *
 */
public class CommonMarkdownPdfRunner
{

    public static void main(String[] args) throws FileNotFoundException, IOException, URISyntaxException, DocumentException
    {
        URL OUTPUT = CommonMarkdownPdfRunner.class.getResource("output.pdf");
        URL MARKDOWN_FILE = CommonMarkdownPdfRunner.class.getResource("markdown.md");
        File outputFile = Paths.get(OUTPUT.toURI()).toFile();
        File mdFile = Paths.get(MARKDOWN_FILE.toURI()).toFile();
        PdfRenderer pr = new PdfRenderer(outputFile);
        Parser parser = Parser.builder().extensions(Arrays.asList(pr)).build();
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader(mdFile)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
        }
        Node document = parser.parse(sb.toString());
        String outputFileRender = pr.render(document);
        System.out.println("Output : " + outputFileRender);

    }

}
