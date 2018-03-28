package scale;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Histogram {
	int width;
	int height;
	int hist[];
	int sum_of_hist[];
	BufferedImage image;
	public Histogram(){
		try {
	         File input = new File("images.jpg");
	         image = ImageIO.read(input);
	         width = image.getWidth();
	         height = image.getHeight();
		} catch(Exception e){
			
		}
		hist = new int[256];
		sum_of_hist = new int[256];
	}
	
	public void caculateHistogram(){
		Raster k = image.getRaster();;
		int m;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				m = k.getSample(j, i, 0);
				hist[m] = hist[m] + 1;
			}
		}
	}
	
	public void caculateSumHist() {
		int sum = 0;
		for(int i = 0; i < 256; i++){
			sum = sum + hist[i];
			sum_of_hist[i] = sum;
		}
	}
	
	public void toOutput() throws IOException {
		int area = width * height;
		float Dm = 100;
		Raster k = image.getRaster();
		WritableRaster hello = (WritableRaster) image.getData();
		int m = 0;
		for(int i = 0; i < height; i++){
			for (int j = 0; j < width; j++) {
				m = k.getSample(j, i, 0);
				hello.setSample(j, i, 0, (Dm/area * sum_of_hist[m]));
				image.setData(hello);
			}
		}
		File output = new File("anhchohistogram.jpg");
		ImageIO.write(image, "jpg", output);
	}
	
	public static void main(String[] args) {
		Histogram hist = new Histogram();
		hist.caculateHistogram();
		hist.caculateSumHist();
		try {
			hist.toOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
