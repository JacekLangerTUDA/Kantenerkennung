import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 11.10.2022</p>
 */
public class Main {

  public static void main(String[] args) throws IOException {

    //todo create gui interface.
//    var defaultFile = new File("testdata/Fokus-auf_Australien.jpg");
//    JFileChooser chooser = new JFileChooser();
    File in = new File("testdata/Fokus-auf_Australien.jpg");
//
//    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//      in = chooser.getSelectedFile();
//    } else {
//      in = defaultFile;
//    }

    Edgeparser filter = new Edgeparser(in);
    var image = filter.findEdges(Filter.CUSTOM);

    File out = new File(String.format("results/%s", in.getName()));

    ImageIO.write(image, "jpg", out);
  }

}
