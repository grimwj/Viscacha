package vizcacha2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class RectAnimation extends VizPanel
{
    BufferedImage image;
    BufferedImage image_filtered;
    
    double scale_factor;
    
    public double angle;
    
    long time_created_ms;
    
    int speed;
    int width;
    int duty;
    
    int image_width;
    int image_height;
    int image_larger_dim;
    
    
    public RectAnimation(int image_width, int image_height, double medium_value, double amplitude, int speed, int direction, int width, double duty, int mask)
    {   
        scale_factor = (double) (width)/speed;
        
        this.angle = direction;
        this.image_width = image_width;
        this.image_height = image_height;
        this.speed = speed;
        this.width = width;
        
        image_larger_dim = image_height;
        if (image_width>image_height)
            image_larger_dim = image_width;
        
        // for mask not to cut out pixels
        image_larger_dim *= 2;
        image_larger_dim += width;
        
        float[] matrix = new float[mask*mask];
        for (int i = 0; i < (mask*mask); i++)
            matrix[i] = 1.0f/((float) (mask*mask));
        
        image = new BufferedImage(image_larger_dim, image_larger_dim, BufferedImage.TYPE_INT_RGB);  
        image_filtered = new BufferedImage(image_larger_dim, image_larger_dim, BufferedImage.TYPE_INT_RGB);  
        
        double phase;
            
        for(int i = 0; i < image_larger_dim; i++)
        {
            phase = ((double) (i)%width);
            
            int sign;
            
            if (phase<duty*width)
                sign = 1;
            else
                sign = -1;

            int grey_scale = (int) ((amplitude*sign + medium_value) * 255);

            if (grey_scale>255)
                grey_scale = 255;
            if (grey_scale<0)
                grey_scale = 0;

            Color color = new Color(grey_scale, grey_scale, grey_scale);
            int color_intval = color.getRGB();

            for(int j = 0; j < image_larger_dim; j++)
            {
                image.setRGB(i, j, color_intval);
            }
        }
        
        BufferedImageOp op = new ConvolveOp( new Kernel(mask, mask, matrix) );
        image_filtered = op.filter(image, null);
        
        time_created_ms = System.nanoTime()/(1000*1000);
    }
    
    public void RectAnimation_UpdateAndDrawLayer(Graphics g, int pos_x, int pos_y, int translate_x, int translate_y)
    {        
        long current_time_ms = (System.nanoTime()/(1000*1000)) - time_created_ms;
        
        double phase = (double) (speed) * current_time_ms / (1000*width);
        
        double tmp = (phase * width)%width ;
        
        int phs = (int) tmp;
        
        // create the transform, note that the transformations happen
        // in reversed order (so check them backwards)
        AffineTransform at = new AffineTransform();

        // 4. translate it to the center of the component
        at.translate(translate_x, translate_y);
        
        // 3. do the actual rotation
        at.rotate(Math.toRadians(angle));

        // 1. translate the object so that you rotate it around the 
        //    center (easier :))
        
        int translate_img_x = -image_larger_dim/2 + phs;
        int translate_img_y = -image_larger_dim/2 + phs;
        
        at.translate(translate_img_x, translate_img_y);
        
        Rectangle2D.Double rect = new Rectangle2D.Double(pos_x,pos_y,image_width,image_height);
        Area rectangle = new Area(rect);
        
        Graphics2D g2d = (Graphics2D) g;
        g.setClip(rectangle);
        g2d.drawImage(image, at, null);
    }
    
    public void RectAnimation_UpdateAndDrawEllipse(Graphics g, int translate_x, int translate_y)
    {        
        long current_time_ms = (System.nanoTime()/(1000*1000)) - time_created_ms;
        
        double phase = (double) (speed) * current_time_ms / (1000*width);
        
        double tmp = (phase * width)%width ;
        
        int phs = (int) tmp;
        
        // create the transform, note that the transformations happen
        // in reversed order (so check them backwards)
        AffineTransform at = new AffineTransform();

        // 4. translate it to the center of the component
        at.translate(translate_x, translate_y);
        
        // 3. do the actual rotation
        at.rotate(Math.toRadians(angle));

        // 1. translate the object so that you rotate it around the 
        //    center (easier :))
        
        int translate_img_x = -image_larger_dim/2 + phs;
        int translate_img_y = -image_larger_dim/2 + phs;
        
        at.translate(translate_img_x, translate_img_y);
        
        double mask_percent = 0.3;
        double raw_percent = 1 - mask_percent;
        
        int e1_x = translate_x - image_width/2;
        int e1_y = translate_y - image_height/2;
        int e2_x = (int) (translate_x - raw_percent*(image_width/2));
        int e2_y = (int) (translate_y - raw_percent*(image_height/2));
        
        Ellipse2D.Double ellipse1 = new Ellipse2D.Double(e1_x,e1_y,image_width,image_height);
        Ellipse2D.Double ellipse2 = new Ellipse2D.Double(e2_x,e2_y,image_width*raw_percent,image_height*raw_percent);
        Area circle_outer = new Area(ellipse1);
        Area circle_inner = new Area(ellipse2);
        circle_outer.subtract(circle_inner);
        
        g.setClip(null);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setClip(circle_inner);
        g2d.drawImage(image, at, null);
        
        g2d.setClip(circle_outer);
        g2d.drawImage(image_filtered, at, null);
        
        g.setClip(null);
    }
    
    
}
