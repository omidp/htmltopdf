package com.omidbiz.htmltopdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.pegdown.LinkRenderer;
import org.pegdown.ast.AbbreviationNode;
import org.pegdown.ast.AnchorLinkNode;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.DefinitionListNode;
import org.pegdown.ast.DefinitionNode;
import org.pegdown.ast.DefinitionTermNode;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.HtmlBlockNode;
import org.pegdown.ast.InlineHtmlNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.QuotedNode;
import org.pegdown.ast.RefImageNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrikeNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TableBodyNode;
import org.pegdown.ast.TableCaptionNode;
import org.pegdown.ast.TableCellNode;
import org.pegdown.ast.TableColumnNode;
import org.pegdown.ast.TableHeaderNode;
import org.pegdown.ast.TableNode;
import org.pegdown.ast.TableRowNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.VerbatimNode;
import org.pegdown.ast.Visitor;
import org.pegdown.ast.WikiLinkNode;

import com.lowagie.text.Anchor;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfWriter;

public class ToPdfSerializer implements Visitor
{

    Document document = new Document();

    PdfWriter pdfWriter;

    BaseFont bf;
    Font font;
    private Font defaultFont;
    protected LinkRenderer linkRenderer;

    public ToPdfSerializer()
    {
        try
        {
            Path path = Paths.get(ToPdfSerializer.class.getResource("/tahoma.ttf").toURI());
            bf = BaseFont.createFont(path.toFile().getAbsolutePath(), BaseFont.IDENTITY_H, true);
            font = new Font(bf, 12);
            defaultFont = new Font(bf, 12);
            linkRenderer = new LinkRenderer();
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File("/home/omidp/1.pdf")));
            pdfWriter.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            document.open();
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

    public void visit(AbbreviationNode node)
    {
        System.out.println("AbbreviationNode");
    }

    public void visit(AnchorLinkNode node)
    {
        System.out.println("AnchorLinkNode");
    }

    public void visit(AutoLinkNode node)
    {
        System.out.println("AutoLinkNode");
    }

    public void visit(BlockQuoteNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(BulletListNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(CodeNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(DefinitionListNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(DefinitionNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(DefinitionTermNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(ExpImageNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(ExpLinkNode node)
    {        
        Anchor anchor = new Anchor();
        anchor.setReference(linkRenderer.render(node ,"").href);
        System.out.println("ExpLinkNode");
        visitChildren(node);
        //linkRenderer.render(node, node.get)
    }

    public void visit(HeaderNode node)
    {
        System.out.println("HEADERNODE");
        font.setStyle(Font.BOLD);
        visitChildren(node);
    }

    public void visit(HtmlBlockNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(InlineHtmlNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(ListItemNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(MailLinkNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(OrderedListNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(ParaNode node)
    {
        System.out.println("Paranode");
        visitChildren(node);
    }

    public void visit(QuotedNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(ReferenceNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(RefImageNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(RefLinkNode node)
    {
        System.out.println("RefLinkNode");
    }

    public void visit(RootNode node)
    {
        System.out.println("RootNode");
        for (ReferenceNode refNode : node.getReferences())
        {
            // visitChildren(refNode);
            // references.put(normalize(printer.getString()), refNode);
            // printer.clear();
        }
        for (AbbreviationNode abbrNode : node.getAbbreviations())
        {
            // visitChildren(abbrNode);
            // String abbr = printer.getString();
            // printer.clear();
            // abbrNode.getExpansion().accept(this);
            // String expansion = printer.getString();
            // abbreviations.put(abbr, expansion);
            // printer.clear();
        }
        visitChildren(node);
        document.close();

    }

    // helpers
    protected void visitChildren(SuperNode node)
    {
        for (Node child : node.getChildren())
        {
            child.accept(this);
        }
    }

    public void visit(SimpleNode node)
    {
        System.out.println("simplenode");
    }

    public void visit(SpecialTextNode node)
    {
        System.out.println("SpecialTextNode");
    }

    public void visit(StrikeNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(StrongEmphSuperNode node)
    {
        System.out.println("StrongEmphSuperNode");
    }

    public void visit(TableBodyNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(TableCaptionNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(TableCellNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(TableColumnNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(TableHeaderNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(TableNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(TableRowNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(VerbatimNode node)
    {
        // TODO Auto-generated method stub

    }

    public void visit(WikiLinkNode node)
    {
        // TODO Auto-generated method stub
        System.out.println("WikiLinkNode");
    }

    public void visit(TextNode node)
    {
        System.out.println("TEXTNODE");
        System.out.println(node.getText());
        MultiColumnText mct = new MultiColumnText();        
        mct.setAlignment(Element.ALIGN_LEFT);
        mct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        mct.setColumnsRightToLeft(true);
        Paragraph p = new Paragraph(node.getText(), font);
        p.setAlignment(Element.ALIGN_LEFT);
        
        try
        {
            
            mct.addSimpleColumn(document.left(), document.right());
            mct.addElement(p);
            document.add(mct);
            resetFont();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }

    public void visit(SuperNode node)
    {
        visitChildren(node);
    }

    public void visit(Node node)
    {
        System.out.println("node");
    }
    
    public void resetFont()
    {
        font = defaultFont;
    }

}
