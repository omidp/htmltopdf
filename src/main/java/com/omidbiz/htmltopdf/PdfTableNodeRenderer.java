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

public class PdfTableNodeRenderer implements NodeRenderer
{

    PdfNodeRendererContext context;
    
    public PdfTableNodeRenderer(PdfNodeRendererContext context)
    {
        this.context = context;
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
            System.out.println("ASDFSa");
//            renderBlock((TableBlock) node);
        } else if (node instanceof TableHead) {
//            renderHead((TableHead) node);
        } else if (node instanceof TableBody) {
//            renderBody((TableBody) node);
        } else if (node instanceof TableRow) {
//            renderRow((TableRow) node);
        } else if (node instanceof TableCell) {
//            renderCell((TableCell) node);
        }
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
