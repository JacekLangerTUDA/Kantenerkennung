/**
 * Filters usable for edge detection. This enum can be extended upon to add more filter.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 11.10.2022</p>
 */
public enum Filter {
  /**
   * Sobel filter for edge detection in images. This filter is more detailed than the standard
   * Prewitt filter.
   */
  SOBEL(0),
  /**
   * Prewitt filter for finding edges in images.
   */
  PREWITT(1),
  /**
   * Custom but experimental filter.
   */
  CUSTOM(-1);
  private final int type;

  /**
   * Returns the filter matrix.
   *
   * @return multidimensional int array.
   */
  int[][] getFilter() {

    return switch (type) {
      case 0 -> new int[][] { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
      case -1 -> new int[][] {
          { -1, -2, -4, -8, -4, -2, -1 },
          { -1, -3, -9, -27, -9, -3, -1 },
          { -1, -4, -16, -64, -16, -4, -1 },
          { 0, 0, 0, 0, 0, 0, 0 },
          { 1, 4, 16, 64, 16, 4, 1 },
          { 1, 3, 9, 27, 9, 3, 1 },
          { 1, 2, 4, 8, 4, 2, 1 },
          };
      default -> new int[][] { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
    };
  }

  /**
   * Returns the respective weight for the filter.
   *
   * @return the weight for the filter
   */
  public int getWeight() {

    return switch (type) {
      case 0 -> 8;
      case -1 -> 342;
      default -> 6;
    };
  }


  /**
   * Creates a new Filter.
   *
   * @param type the type index of the filter
   */
  Filter(int type) {

    this.type = type;
  }

  /**
   * Transponse the filter matrix.
   *
   * @return a transponse matrix of the filter matrix.
   */
  int[][] verticalFilter() {

    var filter = getFilter();
    var temp = new int[filter.length][filter.length];
    for (int h = 0; h < filter.length; h++) {
      for (int w = 0; w < filter.length; w++) {
        temp[h][w] = filter[w][h];
      }
    }
    return temp;
  }
}