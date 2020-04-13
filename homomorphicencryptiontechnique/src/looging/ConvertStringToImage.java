package looging;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class ConvertStringToImage {
	 public static void main(String[] args) {

	        //Convert image to string
	        /*System.out.println("Convert image to string");
	       // String imageString = convertImageToString();
	        System.out.println("String representation of image:"
	                +imageString);
	        System.out.println("Convert image to string done");
	        
	        //Convert string to image
	        System.out.println("Convert String to image");
	        convertStringToImageByteArray("ericsson");
	        System.out.println("Convert String to image done");*/
		 String text = "\u00a9 memorynotfound.com";
	        File input = new File("C:\\Users\\earnjra\\Desktop\\EDC\\image"
                    + "\\Spring.bmp");
	        File output = new File("C:\\Users\\earnjra\\Desktop\\EDC\\image"
                    + "\\Spring3.bmp");

	        // adding text as overlay to an image
	        try {
				addTextWatermark(text, "jpg", input, output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    private static String convertImageToString(){
	        
	        InputStream inputStream = null;
	        
	        String imageString = null;
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try {

	            inputStream = new FileInputStream("C:\\Users\\earnjra\\Desktop\\EDC\\image"
	                    + "\\Spring.bmp");

	            byte[] buffer = new byte[1024];
	            baos = new ByteArrayOutputStream();

	            int bytesRead;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                baos.write(buffer, 0, bytesRead);
	            }

	            byte[] imageBytes = baos.toByteArray();

	            imageString = Base64.encodeBase64String(imageBytes);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            try {
	                baos.close();
	                inputStream.close();                
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	        }
	        return imageString;
	    }
	    
	    private static void convertStringToImageByteArray(String 
	            imageString){
	        
	        OutputStream outputStream = null;
	        byte [] imageInByteArray = Base64.decodeBase64(
	                imageString);
	        try {
	            outputStream = new FileOutputStream("C:\\Users\\earnjra\\Desktop\\EDC\\image"
	                    + "\\Spring2.jpeg");
	            outputStream.write(imageInByteArray);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            try {
	                outputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	    }
	    
	    private static void addTextWatermark(String text, String type, File source, File destination) throws IOException {
	        BufferedImage image = ImageIO.read(source);

	        // determine image type and handle correct transparency
	        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
	        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

	        // initializes necessary graphic properties
	        Graphics2D w = (Graphics2D) watermarked.getGraphics();
	        w.drawImage(image, 0, 0, null);
	        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
	        w.setComposite(alphaChannel);
	        w.setColor(Color.GRAY);
	        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
	        FontMetrics fontMetrics = w.getFontMetrics();
	        Rectangle2D rect = fontMetrics.getStringBounds(text, w);

	        // calculate center of the image
	        int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
	        int centerY = image.getHeight() / 2;

	        // add text overlay to the image
	        w.drawString(text, centerX, centerY);
	        ImageIO.write(watermarked, type, destination);
	        w.dispose();
	    }


	    
	    
}
