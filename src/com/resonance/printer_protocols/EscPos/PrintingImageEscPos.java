package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.IllegalImageSizeException;
import com.resonance.printer_protocols.PrintingImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;


 class PrintingImageEscPos implements PrintingImage {

    private BufferedImage binaryImage;
    private ByteArrayOutputStream imageBytesChain;

     PrintingImageEscPos(BufferedImage image) throws IllegalImageSizeException {
        imageBytesChain = new ByteArrayOutputStream();
        setImage(image);
    }

     @Override
     public void setImage(BufferedImage image) throws IllegalImageSizeException {
         if (!isImageValid(image)) {
             throw new IllegalImageSizeException("image width, image height and (width + 7)/8) * height should take no more 2 bytes");
         }
         imageBytesChain.reset();
         binaryImage = createBinaryImage(image);
         this.setImageBytesChain();
     }

    void setImageBytesChain() {
        for (int y = 0; y < binaryImage.getHeight(); ++y) {
            for (int x = 0; x < binaryImage.getWidth();) {
                byte currentByte = 0;
                for (int bitCount = 0; bitCount < 8 & x< binaryImage.getWidth(); ++bitCount, ++x ) {
                    byte color = (byte) binaryImage.getRGB(x, y);
                    if (color == 0x00)
                        currentByte |= 0b00000001 << (7 - bitCount);
                }
                imageBytesChain.write(currentByte);
            }
        }
    }

    @Override
    public byte[] getImageBytesChain() {
        return imageBytesChain.toByteArray();
    }

     @Override
     public BufferedImage getBufferedImage() {
         return binaryImage;
     }

     private BufferedImage createBinaryImage (BufferedImage inputImage) {
         // Create a black-and-white image of the same size.
         BufferedImage binaryImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
         // Get the graphics context for the black-and-white image.
         Graphics2D g2d = binaryImage.createGraphics();
         // Render the input image on it.
         g2d.drawImage(inputImage, 0, 0, null);
         // Store the resulting image using the PNG format.
         return binaryImage;
     }

     boolean isImageValid (BufferedImage image) {
         final int maxTwoBytesValue = 65535;
         if (((image.getWidth() + 7)/8) * image.getHeight() > maxTwoBytesValue - 10)
             return false;
         if (image.getWidth() > maxTwoBytesValue  |  image.getHeight() > maxTwoBytesValue)
             return false;

         return true;
     }

}
