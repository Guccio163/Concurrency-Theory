import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class Executor implements Callable {
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private int x;
    private int y;
    private BufferedImage I;

    public Executor(int x_, int y_, BufferedImage I_) {
        this.x = x_;
        this.y = y_;
        this.I = I_;
    }

    @Override
    public Object call() throws Exception {
        double zx, zy, cX, cY, tmp;

        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
//        I.setRGB(x, y, iter | (iter << 8));

//        return new int[]{x, y, iter};
        return 0;
    }
}
