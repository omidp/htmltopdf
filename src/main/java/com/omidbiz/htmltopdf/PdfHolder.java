package com.omidbiz.htmltopdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfWriter;

public class PdfHolder
{

    public static final int DEFAULT_FONT_SIZE = 12;
    
    private MultiColumnText multiColumnText;
    private Paragraph paragraph;
    private Font font;
    private Document document;
    private PdfWriter pdfWriter;
    private BaseFont bf;
    

    public PdfHolder()
    {
        Path path;
        try
        {
            document = new Document();
            path = Paths.get(ToPdfSerializer.class.getResource("/tahoma.ttf").toURI());
            bf = BaseFont.createFont(path.toFile().getAbsolutePath(), BaseFont.IDENTITY_H, true);
            font = new Font(bf, DEFAULT_FONT_SIZE);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File("/home/omidp/1.pdf")));
            pdfWriter.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            // document.open();

        }
        catch (DocumentException | IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    public void open()
    {
        document.open();
    }

    public void close()
    {
        document.close();
    }

    public MultiColumnText getMultiColumnText()
    {
        if (multiColumnText == null)
            multiColumnText = new MultiColumnText();
        multiColumnText.setAlignment(Element.ALIGN_LEFT);
        multiColumnText.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        multiColumnText.setColumnsRightToLeft(true);
        multiColumnText.addSimpleColumn(document.left(), document.right());
        return multiColumnText;
    }

    public MultiColumnText getMultiColumnText(boolean createNew)
    {
        if (createNew)
            multiColumnText = new MultiColumnText();
        return getMultiColumnText();
    }

    public Paragraph getParagraph()
    {
        if (paragraph == null)
            paragraph = new Paragraph();
        paragraph.setFont(getFont());
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }

    public Paragraph getParagraph(boolean createNew)
    {
        if (createNew)
            paragraph = new Paragraph();
        return getParagraph();
    }

    public Font getFont()
    {
        return font;
    }

    public PdfWriter getPdfWriter()
    {
        return pdfWriter;
    }
    
    public void addToDocument(Element elm)
    {
        try
        {            
            multiColumnText.addElement(elm);
            document.add(multiColumnText);
        }
        catch (DocumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void resetFont()
    {
        this.font = new Font(bf, DEFAULT_FONT_SIZE);
        this.font.setStyle(Font.NORMAL);
    }

}
