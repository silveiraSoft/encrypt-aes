package com.example.encryptaes;

import com.example.encryptaes.tools.EncriptadorAES;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class EncryptAesApplication {
    static Logger logger = Logger.getLogger(EncryptAesApplication.class.getName());
    public static void main(String[] args) throws Exception {
        SpringApplication.run(EncryptAesApplication.class, args);
        try {
            logger.log( Level.INFO,"Iniciando proceso que permite encriptar datos.");
            InputStream stream = System.in;
            Scanner scanner = new Scanner(stream);
            logger.log( Level.INFO,"Digite la llave secreta:");
            String claveEncriptacion = scanner.next();
            logger.log( Level.INFO,"Digite el texto que desea encriptar. Digite menos de 100 caracteres:");
            String datosOriginales = scanner.next();
            EncriptadorAES encriptador = new EncriptadorAES();

            String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
            String desencriptado = encriptador.desencriptar(encriptado, claveEncriptacion);

            logger.log( Level.INFO, "Cadena Original: {0}", datosOriginales);
            logger.log( Level.INFO, "Escriptado: {0}", encriptado);
            logger.log( Level.INFO, "Desencriptado: {0}", desencriptado);
            scanner.close();
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*
        EncryptAesApplication a = new EncryptAesApplication();
        String encrypted = a.encrypt("My text");
        System.out.println(encrypted);
         */
    }

    public SecretKey crearClave(String clave) throws NoSuchAlgorithmException {
        /*
        try {
            final SecureRandom secureKeyRandomness = SecureRandom.getInstanceStrong();
            final KeyGenerator AES_keyInstance = KeyGenerator.getInstance("AES");
            AES_keyInstance.init(128, secureKeyRandomness);
            return AES_keyInstance.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

         */
        byte[] claveEncriptacion = clave.getBytes(StandardCharsets.UTF_8);

        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

        return new SecretKeySpec(claveEncriptacion, "AES");
    }
    public String encrypt(String cleartext) throws Exception {
        try
        {
            // encoding format needs thought
            byte[] clearTextbytes = cleartext.getBytes("UTF-8");
            final SecretKey secretKey = crearClave("secreto!");
            final Cipher AES_cipherInstance = Cipher.getInstance("AES/GCM/NoPadding");
            AES_cipherInstance.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedText = AES_cipherInstance.doFinal(clearTextbytes);

            byte[] iv = AES_cipherInstance.getIV();
            byte[] message = new byte[12 + clearTextbytes.length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptedText, 0, message, 12, encryptedText.length);

            //return decrypt(message, secretKey);
            return decrypt(message);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return "something went wrong with encrypt";
    } // encrypt.

    public String decrypt(byte[] encryptedText) throws Exception {
        try
        {
            //final SecretKey secretKey = crearClave();
            final SecretKey secretKey = crearClave("secreto!");
            final Cipher AES_cipherInstance = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec params = new GCMParameterSpec(128, encryptedText, 0, 12);
            AES_cipherInstance.init(Cipher.DECRYPT_MODE, secretKey, params);
            byte[] decryptedText = AES_cipherInstance.doFinal(encryptedText, 12, encryptedText.length - 12);
            return new String(decryptedText, "UTF-8");
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return "something went wrong with decrypt";
    }

}
