import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class RowExecutor implements Callable {
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private int y1;
    private int y2;
    private int x;
    private BufferedImage I;

    public RowExecutor(int y1_, int y2_, int x_, BufferedImage I_) {
        this.y1 = y1_;
        this.y2 = y2_;
        this.x = x_;
        this.I = I_;
    }

    @Override
    public Object call() throws Exception {
        double zx, zy, cX, cY, tmp;

        int ys = y2-y1;
        int [][] result = new int[ys][];

        for(int y=y1; y<y2; y++) {

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

//            result[y2-y1-1] = new int[]{x, y, iter};
//            I.setRGB(x, y, iter | (iter << 8));

        }
        return 0;
    }
}
