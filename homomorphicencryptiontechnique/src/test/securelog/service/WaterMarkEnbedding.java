package test.securelog.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class WaterMarkEnbedding {

	public static void main(String[] args) 
	{
		String str="ericsson";
		new WaterMarkEnbedding().watermarkingEmbedding( str);

	}
	private void watermarkingEmbedding(String str) 
	{
		char[] ch=str.toCharArray();
		char[] word = new char[100];
		char[] word1=new char[100];
		int ascii_value=0;
		int bin_ascii_value=0;
		for(int i=0; i<ch.length; i++) 
		{
			word[i]=ch[i];
			for(int j=1;j<word.length; j++) {
				word1[j]=word[j];
				ascii_value=word1[j];
				bin_ascii_value=ascii_value;
				str=bin_ascii_value+str;
			}
				
			}
		System.out.println(str);
			
		
		
	}
	private void watermarkingExtraction() 
	{
		
	}
	private void waterMarkingEmbedding() {
		//create String object to be converted to image
	       String sampleText = "SAMPLE TEXT";
	 
	        //Image file name
	       String fileName = "Image";
	        
	        //create a File Object
	        File newFile = new File("C:\\Users\\earnjra\\Desktop\\EDC\\image\\Image" +".jpeg");
	         
	        //create the font you wish to use
	        Font font = new Font("Tahoma", Font.PLAIN, 11);
	        
	        //create the FontRenderContext object which helps us to measure the text
	        FontRenderContext frc = new FontRenderContext(null, true, true);
	         
	        //get the height and width of the text
	        Rectangle2D bounds = font.getStringBounds(sampleText, frc);
	        int w = (int) bounds.getWidth();
	        int h = (int) bounds.getHeight();
	        
	        //create a BufferedImage object
	       BufferedImage image = new BufferedImage(w, h,   BufferedImage.TYPE_INT_RGB);
	        
	        //calling createGraphics() to get the Graphics2D
	        Graphics2D g = image.createGraphics();
	        
	        //set color and other parameters
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, w, h);
	        g.setColor(Color.BLACK);
	        g.setFont(font);
	             
	       g.drawString(sampleText, (float) bounds.getX(), (float) -bounds.getY());
	       
	      //releasing resources
	      g.dispose();
	       
	        //creating the file
	      //ImageIO.write(image, "jpeg", fileName);
	       //ImageIO.write(image, "jpeg", fileName);
	}
}
