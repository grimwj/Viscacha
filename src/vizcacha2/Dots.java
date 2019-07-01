package vizcacha2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Dots extends VizPanel
{
    int grid_horizontal_start;
    int grid_horizontal_end;
    
    int grid_vertical_start;
    int grid_vertical_end;
    
    Dot Dot_s[];
    
    int size;
    int speed_x, speed_y;
    int max_number;
    Color color;
    Color color2;
    int life_time_ms;
    
    int nb_el_h;
    int nb_el_v;
    
    // 0 - anywhere 1 - from sides
    int mode = 0;
    
    public double coherence = 1;
    double coherence_threshold=1;
    double color_threshold=1;
        
    public Dots()
    {
        
    }
    
    public void Dots_Synchro()
    {
        long current_time = System.nanoTime();
        Random r = new Random();
        
        for(int i=0;i<max_number;i++)
        {
            Dot_s[i].time_created = current_time + r.nextInt(life_time_ms)*1000*1000;
            Dot_s[i].time_last_update = System.nanoTime();
        }
    }

    public void Dots_SynchroUpdate()
    {
        long current_time = System.nanoTime();
        
        for(int i=0;i<max_number;i++)
        {
            Dot_s[i].time_last_update = current_time;
        }
    }
    
    public Dots(int start_x, int end_x, int start_y, int end_y, int size, int speed_x, int speed_y, int max_number, Color color, Color color2, double color_threshold, int life_time_ms, int mode, double coherence)
    {
        this.size = size;
        this.speed_x = speed_x;
        this.speed_y = speed_y;
        this.max_number = max_number;
        this.color = color;
        this.color2 = color2;
        this.color_threshold = color_threshold;
        this.life_time_ms = life_time_ms;
        this.mode = mode;
        this.coherence = coherence;

        double dot_number_speed_scaling = 1;
        
        // szum ma zachowywac sie jak tlo, kropki nie migoca
        if (this.life_time_ms==0)
        {
            this.mode = 1;
            this.life_time_ms = 1000*1000;
        }
        // kropki szumne
        if (this.mode==0)
        {
            int dx = Math.abs((speed_x * life_time_ms)/1000);
            int dy = Math.abs((speed_y * life_time_ms)/1000);
            // przez coherence nie mamy pewnosci co powiekszac...
            int dz = (int) Math.sqrt(dx*dx + dy*dy);
            //int dx = Math.abs((speed_x));
            //int dy = Math.abs((speed_y));
            
            grid_horizontal_start = start_x - 2*size - dx;
            grid_horizontal_end = end_x + 2*size + dx;
            grid_vertical_start = start_y - 2*size - dy;
            grid_vertical_end = end_y + 2*size + dy;
            
            //grid_horizontal_start = start_x - 2*size - dz;
            //grid_horizontal_end = end_x + 2*size + dz;
            //grid_vertical_start = start_y - 2*size - dz;
            //grid_vertical_end = end_y + 2*size + dz;
        }
        else if (this.mode==1)
        {
            //System.out.println("\t\t\t\t\t" + this.life_time_ms);
            int dx = Math.abs((speed_x));
            int dy = Math.abs((speed_y));
            // przez coherence nie mamy pewnosci co powiekszac...
            int dz = (int) Math.sqrt(dx*dx + dy*dy);
            
            grid_horizontal_start = start_x - 2*size - dx;
            grid_horizontal_end = end_x + 2*size + dx;
            grid_vertical_start = start_y - 2*size - dy;
            grid_vertical_end = end_y + 2*size + dy;
            
            //grid_horizontal_start = start_x - 2*size - dz;
            //grid_horizontal_end = end_x + 2*size + dz;
            //grid_vertical_start = start_y - 2*size - dz;
            //grid_vertical_end = end_y + 2*size + dz;
        }
        
        dot_number_speed_scaling = (grid_horizontal_end - grid_horizontal_start) * (grid_vertical_end - grid_vertical_start);
        dot_number_speed_scaling /= (end_x - start_x) * (end_y - start_y);        
        
        this.max_number *= dot_number_speed_scaling;
        /*
        System.out.println("grid_horizontal_end = " + grid_horizontal_end);
        System.out.println("grid_horizontal_start = " + grid_horizontal_start);
        System.out.println("grid_vertical_end = " + grid_vertical_end);
        System.out.println("grid_vertical_start = " + grid_vertical_start);
        
        System.out.println("end_x = " + end_x);
        System.out.println("start_x = " + start_x);
        System.out.println("end_y = " + end_y);
        System.out.println("start_y = " + start_y);
        
        System.out.println("dot_number_speed_scaling = " + dot_number_speed_scaling);
        */
        Dot_s = new Dot[this.max_number];
        
        nb_el_h = 0;
        nb_el_v = 0;
        
        int tmp_gridpos_h, tmp_gridpos_v;
        tmp_gridpos_h = grid_horizontal_start;
        tmp_gridpos_v = grid_vertical_start;
        
        while(tmp_gridpos_h<grid_horizontal_end)
        {
            nb_el_h++;
            tmp_gridpos_h+=size;
        }
        
        while(tmp_gridpos_v<grid_vertical_end)
        {
            nb_el_v++;
            tmp_gridpos_v+=size;
        }
        
        
        // generate random map
        
        Random r = new Random();
        
        int index1d[] = new int[this.max_number];
        
        for (int i = 0; i < this.max_number; i++)
        {
            index1d[i] = r.nextInt(nb_el_h*nb_el_v);
        }
        
        coherence_threshold = coherence * this.max_number;

        
        int speed = (int) Math.sqrt(speed_x*speed_x + speed_y*speed_y);
        double direction_rad;
                
        int tmp_speed_x = 0;
        int tmp_speed_y = 0;
        Color tmp_color = new Color(0,0,0);
        
        for (int i = 0; i < this.max_number; i++)
        {
            int posx = (index1d[i]%nb_el_h) * size;
            int posy = (index1d[i]/nb_el_h) * size;

            posx += grid_horizontal_start;
            posy += grid_vertical_start;
            
            if (i<coherence_threshold)
            {
                tmp_speed_x = speed_x;
                tmp_speed_y = speed_y;
            }
            else
            {
                direction_rad = r.nextInt(360) * 2 * Math.PI / 360;
                tmp_speed_x = (int) (speed * Math.sin(direction_rad));
                tmp_speed_y = (int) (speed * Math.cos(direction_rad));
            }
            
            if (i<color_threshold*this.max_number)
                tmp_color = color;
            else
                tmp_color = color2;
            
            Dot_s[i] = new Dot(posx, posy, tmp_speed_x, tmp_speed_y, size, tmp_color, this.life_time_ms);
        }
    }
    
    public void Dots_Update()
    {
        // 2 tryby kropek-
        // przesuwajace sie
        // pojawiajace sie w losowych miejscach
        
        Random r = new Random();
        
        long time = System.nanoTime();
        
        int speed = (int) Math.sqrt(speed_x*speed_x + speed_y*speed_y);
        double direction_rad;
        
        int tmp_speed_x = 0;
        int tmp_speed_y = 0;
        Color tmp_color = new Color(0,0,0);
        
        // kropki szumne, niech pojawiaja sie losowo
        if (mode==0)
        {        
            for(int i=0; i<Dot_s.length; i++)
            {
                Dot_s[i].Dot_Update(grid_horizontal_start, grid_horizontal_end, grid_vertical_start, grid_vertical_end, time);

                int posx = 0;
                int posy = 0;
                
                //if (!Dot_s[i].isActive && Dot_s[i].isTimedOut)
                if (!Dot_s[i].isActive)
                {
                    posx = r.nextInt(nb_el_h)  * size;
                    posy = r.nextInt(nb_el_v)  * size;
                    
                    posx += grid_horizontal_start;
                    posy += grid_vertical_start;
                    
                    if (i<coherence_threshold)
                    {
                        tmp_speed_x = speed_x;
                        tmp_speed_y = speed_y;
                    }
                    else
                    {
                        direction_rad = r.nextInt(360) * 2 * Math.PI / 360;
                        tmp_speed_x = (int) (speed * Math.sin(direction_rad));
                        tmp_speed_y = (int) (speed * Math.cos(direction_rad));
                    }

                    if (i<color_threshold*max_number)
                        tmp_color = color;
                    else
                        tmp_color = color2;

                    Dot_s[i] = new Dot(posx, posy, tmp_speed_x, tmp_speed_y, size, tmp_color, life_time_ms);
                }
            }
        }
        // kropki ruchome, pojawiaja sie na niewidocznych obrzezach
        else if (mode==1)
        {
            for(int i=0; i<Dot_s.length; i++)
            {                
                Dot_s[i].Dot_Update(grid_horizontal_start, grid_horizontal_end, grid_vertical_start, grid_vertical_end, time);

                int posx = 0;
                int posy = 0;
                
                speed_x = Dot_s[i].Dot_GetSpeedX();
                speed_y = Dot_s[i].Dot_GetSpeedY();
                
                if (!Dot_s[i].isActive)
                {   
                    //Dot_s[i] = null;
                    // losuje z 2*nb_el_h komorek
                    // + 2*(nb_el_v-2)
                    // indeksuje wedlug wskazowek zegara
                    
                    int start_top = 0;
                    int end_top = start_top + nb_el_h - 1;
                    int start_right = end_top + 1;
                    int end_right = start_right + nb_el_v - 1;
                    int start_bottom = end_right + 1;
                    int end_bottom = start_bottom + nb_el_h - 1;
                    int start_left = end_bottom + 1;
                    int end_left = start_left + nb_el_v - 1;

                    int index=0;
                    
                    if (speed_x>0)
                    {   
                        if (speed_y>0)
                        {
                            int left = start_left + r.nextInt(end_left + 1 - start_left);
                            int top = start_top + r.nextInt(end_top + 1 - start_top);
                            
                            int tmp = r.nextInt(nb_el_h*Math.abs(speed_y) + nb_el_v*Math.abs(speed_x));
                            if (tmp>nb_el_h*Math.abs(speed_y))
                            {
                                index = left;
                            }
                            else
                            {
                                index = top;   
                            }
                        }
                        if (speed_y==0)
                        {
                            int left = start_left + r.nextInt(end_left + 1 - start_left);
                            index = left;
                        }
                        if (speed_y<0)
                        {
                            int left = start_left + r.nextInt(end_left + 1 - start_left);
                            int bottom = start_bottom + r.nextInt(end_bottom + 1 - start_bottom);
                            
                            int tmp = r.nextInt(nb_el_h*Math.abs(speed_y) + nb_el_v*Math.abs(speed_x));
                            if (tmp>nb_el_h*Math.abs(speed_y))
                            {
                                index = left;
                            }
                            else
                            {
                                index = bottom;
                            }
                        }
                    }
                    
                    if (speed_x==0)
                    {   
                        if (speed_y>0)
                        {
                            int top = start_top + r.nextInt(end_top + 1 - start_top);
                            index = top;
                        }
                        if (speed_y<0)
                        {
                            int bottom = start_bottom + r.nextInt(end_bottom + 1 - start_bottom);
                            index = bottom;
                        }
                    }
                    //pewnie chodzi o przenoszenie offsetu
                    if (speed_x<0)
                    {   
                        if (speed_y>0)
                        {
                            int right = start_right + r.nextInt(end_right + 1 - start_right);
                            int top = start_top + r.nextInt(end_top + 1 - start_top);
                            
                            int tmp = r.nextInt(nb_el_h*Math.abs(speed_y) + nb_el_v*Math.abs(speed_x));
                            if (tmp>nb_el_h*Math.abs(speed_y))
                            {
                                index = right;
                            }
                            else
                            {
                                index = top;
                            }
                        }
                        if (speed_y==0)
                        {
                            int right = start_right + r.nextInt(end_right + 1 - start_right);
                            index = right;
                        }
                        if (speed_y<0)
                        {
                            int right = start_right + r.nextInt(end_right + 1 - start_right);
                            int bottom = start_bottom + r.nextInt(end_bottom + 1 - start_bottom);
                            
                            int tmp = r.nextInt(nb_el_h*Math.abs(speed_y) + nb_el_v*Math.abs(speed_x));
                            if (tmp>nb_el_h*Math.abs(speed_y))
                            {
                                index = right;
                            }
                            else
                            {
                                index = bottom;
                            }
                                
                        }
                    }
                    
                    
                    // top side 0 - (nb_el_h -1)
                    if ((index >= start_top) && (index <= end_top))
                    {
                        posx = grid_horizontal_start + index*size;
                        posy = grid_vertical_start + 0;
                    }
                    
                    // right side
                    if ((index >= start_right) && (index <= end_right))
                    {
                        posx = grid_horizontal_start + nb_el_h*size;
                        posy = grid_vertical_start + (index - start_right)*size;
                    }
                    
                    // bottom side
                    if ((index >= start_bottom) && (index <= end_bottom))
                    {
                        posx = grid_horizontal_start + (index - start_bottom)*size;
                        posy = grid_vertical_start + nb_el_v*size;
                    }
                    
                    // left side
                    if ((index >= start_left) && (index <= end_left))
                    {
                        posx = grid_horizontal_start + 0;
                        posy = grid_vertical_start + (index - start_left)*size;;
                        
                    }
                    
                    if (Dot_s[i].position_x>grid_horizontal_end)
                    {
                        Dot_s[i].offset_x = Dot_s[i].position_x - grid_horizontal_end;
                        posx += Dot_s[i].offset_x;
                    }
                    
                    if (Dot_s[i].position_x<=grid_horizontal_start)
                    {
                        Dot_s[i].offset_x = Dot_s[i].position_x - grid_horizontal_start;
                        posx += Dot_s[i].offset_x;
                    }
                    
                    if (Dot_s[i].position_y>grid_vertical_end)
                    {
                        Dot_s[i].offset_y = Dot_s[i].position_y - grid_vertical_end;
                        posy += Dot_s[i].offset_y;
                    }
                    
                    if (Dot_s[i].position_y<=grid_vertical_start)
                    {
                        Dot_s[i].offset_y = Dot_s[i].position_y - grid_vertical_start;
                        posy += Dot_s[i].offset_y;
                    }

                    if (i<coherence_threshold)
                    {
                        tmp_speed_x = speed_x;
                        tmp_speed_y = speed_y;
                    }
                    else
                    {
                        direction_rad = r.nextInt(360) * 2 * Math.PI / 360;
                        tmp_speed_x = (int) (speed * Math.sin(direction_rad));
                        tmp_speed_y = (int) (speed * Math.cos(direction_rad));
                    }

                    if (i<color_threshold*max_number)
                        tmp_color = color;
                    else
                        tmp_color = color2;

                    Dot_s[i] = new Dot(posx, posy, tmp_speed_x, tmp_speed_y, size, tmp_color, life_time_ms);
                }
            }
        }
    }
    
    public void Dots_Draw(Graphics g)
    {
        //System.out.println("Drawing dots");
        //g.setColor(color);
        for(int i = 0; i < max_number; i++)
        {
            Dot_s[i].Dot_Draw(g);
        }
    }
    
}
 