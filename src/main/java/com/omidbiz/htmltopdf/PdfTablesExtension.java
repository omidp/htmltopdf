package com.omidbiz.htmltopdf;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.gfm.tables.internal.TableBlockParser;
import org.commonmark.ext.gfm.tables.internal.TableNodeRenderer;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlNodeRendererFactory;
import org.commonmark.renderer.html.HtmlRenderer;

public class PdfTablesExtension implements Parser.ParserExtension, PdfRenderer.PdfRendererExtension {

    private PdfTablesExtension() {
    }

    public static Extension create() {
        return new PdfTablesExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new TableBlockParser.Factory());
    }

    @Override
    public void extend(PdfRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new PdfNodeRendererFactory() {
            @Override
            public NodeRenderer create(PdfNodeRendererContext context) {
                return new PdfTableNodeRenderer(context, context.getHolder());
            }
        });
    }
}
