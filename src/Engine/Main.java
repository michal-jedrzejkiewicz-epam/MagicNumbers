package Engine;

public class Main {

    public static void main(String[] args) {
        IFileValidator fileValidator = new BasicFileExtensionValidator();
        System.out.println("File signature matches with its extension: " +  fileValidator.checkIfFileIsSafe());
    }
}
