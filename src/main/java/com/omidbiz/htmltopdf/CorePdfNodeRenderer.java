package com.omidbiz.htmltopdf;

import java.util.Arrays;
import java.util.HashSet;
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
import com.lowagie.text.Font;

public class CorePdfNodeRenderer extends AbstractVisitor implements NodeRenderer
{

    private PdfHolder holder;

    public CorePdfNodeRenderer()
    {
        holder = new PdfHolder();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes()
    {
        return new HashSet<>(Arrays.asList(Document.class, Heading.class, Paragraph.class, BlockQuote.class, BulletList.class,
                FencedCodeBlock.class, HtmlBlock.class, ThematicBreak.class, IndentedCodeBlock.class, Link.class, ListItem.class,
                OrderedList.class, Image.class, Emphasis.class, StrongEmphasis.class, Text.class, Code.class, HtmlInline.class,
                SoftLineBreak.class, HardLineBreak.class));
    }

    @Override
    public void render(Node node)
    {
        node.accept(this);
    }

    @Override
    public void visit(Document document)
    {
        // No rendering itself
        holder.open();
        visitChildren(document);
        
        // TODO:document has no page
        holder.close();
    }

    @Override
    public void visit(Heading heading)
    {
        System.out.println("HEADING");
        int level = heading.getLevel();
        float size = holder.getFont().getSize();
        holder.getFont().setSize(size+level);
        holder.getFont().setStyle(Font.BOLD);
        visitChildren(heading);
        holder.getParagraph().add(Chunk.NEWLINE);
    }

    @Override
    public void visit(Paragraph paragraph)
    {
        System.out.println("PARAGRAPH");
        try
        {
            holder.getMultiColumnText().addElement(holder.getParagraph());
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        visitChildren(paragraph);
        holder.addToDocument(holder.getParagraph());
    }

    @Override
    public void visit(Emphasis emphasis)
    {
        System.out.println("emphasis");
        float size = holder.getFont().getSize();
        holder.getFont().setSize(size+2);
        visitChildren(emphasis);
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis)
    {
        System.out.println("strongemph");
        holder.getFont().setStyle(Font.BOLD);
        visitChildren(strongEmphasis);
    }

    @Override
    public void visit(Text text)
    {
        System.out.println("text");
        Chunk chunk = new Chunk(text.getLiteral());
        chunk.setFont(holder.getFont());
        holder.getParagraph().add(chunk);
        holder.resetFont();
    }

    @Override
    public void visit(SoftLineBreak softLineBreak)
    {
        System.out.println("SOFTLINEBR");
        // add chunk newline to text
        holder.getParagraph().add(Chunk.NEWLINE);
    }

    @Override
    public void visit(HardLineBreak hardLineBreak)
    {
        System.out.println("HARDLINEBR");
//        holder.getMultiColumnText().addText(Chunk.NEWLINE);
        holder.getParagraph().add(Chunk.NEWLINE);
    }

}
