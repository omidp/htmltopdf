package com.omidbiz.htmltopdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.TextDirection;

@Deprecated
public class SimpleUsage
{
    public static void main(String[] args)
    {
        //change path to files, files are available in resources
        new SimpleUsage().exportToPdfBox("file:///home/omidp/temp/htmltopdf/output.html", "/home/omidp/temp/htmltopdf/output.pdf");
    }

    public void exportToPdfBox(String url, String out)
    {
        OutputStream os = null;

        try
        {
            os = new FileOutputStream(out);

            try
            {
                // There are more options on the builder than shown below.
                PdfRendererBuilder builder = new PdfRendererBuilder();                
                builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
                builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
                builder.defaultTextDirection(TextDirection.RTL); // OR RTL
                builder.withUri(url);
                builder.toStream(os);
                builder.run();
                
                

            }
            catch (Exception e)
            {
                e.printStackTrace();
                // LOG exception
            }
            finally
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                    // swallow
                }
            }
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
            // LOG exception.
        }
    }
}
