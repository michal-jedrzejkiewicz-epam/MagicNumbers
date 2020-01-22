package Engine;

public class Main {

    public static void main(String[] args) {
        try {
            IFileValidator fileValidator = new BasicFileExtensionValidator(args[0]);
            System.out.println("File signature matches with its extension: " + fileValidator.checkIfFileIsSafe());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
