package com.omidbiz.htmltopdf;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableBody;
import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.ext.gfm.tables.TableRow;
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
import org.commonmark.renderer.html.HtmlNodeRendererContext;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.omidbiz.htmltopdf.PdfHolder.ChunkHolder;
import com.omidbiz.htmltopdf.PdfHolder.PdfTextType;
import com.omidbiz.htmltopdf.pdf.ITextHeader;
import com.omidbiz.htmltopdf.pdf.ITextImage;
import com.omidbiz.htmltopdf.pdf.ITextLink;
import com.omidbiz.htmltopdf.pdf.ITextNewLine;
import com.omidbiz.htmltopdf.pdf.ITextObject;
import com.omidbiz.htmltopdf.pdf.ITextParagraph;
import com.omidbiz.htmltopdf.pdf.ITextTable;
import com.omidbiz.htmltopdf.pdf.ITextTable.ITextTableHolder;

/**
 * @author Omid Pourhadi
 *
 */
public class CorePdfNodeRenderer extends AbstractVisitor implements NodeRenderer, PdfPCellEvent
{

    private ITextObject itextObject;
    private PdfHolder pdfHolder;

    public CorePdfNodeRenderer(PdfHolder pdfHolder)
    {
        this.pdfHolder = pdfHolder;
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes()
    {
        return new HashSet<>(Arrays.asList(Document.class, Heading.class, Paragraph.class, BlockQuote.class, BulletList.class,
                FencedCodeBlock.class, HtmlBlock.class, ThematicBreak.class, IndentedCodeBlock.class, Link.class, ListItem.class,
                OrderedList.class, Image.class, Emphasis.class, StrongEmphasis.class, Text.class, Code.class, HtmlInline.class,
                SoftLineBreak.class, HardLineBreak.class, TableBlock.class, TableHead.class, TableBody.class, TableRow.class,
                TableCell.class));
    }

    @Override
    public void render(Node node)
    {
        if (node instanceof TableBlock)
        {
            renderBlock((TableBlock) node);
        }
        else if (node instanceof TableHead)
        {
            renderHead((TableHead) node);
        }
        else if (node instanceof TableBody)
        {
            renderBody((TableBody) node);
        }
        else if (node instanceof TableRow)
        {
            renderRow((TableRow) node);
        }
        else if (node instanceof TableCell)
        {
            renderCell((TableCell) node);
        }
        else
        {
            node.accept(this);
        }
    }

    @Override
    public void visit(Document document)
    {
        // No rendering itself
        // begin processing
        pdfHolder.open();
        visitChildren(document);
        pdfHolder.close();
        // end of processing
    }

    @Override
    public void visit(Heading heading)
    {
        int level = heading.getLevel();
        System.out.println(level);
        itextObject = new ITextHeader();
        itextObject.createITextObject(heading);
        visitChildren(heading);
        Object elm = itextObject.getITextObject();
        MultiColumnText mt = pdfHolder.addToColumnText((Element) elm);
        pdfHolder.addToDocument(mt);
    }

     private void reset()
     {
         itextObject = null;
     }

    @Override
    public void visit(Paragraph paragraph)
    {
        System.out.println("PARAGRAPH");
        itextObject = new ITextParagraph(paragraph);
        itextObject.createITextObject(paragraph);
        visitChildren(paragraph);
        // if already addded itext object is null
        if (itextObject != null)
        {
            Object elm = itextObject.getITextObject();
            MultiColumnText mt = pdfHolder.addToColumnText((Element) elm);
            pdfHolder.addToDocument(mt);
        }
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
        // TODO : noway itextobject became null
        itextObject.createITextObject(strongEmphasis);
        visitChildren(strongEmphasis);
        // if already added
        com.lowagie.text.Paragraph p = (com.lowagie.text.Paragraph) itextObject.getITextObject();
        p.setFont(pdfHolder.getFont());
    }

    @Override
    public void visit(Text text)
    {
        System.out.println("text");
        System.out.println(text.getLiteral());
        if (itextObject != null)
            itextObject.handleTextAdd(text.getLiteral());

    }

    @Override
    public void visit(SoftLineBreak softLineBreak)
    {
        System.out.println("SOFTLINEBR");
        visitChildren(softLineBreak);
    }

    @Override
    public void visit(HardLineBreak hardLineBreak)
    {
        System.out.println("HARDLINE");        
        visitChildren(hardLineBreak);
        itextObject = new ITextNewLine();
        itextObject.createITextObject(hardLineBreak);
        com.lowagie.text.Paragraph newLine = (com.lowagie.text.Paragraph) itextObject.getITextObject();
        pdfHolder.addToDocument(newLine);
        reset();
        //
    }

    @Override
    public void visit(Link link)
    {
        itextObject = new ITextLink();
        itextObject.createITextObject(link);
        visitChildren(link);

    }

    @Override
    public void visit(Image image)
    {
        System.out.println("IIIIIIIIIIIIIIIIII");
        itextObject = new ITextImage();
        itextObject.createITextObject(image);
        com.lowagie.text.Image img = (com.lowagie.text.Image) itextObject.getITextObject();
        pdfHolder.addToDocument(img);
        visitChildren(image);
        reset();
    }

    private void renderBlock(TableBlock tableBlock)
    {
        System.out.println("start TableBlock");
        itextObject = new ITextTable();
        visitChildren(tableBlock);        
        System.out.println("end TableBlock");
        // process here
        ITextTableHolder tableHolder = (ITextTableHolder) itextObject.getITextObject();
        List<TableCell> cells = new ArrayList<>(tableHolder.getCells());
        List<TableCell> headerCells = new ArrayList<>();
        for (TableCell tableCell : tableHolder.getCells())
        {
            if(tableCell.isHeader())
            {
                headerCells.add(tableCell);
                cells.remove(tableCell);
            }
        }
        int numberOfColumns = headerCells.size() == 0 ? cells.size(): headerCells.size();
        PdfPTable pdfTable = new PdfPTable(numberOfColumns);
        pdfTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        pdfTable.setWidthPercentage(100);
        //
        List<Chunk> chunks = tableHolder.getChunks();
        for (Chunk chunk : chunks)
        {
            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Paragraph para = new com.lowagie.text.Paragraph(chunk);
            para.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(para);
            cell.setCellEvent(this);
            pdfTable.addCell(cell);
        }
        this.pdfHolder.addToDocument(new com.lowagie.text.Paragraph(Chunk.NEWLINE));
        this.pdfHolder.addToDocument(pdfTable);
    }

    private void renderHead(TableHead tableHead)
    {
        System.out.println("start TableHead");
        visitChildren(tableHead);
        System.out.println("end TableHead");
    }

    private void renderBody(TableBody tableBody)
    {
        System.out.println("start TableBody");
        visitChildren(tableBody);
        System.out.println("end TableBody");
    }

    private void renderRow(TableRow tableRow)
    {
        System.out.println("start TableRow");
        visitChildren(tableRow);
        System.out.println("end TableRow");
    }

    private void renderCell(TableCell tableCell)
    {
        itextObject.createITextObject(tableCell);
        System.out.println("start TableCell");
        String tag = tableCell.isHeader() ? "th" : "td";
        visitChildren(tableCell);
        System.out.println("end TableCell");
    }

    @Override
    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases)
    {
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs");
    }

    @Override
    protected void visitChildren(Node parent)
    {
        Node node = parent.getFirstChild();
        while (node != null)
        {
            // A subclass of this visitor might modify the node, resulting in
            // getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            render(node);
            // node.accept(this);
            node = next;
        }
    }

}
