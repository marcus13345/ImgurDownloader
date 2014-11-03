import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GraphicalImgurRequest extends ImgurRequest{
	private Point point = new Point(0, 0);
	private Point desiredPoint = new Point(0, 0);
	private double progress = 0;
	
	public GraphicalImgurRequest(double x1, double y1, double x2, double y2) {
		point.x = x1;
		point.y = y1;
		desiredPoint.x = x2;
		desiredPoint.y = y2;
		
		
	}
	
	public void setDestination(double x, double y) {
		desiredPoint.x = x;
		desiredPoint.y = y;
	}
	
	public void tick() {
		double x = point.x;
		double y = point.y;
		
		double x2 = desiredPoint.x;
		double y2 = desiredPoint.y;

		y -= (y - y2) / 8d;
		x -= (x - x2) / 8d;
		
		point.x = x;
		point.y = y;
		
		double newProgress = getProgress();
		if(newProgress == newProgress)
		progress -= (progress - newProgress) / 8d;
		
	}
	
	private class Point {
		public double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public void draw(Graphics2D g) {
		final int WIDTH = 300;
		
		int XOFF = (int)(point.x);
		int YOFF = (int)(point.y);
		
		FontMetrics metrics = g.getFontMetrics();
		
		g.setColor(Color.BLACK);
		g.fillRect(XOFF, YOFF + 13, (int) (WIDTH * progress), 20);
		g.drawRect(XOFF, YOFF + 13, WIDTH, 20);

		//total images
		String str = "" + getImagesDiscovered();
		g.drawString(str, XOFF + WIDTH - (metrics.charsWidth(str.toCharArray(), 0, str.length())) / 2, YOFF + 45);

		//downloaded
		str = "" + getImagesDownloaded();
		g.drawString(str, XOFF + (int) (WIDTH * progress) - (metrics.charsWidth(str.toCharArray(), 0, str.length())) / 2, YOFF + 10);
		
		// name of the thing
		str = getTitle();
		g.drawString(str, XOFF + (WIDTH / 2) - (metrics.charsWidth(str.toCharArray(), 0, str.length())) / 2, YOFF);
		
	}
}
