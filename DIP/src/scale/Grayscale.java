package scale;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Grayscale {
	
	
	
	public static void main(String[] args){
		try{
			File input = new File("anhcho.jpg");
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage out_image= new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					Color c = new Color(image.getRGB(j, i));
					
					int red = (int)(c.getRed() * 0.299);
					int green = (int)(c.getGreen() * 0.587);
					int blue = (int)(c.getBlue() * 0.114);
					
					int sum = red + green + blue;
					Color newColor = new Color(sum);
					
					out_image.setRGB(j, i, image.getRGB(j, i));
				}
			}
			File output = new File("anhchograyscale.jpg");
			ImageIO.write(out_image, "jpg", output);
		} catch (Exception e){
			System.out.println(e);
		}
	}
}
