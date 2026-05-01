package com.java.poc.pdf;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class PdfCompressor {

    // JPEG quality: lower = smaller file. (0.5 = good balance)
    private static final float JPEG_QUALITY = 0.5f;

    public static void main(String[] args) throws Exception {
        String inputPdf = "src/test/java/com/java/poc/pdf/FGCU_ID.pdf";
        String outputPdf = "ID_Compressed.pdf";

        compressPdfImages(inputPdf, outputPdf);
        System.out.println("Compression complete → " + outputPdf);
    }

    public static void compressPdfImages(String source, String destination) throws IOException {
        PDDocument document = PDDocument.load(new File(source));

        for (PDPage page : document.getPages()) {
            var resources = page.getResources();

            for (COSName name : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(name);

                if (xObject instanceof PDImageXObject) {
                    PDImageXObject imageObject = (PDImageXObject) xObject;

                    // Extract original BufferedImage
                    BufferedImage image = imageObject.getImage();

                    // Compress image
                    byte[] compressedImageBytes = compressJpeg(image, JPEG_QUALITY);

                    // Replace with new lower-size JPEG image
                    PDImageXObject newImage = PDImageXObject.createFromByteArray(
                            document,
                            compressedImageBytes,
                            "jpeg-compressed"
                    );

                    resources.put(name, newImage);
                }
            }
        }

        document.save(destination);
        document.close();
    }

    private static byte[] compressJpeg(BufferedImage image, float quality) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter jpgWriter = writers.next();

        ImageWriteParam param = jpgWriter.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);  // <-- reduce quality to shrink size

        jpgWriter.setOutput(new MemoryCacheImageOutputStream(baos));
        jpgWriter.write(null, new IIOImage(image, null, null), param);
        jpgWriter.dispose();

        return baos.toByteArray();
    }
}
