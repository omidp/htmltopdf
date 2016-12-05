package com.omidbiz.htmltopdf.pdf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.StrongEmphasis;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextTable extends ITextObject
{

    ITextTableHolder tableHolder;

    public ITextTable()
    {
        this.tableHolder = new ITextTableHolder();
    }

    @Override
    public void createITextObject(Node node)
    {
        if(node instanceof TableCell)
            this.tableHolder.addTableCell((TableCell) node);
    }

    @Override
    public Object getITextObject()
    {
        return this.tableHolder;
    }

    @Override
    public void handleTextAdd(Object elm)
    {
        Font font = PdfHolder.getFont();        
        this.tableHolder.addChunk(new Chunk((String)elm, font));
    }

    @Override
    public void handleAdd(Object elm)
    {

    }

    public static class ITextTableHolder
    {
        private List<TableCell> cells = new ArrayList<>();;
        private List<Chunk> chunks = new ArrayList<>();;

        public void addTableCell(TableCell cell)
        {
            cells.add(cell);
        }
        
        public void addChunk(Chunk c)
        {
            chunks.add(c);
        }

        public List<TableCell> getCells()
        {
            return Collections.unmodifiableList(cells);
        }

        public List<Chunk> getChunks()
        {
            return Collections.unmodifiableList(chunks);
        }
        
        

    }

}
