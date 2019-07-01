package vizcacha2;

import java.awt.Color;
import java.awt.Graphics;

public class Dot
{
    public double position_x;
    public double position_y;
    private final int speed_x;
    private final int speed_y;
    private final int dot_size;
    private final Color color;
    public boolean isActive = false;
    public boolean isTimedOut = false;
    private int life_time_ms;
    public int current_time_ms;
    public long time_created;
    public long time_last_update;
    
    public double offset_x, offset_y;
    
    public Dot(double position_x, double position_y, int speed_x, int speed_y, int dot_size, Color color, int life_time_ms)
    {
        time_created = System.nanoTime();
        time_last_update = System.nanoTime();
        isActive = true;
        isTimedOut = false;
        
        this.life_time_ms = life_time_ms;
        this.position_x = position_x;
        this.position_y = position_y;
        this.speed_x = speed_x;
        this.speed_y = speed_y;
        this.dot_size = dot_size;
        this.color = color;
        this.offset_x = 0;
        this.offset_y = 0;
    }
    
    public int Dot_GetCurrentTimeMs()
    {
        return current_time_ms;
    }
    
    public double Dot_GetOffsetX()
    {
        double tmp = this.offset_x;
        this.offset_x = 0;
        return tmp;
    }
    
    public double Dot_GetOffsetY()
    {
        double tmp = this.offset_y;
        this.offset_y = 0;
        return tmp;
    }
    
    public void Dot_Deactivate()
    {
        isActive = false;
    }
    
    public void Dot_Update(int grid_horizontal_start, int grid_horizontal_end, int grid_vertical_start, int grid_vertical_end, long time)
    {   
        if (isActive)
        {
            long update_time = time;
            long delta_time_ms = (update_time - time_last_update)/(1000*1000);
            
            position_x += (double) (speed_x * delta_time_ms)/1000;
            position_y += (double) (speed_y * delta_time_ms)/1000;

            if ((position_x>grid_horizontal_end + 2*dot_size) || (position_y>grid_vertical_end + 2*dot_size))
                Dot_Deactivate();
            
            if ((position_x<grid_horizontal_start - 2*dot_size) || (position_y<grid_vertical_start - 2*dot_size))
                Dot_Deactivate();
            
            current_time_ms = (int) ((update_time - time_created)/(1000*1000));
            
            if (current_time_ms > life_time_ms)
            {
                Dot_Deactivate();
                isTimedOut = true;
            }
        }
    }
    
    public void Dot_Draw(Graphics g)
    {        
        g.setColor(color);
        g.fillRect( (int) (Math.round(position_x)), (int) (Math.round(position_y)), dot_size, dot_size);
    }
    
    public int Dot_GetSpeedX()
    {
        return speed_x;
    }
    
    public int Dot_GetSpeedY()
    {
        return speed_y;
    }
    
}
