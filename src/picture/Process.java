package picture;

public class Process {
  private Picture[] pictures;

  public Process(Picture[] pictures) {
    this.pictures = pictures;
  }

  public Picture invert() {
    Picture newPic = Utils.createPicture(pictures[0].getWidth(), pictures[0].getHeight());

    for (int x = 0; x < pictures[0].getWidth(); x++) {
      for (int y = 0; y < pictures[0].getHeight(); y++) {
        Color oldColor = pictures[0].getPixel(x, y);
        Color newColor = new Color(255 - oldColor.getRed(), 255 - oldColor.getGreen(),
                255 - oldColor.getBlue());
        newPic.setPixel(x, y, newColor);
      }
    }

    return newPic;
  }

  public Picture grayscale() {
    Picture newPic = Utils.createPicture(pictures[0].getWidth(), pictures[0].getHeight());

    for (int x = 0; x < pictures[0].getWidth(); x++) {
      for (int y = 0; y < pictures[0].getHeight(); y++) {
        Color oldColor = pictures[0].getPixel(x, y);
        int average = (oldColor.getBlue() + oldColor.getGreen() + oldColor.getRed()) / 3;
        Color newColor = new Color(average, average, average);
        newPic.setPixel(x, y, newColor);
      }
    }

    return newPic;
  }

  public Picture rotate(String angle) {
    int times = Integer.parseInt(angle) / 90;

    Picture oldPic = pictures[0];
    Picture newPic = null;

    for (int i = 0; i < times; i++) {
      newPic = Utils.createPicture(oldPic.getHeight(), oldPic.getWidth());

      for (int x = 0; x < oldPic.getWidth(); x++) {
        for (int y = 0; y < oldPic.getHeight(); y++) {
          int newX = oldPic.getHeight() - 1 - y;
          int newY = x;
          newPic.setPixel(newX, newY, oldPic.getPixel(x, y));
        }
      }
      oldPic = newPic;
    }

    return newPic;
  }

  public Picture flip(String direction) {
    Picture newPic = Utils.createPicture(pictures[0].getWidth(), pictures[0].getHeight());

    if (direction.equals("H")) {
      for (int x = 0; x < pictures[0].getWidth(); x++) {
        for (int y = 0; y < pictures[0].getHeight(); y++) {
          Color oldColor = pictures[0].getPixel(x, y);
          int newX = pictures[0].getWidth() - 1 - x;
          int newY = y;
          newPic.setPixel(newX, newY, oldColor);
        }
      }
    }
    else if (direction.equals("V")) {
      for (int x = 0; x < pictures[0].getWidth(); x++) {
        for (int y = 0; y < pictures[0].getHeight(); y++) {
          Color oldColor = pictures[0].getPixel(x, y);
          int newX = x;
          int newY = pictures[0].getHeight() - 1 - y;
          newPic.setPixel(newX, newY, oldColor);
        }
      }
    }

    return newPic;
  }

  public Picture blend() {
    int newWidth = pictures[0].getWidth();
    int newHeight = pictures[0].getHeight();

    for (int i = 1; i < pictures.length; i++) {
      if (pictures[i].getWidth() < newWidth) {
        newWidth = pictures[i].getWidth();
      }
      if (pictures[i].getHeight() < newHeight) {
        newHeight = pictures[i].getHeight();
      }
    }

    Picture newPic = Utils.createPicture(newWidth, newHeight);
    for (int x = 0; x < newPic.getWidth(); x++) {
      for (int y = 0; y < newPic.getHeight(); y++) {
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;

        for (Picture pic : pictures) {
          totalRed += pic.getPixel(x, y).getRed();
          totalGreen += pic.getPixel(x, y).getGreen();
          totalBlue += pic.getPixel(x, y).getBlue();
        }

        int avgRed = totalRed / pictures.length;
        int avgGreen = totalGreen / pictures.length;
        int avgBlue = totalBlue / pictures.length;

        newPic.setPixel(x, y, new Color(avgRed, avgGreen, avgBlue));
      }
    }

    return newPic;
  }

  public Picture blur() {
    Picture newPic = Utils.createPicture(pictures[0].getWidth(), pictures[0].getHeight());
    for (int x = 0; x < pictures[0].getWidth(); x++) {
      for (int y = 0; y < pictures[0].getHeight(); y++) {
        if (x == 0 || x == pictures[0].getWidth() - 1
                || y == 0 || y == pictures[0].getHeight() - 1) {
          newPic.setPixel(x, y, pictures[0].getPixel(x, y));
          continue;
        }

        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;

        for (int i = x - 1; i < x + 2; i++) {
          for (int j = y - 1; j < y + 2; j++) {
            totalRed += pictures[0].getPixel(i, j).getRed();
            totalGreen += pictures[0].getPixel(i, j).getGreen();
            totalBlue += pictures[0].getPixel(i, j).getBlue();
          }
        }

        int avgRed = totalRed / 9;
        int avgGreen = totalGreen / 9;
        int avgBlue = totalBlue / 9;

        newPic.setPixel(x, y, new Color(avgRed, avgGreen, avgBlue));
      }
    }
    return newPic;
  }

  public Picture mosaic(String tileSizeStr) {
    int tileSize = Integer.parseInt(tileSizeStr);
    int width = pictures[0].getWidth();
    int height = pictures[0].getHeight();
    for (int i = 1; i < pictures.length; i++) {
      if (pictures[i].getWidth() < width) {
        width = pictures[i].getWidth();
      }
      if (pictures[i].getHeight() < height) {
        height = pictures[i].getHeight();
      }
    }

    Picture newPic = Utils.createPicture(width, height);
    for (int x = 0; x <= width - tileSize; x += tileSize) {
      for (int y = 0; y <= height - tileSize; y += tileSize) {
        int desiredPicIndex = ((int)(y / tileSize) % pictures.length +
                (int)(x / tileSize) % pictures.length) % pictures.length;
        Picture desiredPic = pictures[desiredPicIndex];
        for (int i = x; i < x + tileSize; i++) {
          for (int j = y; j < y + tileSize; j++) {
            newPic.setPixel(i, j, desiredPic.getPixel(i, j));
          }
        }
      }
    }

    return newPic;
  }
}
