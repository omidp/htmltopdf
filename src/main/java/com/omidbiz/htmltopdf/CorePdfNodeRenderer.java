package com.omidbiz.htmltopdf;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.MultiColumnText;
import com.omidbiz.htmltopdf.PdfHolder.ChunkHolder;
import com.omidbiz.htmltopdf.PdfHolder.PdfTextType;

public class CorePdfNodeRenderer extends AbstractVisitor implements NodeRenderer
{

    private final PdfHolder holder;
    private PdfTextType pdfTextType;

    public CorePdfNodeRenderer() throws URISyntaxException, DocumentException, IOException
    {
        this.holder = new PdfHolder();
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
        // begin processing
        this.holder.open();
        visitChildren(document);
        // end of processing
        Collection<ChunkHolder> chunks = this.holder.getChunks();
        Iterator<ChunkHolder> iterator = chunks.iterator();
        MultiColumnText mt = this.holder.getMultiColumnText();
        com.lowagie.text.Paragraph p = this.holder.getParagraph();
        while (iterator.hasNext())
        {
            PdfHolder.ChunkHolder ch = (PdfHolder.ChunkHolder) iterator.next();
            if(ch.isCreateNewParagraph())
                p.add(Chunk.NEWLINE);
            p.add(ch.getChunk());

        }

        try
        {
            mt.addElement(p);
            this.holder.addToDocument(mt);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        this.holder.close();
    }

    @Override
    public void visit(Heading heading)
    {
        int level = heading.getLevel();
        this.pdfTextType = PdfTextType.H1;
        visitChildren(heading);
    }

    @Override
    public void visit(Paragraph paragraph)
    {
        System.out.println("PARAGRAPH");
        pdfTextType = PdfTextType.PARAGRAPH;
        visitChildren(paragraph);
    }

    @Override
    public void visit(Emphasis emphasis)
    {
        visitChildren(emphasis);
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis)
    {
        System.out.println("StrongEmphasis");
        pdfTextType = PdfTextType.StrongEmphasis;
        visitChildren(strongEmphasis);

    }

    @Override
    public void visit(Text text)
    {
        System.out.println("text");
        System.out.println(text.getLiteral());
        Chunk chunk = this.holder.getChunk();
        chunk.append(text.getLiteral());
        boolean textTypeOnly = true;
        if (pdfTextType.equals(PdfTextType.H1))
        {
            Font font = this.holder.getFont();
            font.setStyle(Font.BOLD);
            chunk.setFont(font);
            this.holder.addChunk(new ChunkHolder(chunk, true, true, PdfTextType.H1));
            textTypeOnly = false;
        }

        if (pdfTextType.equals(PdfTextType.StrongEmphasis))
        {
            Font font = this.holder.getFont();
            font.setStyle(Font.BOLD);
            chunk.setFont(font);
            this.holder.addChunk(new ChunkHolder(chunk, false, false, PdfTextType.StrongEmphasis));
            textTypeOnly = false;
        }

        if (pdfTextType.equals(PdfTextType.PARAGRAPH))
        {
            this.holder.addChunk(new ChunkHolder(chunk, true, false, PdfTextType.StrongEmphasis));
            textTypeOnly = false;
        }

        if (pdfTextType.equals(PdfTextType.SoftLineBreak))
        {
            this.holder.addChunk(new ChunkHolder(chunk, true, true, PdfTextType.StrongEmphasis));
            textTypeOnly = false;
        }

        if (textTypeOnly)
        {
            this.holder.addChunk(new ChunkHolder(chunk, false, false, PdfTextType.NO_MORE_TEXT));
        }

        pdfTextType = PdfTextType.NO_MORE_TEXT;

    }

    @Override
    public void visit(SoftLineBreak softLineBreak)
    {
        System.out.println("SOFTLINEBR");
        pdfTextType = PdfTextType.SoftLineBreak;
        // visitChildren(softLineBreak);
    }

    @Override
    public void visit(HardLineBreak hardLineBreak)
    {

        visitChildren(hardLineBreak);
        //
    }

}
