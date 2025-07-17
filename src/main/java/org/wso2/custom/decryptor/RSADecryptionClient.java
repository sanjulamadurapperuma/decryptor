package org.wso2.custom.decryptor;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class RSADecryptionClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt for the Base64 encoded JSON string
            System.out.print("Enter the Base64 encoded JSON string: ");
            String base64Input = scanner.nextLine();

            // Decode the Base64 string into the JSON string
            String decodedJson = new String(Base64.getDecoder().decode(base64Input), StandardCharsets.UTF_8);

            // Parse the decoded JSON string into a JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(decodedJson);

            // Extract the encrypted password from the JSON field "c"
            String encryptedPassword = rootNode.path("c").asText();

            // Prompt for the keystore file path
            System.out.print("Enter the keystore file path (JKS format): ");
            String keystoreFilePath = scanner.nextLine();

            // Prompt for the keystore password
            System.out.print("Enter the keystore password: ");
            String keystorePassword = scanner.nextLine();

            // Prompt for the alias of the private key in the keystore
            System.out.print("Enter the alias of the private key in the keystore: ");
            String alias = scanner.nextLine();

            // Prompt for the private key password (if any)
            System.out.print("Enter the password for the private key: ");
            String privateKeyPassword = scanner.nextLine();

            // Decrypt the password
            String decryptedPassword = decryptPassword(encryptedPassword, keystoreFilePath, keystorePassword, alias, privateKeyPassword);
            System.out.println("Decrypted value: " + decryptedPassword);
        } catch (Exception e) {
            System.out.println("An error occurred during decryption: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String decryptPassword(String encryptedPassword, String keystoreFilePath, String keystorePassword, String alias, String privateKeyPassword) throws Exception {
        // Decode the Base64 encoded encrypted password
        byte[] encryptedPasswordBytes = Base64.getDecoder().decode(encryptedPassword);

        // Load the keystore
        FileInputStream keystoreStream = new FileInputStream(keystoreFilePath);
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(keystoreStream, keystorePassword.toCharArray());

        // Retrieve the private key from the keystore
        PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, privateKeyPassword.toCharArray());

        // Get a Cipher object for RSA decryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA1andMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Decrypt the password
        byte[] decryptedBytes = cipher.doFinal(encryptedPasswordBytes);

        // Return the decrypted password as a String
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
