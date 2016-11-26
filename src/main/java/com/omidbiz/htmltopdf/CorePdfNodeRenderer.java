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

public class CorePdfNodeRenderer extends AbstractVisitor implements NodeRenderer 
{

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
    }

    @Override
    public void visit(Heading heading) {
       System.out.println("HEADING");
    }
    
    @Override
    public void visit(Paragraph paragraph) {
        System.out.println("PARAGRAPH");
        visitChildren(paragraph);
    }
    
    @Override
    public void visit(Emphasis emphasis) {
     System.out.println("emphasis");
     visitChildren(emphasis);
    }
    
    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        System.out.println("strongemph");
        visitChildren(strongEmphasis);
        
    }

    @Override
    public void visit(Text text) {
        System.out.println("text");
        
    }
    
    @Override
    public void visit(SoftLineBreak softLineBreak) {
        System.out.println("SOFTLINEBR");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
     System.out.println("HARDLINEBR");
    }


}
