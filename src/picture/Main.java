package picture;

public class Main {

  public static void main(String[] args) {
    String operation = args[0];
    String opInput = "";
    String[] picInputs;
    if (operation.equals("rotate") || operation.equals("flip")) {
      opInput = args[1];
      picInputs = new String[args.length - 3];
      for (int i = 2; i < args.length - 1; i++) {
        picInputs[i - 2] = args[i];
      }
    }
    else {
      picInputs = new String[args.length - 2];
      for (int i = 1; i < args.length - 1; i++) {
        picInputs[i - 1] = args[i];
      }
    }

    String outputLocation = args[args.length - 1];

    Picture[] pictures = new Picture[picInputs.length];
    for (int i = 0; i < pictures.length; i++) {
      pictures[i] = Utils.loadPicture(picInputs[i]);
    }

    Process process = new Process(pictures);
    Picture output;

    if (operation.equals("invert")) {
      output = process.invert();
    }
    else if (operation.equals("grayscale")) {
      output = process.grayscale();
    }
    else if (operation.equals("rotate")) {
      output = process.rotate(opInput);
    }
    else if (operation.equals("flip")) {
      output = process.flip(opInput);
    }
    else if (operation.equals("blend")) {
      output = process.blend();
    }
    else if (operation.equals("blur")) {
      output = process.blur();
    }
    else {
      System.out.print("Error, operation doesn't exist!");
      return;
    }

    Utils.savePicture(output, outputLocation);
  }
}
