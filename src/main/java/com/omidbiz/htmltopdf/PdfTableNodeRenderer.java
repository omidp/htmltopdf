package com.omidbiz.htmltopdf;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableBody;
import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.ext.gfm.tables.TableRow;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;

import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;

public class PdfTableNodeRenderer implements NodeRenderer
{

    PdfNodeRendererContext context;
    PdfHolder holder;
    private PdfPTable table;
    
    public PdfTableNodeRenderer(PdfNodeRendererContext context, PdfHolder holder)
    {
        this.context = context;
        this.holder = holder;
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<>(Arrays.asList(
                TableBlock.class,
                TableHead.class,
                TableBody.class,
                TableRow.class,
                TableCell.class
        ));
    }

    @Override
    public void render(Node node) {
        if (node instanceof TableBlock) {
            renderBlock((TableBlock) node);
        } else if (node instanceof TableHead) {
            renderHead((TableHead) node);
        } else if (node instanceof TableBody) {
            renderBody((TableBody) node);
        } else if (node instanceof TableRow) {
            renderRow((TableRow) node);
        } else if (node instanceof TableCell) {
            renderCell((TableCell) node);
        }
    }
    
    
    private void renderBlock(TableBlock tableBlock) {
        System.out.println("start TableBlock");
        renderChildren(tableBlock);
        System.out.println("end TableBlock");
        //process here 
    }
    
    private void renderHead(TableHead tableHead) {
        System.out.println("start TableHead");
        renderChildren(tableHead);
        System.out.println("end TableHead");
    }

    private void renderBody(TableBody tableBody) {
        System.out.println("start TableBody");
        renderChildren(tableBody);
        System.out.println("end TableBody");
    }

    private void renderRow(TableRow tableRow) {
        System.out.println("start TableRow");
        renderChildren(tableRow);
        System.out.println("end TableRow");
    }

    private void renderCell(TableCell tableCell) {
        System.out.println("start TableCell");
        String tag = tableCell.isHeader() ? "th" : "td";
        renderChildren(tableCell);
        System.out.println("end TableCell");
    }
    
    private void renderChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }

}
