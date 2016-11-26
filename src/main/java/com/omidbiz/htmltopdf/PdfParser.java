package com.omidbiz.htmltopdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

public class PdfParser
{

    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        PegDownProcessor proc = new PegDownProcessor();
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
        RootNode root = proc.parseMarkdown(sb.toString().toCharArray());
        ToPdfSerializer serializer = new ToPdfSerializer();
        serializer.visit(root);
//        ToHtmlSerializer html = new ToHtmlSerializer(new LinkRenderer());
//        html.visit(root);
//        System.out.println(html.toHtml(root));
    }
    
}
