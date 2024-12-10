import java.io.*;
import java.util.*;

public class PasswordChecker {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java PasswordChecker <password> <dictionary file>");
            return;
        }

        String password = args[0];
        String dictionaryFile = args[1];

        // Load dictionary and hash tables
        HashTableSeparateChaining tableSeparateChaining1 = new HashTableSeparateChaining(1000, true);
        HashTableSeparateChaining tableSeparateChaining2 = new HashTableSeparateChaining(1000, false);
        HashTableLinearProbing tableLinearProbing1 = new HashTableLinearProbing(20000, true);
        HashTableLinearProbing tableLinearProbing2 = new HashTableLinearProbing(20000, false);

        loadDictionary(dictionaryFile, tableSeparateChaining1, tableSeparateChaining2, tableLinearProbing1, tableLinearProbing2);

        // Check password
        System.out.println("Checking password: " + password);
        boolean isStrong = isStrongPassword(password, tableSeparateChaining1);
        System.out.println("Password strength: " + (isStrong ? "Strong" : "Weak"));
    }

    private static boolean isStrongPassword(String password, HashTableSeparateChaining hashTable) {
        if (password.length() < 8) return false;

        // Check if password is in the dictionary
        if (hashTable.contains(password)) return false;

        // Check if password is a dictionary word followed by a digit
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                String wordPart = password.substring(0, i);
                if (hashTable.contains(wordPart)) return false;
            }
        }
        return true;
    }

    private static void loadDictionary(String fileName, Object... hashTables) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String word;
    int lineNumber = 1;
    while ((word = reader.readLine()) != null) {
        for (Object hashTable : hashTables) {
            if (hashTable instanceof HashTableSeparateChaining) {
                ((HashTableSeparateChaining) hashTable).put(word, lineNumber);
            } else if (hashTable instanceof HashTableLinearProbing) {
                ((HashTableLinearProbing) hashTable).put(word, lineNumber);
            }
        }
        lineNumber++;
    }
    reader.close();
}

    }


