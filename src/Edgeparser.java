import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Image edge parser to find edges in images.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 11.10.2022</p>
 */
public class Edgeparser {

  /**
   * The image file to parse.
   */
  File imagefile;

  /**
   * Creates a new Edgeparser object.
   *
   * @param imagefile The image file to parse
   */
  public Edgeparser(File imagefile) {

    this.imagefile = imagefile;
  }

  private static Color[][] getRgbArray(BufferedImage img, int[][] flt, int off, int h, int w) {

    var pixelArr = new Color[flt.length][flt.length];
    for (int i = 0; i < flt.length; i++) {
      for (int j = 0; j < flt.length; j++) {
        // x and y offset is dependend on the size of the filter in use if the filter used has a
        // size of the 3x3 the offset will be set to 1 in order to iterate around the current pixel
        int wo = w + j - off; // calc the x offset position of for the current pixel
        int ho = h + i - off; // y offset for the current pixel
        int clr = img.getRGB(wo, ho);
        pixelArr[i][j] = new Color(clr);
      }
    }
    return pixelArr;
  }

  /**
   * Find edges in an image using the filter provided. Different filter will produce different
   * outputs.
   *
   * @param filter the filter to use
   * @return a Buffered image that is the image containing only edge data of the original.
   */
  public BufferedImage findEdges(Filter filter) {

    BufferedImage buff = null;
    try {
      BufferedImage img = ImageIO.read(this.imagefile);
      int iw = img.getWidth();
      int ih = img.getHeight();
      buff = new BufferedImage(iw, ih, img.getType());

      int[][] flt = filter.getFilter();
      int[][] vFlt = filter.verticalFilter();
      int off = (flt.length - 1) / 2;     // the offset of pixels where to start.
      int weight = filter.getWeight();
      // iterate over all pixel to find edges
      for (int h = off; h < ih - off; h++) {
        for (int w = off; w < iw - off; w++) {

          Color[][] pxlArr = getRgbArray(img, flt, off, h, w);
          int rgbEdgeValH = computeWithWeights(pxlArr, flt) / weight;
          int rgbEdgeValY = computeWithWeights(pxlArr, vFlt) / weight;

          int magnitude = (int) Math.sqrt(rgbEdgeValH * rgbEdgeValH + rgbEdgeValY * rgbEdgeValY);

          buff.setRGB(w, h, new Color(magnitude, magnitude, magnitude).getRGB());
        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return buff;
  }


  private int computeWithWeights(Color[][] pxlArr, int[][] flt) {

    int r, g, b, edge;
    r = b = g = edge = 0;
    for (int h = 0; h < pxlArr.length; h++) {
      for (int w = 0; w < pxlArr.length; w++) {
        if (flt[h][w] == 0) {
          continue;
        }

        var rgb = pxlArr[h][w];

        int rC = rgb.getRed();
        int gC = rgb.getGreen();
        int bC = rgb.getBlue();

        // convert color to grayscale before multiplying with matrix val
        edge += (int) (.3 * rC + .59 * gC + .11 * bC) * flt[h][w];
      }
    }

    return edge;
  }

}
