package com.omidbiz.htmltopdf;

import java.util.ArrayList;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.internal.renderer.NodeRendererMap;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;

public class PdfRenderer implements Renderer
{

    private final List<AttributeProviderFactory> attributeProviderFactories;
    private final List<PdfNodeRendererFactory> nodeRendererFactories;

    private PdfRenderer(Builder builder)
    {
        this.attributeProviderFactories = new ArrayList<>(builder.attributeProviderFactories);
        this.nodeRendererFactories = new ArrayList<>(builder.nodeRendererFactories.size() + 1);
        this.nodeRendererFactories.addAll(builder.nodeRendererFactories);
        this.nodeRendererFactories.add(new PdfNodeRendererFactory() {
            @Override
            public NodeRenderer create(PdfNodeRendererContext context) {
                return new CorePdfNodeRenderer(context);
            }
        });
    }

    @Override
    public void render(Node node, Appendable output)
    {
        RendererContext context = new RendererContext();
        context.render(node);

    }

    public static Builder builder()
    {
        return new Builder();
    }

    @Override
    public String render(Node node)
    {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

    public static class Builder
    {
        private List<AttributeProviderFactory> attributeProviderFactories = new ArrayList<>();
        private List<PdfNodeRendererFactory> nodeRendererFactories = new ArrayList<>();

        public PdfRenderer build()
        {
            return new PdfRenderer(this);
        }

        public Builder attributeProviderFactory(AttributeProviderFactory attributeProviderFactory)
        {
            this.attributeProviderFactories.add(attributeProviderFactory);
            return this;
        }

        public Builder extensions(Iterable<? extends Extension> extensions)
        {
            for (Extension extension : extensions)
            {
                if (extension instanceof PdfRendererExtension)
                {
                    PdfRendererExtension htmlRendererExtension = (PdfRendererExtension) extension;
                    htmlRendererExtension.extend(this);
                }
            }
            return this;
        }
        
        public Builder nodeRendererFactory(PdfNodeRendererFactory nodeRendererFactory) {
            this.nodeRendererFactories.add(nodeRendererFactory);
            return this;
        }
    }

    public interface PdfRendererExtension extends Extension
    {
        void extend(Builder rendererBuilder);
    }

    private class RendererContext implements PdfNodeRendererContext, AttributeProviderContext
    {
        private final List<AttributeProvider> attributeProviders;
        private final NodeRendererMap nodeRendererMap = new NodeRendererMap();

        private RendererContext()
        {

            attributeProviders = new ArrayList<>(attributeProviderFactories.size());
            for (AttributeProviderFactory attributeProviderFactory : attributeProviderFactories)
            {
                attributeProviders.add(attributeProviderFactory.create(this));
            }

            // The first node renderer for a node type "wins".
            for (int i = nodeRendererFactories.size() - 1; i >= 0; i--)
            {
                PdfNodeRendererFactory nodeRendererFactory = nodeRendererFactories.get(i);
                NodeRenderer nodeRenderer = nodeRendererFactory.create(this);
                nodeRendererMap.add(nodeRenderer);
            }
        }

        @Override
        public void render(Node node)
        {
            nodeRendererMap.render(node);
        }

       
    }

}
