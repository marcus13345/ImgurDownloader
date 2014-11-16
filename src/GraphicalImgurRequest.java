import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GraphicalImgurRequest extends ImgurRequest{
	private Point point = new Point(0, 0);
	private Point desiredPoint = new Point(0, 0);
	private double progress = 0;
	private double scanProgress = 0;
	private ImageListener listener;
	
	public GraphicalImgurRequest(double x1, double y1, double x2, double y2, ImageListener listener) {
		super(listener);
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
		
		double newScanProgress = getScanProgress();
		if(newScanProgress == newScanProgress)
		scanProgress -= (scanProgress - newScanProgress) / 8d;
		
	}
	
	private class Point {
		public double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	public final static int WIDTH = 100;
	public void draw(Graphics2D g) {
		
		
		int XOFF = (int)(point.x);
		int YOFF = (int)(point.y);
		
		FontMetrics metrics = g.getFontMetrics();
		
		//the bar
		g.setColor(new Color(150, 150, 150));
		g.fillRect(XOFF, YOFF + 13, (int) (WIDTH * scanProgress) + 1, 20);
		g.drawRect(XOFF, YOFF + 13, WIDTH, 20);
		g.setColor(new Color(70, 70, 70));
		g.fillRect(XOFF, YOFF + 13, (int) (WIDTH * progress) + 1, 20);
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
