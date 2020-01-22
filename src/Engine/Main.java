package Engine;

public class Main {

    public static void main(String[] args) throws Exception {
        IFileValidator fileValidator = new BasicFileExtensionValidator(args[0]);
        System.out.println("File signature matches with its extension: " +  fileValidator.checkIfFileIsSafe());
    }
}
