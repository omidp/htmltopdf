package com.omidbiz.htmltopdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;
import org.commonmark.renderer.NodeRenderer;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfWriter;

public class CorePdfNodeRenderer extends AbstractVisitor implements NodeRenderer 
{

    com.lowagie.text.Document doc = new com.lowagie.text.Document();

    PdfWriter pdfWriter;
    BaseFont bf;
    Font font;
    private Font defaultFont;
    
    public CorePdfNodeRenderer()
    {
        try
        {
            Path path = Paths.get(ToPdfSerializer.class.getResource("/tahoma.ttf").toURI());
            bf = BaseFont.createFont(path.toFile().getAbsolutePath(), BaseFont.IDENTITY_H, true);
            font = new Font(bf, 12);
            defaultFont = new Font(bf, 10);
            defaultFont.setStyle(Font.NORMAL);
            defaultFont.setSize(12);
            
            pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(new File("/home/omidp/1.pdf")));
            pdfWriter.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            doc.open();
            
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (URISyntaxException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes()
    {
        return new HashSet<>(Arrays.asList(
                Document.class,
                Heading.class,
                Paragraph.class,
                BlockQuote.class,
                BulletList.class,
                FencedCodeBlock.class,
                HtmlBlock.class,
                ThematicBreak.class,
                IndentedCodeBlock.class,
                Link.class,
                ListItem.class,
                OrderedList.class,
                Image.class,
                Emphasis.class,
                StrongEmphasis.class,
                Text.class,
                Code.class,
                HtmlInline.class,
                SoftLineBreak.class,
                HardLineBreak.class
        ));
    }

    @Override
    public void render(Node node)
    {
        node.accept(this);        
    }
    
    @Override
    public void visit(Document document) {
        // No rendering itself
        visitChildren(document);
        for (com.lowagie.text.Paragraph item : pars)
        {
            MultiColumnText mct = new MultiColumnText();        
            mct.setAlignment(Element.ALIGN_LEFT);
            mct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            mct.setColumnsRightToLeft(true);
            mct.addSimpleColumn(doc.left(), doc.right());
            item.setFont(font);
            item.setAlignment(Element.ALIGN_LEFT);
            try
            {
                mct.addElement(item);
                doc.add(mct);
            }
            catch (DocumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //TODO:document has no page
        doc.close();
    }

    @Override
    public void visit(Heading heading) {
       System.out.println("HEADING");
    }
    
    @Override
    public void visit(Paragraph paragraph) {
        System.out.println("PARAGRAPH");
        par = new com.lowagie.text.Paragraph();
        visitChildren(paragraph);
        pars.add(par);
    }
    
    @Override
    public void visit(Emphasis emphasis) {
     System.out.println("emphasis");
     visitChildren(emphasis);
    }
    
    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        System.out.println("strongemph");
        this.font = new Font(bf);
        this.font.setSize(12);
        this.font.setStyle(Font.BOLD);
        visitChildren(strongEmphasis);        
    }

    @Override
    public void visit(Text text) {
        System.out.println("text");
        System.out.println(font.getStyle());
        System.out.println(text.getLiteral());
        Chunk c = new Chunk(text.getLiteral(), font);
//        c.setFont(font);
        par.add(c);
        resetFont();
    }
    
    private void resetFont()
    {
        this.font =new Font(bf); 
        this.font.setSize(12);
        this.font.setStyle(-1);
//        c.setFont(font);
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        System.out.println("SOFTLINEBR");
        par.add(Chunk.NEWLINE);
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
     System.out.println("HARDLINEBR");
    }

    
    
    ///itext
    List<com.lowagie.text.Paragraph> pars = new ArrayList<>();
    com.lowagie.text.Paragraph par;
//     List<Chunk> chunks = new ArrayList<>();

}
